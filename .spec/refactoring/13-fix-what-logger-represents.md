# Goal

There is ambiguity in what logger represents. It holds two responsibilities, displaying output from application on console screen and logging application internals.

# Current Implementation
Logger has `ConsoleLogger` which acts as interface to intract with user and also logs application details

## Desired Implementation

- Instead of `ConsoleView`, the class should be called `Console` and it should retain the existing functionality
- Instead of using logger, the presentation level classes should all use `Console` instead to display information from console/ screen
- `Logger` should only focus on logging information in a file/ not on screen
- Remove `ConsoleLogger` and replace it with a file logger which logs it in a `log.txt` with date and time as suffix so file is not overwritten.
- Logger should have info, debug, trace and error levels
- Each log statement must present be prefixed by the date-time and class which logged it.
- Replace all the business and data level logging with the file logger.

## Acceptance Criteria
- Remaining application behaviour is not impacted

