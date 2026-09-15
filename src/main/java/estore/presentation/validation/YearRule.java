package estore.presentation.validation;

public class YearRule extends Rule {
    @Override
    public boolean validate(String input) {
        return input != null && input.matches("[0-9]{4}");
    }
}