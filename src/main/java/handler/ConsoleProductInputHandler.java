package handler;

import java.util.ArrayList;
import java.util.Scanner;

import constants.AppConstants;
import constants.ProductType;
import io.ProductDataExporter;
import model.Book;
import model.Electronics;
import model.Product;
import service.ProductService;

public class ConsoleProductInputHandler implements IProductInputHandler {

    private ProductService productService;
    private ProductDataExporter ProductDataExporter;
    private Scanner scanner;

    public ConsoleProductInputHandler(ProductService productService, ProductDataExporter productDataExporter) {
        this.scanner = new Scanner(System.in);
        this.productService = productService;
        this.ProductDataExporter = productDataExporter;
    }

    private String getProductId() {
        while (true) {
            try {
                System.out.println("Enter product id (6 digits):");
                String productId = scanner.nextLine();
                Product.validateID(productId);

                if (this.productService.productExists(productId)) {
                    System.out.println("Product with id " + productId + " already exists.");
                    continue;
                }

                return productId;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getProductYear() {
        while (true) {
            try {
                System.out.println("Enter product year: ");
                String input = scanner.nextLine();
                int year = Integer.parseInt(input);

                Product.validateYear(year);
                return year;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String getProductName() {
        while (true) {
            try {
                System.out.println("Enter product name: ");
                String name = scanner.nextLine();

                Product.validateName(name);
                return name;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public double getProductPrice() {
        while (true) {
            try {
                System.out.println("Enter the price of item: ");

                String inputPrice = scanner.nextLine();
                double price = Product.validatePrice(inputPrice);
                return price;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getElectronicsMaker() {

        while (true) {
            try {
                System.out.println("Enter electronics maker: ");
                String maker = this.scanner.nextLine();
                Electronics.validateMaker(maker);
                return maker;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getBookAuthor() {

        while (true) {
            try {
                System.out.println("Enter book author: ");
                String author = this.scanner.nextLine();
                Book.validateAuthor(author);
                return author;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public String getBookPublisher() {

        while (true) {
            try {
                System.out.println("Enter book publisher: ");
                String publisher = this.scanner.nextLine();
                Book.validatePublisher(publisher);
                return publisher;

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void addProduct() {
        int option = 0;

        while (true) {
            System.out.println("Enter product type " + ProductType.getProductTypes());

            try {
                String selection = scanner.nextLine();
                option = Integer.parseInt(selection);

                if (option == ProductType.BOOK || option == ProductType.ELECTRONICS) {
                    break;
                } else {
                    System.out.println("Invalid option, Try again.");
                    continue;
                }

            } catch (NumberFormatException exception) {
                System.out.println("Invalid option, Try again.");
                continue;
            }
        }

        String productID = getProductId();
        String name = getProductName();
        int year = getProductYear();
        double price = getProductPrice();

        switch (option) {
            case 1:
                String author = getBookAuthor();
                String publisher = getBookPublisher();
                Product book = new Book(productID, name, price, year, author, publisher);
                this.productService.addProduct(book);
                break;

            case 2:
                String maker = getElectronicsMaker();
                Product electronic = new Electronics(productID, name, price, year, maker);
                this.productService.addProduct(electronic);
                break;
        }
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

    public void search() {
        System.out.println("Build search query: ");

        String name = getProductName();

        // the year is supposed to be like this: 2001-2008, -2001. 2001-
        String year = getSearchYearQuery();
        int[] values = breakYear(year);

        if (!name.isEmpty()) {
            String[] searchTokens = name.toLowerCase().split(" ");

            for (String searchToken : searchTokens) {
                ArrayList<Integer> returnedList = this.productService.getProductIndexes(searchToken);
            }
        }

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

    private int getCommand() {
        while (true) {
            System.out.println("Enter command: (1) Add (2) Search (3) Quit");

            try {
                String selection = scanner.nextLine();
                selection = selection.toLowerCase().trim();

                int command = Integer.parseInt(selection);

                if (command >= 1 && command <= 3) {
                    return command;
                } else {
                    System.out.println("Invalid command");
                    continue;
                }

            } catch (NumberFormatException exception) {
                System.out.println("Invalid command");
                continue;
            }

        }
    }

    public void start(String[] arguments) {
        System.out.println("\n-------Welcome to EStoreSearch --------");

        while (true) {
            int command = getCommand();

            switch (command) {
                case AppConstants.MENU_OPTION_ADD:
                    this.addProduct();
                    break;

                case AppConstants.MENU_OPTION_SEARCH:
                    this.search();
                    break;

                case AppConstants.MENU_OPTION_QUIT:
                    this.ProductDataExporter.export(arguments[0]);
                    System.exit(0);
                    break;
            }
        }
    }
}