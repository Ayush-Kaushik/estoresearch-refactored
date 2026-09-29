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

    # Think about adding this step as in most scenarios the program will quit at the end
    # And we will assert
    # or we quit the program and gather all the details
    And the operator quits
