# Goal

The code base currently uses `System.out.println` for logging. The goal of this refactoring is to introduce a logger to replace the use of `System.out.println` for better logging practices.

## Current Implementation

The code base is scattered with `System.out.println` statements for logging purposes. This approach is not ideal for production-level applications as it lacks flexibility, configurability, and the ability to manage log levels.

## Desired Implementation

The code base should use a dedicated logger interface which can be implemented for different logging strategy. For example, we use `System.out.println` for console logging, but we can also implement a concrete logger which outputs to console for user interation. Similarly, we should have a dedicated file logger or a remote logger in the future without changing the existing code. 

# Behavior to preserve
- Existing application behavior remains unaffected
- Logging should be consistent and manageable through a logger interface

# Acceptance Criteria
- [ ] Replace all `System.out.println` statements with a logger interface
