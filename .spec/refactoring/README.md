# Refactoring

Refactoring specifications describe changes to the implementation
that must preserve the product's existing behaviour.

## Behavioural contract

All refactorings must preserve the behaviour defined by:

- `.spec/product.md`
- Feature specifications under `.spec/features/`
- Existing automated tests

A refactoring must not intentionally change user-visible behaviour
unless the corresponding product specification is also changed.

## Refactoring principles

- Improve implementation structure without changing behaviour.
- Do not introduce unrelated functionality.
- Preserve existing public/user-facing interfaces unless the
  refactoring explicitly requires otherwise.
- Prefer small, independently verifiable changes.
- Run the relevant tests after each refactoring.
- Update documentation when implementation changes affect documented
  architecture or developer workflows.

## Refactoring workflow

1. Create a GitHub issue describing the refactoring.
2. Create a refactoring specification for the issue.
3. Identify the existing behaviour that must be preserved.
4. Implement the refactoring.
5. Run the relevant tests.
6. Verify that the behavioural contract remains satisfied.
7. Close the GitHub issue when the acceptance criteria are met.

## Refactoring vs feature work

A refactoring changes how the system is implemented.

A feature changes what the system does.

If a change intentionally alters user-visible behaviour, it should
be treated as feature work and the product specification must be
updated accordingly.