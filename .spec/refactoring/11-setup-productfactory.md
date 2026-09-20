# Goal

`ProductAdditionService.cs` has a unique method for each product type that will be created, this will not scale well. Instead we will leverage a factory pattern which receives DTO from presentation layer.

## Current Implementation

same as what is mentioned in goal.

## Desired Implementation

- Inside Presentation layer create a `ProductCreationRequest` interface which will be implemented by `ElectronicsCreationRequest` and `BookCreationRequest` concrete DTOs.
- Inside `ProductAddView` create methods for collecting book and electronics details and creating the DTOs mentioned above.
- Inside the `AddOperationHandler` invoke the specific view method to create the DTOs based on option that user has selected.
- Check if `ProductAdditionService` can be removed and `ProductService` can be directly utilized

## Acceptance Criteria

- A Clear boundary between presentation and business layer is established
- The DTOs are created in the correct location - presentation
- The existing behaviour of the application is not affected
- Appropriate unit and integration tests are created to test the boundaries