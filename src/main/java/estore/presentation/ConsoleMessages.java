package estore.presentation;

public final class ConsoleMessages {
    public static final String WELCOME_MESSAGE = "------- Welcome to E-Search --------";
    public static final String INVALID_OPTION_SELECTED = "*** Invalid option selected ***";
    public static final String INVALID_OPTION = "*** Invalid option ***";
    public static final String CATALOG_METADATA = "Catalog metadata";
    public static final String TOTAL_PRODUCTS = "Total products: ";
    public static final String BOOKS = "Books: ";
    public static final String ELECTRONICS = "Electronics: ";
    public static final String SEARCH_SPECIFICATIONS = "Enter the specs and find the matching product";
    public static final String ENTER_ID_OPTION = "Do you wish to enter the Product ID: (1)Yes (2)No";
    public static final String ENTER_NAME_OPTION = "Do you wish to enter Product name: (1)Yes (2)No";
    public static final String ENTER_YEAR_OPTION = "Do you wish to enter Product year: (1)Yes (2)No";
    public static final String WRONG_INPUT = "Wrong input";
    public static final String WRONG_INPUT_MESSAGE = "Wrong input.Please try again";
    public static final String HORIZONTAL_LINE = "--------------------------------------";
    public static final String TEST_MODE_ENV_VAR = "ESEARCH_TEST_MODE";
    public static final String TEST_MODE_DELIMITER = "<<<END_OF_OUTPUT>>>";

    private ConsoleMessages() {
        // Utility class
    }

    public static boolean isTestModeEnabled() {
        return isEnabled(System.getenv(TEST_MODE_ENV_VAR));
    }

    private static boolean isEnabled(String value) {
        return value != null && ("1".equals(value) || "true".equalsIgnoreCase(value)
                || "yes".equalsIgnoreCase(value) || "on".equalsIgnoreCase(value));
    }
}
