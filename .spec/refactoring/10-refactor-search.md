# Goal

Currently, `EStoreSearch` has the entire implementation and all the responsibilities related to search. The goal is to build a modular search engine which includes accepting user input and searching the products based on the search query.

## Current Implementation

The current implementation has all the responsibilities related to search inside single monolith class.


## Desired Implementation

- Extract the search operation from `EStoreSearch` class
- Follow the existing command pattern to setup search handler and move all the search related operations inside it
- Modularize the way query is created to search the products based on user input
- Finally create a ProductSearchService which handles the interaction with data layer to get the products

## Behaviour To Preserve

- Ensure the existing strategy to search isn't affected behaviourally
- Ensure existing refactored logic isn't modifed
- Ensure the `architecture.md` is followed

## Acceptance Criteria
- Project builds without error
