# Architecture

The codebase follows **separation of concerns** using a layered architecture. The code is divided into three layers:

1. Presentation layer
2. Business logic layer
3. Data access layer

Each layer has a distinct responsibility and should not take on responsibilities belonging to another layer.

## Presentation Layer

The presentation layer is responsible for interacting with the user.

It handles:

* Reading user input
* Displaying menus and prompts
* Displaying operation results
* Displaying user-facing error messages

The presentation layer communicates with the business logic layer to perform operations based on user actions.

The presentation layer should **not contain business rules or data-storage logic**.

## Business Logic Layer

The business logic layer contains the core functionality and business rules of the application.

It is responsible for:

* Validating business rules
* Managing product-related operations
* Adding products
* Searching for products
* Deleting products
* Coordinating operations involving product data

The business logic layer may request data from the data access layer, but should **not depend on how that data is physically stored**.

The business logic layer should **not read from standard input, write to standard output, or contain console-specific presentation logic**.

## Data Access Layer

The data access layer is responsible for storing and retrieving product data.

It handles:

* Loading product records from files
* Saving product records to files
* Maintaining the in-memory product catalog
* Maintaining indexes required for data retrieval

The data access layer abstracts the underlying storage mechanism so that the business logic layer can work with product data without needing to know how that data is stored or retrieved.

The data access layer should **not contain user-interface logic or business rules**.

## Layer Dependencies

Dependencies should flow in one direction:

**Presentation → Business Logic → Data Access**

The presentation layer may depend on the business logic layer.

The business logic layer may depend on the data access layer.

The data access layer must not depend on the presentation layer.

Business logic should not directly perform console input/output, and presentation code should not directly manipulate persisted product data.

# Project Structure

The codebase is organized into packages corresponding to the
architectural layers:

    estore/
    ├── presentation/
    ├── business/
    └── data/

Code should be placed in the package corresponding to its primary
responsibility.

## Presentation

`estore.presentation`

Contains user-interface and console interaction code.

## Business Logic

`estore.business`

Contains application behavior and business rules.

## Data Access

`estore.data`

Contains persistence and data retrieval concerns.
