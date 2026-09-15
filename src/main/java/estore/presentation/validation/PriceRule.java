package estore.presentation.validation;

public class PriceRule extends Rule {
    @Override
    public boolean validate(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        try {
            return Double.parseDouble(input) > 0;
        } catch (NumberFormatException exception) {
            return false;
        }
    }
}