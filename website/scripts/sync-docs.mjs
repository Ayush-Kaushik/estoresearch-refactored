import { execSync } from 'node:child_process';
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);
const repoRoot = path.resolve(__dirname, '..', '..');
const websiteRoot = path.resolve(__dirname, '..');
const docsRoot = path.join(websiteRoot, 'docs');

const readGitRemote = () => {
  try {
    const remote = execSync('git -C "' + repoRoot + '" remote get-url origin', {
      encoding: 'utf8',
      stdio: ['ignore', 'pipe', 'pipe'],
    }).trim();
    return remote;
  } catch {
    return '';
  }
};

const getTrackedBranch = () => {
  try {
    return execSync('git -C "' + repoRoot + '" branch --show-current', {
      encoding: 'utf8',
      stdio: ['ignore', 'pipe', 'pipe'],
    }).trim() || 'main';
  } catch {
    return 'main';
  }
};

const remoteUrl = readGitRemote();
const branchName = getTrackedBranch();
const repoUrl = remoteUrl ? remoteUrl.replace(/\.git$/, '').replace(/^git@github\.com:/, 'https://github.com/') : 'https://github.com';

const urlForRepoPath = (relPath) => {
  if (!remoteUrl || !remoteUrl.includes('github.com')) return relPath;
  const normalized = remoteUrl.replace(/\.git$/, '').replace(/^git@github\.com:/, 'https://github.com/');
  const suffix = normalized.replace(/^https:\/\/github\.com\//, '');
  const [owner, repo] = suffix.split('/');
  if (!owner || !repo) return relPath;

  const safe = relPath.replace(/^\.\//, '').replace(/^\.\//, '');
  if (safe.endsWith('/')) {
    return `https://github.com/${owner}/${repo}/tree/${branchName}/${safe.slice(0, -1)}`;
  }
  const isDir = ['src/main/java/estore', 'src/main/java/estore/business', 'src/main/java/estore/data', 'src/main/java/estore/presentation', 'src/test/java', 'src/test/resources/estore', 'src/main/java', 'src/test/java/estore'].includes(safe);
  if (isDir) {
    return `https://github.com/${owner}/${repo}/tree/${branchName}/${safe}`;
  }
  return `https://github.com/${owner}/${repo}/blob/${branchName}/${safe}`;
};

const mapInternalDocsRoute = (target) => {
  const map = {
    '.spec/README.md': '../specifications/specifications-overview.md',
    '.spec/README.md#purpose-of-a-spec-file': '../specifications/specifications-overview.md',
    '.spec/architecture.md': '../architecture/architecture-overview.md',
    '.spec/product.md': '../specifications/product.md',
    '.spec/features/add-product.md': '../specifications/features/add-product.md',
    '.spec/features/search-product.md': '../specifications/features/search-product.md',
    '.spec/features/view-product-catalog-metadata.md': '../specifications/features/view-product-catalog-metadata.md',
    'README.md': '../getting-started/project-overview.md',
    'src/test/TEST-INFRASTRUCTURE.md': '../testing/test-infrastructure.md',
  };
  return map[target] || null;
};

const rewriteMarkdownLinks = (content) => content.replace(/\[[^\]]+\]\(([^)]+)\)/g, (match, target) => {
  if (!target || target.startsWith('http') || target.startsWith('#') || target.startsWith('/')) return match;

  const internal = mapInternalDocsRoute(target);
  if (internal) {
    const label = match.match(/^\[([^\]]+)\]/)?.[1] || '';
    return `[${label}](${internal})`;
  }

  if (target.startsWith('.spec/') || target.startsWith('src/') || target.startsWith('.github/') || target === 'README.md' || target === 'pom.xml') {
    const label = match.match(/^\[([^\]]+)\]/)?.[1] || '';
    return `[${label}](${urlForRepoPath(target)})`;
  }

  return match;
});

const ensureDocsDir = () => {
  fs.rmSync(docsRoot, { recursive: true, force: true });
  fs.mkdirSync(docsRoot, { recursive: true });
  fs.mkdirSync(path.join(docsRoot, 'engineering-evidence'), { recursive: true });

  const landing = `---
title: E-Search documentation
sidebar_label: Home
---

# E-Search documentation

This documentation site publishes the canonical engineering documentation for the EStore Search project.

## Start here

- [Project overview](./getting-started/project-overview.md)
- [Architecture overview](./architecture/architecture-overview.md)
- [Product specification](./specifications/product.md)
- [Testing infrastructure](./testing/test-infrastructure.md)

## Repository source of truth

The application source of truth remains the project repository itself. The canonical docs live in the root [README.md](${repoUrl}) and the product/specification files in the repo under [.spec](${repoUrl}/tree/${branchName}/.spec). Docusaurus acts as the presentation layer used for publishing this material to a static website.
`;
  fs.writeFileSync(path.join(docsRoot, 'index.md'), landing, 'utf8');
};

const writeDoc = (sourceFile, targetRelativePath, title, sidebarLabel) => {
  const sourceContent = fs.readFileSync(sourceFile, 'utf8');
  const generated = rewriteMarkdownLinks(sourceContent);

  const fullTargetPath = path.join(docsRoot, targetRelativePath);
  fs.mkdirSync(path.dirname(fullTargetPath), { recursive: true });

  const docFrontmatter = `---\ntitle: ${title}\nsidebar_label: ${sidebarLabel}\n---\n\n`;
  fs.writeFileSync(fullTargetPath, docFrontmatter + generated, 'utf8');
};

ensureDocsDir();

writeDoc(path.join(repoRoot, 'README.md'), 'getting-started/project-overview.md', 'Project overview', 'Overview');
writeDoc(path.join(repoRoot, '.spec', 'README.md'), 'specifications/specifications-overview.md', 'Specifications overview', 'Overview');
writeDoc(path.join(repoRoot, '.spec', 'architecture.md'), 'architecture/architecture-overview.md', 'Architecture overview', 'Architecture');
writeDoc(path.join(repoRoot, 'src', 'test', 'TEST-INFRASTRUCTURE.md'), 'testing/test-infrastructure.md', 'Test infrastructure', 'Test infrastructure');
writeDoc(path.join(repoRoot, '.spec', 'product.md'), 'specifications/product.md', 'Product specification', 'Product spec');
writeDoc(path.join(repoRoot, '.spec', 'features', 'add-product.md'), 'specifications/features/add-product.md', 'Add product', 'Add product');
writeDoc(path.join(repoRoot, '.spec', 'features', 'search-product.md'), 'specifications/features/search-product.md', 'Search product', 'Search product');
writeDoc(path.join(repoRoot, '.spec', 'features', 'view-product-catalog-metadata.md'), 'specifications/features/view-product-catalog-metadata.md', 'View product catalog metadata', 'View metadata');

const evidenceIntro = `---\ntitle: Coverage overview\nsidebar_label: Coverage\n---\n\n# Coverage overview\n\nThis section is reserved for generated JaCoCo and other engineering evidence reports from the CI pipeline. The canonical project reports are produced under the Maven build output and can later be copied into the docs site as static assets.\n\nFor now, the site is structured so the generated output can be published under a dedicated reports path without mixing it with the canonical Markdown documentation.\n`;
fs.writeFileSync(path.join(docsRoot, 'engineering-evidence', 'coverage-overview.md'), evidenceIntro, 'utf8');
fs.writeFileSync(path.join(docsRoot, 'engineering-evidence', 'security-overview.md'), `---\ntitle: Security overview\nsidebar_label: Security\n---\n\n# Security overview\n\nThis section is reserved for generated security scan reports and remediation notes. The repository remains the source of truth for dependency and policy decisions; generated scans belong in the static reporting layer.\n`, 'utf8');
fs.writeFileSync(path.join(docsRoot, 'engineering-evidence', 'test-results-overview.md'), `---\ntitle: Test results overview\nsidebar_label: Test results\n---\n\n# Test results overview\n\nThis page is the placeholder for generated test results, Surefire/Failsafe output, and CI result summaries. The Java project already produces these reports under the Maven build output, and the Docusaurus site is structured to surface them without treating them as canonical specification documents.\n`, 'utf8');

console.log('Docs synced from canonical Markdown into website/docs');
