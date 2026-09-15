# Goal

Right now there is a lot of redundancy in console input related code. For example, we have `readProductId()` and `readProductName()` which are very similar. We want to refactor this code to reduce redundancy and improve maintainability.

## Current Implementation

Inside `EStoreSearch`, there are methods that accept user input for search and addition purpose, each one of them instantiate their own instance of `Scanner` and have their own logic to read input. This leads to code duplication and makes it harder to maintain.

## Desired Implementation

We want to have a single method which accepts input and applies the necessary validation rules defined in the `validation` package. This method should be able to handle different types of input (like product ID, product name, etc.) and apply the appropriate validation rules. Rather than having multiple methods for each type of input, we should have a single method that can be reused across the application and it simply returns the validated input. This will reduce code duplication and improve maintainability.

## Behaviour to preserve

- Existing application behavior remains unaffected
- Redundancy is visibly reduced in the codebase
- Less/ single number of scanner class instance being used.