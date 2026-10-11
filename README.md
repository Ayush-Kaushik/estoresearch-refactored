# EStore Search


[![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=alert_status&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=coverage&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=duplicated_lines_density&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=ncloc&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) 

[![Maintainability issues](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=software_quality_maintainability_issues&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=sqale_index&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=sqale_rating&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Ayush-Kaushik_estoresearch-refactored&metric=reliability_rating&token=50dce2522cb63689abf45c990b910165cb2f7878)](https://sonarcloud.io/summary/new_code?id=Ayush-Kaushik_estoresearch-refactored) 

EStore Search is a Java console application for managing a small catalog of books and electronics. It loads a catalog from a text file, lets a user add records, search by ID/name/year, view catalog metadata, and export the catalog back to a file.

For detailed product behavior and validation rules, see the specs in [`.spec/README.md`](.spec/README.md), [`.spec/product.md`](.spec/product.md), and the feature docs under [`.spec/features`](.spec/features).

---

## Overview

The app is built around a layered design:

- **Presentation:** console input/output and menu flow
- **Business:** product validation and search logic
- **Data:** in-memory catalog and repository management

This is implemented in the package structure under [`src/main/java/estore`](src/main/java/estore), with the architecture described in [`.spec/architecture.md`](.spec/architecture.md).

### Main features

- Load catalog records from a text file
- Add books or electronics to the catalog
- Search by product ID, name keyword, and year filters
- View metadata such as total products and counts by type
- Quit and write the catalog to `java.txt`

See also:

- [`.spec/features/add-product.md`](.spec/features/add-product.md)
- [`.spec/features/search-product.md`](.spec/features/search-product.md)
- [`.spec/features/view-product-catalog-metadata.md`](.spec/features/view-product-catalog-metadata.md)

---

## Tech stack

- Java 8+
- Maven
- JUnit 4
- Cucumber for acceptance tests

The Maven configuration is in [`pom.xml`](pom.xml), and the project entry point is defined in [`src/main/java/estore/EStoreSearch.java`](src/main/java/estore/EStoreSearch.java).

---

## Project structure

```text
estoresearch-refactored/
├── .github/                     # GitHub automation and repo metadata
├── .spec/                       # Product specs and feature requirements
│   ├── README.md
│   ├── architecture.md
│   ├── product.md
│   └── features/
├── src/
│   ├── main/java/estore/
│   │   ├── EStoreSearch.java
│   │   ├── business/
│   │   │   ├── BookCreationRequest.java
│   │   │   ├── ElectronicsCreationRequest.java
│   │   │   ├── IProductCreationRequest.java
│   │   │   ├── IProductService.java
│   │   │   ├── ProductFactory.java
│   │   │   ├── ProductSearchService.java
│   │   │   ├── ProductService.java
│   │   │   └── SearchQuery.java
│   │   ├── data/
│   │   │   ├── Book.java
│   │   │   ├── CatalogFileReader.java
│   │   │   ├── CatalogFileWriter.java
│   │   │   ├── Electronics.java
│   │   │   ├── IProductCatalogRepository.java
│   │   │   ├── InMemoryProductCatalogRepository.java
│   │   │   └── Product.java
│   │   └── presentation/
│   │       ├── Console.java
│   │       ├── IConsole.java
│   │       ├── ProductAddView.java
│   │       ├── ProductSearchView.java
│   │       ├── command/
│   │       │   ├── AddOperationHandler.java
│   │       │   ├── Command.java
│   │       │   ├── IOperationHandler.java
│   │       │   ├── QuitOperationHandler.java
│   │       │   ├── SearchOperationHandler.java
│   │       │   └── ViewOperationHandler.java
│   │       └── validation/
│   │           ├── MenuOptionRule.java
│   │           ├── NameRule.java
│   │           ├── PriceRule.java
│   │           ├── ProductIDRule.java
│   │           ├── Rule.java
│   │           └── YearRule.java
│   └── test/
│       ├── java/estore/
│       │   ├── e2e/
│       │   ├── integration/
│       │   ├── logging/
│       │   └── unit/
│       └── resources/
│           ├── import.txt
│           └── estore/
├── pom.xml
├── README.md
└── target/
```

Key implementation areas:

- [`src/main/java/estore/EStoreSearch.java`](src/main/java/estore/EStoreSearch.java) — application entry point and menu flow
- [`src/main/java/estore/business`](src/main/java/estore/business) — creation requests, product factory, service, and search logic
- [`src/main/java/estore/data`](src/main/java/estore/data) — domain models, repository, and file-based catalog persistence
- [`src/main/java/estore/presentation`](src/main/java/estore/presentation) — console UI, menu actions, and validation rules
- [`src/test/java/estore`](src/test/java/estore) — unit, integration, and end-to-end acceptance tests

---

## Quickstart

### Requirements

- Java 8 or later
- Maven

### Build

```bash
mvn test
```

### Run the app

After building, run the program with a catalog file:

```bash
mvn exec:java -Dexec.args="src/test/resources/import.txt"
```

You can also run the compiled class directly:

```bash
java -cp target/classes estore.EStoreSearch src/test/resources/import.txt
```

The sample import file is available at [`src/test/resources/import.txt`](src/test/resources/import.txt).

---

## Menu options

The main menu includes:

1. Add product
2. Search catalog
3. Quit and export
4. View metadata

The expected flows and acceptance criteria are documented in:

- [`.spec/product.md`](.spec/product.md)
- [`.spec/features/add-product.md`](.spec/features/add-product.md)
- [`.spec/features/search-product.md`](.spec/features/search-product.md)
- [`.spec/features/view-product-catalog-metadata.md`](.spec/features/view-product-catalog-metadata.md)

---

## Catalog file format

The app reads a line-based file format similar to:

```text
type = "book"
productID = "123456"
name = "Learning Java"
price = "45.99"
year = "2023"
authors = "Jane Doe"
publisher = "Education Press"
```

This format is covered in [`.spec/product.md`](.spec/product.md) and is used by [`src/main/java/estore/CatalogFileReader.java`](src/main/java/estore/CatalogFileReader.java).

---

## Testing

The project includes unit and acceptance-style tests under [`src/test/java`](src/test/java), with scenarios in [`src/test/resources/estore`](src/test/resources/estore).

Tests can be run with:

```bash
mvn test
```

The end-to-end scenarios cover add/search behavior and catalog metadata reading.

---

## More details and specs

If you want deeper product requirements and historical refactoring notes, start here:

- [`.spec/README.md`](.spec/README.md) — how the specs are organized and used
- [`.spec/product.md`](.spec/product.md) — end-to-end product specification
- [`.spec/architecture.md`](.spec/architecture.md) — design and layering rules
- [`.spec/features/add-product.md`](.spec/features/add-product.md)
- [`.spec/features/search-product.md`](.spec/features/search-product.md)
- [`.spec/features/view-product-catalog-metadata.md`](.spec/features/view-product-catalog-metadata.md)

---

## License

This project is for educational and project-based use within the current repository. The spec files describe the intended behavior for the application and should be treated as the source of truth when validating or extending the product.