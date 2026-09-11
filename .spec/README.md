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

---

## Product snapshot (E-Search)

EStoreSearch is a **command-line electronic-store catalog**. An operator loads products from a text file, adds books or electronics, searches by ID / name keywords / year, and on quit writes the catalog to `java.txt`.

**Entry point:** `estore.EStoreSearch`  
**Run:** `java -cp target/classes estore.EStoreSearch <input-file>`  
(or `mvn exec:java -Dexec.args="<input-file>"`)

Exactly **one** argument is required. Otherwise the process prints `Not enough arguments` and exits. If the file cannot be opened, it prints `Problem opening files` and exits.

### Domain model

Shared fields on every product (`Product`):

| Field | Rule when adding interactively |
|-------|---------------------------------|
| Product ID | Exactly 6 digits, unique in the catalog |
| Name | Required, non-empty |
| Year | Integer `1000`–`9999` |
| Price | Numeric and `> 0`, or blank → `0.0` |

**Book** also has optional `authors` and `publisher` (blank allowed).  
**Electronics** also has optional `maker` (blank allowed).

In-memory store:

- `productList` — ordered list of `Product` (books and electronics)
- `map` — inverted index: lowercase **name token** → list of indexes in `productList`

The index is updated when a product is loaded from file or added from the menu. Search by name uses this index; search with an empty name scans the full list.

### Interactive commands

After a successful file load, the loop is:

```
-------Welcome to E-Search --------
(1)Add (2)Search (3)Quit
```

Accepted input (case-insensitive, trimmed): `1` / `add` / `a`, `2` / `search` / `s`, `3` / `quit` / `q`. Anything else is rejected with `*** enter only numbers or commands ***`.

| Command | Behaviour |
|--------|-----------|
| Add | Choose `(1)Book` or `(2)Electronics`, then collect fields in order: ID, year, name, price, then type-specific fields. Confirms the new entry on stdout. |
| Search | Optional filters: ID, name, year (each Yes/No). Matches are printed via `datadump()`. |
| Quit | Writes the catalog to `java.txt` and exits. |

### Catalog file format (load)

The loader reads `key = "value"` lines. A line that does **not** contain a single `=` (typically a **blank line**) flushes the accumulated fields into a product.

Required keys for a record: `type`, `productID`, `name`, `price`, `year`.

- `type` must be `book` or `electronics` (lowercase).
- Book extras: `authors`, `publisher` (missing → empty string).
- Electronics extra: `maker` (missing → empty string).
- The first and last character of each value are stripped (meant for wrapping quotes).

Example:

```
type = "book"
productID = "100001"
name = "Java Complete Reference"
price = "59.99"
year = "2022"
authors = "Herbert Schildt"
publisher = "Oracle Press"

```

### Search rules (intended)

Filters combine. A product is shown only if it satisfies every criterion the user chose to enter.

- **ID:** optional; if provided, must be 6 digits; empty ID means “any ID”.
- **Name:** optional; case-insensitive. One word looks up that token in the index. Multiple words are meant to mean **all** keywords appear in the name (AND).
- **Year:** optional formats:
  - `2020` — exact year
  - `-2020` — year ≤ 2020
  - `2020-` — year ≥ 2020
  - `2018-2022` — inclusive range

If name is omitted, ID and/or year still filter the full catalog. If every filter is skipped, every product should match.

Search output uses `datadump()` (human-readable one-liners), not the file `toString()` format.

### Export on quit

`printOutput` writes `java.txt` in the process working directory (overwrites). Each product’s `toString()` is the book/electronics block with quoted fields, which is the same family of format as the input file.

### Current types (implementation map)

| Class | Role |
|-------|--------|
| `estore.EStoreSearch` | CLI, file I/O, add, search, inverted index |
| `estore.Product` | Shared fields and add-time validation |
| `estore.Book` | Authors, publisher, file/search formatting |
| `estore.Electronics` | Maker, file/search formatting |

## Known gaps in the current code

Use these as candidates for later feature specs; do not treat them as required product behaviour unless you promote them into `product.md`.

- Sample resource `src/test/resources/import.txt` uses `type=Book` / `id=...` without quotes. The loader expects `type = "book"`, `productID`, and a blank line between records.
- Multi-keyword name search currently reassigns the index list per token and `retainAll`s the same list; it does not reliably AND tokens.
- Interactive add order is ID → year → name → price (not ID → name → year → price).
- `validatePrice` cannot actually accept a blank price as written (empty string fails `parseDouble` after the blank branch).
- Quit always writes `java.txt`, not the path passed as `args[0]`.
- Last product in an input file is not flushed unless a trailing non-`key=value` line (blank line) follows it.

## Source of this snapshot

Derived from `src/main/java/estore/*.java` and `UserGuide.md`. Prefer updating [product.md](product.md) when you decide the **intended** product, then change code to match.
