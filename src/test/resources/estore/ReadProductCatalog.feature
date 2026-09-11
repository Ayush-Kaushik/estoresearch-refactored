Feature: Read a product catalog file
  As a store operator
  I want the EStore Search program to read a product catalog file
  So that the catalog can be updated and queried in one session

  Scenario: Operator runs the program with empty product catalog
    Given a catalog file with no products
    When the operator starts E-Search
    When the operator views the product catalog metadata
    Then the program should indicate that that the catalog contains 0 products

  Scenario: Operator runs the program with non-empty product catalog
    Given a new catalog file with products
    And catalog file contains 2 books products
    And catalog file contains 1 electronics product
    When the operator starts E-Search
    When the operator views the product catalog metadata
    Then the program should display the catalog metadata
    Then catalog metadata should indicate that the catalog contains 3 products
    Then catalog metadata should indicate 2 books products
    Then catalog metadata should indicate 1 electronics product
