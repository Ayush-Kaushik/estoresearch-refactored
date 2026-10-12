package estore.presentation.validation;

public class AddProductYearRule extends Rule {
    @Override
    public boolean validate(String input) {
        return input != null && input.matches("\\d{4}");
    }
}
