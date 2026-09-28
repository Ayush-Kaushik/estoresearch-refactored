# Goal

Introduce a test mode which is toggled by an environment variable. The test mode will append a delimeter to the end of the console output so that tests can capture the interactions well before assertion.

## Current Implemetation

There is no environment variable which controls the addition of delimeter.

## Desired Implementation

- Introduce an environment variable which controls the addition of delimeter.
- The delimeter must be console safe
- It is only appended to the console out when envionment variable is passed


## Acceptance Criteria
- The build does not fail
- A small unit test which validates that delimiter is indeed being appended when test mode is enabled
- The e2e tests do not pass so do not run them