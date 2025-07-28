package refactored.view;

import java.util.Scanner;

import refactored.service.ProductService;

public class SearchProductOperationHandler implements IOperationHandler {

  private Scanner scanner;
  private ProductService productService;

  public SearchProductOperationHandler(ProductService productService) {
    this.scanner = new Scanner(System.in);
    this.productService = productService;
  }

  private String promptName() {
    System.out.println("Enter product name (or press Enter to skip):");
    return scanner.nextLine().trim();
  }

  public String promptYear() {

    return productYear;
  }

  public int[] breakYear(String productSearchYearQuery) {
    String substr1;
    String substr2;

    int[] val = new int[3];
    val[0] = 0;
    val[1] = 0;
    val[2] = 0;

    if (!productSearchYearQuery.isEmpty()) {
      if (!productSearchYearQuery.contains("-")) {
        substr1 = productSearchYearQuery.substring(0, 4);
        val[0] = Integer.parseInt(substr1);
        val[2] = 0;
        val[1] = 1;
      }

      if (productSearchYearQuery.contains("-")) {
        if (productSearchYearQuery.length() == 5) {

          // if - is placed at the beginnning. for example -2008
          if (productSearchYearQuery.indexOf('-') == 0) {
            substr1 = productSearchYearQuery.substring(1, 5);
            val[0] = Integer.parseInt(substr1);
            val[2] = 0;
            val[1] = 0;
          }

          // if - placed at the end of the string
          if (productSearchYearQuery.indexOf('-') == 4) {
            substr1 = productSearchYearQuery.substring(0, 4);
            val[0] = Integer.parseInt(substr1);
            val[1] = 4;
            val[2] = 0;
          }
        }

        if (productSearchYearQuery.length() == 9) {
          substr1 = productSearchYearQuery.substring(0, 4);
          substr2 = productSearchYearQuery.substring(5, 9);
          val[0] = Integer.parseInt(substr1);
          val[2] = Integer.parseInt(substr2);
          val[1] = 4;
        }
      }

      else {
        val[0] = Integer.parseInt(productSearchYearQuery);
      }

    }
    return val;
  }

  public void execute() {

  }
}

public class YearRangeParser {
  public static YearRange parse(String input) {
    if (input == null || input.trim().isEmpty()) {
      return new YearRange(YearRangeMode.EMPTY, 0, 0);
    }

    input = input.trim();
    if (input.matches("^\\d{4}$")) {
      return new YearRange(YearRangeMode.EXACT, Integer.parseInt(input), 0);
    }

    if (input.matches("^\\d{4}-$")) {
      return new YearRange(YearRangeMode.FROM, Integer.parseInt(input.substring(0, 4)), 0);
    }

    if (input.matches("^-\\d{4}$")) {
      return new YearRange(YearRangeMode.UNTIL, 0, Integer.parseInt(input.substring(1)));
    }

    if (input.matches("^\\d{4}-\\d{4}$")) {
      String[] parts = input.split("-");
      return new YearRange(
          YearRangeMode.RANGE,
          Integer.parseInt(parts[0]),
          Integer.parseInt(parts[1]));
    }

    throw new IllegalArgumentException("Invalid year format: " + input);
  }
}