package estore.presentation;

import java.util.Scanner;

import estore.logging.ConsoleLogger;
import estore.logging.ILogger;
import estore.presentation.validation.Rule;

/**
 * Presentation-layer entry point for console interaction.
 *
 * This class intentionally contains only user-interface concerns. The actual
 * product logic remains in the business layer.
 */
public class ConsoleView {
    private final Scanner scanner;
    private final ILogger logger;

    public ConsoleView() {
        this(new ConsoleLogger());
    }

    public ConsoleView(ILogger logger) {
        this.scanner = new Scanner(System.in);
        this.logger = logger;
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
        logger.log(message);
    }

    public void printPrompt(String prompt) {
        logger.print(prompt);
    }
}
