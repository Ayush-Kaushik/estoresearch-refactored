# Goal

Add file based logging to the key components of the `business`, `presentation` and `data` layers.

## Current Implementation

There is no clear logging present in the console which tells if the program is running smoothly.

## Desired Implementation

- Add logging to all the critical workflows of the program
- Make sure the logger itself is running on a separate thread so that it is not blocked by the main thread
- When the program exits, the worker thread related to logging is also released

## Acceptance Criteria

- The existing behaviour of the application is not impacted
- The existing tests do not fail