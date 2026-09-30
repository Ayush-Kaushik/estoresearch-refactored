module.exports = {
  docsSidebar: [
    {
      type: 'category',
      label: 'Getting Started',
      collapsed: false,
      items: ['getting-started/project-overview'],
    },
    {
      type: 'category',
      label: 'Architecture',
      collapsed: false,
      items: ['architecture/architecture-overview'],
    },
    {
      type: 'category',
      label: 'Specifications',
      collapsed: false,
      items: [
        'specifications/specifications-overview',
        'specifications/product',
        {
          type: 'category',
          label: 'Feature specs',
          collapsed: true,
          items: [
            'specifications/features/add-product',
            'specifications/features/search-product',
            'specifications/features/view-product-catalog-metadata',
          ],
        },
      ],
    },
    {
      type: 'category',
      label: 'Testing',
      collapsed: false,
      items: ['testing/test-infrastructure'],
    },
    {
      type: 'category',
      label: 'Engineering Evidence',
      collapsed: false,
      items: [
        'engineering-evidence/coverage-overview',
        'engineering-evidence/e2e-coverage',
        'engineering-evidence/security-overview',
      ],
    },
  ],
};
