---
name: setup-test-infrastructue
description: Use this skill to evaluate existing test infrastructue and rectify issues with it.
license: MIT
user-invocable: true
---

# Setup Test Infrastructure

## Purpose

Establish or repair the application's test infrastructure according to
the requirements provided by the user.

The agent is responsible for determining the implementation details
required to satisfy those requirements.

Do not assume a particular testing framework, build configuration,
directory structure, or dependency version unless it already exists
or the user explicitly requires it.

## User Requirements

Before making changes, determine:

1. What test framework is required?
2. Where should tests live?
3. How should tests be executed?
4. What application behaviour should the tests exercise?
5. What existing test infrastructure must be preserved?

If the user has already provided these requirements, do not ask again.

## Procedure

### 1. Inspect

Inspect:

- TEST-INFRASTRUCTURE.md file for an understanding of the requested test infrastructure
- project structure
- build configuration
- existing tests
- existing test dependencies
- application entry points
- relevant production code
- relevant documentation

Determine the smallest change required to establish the requested
test infrastructure.

### 2. Plan

Before modifying files:

- identify missing dependencies
- identify missing configuration
- identify missing test directories/files
- identify compatibility concerns
- identify the command that should execute the tests

Do not redesign unrelated parts of the application.

### 3. Implement

Make the smallest changes necessary to establish the infrastructure.

Create one minimal test demonstrating that the infrastructure actually
works.

### 4. Verify

Run the project's test command.

For example:

    mvn test

Do not assume the command succeeded merely because the command exited
without an obvious configuration error. Inspect the test output and
verify that the intended test was actually discovered and executed.

### 5. Repair Loop

If the tests fail:

1. Read the failure.
2. Determine whether the failure is caused by:
   - test infrastructure
   - test configuration
   - dependency/version incompatibility
   - test implementation
   - production behaviour
3. Fix the smallest relevant problem.
4. Run the tests again.
5. Repeat until the requested infrastructure works.

Do not modify production behaviour simply to make infrastructure tests pass.

### 6. Completion

Stop only when:

- the test infrastructure initializes successfully
- the intended test is discovered
- the intended test executes
- the test passes
- existing tests continue to pass

Report:

- what was changed
- why it was changed
- test command executed
- final test result
