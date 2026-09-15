package estore.presentation.validation;

public class NameRule extends Rule {
    @Override
    public boolean validate(String input) {
        return input != null && !input.isEmpty();
    }
}