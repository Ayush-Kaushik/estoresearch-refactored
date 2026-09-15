package estore.presentation;

import java.util.Scanner;

import estore.presentation.validation.Rule;

/**
 * Presentation-layer entry point for console interaction.
 *
 * This class intentionally contains only user-interface concerns. The actual
 * product logic remains in the business layer.
 */
public class ConsoleView {
    private final Scanner scanner;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
    }

    public String readInput() {
        return scanner.nextLine();
    }

    public String readValidatedInput(String prompt, Rule rule, String validationMessage) {
        while (true) {
            printPrompt(prompt);
            String input = readInput();
            if (rule.validate(input)) {
                return input;
            }
            printMessage(validationMessage);
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printPrompt(String prompt) {
        System.out.print(prompt);
    }
}
