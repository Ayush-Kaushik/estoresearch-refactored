# Add Product

## Goal

Allow the operator to add a new Book or Electronics product
to the in-memory catalog through the Add command.

## Required behaviour

### Product type

- Selecting Add presents:
  - `(1)Book`
  - `(2)Electronics`
- Only `1` or `2` are accepted.

### Common fields

Collect the following fields:

1. Product ID
2. Name
3. Year
4. Price

Validation:

- Product ID must contain exactly 6 digits.
- Product ID must be unique.
- Name must be non-empty.
- Year must be between 1000 and 9999.
- Price must be numeric and non-negative.
- Blank price is interpreted as `0.0`.

If validation fails, re-prompt for the invalid field.

### Book

When the product type is Book:

- Collect authors.
- Collect publisher.
- Both fields may be blank.

### Electronics

When the product type is Electronics:

- Collect maker.
- Maker may be blank.

### Catalog update

After successful validation:

- Add the product to `productList`.
- Update the name index.
- Preserve the existing catalog order.
- Print the product addition confirmation.

## Out of scope

- Changing Search behaviour.
- Changing file import behaviour.
- Changing file export behaviour.
- Editing existing products.
- Deleting products.
- Changing the main menu commands.

## Acceptance criteria

- [ ] Operator can add a Book.
- [ ] Operator can add Electronics.
- [ ] Product ID requires exactly 6 digits.
- [ ] Duplicate Product IDs are rejected.
- [ ] Name cannot be empty.
- [ ] Year must be between 1000 and 9999.
- [ ] Price must be numeric and non-negative.
- [ ] Blank price becomes `0.0`.
- [ ] Book authors and publisher can be blank.
- [ ] Electronics maker can be blank.
- [ ] Successfully added products appear in subsequent searches.
- [ ] Name search can find a newly added product.
- [ ] Existing import, search, and export behaviour remains unchanged.