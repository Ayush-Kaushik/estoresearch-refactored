package estore;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import estore.business.ProductAdditionService;
import estore.business.IProductService;
import estore.business.ProductService;
import estore.dataaccess.Book;
import estore.dataaccess.Electronics;
import estore.dataaccess.InMemoryProductCatalogRepository;
import estore.dataaccess.Product;
import estore.presentation.ProductAddView;
import estore.presentation.ConsoleView;
import estore.presentation.validation.NameRule;
import estore.presentation.validation.ProductIDRule;

public class EStoreSearch {

    public static HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    public static ArrayList<Product> productList = new ArrayList<Product>();
    private static final InMemoryProductCatalogRepository catalogRepository = new InMemoryProductCatalogRepository(
            productList, map);
    private static final IProductService productService = new ProductService(catalogRepository);
    private static final ConsoleView consoleView = new ConsoleView();

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Please provide the catalog file name as a command line argument.");
            System.exit(0);
        }

        readCatalog(args);
        runProgram(args);
    }

    public static void readCatalog(String[] args) {
        try {
            CatalogFileReader catalogFileReader = new CatalogFileReader(productList, map);
            catalogFileReader.read(args[0]);
        } catch (FileNotFoundException e) {
            System.out.println("Problem opening file: " + args[0]);
            System.exit(0);
        }
    }

    public static void runProgram(String[] args) {
        String option = "-1";

        while (!option.equals("3")) {
            boolean done = false;
            while (!done) {
                try {
                    System.out.println("\n-------Welcome to E-Search --------");
                    System.out.print(
                            "Select the numbers or type command corresponding to the options below: (1)Add (2)Search (3)Quit (4)View Metadata\n");
                    option = consoleView.readInput();
                    option = option.toLowerCase().trim();

                    if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("4")
                            || option.equals("metadata") || option.equals("m") || option.equals("q")
                            || option.equals("quit") || option.equals("add") || option.equals("search")
                            || option.equals("a") || option.equals("s")) {
                        done = true;
                    }

                    else {
                        done = false;
                        throw new Exception("*** enter only numbers or commands ***");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }

            switch (option) {
                case "1":
                case "add":
                case "a":
                    System.out.println("Selected option: Add\n");
                    ProductAdditionService productAdditionService = new ProductAdditionService(productService);
                    ProductAddView productAddView = new ProductAddView(
                        productAdditionService, 
                        productList,
                        consoleView);

                    productAddView.add();
                    break;

                case "2":
                case "search":
                case "s":
                    System.out.println("Selected option: Search\n");
                    search(productList, map);
                    break;

                case "4":
                case "metadata":
                case "m":
                    System.out.println("Selected option: View Metadata\n");
                    printMetadata(productList);
                    break;

                case "3":
                case "q":
                case "quit":
                    System.out.println("Selected option Quit\n");
                    CatalogFileWriter catalogFileWriter = new CatalogFileWriter(productList);
                    catalogFileWriter.write();
                    System.exit(0);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * Displays summary information about the products currently in the catalog.
     * The catalog is kept as a list of polymorphic Product instances, so the
     * concrete type can be counted without changing the product model classes.
     *
     * @param productList the in-memory catalog
     */
    public static void printMetadata(ArrayList<Product> productList) {
        if (productList.isEmpty()) {
            System.out.println("Catalog is empty");
            return;
        }

        int bookCount = 0;
        int electronicsCount = 0;
        for (Product product : productList) {
            if (product instanceof Book) {
                bookCount++;
            } else if (product instanceof Electronics) {
                electronicsCount++;
            }
        }

        System.out.println("Catalog metadata");
        System.out.println("Total products: " + productList.size());
        System.out.println("Books: " + bookCount);
        System.out.println("Electronics: " + electronicsCount);
    }

    /**
     * @param productList - The array list that consists of book and electronic
     *                    entries is used to search and return an item based on user
     *                    values
     * @param map         - The hash-map created and updated previously is used here
     *                    for searching the right name based on the index where they
     *                    appear on the array list.
     *                    This method performs the search operation using
     *                    combination of Array list productList and Hash-map map to
     *                    return the right entry based on user input.
     *                    methods called = search_ID(), search_name(), spaceCount(),
     */
    public static void search(ArrayList<Product> productList, HashMap map) {

        System.out.println("Enter the specs and find the matching product");

        String ID = search_ID();
        String name = search_Name();
        String year = search_Year();

        int values[] = new int[3];

        values = breakYear(year);

        // This method calls the breakYear method which splits the user input to filter
        // right values from the list

        boolean validSpace = spaceCount(name);

        // Set set = map.entrySet();
        // System.out.println("Set values: " + set);

        // This list stores the indexes returned from the list and comapres them to
        // other returned lists using retain all
        ArrayList<Integer> returnedList = new ArrayList<>();

        if (name.isEmpty() == false)
            ;
        {

            if (validSpace == true) {
                String[] searchTokens = name.toLowerCase().split(" ");
                for (int j = 0; j < searchTokens.length; j++) {
                    if (map.containsKey(searchTokens[j])) {
                        returnedList = (ArrayList<Integer>) map.get(searchTokens[j]);
                        returnedList.retainAll((ArrayList<Integer>) map.get(searchTokens[j]));
                    }
                }
            }

            if (validSpace == false) {
                if (map.containsKey(name.toLowerCase())) {
                    returnedList = (ArrayList<Integer>) map.get(name.toLowerCase());
                }
            }

            for (Integer p : returnedList) {
                if (ID.equals(productList.get(p).getID()) || ID.isEmpty()) {
                    if (year.isEmpty()
                            || (year.length() == 5 && productList.get(p).getYear() <= values[0] && values[1] == 0)
                            || (year.length() == 5 && productList.get(p).getYear() >= values[0] && values[1] == 4)
                            || (year.length() == 9 && productList.get(p).getYear() >= values[0]
                                    && productList.get(p).getYear() <= values[2] && values[1] == 4)
                            || (year.length() == 4 && productList.get(p).getYear() == values[0] && values[1] == 1)) {
                        System.out.println(productList.get(p).datadump());
                    }
                }
            }
        }

        if (name.isEmpty() == true) {

            for (int i = 0; i < productList.size(); i++) {
                if (ID.equals(productList.get(i).getID()) || ID.isEmpty()) {
                    if (year.isEmpty()
                            || (year.length() == 5 && productList.get(i).getYear() <= values[0] && values[1] == 0)
                            || (year.length() == 5 && productList.get(i).getYear() >= values[0] && values[1] == 4)
                            || (year.length() == 9 && productList.get(i).getYear() >= values[0]
                                    && productList.get(i).getYear() <= values[2] && values[1] == 4)
                            || (year.length() == 4 && productList.get(i).getYear() == values[0] && values[1] == 1)) {
                        System.out.println(productList.get(i).datadump());
                    }

                }
            }
        }

    }

    /**
     * @param name - This method simply checks if user input one word name or it
     *             consists of more than one word.
     * @return validSpace - If one name then vallidSpace returns false else true
     */
    public static boolean spaceCount(String name) {
        boolean validSpace = false;
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (ch == ' ')
                validSpace = true;
        }
        return validSpace;
    }

    public static String search_ID() {
        System.out.println("Do you wish to enter the product ID: (1) Yes (2) No ");
        int option = readIntegerOption();
        String product_ID = "";

        if (option == 1) {
            product_ID = consoleView.readValidatedInput(
                    "Enter the product ID: \n",
                    new ProductIDRule(),
                    "Wrong input.Please try again");
        } else {
            product_ID = "";
        }

        return product_ID;
    }

    public static String search_Name() {
        System.out.println("Do you wish to enter product name: (1)Yes (2)No");
        int option = readIntegerOption();
        String product_name = "";

        if (option == 1) {
            product_name = consoleView.readValidatedInput(
                    "Enter the product name: \n",
                    new NameRule(),
                    "Wrong input.Please try again");
        } else {
            product_name = "";
        }

        return product_name;
    }

    /**
     * @return product_year - The method simply gets valid value for year from user
     *         and returns it
     *         I used this method straight from my assignment - 1 without
     *         modifications
     */
    public static String search_Year() {
        String product_year = "";
        System.out.println("Do you wish to enter product year:\n(1)Yes (2)No");

        int option;

        outerloop: do {
            option = readIntegerOption();

            if (option == 1) {
                do {

                    System.out.println("Enter the product year: ");
                    product_year = consoleView.readInput();

                    if ((product_year.isEmpty() == false) && (product_year.matches("[a-zA-Z]+") == false)) {
                        break outerloop;
                    }

                } while (product_year.isEmpty() == true && product_year.matches("[a-zA-Z]+") == true);

            }

            if (option == 2) {
                product_year = "";
                break;
            }

            else {
                System.out.println("Wrong input");
            }

        } while (option != 1 || option != 2);

        return product_year;

    }

    private static int readIntegerOption() {
        while (true) {
            try {
                return Integer.parseInt(consoleView.readInput().trim());
            } catch (NumberFormatException exception) {
                System.out.print("Please enter one of the options: ");
            }
        }
    }

    /**
     * @param product_year
     * @return val - array of years in case user has input: 2001 - 2008, -2001.
     *         2001-
     *         I used this method straight from my assignment - 1 without
     *         modifications
     */
    public static int[] breakYear(String product_year) {

        String substr1;
        String substr2;

        int[] val = new int[3];
        val[0] = 0;
        val[1] = 0;
        val[2] = 0;

        if (product_year.isEmpty() == false) {
            if (product_year.contains("-") == false) {
                substr1 = product_year.substring(0, 4);
                val[0] = Integer.parseInt(substr1);
                val[2] = 0;
                val[1] = 1;// No - sign
                // System.out.println("year without - sign: " + substr1);
            }

            if (product_year.contains("-")) {
                if (product_year.length() == 5) {

                    if (product_year.indexOf('-') == 0) // if - is placed at the beginnning. for example -2008
                    {
                        substr1 = product_year.substring(1, 5);
                        val[0] = Integer.parseInt(substr1);
                        val[2] = 0;
                        val[1] = 0; // - found at index 0
                        // System.out.println(substr1);
                    }

                    if (product_year.indexOf('-') == 4) // if - placed at the end of the string
                    {
                        substr1 = product_year.substring(0, 4);
                        // what about val[1]
                        val[0] = Integer.parseInt(substr1);
                        val[1] = 4; // - found at index 4
                        val[2] = 0;
                        // System.out.println(substr1);
                    }
                }

                if (product_year.length() == 9) {
                    substr1 = product_year.substring(0, 4);
                    substr2 = product_year.substring(5, 9);
                    val[0] = Integer.parseInt(substr1);
                    val[2] = Integer.parseInt(substr2);
                    val[1] = 4;
                }
            }

            else {
                val[0] = Integer.parseInt(product_year);
            }

        }
        return val;
    }
}
