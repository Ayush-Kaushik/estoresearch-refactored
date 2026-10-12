package estore.presentation.command;

import java.util.HashMap;
import java.util.Map;

public enum Command {
    ADD(1, "add", "a"),
    SEARCH(2, "search", "s"),
    QUIT(3, "quit", "q"),
    METADATA(4, "metadata", "m");

    private final int index;
    private final String[] aliases;
    private static final Map<String, Command> COMMAND_TYPE_MAP = new HashMap<String, Command>();

    static {
        for (Command command : Command.values()) {
            COMMAND_TYPE_MAP.put(String.valueOf(command.index), command);
            for (String alias : command.aliases) {
                COMMAND_TYPE_MAP.put(alias, command);
            }
        }
    }

    Command(int index, String... aliases) {
        this.index = index;
        this.aliases = aliases;
    }

    public int getIndex() {
        return index;
    }

    public static String getOptions() {
        StringBuilder options = new StringBuilder();
        for (Command command : Command.values()) {
            options.append("(").append(command.index).append(")")
                    .append(command.name().charAt(0))
                    .append(command.name().substring(1).toLowerCase())
                    .append(" ");
        }
        return options.toString();
    }

    public static Command findByType(String type) {
        if (type == null) {
            return null;
        }
        return COMMAND_TYPE_MAP.get(type.toLowerCase().trim());
    }
}