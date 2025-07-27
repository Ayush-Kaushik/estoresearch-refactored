package refactored.view;

import java.util.Scanner;

import refactored.service.IProductService;

public class SearchProductOperationHandler implements IOperationHandler {

  private Scanner scanner;
  private IProductService productService;

  public SearchProductOperationHandler(IProductService productService) {
    this.scanner = new Scanner(System.in);
    this.productService = productService;
  }

  private String getProductName() {
    System.out.println("Enter product name: ");
    String name = scanner.nextLine();
    return name;
  }

  // NOTE: Look at this method again and fix it, the loop logic is no
  public String getSearchYearQuery() {
    String productYear = "";

    System.out.println("Do you wish to enter product year:\n(1)Yes (2)No");

    int option;

    do {
      while (!scanner.hasNextInt()) {
        scanner.next();
        System.out.print("Please enter one of the options: ");
      }

      option = scanner.nextInt();
      scanner.nextLine();

      if (option == 1) {
        do {
          System.out.println("Enter the product year: ");
          productYear = scanner.nextLine();

          if (!productYear.isEmpty() && !productYear.matches("[a-zA-Z]+")) {
            break;
          }
        } while (productYear.isEmpty() == true && productYear.matches("[a-zA-Z]+") == true);
      }

      if (option == 2) {
        productYear = "";
        break;
      }

      else {
        System.out.println("Wrong input");
      }

    } while (option != 1 || option != 2);

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
    System.out.println("[Search Product] Build search query: ");

    // the search happens based on three criteria: ID, name and year
    // first ask user to enter ID - they can leave it blank if they dont want to
    // search
    // then ask them for name - they can leave it blank if they dont want to search
    // then ask them for year - they can leave it blank if they dont want to search
    // if all three are blank, then print all the items in the list

    // use builder pattern to build search query
    // use strategy pattern to execute search query

    // QueryBuilder addName
    // QueryBuilder addID
    // QueryBuilder addYear
    // build
    // returns a query object

    // now pass the query object to the search service
    // search service will execute the query and return the results

    String name = getProductName();

    // the year is supposed to be like this: 2001-2008, -2001. 2001-
    String year = getSearchYearQuery();
    int[] values = breakYear(year);

    // if (!name.isEmpty()) {
    // String[] searchTokens = name.toLowerCase().split(" ");

    // for (String searchToken : searchTokens) {
    // ArrayList<Integer> returnedList =
    // this.productService.getProductIndexes(searchToken);
    // }
    // }

    // for (Integer item : returnedList) {
    // if (ID.equals(productList.get(p).getID()) || ID.isEmpty()) {
    // if (year.isEmpty()
    // || (year.length() == 5 && productList.get(p).getYear() <= values[0] &&
    // values[1] == 0)
    // || (year.length() == 5 && productList.get(p).getYear() >= values[0] &&
    // values[1] == 4)
    // || (year.length() == 9 && productList.get(p).getYear() >= values[0]
    // && productList.get(p).getYear() <= values[2] && values[1] == 4)
    // || (year.length() == 4 && productList.get(p).getYear() == values[0] &&
    // values[1] == 1)) {
    // System.out.println(productList.get(p).datadump());
    // }
    // }
    // }

  }
}