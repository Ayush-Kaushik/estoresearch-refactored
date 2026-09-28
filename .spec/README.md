# Spec-driven development

This folder is the source of truth for **what the product should do**. Code in `src/main/java/estore/` is the current implementation. Specs describe intended behaviour so an AI (or a human) can change the system without rediscovering rules from a 800-line console class.

## Purpose of a spec file

A spec file is a short, testable description of a slice of the product:

- **Who** uses it and **why**
- **Inputs, outputs, and side effects**
- **Rules** (validation, search matching, file format)
- **Acceptance checks** an agent can verify after a change

Specs are not architecture docs and not a dump of the existing code. They capture behaviour you want to keep, change, or add. When a spec and the code disagree, treat the spec as the target and the code as the current state (see [Known gaps](#known-gaps-in-the-current-code)).

### How to use this with an AI assistant

1. Put or update a spec under `.spec/` (start from [product.md](product.md)).
2. Point the agent at the spec: “Implement `.spec/<name>.md`. Do not invent behaviour that is not in the spec.”
3. Ask for tests or a manual run that match the acceptance checks.
4. After the change, update the spec if you accepted a behaviour change. Do not leave the spec describing the old product.

Suggested prompt shape:

```text
Read .spec/README.md and .spec/product.md.
Implement the change described in .spec/<feature>.md.
Keep behaviour that the spec marks as required.
Do not refactor unrelated code.
```

## Suggested files

| File | Role |
|------|------|
| `README.md` | This guide: how specs work, product snapshot, gaps |
| `product.md` | End-to-end product spec (commands, catalog, search, I/O) |
| `<feature>.md` | One upcoming change (add when you start a slice) |

Keep feature specs small: one user-visible capability or one bugfix with acceptance checks.

### Feature spec template

```markdown
# <Feature name>

## Goal
One paragraph: what the user can do after this ships.

## Required behaviour
- ...

## Out of scope
- ...

## Acceptance
- [ ] ...
```

## Known gaps in the current code

This section tracks product behaviours that are intentionally unimplemented or still inconsistent with the specification. Use it as a short checklist when reviewing whether current app behaviour matches the intended product contract.

- [ ] Confirm or document any gaps discovered during implementation.
- [ ] Update the spec and implementation together when behaviour intentionally changes.

---

