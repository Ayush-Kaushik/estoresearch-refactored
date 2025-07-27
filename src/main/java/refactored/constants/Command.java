package refactored.constants;

import java.util.HashMap;

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

  public static Command findByType(int type) {
    return commandTypeMap.get(type);
  }
}