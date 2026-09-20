# Goal

`EStoreSearch` initialized the dictionary and product list and then injected into the repository. Instead have the repository full responsibility of the inverted index data structure.

## Current Implementation

`EStoreSearch` initializes the data structure and inject it into `InMemoryProductCatalogRepository`. 


## Desired Implementation

Instead, the class should be responsible for creating its own instances of the datastructure and business layer should interact with it. The presentation and business layer should leverage the repository interface to interact with the data structure.

## Acceptance Criteria

- Behavior does not change
- Presentation and business layer has no responsibility of the data structure
- Only concrete implementation of repository knows about the datastructure and controls it