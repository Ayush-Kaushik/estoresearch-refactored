Feature: Read a product catalog file
  As a store operator
  I want the EStore Search program to read a product catalog file
  So that the catalog can be updated and queried in one session

  Scenario: Operator runs the program with empty product catalog
    Given a new catalog file
    When the operator starts E-Search
    When the operator views the product catalog metadata
    Then catalog metadata should indicate 0 products

  Scenario: Operator runs the program with non-empty product catalog
    Given a new catalog file
    And catalog file contains 2 books
    And catalog file contains 1 electronics
    When the operator starts E-Search
    When the operator views the product catalog metadata
    Then the program should display the catalog metadata
    Then catalog metadata should indicate 3 products
    Then catalog metadata should indicate 2 books
    Then catalog metadata should indicate 1 electronics

  Scenario: Operator skips an unsupported product type and loads a valid product
    Given a new catalog file
    And catalog file contains an unsupported product type
    And catalog file contains 1 books
    When the operator starts E-Search
    When the operator views the product catalog metadata
    Then catalog metadata should indicate 1 products
    Then catalog metadata should indicate 1 books
    Then catalog metadata should indicate 0 electronics
