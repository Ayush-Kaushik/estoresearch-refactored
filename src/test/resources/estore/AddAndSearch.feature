Feature: Add a product and search for it
  As a store operator
  I want to start E-Search, add a book, and find it
  So that the catalog can be updated and queried in one session

  Scenario: Operator runs the program, adds a book, then searches by name
    Given a new catalog file
    When the operator starts E-Search
    When the operator adds products with:
      | type        | productID | year | name          | price  | authors  | publisher       | manufacturer |
      | book        | 123456    | 2023 | Learning Java | 45.99  | Jane Doe | Education Press |              |
      | electronics | 234567    | 2024 | Laptop        | 999.99 |          |                 | Lenovo       |
      | book        | 345678    | 2022 | Java Testing  | 39.99  | John Doe | Tech Press      |              |
    And the operator searches for products named "Learning Java"
    Then the search result should contain 1 products
    And the search result should contain
      | productID | name          |
      | 123456    | Learning Java |
    And the operator quits

  Scenario: Operator searches for products from an exact year
    Given a new catalog file
    When the operator starts E-Search
    And the operator adds products with:
      | type        | productID | year | name          | price  | authors  | publisher       | manufacturer |
      | book        | 123456    | 2023 | Learning Java | 45.99  | Jane Doe | Education Press |              |
      | electronics | 234567    | 2024 | Laptop        | 999.99 |          |                 | Lenovo       |
      | book        | 345678    | 2022 | Java Testing  | 39.99  | John Doe | Tech Press      |              |
    And the operator searches using ID "", name "", and year "2023"
    Then the search result should contain 1 products
    And the search results include product id "123456"
    And the operator quits

  Scenario Outline: Operator searches using year boundaries and ranges
    Given a new catalog file
    When the operator starts E-Search
    And the operator adds products with:
      | type        | productID | year | name          | price  | authors  | publisher       | manufacturer |
      | book        | 123456    | 2023 | Learning Java | 45.99  | Jane Doe | Education Press |              |
      | electronics | 234567    | 2024 | Laptop        | 999.99 |          |                 | Lenovo       |
      | book        | 345678    | 2022 | Java Testing  | 39.99  | John Doe | Tech Press      |              |
    And the operator searches using ID "", name "", and year "<year>"
    Then the search result should contain 2 products
    And the search results include product id "<expectedProductID1>"
    And the search results include product id "<expectedProductID2>"
    And the operator quits

    Examples:
      | year      | expectedProductID1 | expectedProductID2 |
      | -2023     | 123456             | 345678             |
      | 2023-     | 123456             | 234567             |
      | 2023-2024 | 123456             | 234567             |

  Scenario Outline: Operator searches by year and combinations of product criteria
    Given a new catalog file
    When the operator starts E-Search
    And the operator adds products with:
      | type        | productID | year | name          | price  | authors  | publisher       | manufacturer |
      | book        | 123456    | 2023 | Learning Java | 45.99  | Jane Doe | Education Press |              |
      | electronics | 234567    | 2024 | Laptop        | 999.99 |          |                 | Lenovo       |
      | book        | 345678    | 2022 | Java Testing  | 39.99  | John Doe | Tech Press      |              |
    And the operator searches using ID "<productID>", name "<name>", and year "<year>"
    Then the search result should contain <productCount> products
    And the search results include product id "<expectedProductID>"
    And the operator quits

    Examples:
      | productID | name          | year | productCount | expectedProductID |
      |           |               | 2023 | 1            | 123456            |
      |           | Learning Java | 2023 | 1            | 123456            |
      | 123456    |               | 2023 | 1            | 123456            |
      | 123456    | Learning Java | 2023 | 1            | 123456            |
