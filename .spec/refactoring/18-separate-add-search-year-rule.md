# Goal

Create a separate validation rule for the `add` and `search` workflows of the program to validate the year input.

## Current Implementation

`presentation` contains a single validation rule for the year input which is used when adding a new product. The rule which should have existed for search is within `ProductSearchView` class.

## Desired Implementation

- Update the existing add rule name to `AddProductYearRule` and its usage accordingly
- Create a new rule named `SearchProductYearRule` and use it in product search section where the year input is validated.
- The search rule should have validation logic defined inside `./features/search-product.md` file.

## Acceptance Criteria

- The existing tests behaviour is maintained
- The existing behavior is not changed