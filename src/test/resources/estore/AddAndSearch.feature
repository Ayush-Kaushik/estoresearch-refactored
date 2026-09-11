Feature: Add a product and search for it
  As a store operator
  I want to start E-Search, add a book, and find it again
  So that the catalog can be updated and queried in one session

  Scenario: Operator runs the program, adds a book, then searches by name
    Given a catalog file with no products
    When the operator starts E-Search
    And the operator adds a book with:
      | productID | 123456          |
      | year      | 2023            |
      | name      | Learning Java   |
      | price     | 45.99           |
      | authors   | Jane Doe        |
      | publisher | Education Press |
    And the operator searches for products named "Learning Java"
    And the operator quits
    Then the program output includes "Adding this entry in list"
    And the search results include product id "123456"
    And the search results include "Learning Java"
