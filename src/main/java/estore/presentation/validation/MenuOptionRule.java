package estore.presentation.validation;

import java.util.HashMap;
import java.util.Map;

public class MenuOptionRule extends Rule {
    // Set.of was introduced in a later Java version; switch back when the project version is updated.
    private final Map<String, Boolean> validSelections = new HashMap<>();

    public MenuOptionRule() {
        validSelections.put("1", true);
        validSelections.put("2", true);
        validSelections.put("3", true);
        validSelections.put("4", true);
        validSelections.put("add", true);
        validSelections.put("search", true);
        validSelections.put("quit", true);
        validSelections.put("metadata", true);
        validSelections.put("a", true);
        validSelections.put("s", true);
        validSelections.put("q", true);
        validSelections.put("m", true);
    }

    @Override
    public boolean validate(String input) {
        if (input == null) {
            return false;
        }

        String normalizedInput = input.toLowerCase().trim();
        return validSelections.containsKey(normalizedInput);
    }
}
