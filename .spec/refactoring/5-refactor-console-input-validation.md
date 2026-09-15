# Goal

I want to refactor the console input validation logic in the `EStoreSearch` class to improve code readability, maintainability, and separation of concerns. The current implementation has input validation logic tightly coupled with the main application logic, making it difficult to manage and extend.

# Current implementation

The `EStoreSearch` class currently handles console input validation directly within its methods. This includes checking for valid product IDs, names, prices, and years. The validation logic is scattered throughout the class, leading to code duplication and making it harder to understand and maintain.

# Desired implementation

I want to have a dedicated Rule class which is abstract and exposes methods like `validate` and then the concrete class will pick the abstract class and implement the `validate` method. The concrete class will have the actual validation logic for each type of input (e.g., product ID, name, price, year). This will allow for better separation of concerns and make it easier to add new validation rules in the future.

I want to keep the validation for each type of input in separate classes, following the Single Responsibility Principle. Each validation class will be responsible for validating a specific type of input, and the `EStoreSearch` class will delegate the validation to these classes.

Example rule psuedocode:

```java
public abstract class Rule {
    public abstract boolean validate(String input);
}

public class ProductIDRule extends Rule {
    @Override
    public boolean validate(String input) {
        // Implement validation logic for product ID
    }
}

```

Your job is to extract such validations in their respective classes and make the `EStoreSearch` class use these classes for validation.

I want to ensure that the existing application behavior remains unaffected after this refactoring. The goal is to improve code organization and maintainability without changing how the application functions from the user's perspective.

Plase the rules under a presentation package called `validation` and make sure to use the existing tests to verify that the refactoring does not introduce any regressions.

## Behavior to preserve
- Existing application behavior remains unaffected

## Out of scope
- Changing product adding semantics.
- Changing the console interaction.

## Acceptance

- [ ] Adding products remains unchanged
- [ ] Relevant Cucumber scenarios pass.
- [ ] `EStoreSearch` no longer owns the extracted product adding responsibility.
- [ ] No unrelated behavior changes.
- [ ] No need to run tests.