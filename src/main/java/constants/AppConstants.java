
package constants;

public final class AppConstants {
    
    // Prevent instantiation
    private AppConstants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    
    // Product validation constants
    public static final int PRODUCT_ID_LENGTH = 6;
    public static final int PRODUCT_YEAR_MIN = 1000;
    public static final int PRODUCT_YEAR_MAX = 9999;
    
    // Menu option constants
    public static final int MENU_OPTION_ADD = 1;
    public static final int MENU_OPTION_SEARCH = 2;
    public static final int MENU_OPTION_QUIT = 3;
    
    // Product type constants
    public static final int PRODUCT_TYPE_BOOK = 1;
    public static final int PRODUCT_TYPE_ELECTRONICS = 2;
    
    // Search year query constants
    public static final int YEAR_QUERY_YES = 1;
    public static final int YEAR_QUERY_NO = 2;
    public static final int YEAR_STRING_LENGTH_SINGLE = 4;
    public static final int YEAR_STRING_LENGTH_WITH_DASH = 5;
    public static final int YEAR_STRING_LENGTH_RANGE = 9;
    
    // Array indices for year parsing
    public static final int YEAR_VALUE_INDEX = 0;
    public static final int YEAR_TYPE_INDEX = 1;
    public static final int YEAR_END_VALUE_INDEX = 2;
    
    // Year query types
    public static final int YEAR_QUERY_TYPE_EXACT = 1;
    public static final int YEAR_QUERY_TYPE_LESS_THAN = 0;
    public static final int YEAR_QUERY_TYPE_GREATER_THAN = 4;
    public static final int YEAR_QUERY_TYPE_RANGE = 4;
}
