package estore.presentation.validation;

public class ProductIDRule extends Rule {
    @Override
    public boolean validate(String input) {
        return input != null && input.length() == 6 && input.matches("[0-9]+");
    }
}