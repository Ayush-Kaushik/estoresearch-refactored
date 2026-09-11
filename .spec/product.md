# Product: E-Search (eStoreSearch)

Status: **baseline** — intended behaviour for the console catalog. Implementation lives in `estore.EStoreSearch` and related model classes.

## Problem

A store operator needs to keep a small catalog of **books** and **electronics**, load it from a text file, add items, find items by ID, name keywords, and year, and save the catalog when they leave.

## Users and environment

- Operator at a terminal (no GUI, no network).
- Java 8+ console app; Maven artifact main class `estore.EStoreSearch`.
- Persistence is plain text, not a database.

## Launch

- **Command:** `java -cp target/classes estore.EStoreSearch <catalog-file>`
- **Argument:** exactly one path. Missing argument → print `Not enough arguments` and exit.
- **Missing file:** print `Problem opening files` and exit.
- After a successful open, load records, then show the main menu until quit.

## Catalog records

Every product has: 6-digit `productID`, non-empty `name`, `year` in `1000`–`9999`, `price` (non-negative number; blank on add means `0.0`).

- **Book:** `authors`, `publisher` (optional).
- **Electronics:** `maker` (optional).

IDs are unique in the in-memory catalog. Adding an ID that already exists must be rejected and the operator re-prompted.

## File format (import)

Line-oriented `key = "value"` records. A blank line ends a record and creates the product.

| Key | Used by |
|------|---------|
| `type` | `book` or `electronics` |
| `productID` | both |
| `name` | both |
| `price` | both |
| `year` | both |
| `authors` | book |
| `publisher` | book |
| `maker` | electronics |

Values are wrapped in double quotes. Missing optional fields become empty strings. After load, each product name is indexed: split on spaces, keys stored lowercase, values are indexes into the product list.

## Main menu

Prompt:

```
-------Welcome to E-Search --------
Select the numbers or type command corresponding to the options below: (1)Add (2)Search (3)Quit
```

| Operator input | Action |
|----------------|--------|
| `1`, `add`, `a` | Add |
| `2`, `search`, `s` | Search |
| `3`, `quit`, `q` | Export and exit |

Matching is case-insensitive after trim. Invalid input: `*** enter only numbers or commands ***` and re-prompt.

### Add

1. `(1)Book` or `(2)Electronics` (numeric only).
2. Collect ID, year, name, price (shared validation).
3. Book: author, publisher (blank allowed). Electronics: maker (blank allowed).
4. Append to the catalog, update the name index, print a confirmation line.

### Search

Prompt: `Enter the specs and find the matching product`. Then three optional filters (each Yes=`1` / No=`2`):

1. **Product ID** — if yes, 6 digits.
2. **Name** — if yes, non-empty string; case-insensitive; multiple words mean all tokens must appear in the product name.
3. **Year** — if yes, one of: `YYYY`, `-YYYY`, `YYYY-`, `YYYY-YYYY`.

A product is printed only if it matches all provided filters. Omitted filters do not constrain that dimension. Results use the human-readable `datadump` line (ID, name, year, price, plus author/publisher or maker).

### Quit

Write the full catalog to `java.txt` in the working directory (overwrite). Format is the quoted `type` / `productID` / … blocks from each product’s `toString()`. Print `Successful write to file` (or `Unable to open file` and exit). Then process exits.

## Out of scope (this baseline)

- GUI, web API, multi-user, or database.
- Search by author, publisher, or maker.
- Deleting or editing products.
- Writing back to the original input path (unless a later spec changes this).

## Acceptance (baseline)

- [ ] App starts only with one file argument and loads quoted `key = "value"` records separated by blank lines.
- [ ] Main menu accepts number, word, and short aliases for add/search/quit.
- [ ] Add enforces unique 6-digit IDs, required name, 4-digit year, numeric price.
- [ ] Search can filter by ID, name keywords (AND, case-insensitive), and year exact/range/open-ended.
- [ ] Quit writes `java.txt` in the import-compatible quoted format.
