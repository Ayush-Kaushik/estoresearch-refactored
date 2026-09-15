## Goal

Separate adding new products from `EStoreSearch` to reduce
the size and responsibilities of the main application class.

## Current implementation

File writing functionality is currently implemented in `CatalogProductAdder` class

## Desired implementation change

- Follow the boundaries and responsibilities defined in the `architecture.md` document.
- Ask questions if the boundaries are unclear.
- Preserve the existing application behavior.
- `EStoreSearch` should delegate adding product operations to the extracted
  component.
- After the refactoring, `EStoreSearch` should no longer own the product adding responsibility.
- After the refactoring, `CatalogProductAdder` should be removed and split into presentation, service and repository layers.

## Behavior to preserve
- Existing application behavior remains unaffected

## Out of scope
- Changing product adding semantics.
- Changing the console interaction.

## Acceptance

- [ ] Adding products remains unchanged
- [ ] Relevant Cucumber scenarios pass.
- [ ] `EStoreSearch` no longer owns the extracted product adding responsibility.
- [ ] No unrelated behavior changes.
- [ ] No need to run tests