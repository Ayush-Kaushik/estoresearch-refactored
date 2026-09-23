package estore.presentation;

import estore.presentation.validation.Rule;

/** Handles user-visible console interaction without coupling with logging internals. */
public interface IConsole {
    public String readInput();

    public String readValidatedInput(String prompt, Rule rule, String validationMessage);

    public void printMessage(String message);

    public void printPrompt(String prompt);
}
