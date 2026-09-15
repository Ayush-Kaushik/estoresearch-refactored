# Goal

Currently the model classes are responsible for accepting user input. This is a violation of the Single Responsibility Principle. The goal of this refactoring is to move the user input validation logic from the model classes to the view classes. I want to keep them purely as data classes. The view classes will be responsible for accepting user input and validating it.

## Current Implementation

The model classes (Book, Electronics, and Clothing) are responsible for accepting user input and validating it. This is a violation of the Single Responsibility Principle. The model classes should only be responsible for holding data and not for accepting user input.

## Desired Implementation

The user input validation logic should be moved from the model classes to the view classes. The view classes will be responsible for accepting user input and validating it. The model classes will only be responsible for holding data.

## Behavior to preserve
- Existing application behavior remains unaffected
- Model classes become purely data classes and are no longer responsible for accepting user input
- Less/ single number of scanner class instance being used.

## Acceptance Criteria
- [ ] Model classes are no longer responsible for accepting user input
- [ ] View classes are responsible for accepting user input and validating it
