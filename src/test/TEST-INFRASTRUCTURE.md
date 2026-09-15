## Test Infrastructure Architecture

The test infrastructure must follow these principles.

### Specification-driven testing

Gherkin feature files are the specification for test behaviour.

Each feature file represents one cohesive application feature.

A feature file contains scenarios that exercise different behaviours
of that feature.

The implementation of the test infrastructure must not become the
source of truth for expected behaviour. Expected behaviour belongs
in the Gherkin scenarios.

### Feature organization

Tests should be organized as:

test/
└── features/
    ├── <feature-a>.feature
    ├── <feature-b>.feature
    └── ...

Each feature file should contain scenarios belonging to the same
application feature.

### Lifecycle management

Provide a common lifecycle mechanism for Cucumber tests.

The lifecycle mechanism must support:

- setup before each scenario
- cleanup after each scenario
- setup before a test-suite execution
- cleanup after a test-suite execution

Lifecycle behaviour that is common across features must not be
duplicated inside individual step-definition classes.

### Console application lifecycle

The console application is treated as an external application under
test.

At the beginning of a test-suite execution:

1. Create/start the console application environment.

At the end of the test-suite execution:

1. Terminate the console application environment.
2. Remove/clean up its state.

When a new test-suite execution begins, the application environment
must be recreated rather than reusing state from the previous suite.

### Scenario isolation

Each scenario must be independently executable.

Scenario state must not leak into another scenario.

Scenario-specific setup and cleanup belongs in the common lifecycle
mechanism where appropriate.

### Shared behaviour

Common interactions with the console application should be
implemented as reusable Cucumber steps.

Do not duplicate console execution, input handling, output capture,
or lifecycle management across feature-specific step definitions.

### Implementation freedom

The agent may determine the concrete Java, Cucumber, JUnit, and Maven
implementation required to satisfy these requirements.

Do not prescribe implementation details unless required to satisfy
the architecture above.