package estore.presentation.validation;

public class SearchProductYearRule extends Rule {
    @Override
    public boolean validate(String input) {
        return input != null && input.matches("(?:\\d{4}|-\\d{4}|\\d{4}-|\\d{4}-\\d{4})");
    }
}
