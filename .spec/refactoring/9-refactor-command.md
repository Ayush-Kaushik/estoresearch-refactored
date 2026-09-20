# Goal

Refactor the command receiving and execution switch statement into a command and strategy pattern.


# Current Implementation

The current implementation uses the switch statement to determine which command has to be executed.

# Desired Implementation

The desired implementation should use command and strategy pattern like this (Follow these snippets, no need to look into code base since it doesn't have this implementation):

```java
public enum Command {
  ADD(1),
  SEARCH(2),
  QUIT(3),
  VIEW(4);

  private final int index;

  Command(int value) {
    this.index = value;
  }

  public int getIndex() {
    return this.index;
  }

  private static String commandTypes = "";
  private static final HashMap<Integer, Command> commandTypeMap = new HashMap<Integer, Command>();

  static {

    StringBuilder sb = new StringBuilder();

    for (Command command : Command.values()) {
      commandTypeMap.put(command.getIndex(), command);
      sb.append("[" + command.getIndex() + "] " + command.name()).append(" ");
    }

    commandTypes = sb.toString();
  }

  public static String getOptions() {
    return commandTypes;
  }

  public static Command findByType(string type) {

    // use common hashmap which maps the command type to the enum command

    return commandTypeMap.get(type);
  }
}
```


Then have and IOperationHandler interface which has method execute() and then have different classes implement this interface for each command type. 

Then in the main class, we can have a map of command to operation handler and then execute the command based on the user input.

# Behavior to preserve
- Existing application behavior remains unaffected

## Acceptance Criteria
- [ ] Refactor works without errors when building source code
- [ ] No need to run tests, I will run those myself



