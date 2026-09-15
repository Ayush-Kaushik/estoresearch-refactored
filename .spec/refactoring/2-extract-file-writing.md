## Goal

Separate writing product catalog to file responsibilities from `EStoreSearch` to reduce
the size and responsibilities of the main application class.

## Current implementation

File writing functionality is currently implemented in `printOutput` method within `EStoreSearch` class

## Desired implementation change

- Extract file writing responsibilities into an appropriate class.
- `EStoreSearch` should delegate file writing operations to the extracted
  component.
- Preserve the existing application behavior.

## Behavior to preserve
- Existing application behavior remains unaffected

## Out of scope

- Changing file writing semantics.
- Changing the console interaction.

## Acceptance

- [ ] File writing behavior remains unchanged
- [ ] Existing tests pass.
- [ ] Relevant Cucumber scenarios pass.
- [ ] `EStoreSearch` no longer owns the extracted file writing responsibility.
- [ ] No unrelated behavior changes.