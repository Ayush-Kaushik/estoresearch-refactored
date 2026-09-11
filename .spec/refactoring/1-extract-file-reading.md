## Goal

Separate file reading responsibilities from `EStoreSearch` to reduce
the size and responsibilities of the main application class.

## Current implementation

File reading functionality is currently implemented in `fileRead` method within `EStoreSearch` class

## Desired implementation change

- Extract search-related responsibilities into an appropriate class.
- `EStoreSearch` should delegate file reading operations to the extracted
  component.
- The extracted data should be stored in memory then appropriate `HashMap` is created from that stored data read from file
- Preserve the existing application behavior.

## Behavior to preserve
- Existing application behavior remains unaffected

## Out of scope

- Changing file reading semantics.
- Changing the console interaction.

## Acceptance

- [ ] File reading behavior remains unchanged
- [ ] Existing tests pass.
- [ ] Relevant Cucumber scenarios pass.
- [ ] `EStoreSearch` no longer owns the extracted file reading responsibility.
- [ ] No unrelated behavior changes.