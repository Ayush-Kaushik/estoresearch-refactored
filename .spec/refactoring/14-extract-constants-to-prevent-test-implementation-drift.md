# Goal

The `presentation` layer is scattered with console prompts and messages. The same messages and prompts are duplicated in the `e2e` tests. The goal is to have two classes called `ConsolePrompts` and `ConsoleMessages` which consists of these constants which is shared by both tests and application. This prevents test/implementation drift when a message/ prompt is changed.

## Current Implementation

`presentation` layer comprises of scatted console prompts and messages

## Desired Implementation

consolidate all the messages and prompts in `ConsoleMessages` and `ConsolePrompts` class respectively inside `presentation` layer. Make both application and tests use the same constants.

## Acceptance Criteria

- Behaviour shouldn't be affected