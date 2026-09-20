# Test Infrastructure Architecture

The test infrastructure is divided into three test levels:

```text
test/
├── unit/
│   └── Unit tests
│
├── integration/
│   └── Integration tests
│
└── e2e/
    ├── features/
    │   ├── <feature-a>.feature
    │   ├── <feature-b>.feature
    │   └── ...
    │
    └── Cucumber test infrastructure
```

Each test level has a distinct purpose and boundary.

## Unit Tests

Unit tests verify individual components in isolation.

A unit test should focus on the behaviour of a single component without requiring unrelated components or external application infrastructure.

Unit tests should be organized under:

```text
test/
└── unit/
    └── ...
```

The concrete testing framework, test organization, and isolation mechanism are left to the implementation.

## Integration Tests

Integration tests verify that multiple components work correctly together.

An integration test may exercise several application modules or layers in order to verify their interactions.

Integration tests should be organized under:

```text
test/
└── integration/
    └── ...
```

Integration tests are distinct from end-to-end tests because they test a selected integration boundary rather than the complete application through its external interface.

The concrete testing framework, test organization, and integration boundaries are left to the implementation.

## End-to-End Tests

End-to-end tests verify the complete application through its externally observable interface.

The EStore console application is treated as an external application under test.

The end-to-end test infrastructure uses specification-driven testing.

### Specification-driven testing

Gherkin feature files are the specification for end-to-end test behaviour.

Each feature file represents one cohesive application feature.

A feature file contains scenarios that exercise different behaviours of that feature.

The implementation of the test infrastructure must not become the source of truth for expected behaviour.

Expected behaviour belongs in the Gherkin scenarios.

### Feature organization

End-to-end tests should be organized as:

```text
test/
└── e2e/
    └── features/
        ├── <feature-a>.feature
        ├── <feature-b>.feature
        └── ...
```

Each feature file should contain scenarios belonging to the same application feature.

### Lifecycle management

Provide a common lifecycle mechanism for Cucumber tests.

The lifecycle mechanism must support:

* setup before each scenario
* cleanup after each scenario
* setup before a test-suite execution
* cleanup after a test-suite execution

Lifecycle behaviour that is common across features must not be duplicated inside individual step-definition classes.

### Console application lifecycle

The console application is treated as an external application under test.

At the beginning of a test-suite execution:

1. Create/start the console application environment.

At the end of the test-suite execution:

1. Terminate the console application environment.
2. Remove/clean up its state.

When a new test-suite execution begins, the application environment must be recreated rather than reusing state from the previous suite.

### Scenario isolation

Each scenario must be independently executable.

Scenario state must not leak into another scenario.

Scenario-specific setup and cleanup belongs in the common lifecycle mechanism where appropriate.

### Shared behaviour

Common interactions with the console application should be implemented as reusable Cucumber steps.

Do not duplicate console execution, input handling, output capture, or lifecycle management across feature-specific step definitions.

### E2E application boundary

End-to-end tests must interact with the application through its externally observable interface.

The tests should not depend on the application's internal class structure, implementation details, or internal execution paths.

The application may be internally refactored without requiring changes to an E2E test, provided its externally observable behaviour remains unchanged.

### Implementation freedom

The agent may determine the concrete Java, Cucumber, JUnit, and Maven implementation required to satisfy these requirements.

Do not prescribe implementation details unless required to satisfy the architecture above.
