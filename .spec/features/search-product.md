# Search Product

## Goal

Allow the operator to search the catalog using one or more optional
filters: Product ID, product name keywords, and year.

## Required behaviour

### Search flow

- Selecting Search from the main menu starts a product search.
- Display the prompt:
  `Enter the specs and find the matching product`
- The operator may choose whether to search by:
  - Product ID
  - Name
  - Year
- Each filter uses:
  - `1` = Yes
  - `2` = No
- Filters are optional.
- A product must satisfy all selected filters to be returned.

### Product ID filter

When Product ID search is selected:

- Accept exactly 6 digits.
- Match the product ID exactly.
- Only the product with the matching ID should be returned.

### Name filter

When Name search is selected:

- The name must be non-empty.
- Matching is case-insensitive.
- A single word matches products whose name contains that keyword.
- Multiple words require every keyword to appear in the product name.
- Keyword matching is case-insensitive.
- Matching should use the product name index where applicable.

For example:

- Search: `java`
  - `Java Complete Reference` → match
  - `Python Programming` → no match

- Search: `java reference`
  - `Java Complete Reference` → match
  - `Java Programming` → no match

### Year filter

When Year search is selected, accept these formats:

- `YYYY` — exact year
- `-YYYY` — year less than or equal to YYYY
- `YYYY-` — year greater than or equal to YYYY
- `YYYY-YYYY` — inclusive range

Examples:

- `2020` → only products from 2020
- `-2020` → products from 2020 or earlier
- `2020-` → products from 2020 or later
- `2018-2022` → products from 2018 through 2022

### Combining filters

Filters must be combined using AND semantics.

For example:

- ID + Name → product must match both.
- Name + Year → product must match both.
- ID + Year → product must match both.
- ID + Name + Year → product must match all three.

If no filters are selected, every product in the catalog should be returned.

### Search results

For every matching product:

- Print the human-readable `datadump()` representation.
- Do not print the file serialization produced by `toString()`.

If no products match, no product results are printed.

## Out of scope

- Searching by author.
- Searching by publisher.
- Searching by maker.
- Adding or editing products.
- Deleting products.
- Changing catalog import behaviour.
- Changing catalog export behaviour.
- Changing the main menu commands.

## Acceptance

- [ ] Operator can search by exact Product ID.
- [ ] Invalid Product IDs are rejected.
- [ ] Operator can search by a single name keyword.
- [ ] Name matching is case-insensitive.
- [ ] Multiple name keywords use AND semantics.
- [ ] Operator can search by an exact year.
- [ ] Operator can search by a maximum year.
- [ ] Operator can search by a minimum year.
- [ ] Operator can search by an inclusive year range.
- [ ] ID and Name filters can be combined.
- [ ] ID and Year filters can be combined.
- [ ] Name and Year filters can be combined.
- [ ] All three filters can be combined.
- [ ] Skipped filters do not constrain the search.
- [ ] Selecting no filters returns every product.
- [ ] Matching products are printed using `datadump()`.
- [ ] Existing Add and Export behaviour remains unchanged.