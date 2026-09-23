package estore.presentation;

import java.util.Scanner;

import estore.presentation.validation.Rule;

/** Handles user-visible console interaction without coupling with logging internals. */
public class Console implements IConsole {
    private final Scanner scanner;

    public Console() {
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
