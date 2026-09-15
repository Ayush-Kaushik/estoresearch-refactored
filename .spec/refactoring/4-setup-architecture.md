## Goal

This is to simply setup the directory structure for the project and to setup the architecture for the project. This is to ensure that the project is structured in a way that is easy to understand and maintain.

## Current implementation

All the functionalities are implemented in a single class `EStoreSearch`. This class is responsible for reading user input, displaying menus and prompts, displaying operation results, displaying user-facing error messages, validating business rules, managing product-related operations, adding products, searching for products, deleting products, coordinating operations involving product data, loading product records from files, saving product records to files, maintaining the in-memory product catalog, and maintaining indexes required for data retrieval.

## Desired implementation change

- Setup the architecture for the project as defined in `architecture.md`.

## Behavior to preserve
- Existing application behavior remains unaffected

## Out of scope

- Do not modify the existing classes at all
- Only setup the directory structure and create the necessary classes and interfaces as defined in `architecture.md`.

## Acceptance

- [ ] All behaviour remains unchanged
- [ ] No need to run any tests
- [ ] Directory structure is setup as defined in `architecture.md`