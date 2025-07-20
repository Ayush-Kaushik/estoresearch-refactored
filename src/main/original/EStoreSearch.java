package main.original;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class EStoreSearch {

    public static HashMap<String, ArrayList<Integer>> map = new HashMap<>();
    public static ArrayList<Product> productList = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 1) {
            fileRead(args);
        } else {
            System.out.println("Not enough arguments");
            System.exit(0);
        }
    }

    /**
     * @param args - This method accepts the command line argument i.e the file
     *             name. Also, this method will read the file and add it to the
     *             array list.
     */

    public static void fileRead(String[] args) {
        Scanner inputStream = null;
        try {
            HashMap<String, String> inputMap = new HashMap<>();

            inputStream = new Scanner(new FileInputStream(args[0]));

            String line = null;

            while (inputStream.hasNextLine()) {
                line = inputStream.nextLine();
                // System.out.println("The output from file read is:" + line);

                String[] values = line.split("=");
                if (values.length == 2) {

                    // This block of code trims the quotations around the value
                    String key = values[0].trim();
                    char[] array = values[1].trim().toCharArray();
                    array[0] = ' ';
                    array[array.length - 1] = ' ';
                    String value = new String(array).trim();

                    // System.out.println("Key: " + key + " Value: " + value);

                    inputMap.put(key, value); // adding the value to the map
                }

                else {
                    String type = inputMap.get("type");
                    String ID = inputMap.get("productID");
                    String name = inputMap.get("name");
                    String price = inputMap.get("price");
                    String year = inputMap.get("year");

                    if (type.equals("book")) {
                        String author = inputMap.get("authors");

                        if (author == null) {
                            author = "";
                        }

                        String publisher = inputMap.get("publisher");

                        if (publisher == null) {
                            publisher = "";
                        }

                        Book book = new Book(ID, name, Double.parseDouble(price), Integer.parseInt(year), author,
                                publisher);
                        productList.add(book);
                        inputMap.clear();

                        // ----- Checking for the index of the value added -------
                        int number = returnIndex(book, productList);
                        // System.out.println("Index of the value added in array list: " + number);

                        // This is the method for adding values as mentioned in the project specs -->
                        // the values are index of the items in array list that contains the same
                        // keyword in their names
                        boolean validSpace = spaceCount(name);
                        if (validSpace == true) {
                            String[] tokens = name.toLowerCase().split(" "); // adding the keys in lowercase so it can
                                                                             // find java and JAVA
                            for (String token : tokens) {
                                if (map.containsKey(token)) {
                                    map.get(token).add(number);
                                } else {
                                    map.put(token.toLowerCase(), new ArrayList<>()); // key and new array of value
                                    map.get(token.toLowerCase()).add(number);
                                }
                            }
                        } else {
                            if (map.containsKey(name.toLowerCase()))
                                map.get(name.toLowerCase()).add(number);

                            else {
                                map.put(name.toLowerCase(), new ArrayList<>());
                                map.get(name).add(number);
                            }
                        }

                    }

                    if (type.equals("electronics")) {
                        String maker = inputMap.get("maker");

                        if (maker == null) {
                            maker = "";
                        }

                        Electronics electronic = new Electronics(ID, name, Double.parseDouble(price),
                                Integer.parseInt(year), maker);
                        productList.add(electronic);
                        inputMap.clear();

                        // ----- Checking for the index of the value added -------
                        int number = returnIndex(electronic, productList);
                        // System.out.println("Index of the value added in array list: " + number);

                        // This is the method for adding values as mentioned in the project specs -->
                        // the values are index of the items in array list that contains the same
                        // keyword in their names
                        boolean validSpace = spaceCount(name);

                        if (validSpace == true) {
                            String[] tokens = name.toLowerCase().split(" "); // adding the keys in lowercase so it can
                                                                             // find java and JAVA
                            for (String token : tokens) {
                                if (map.containsKey(token)) {
                                    map.get(token).add(number);
                                } else {
                                    map.put(token.toLowerCase(), new ArrayList<>()); // key and new array of value
                                    map.get(token.toLowerCase()).add(number);
                                }
                            }
                        } else {
                            if (map.containsKey(name.toLowerCase()))
                                map.get(name.toLowerCase()).add(number);

                            else {
                                map.put(name.toLowerCase(), new ArrayList<>());
                                map.get(name).add(number);
                            }
                        }

                    }

                }

            }

        } catch (FileNotFoundException e) {
            System.out.println("Problem opening files");
            System.exit(0);

        }

        userInput(args);
    }

    /**
     * @param args - This method accepts the command line argument i.e the file name
     *             This method consists of main command loop -- Add, Search, Quit
     *             Also, it calls other methods that support the eStore Search
     */
    public static void userInput(String[] args) {
        Scanner keyboard = new Scanner(System.in); // Created an instance of scanner object called scanner

        String option = "-1"; // options in main command

        while (!option.equals("3")) {
            boolean done = false;
            while (!done) {
                try {
                    System.out.println("\n-------Welcome to E-Search --------");
                    System.out.print(
                            "Select the numbers or type command corresponding to the options below: (1)Add (2)Search (3)Quit\n");
                    option = keyboard.nextLine();

                    option = option.toLowerCase().trim();
                    // System.out.println(option);

                    if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("q")
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
                    add(productList);
                    break;

                case "2":
                case "search":
                case "s":
                    System.out.println("Selected option: Search\n");
                    search(productList, map);
                    break;

                case "3":
                case "q":
                case "quit":
                    System.out.println("Selected option Quit\n");

                    printOutput(productList);
                    System.exit(0);
                    break;

                default:
                    break;
            }

        }

    }

    /**
     * @param productList - This is the only one and main array-list that is used to
     *                    store book and electronic entry
     *                    get the user input to decide what type of value he wants
     *                    to add
     *                    when calling the product ID method, it also send the
     *                    product list array, this will be used to check if the the
     *                    ID is already present in the system.
     *                    User allowed to change the product ID in case mistake is
     *                    made. String returned
     *                    The first three elements = ID, name, year and price are
     *                    common to both books and electronics class
     *                    getting author, publisher and maker depends upon the type
     *                    of object user wants to create
     */
    public static void add(ArrayList<Product> productList) {
        Scanner scanOption = new Scanner(System.in);
        boolean done = false;
        int option = -1;

        while (!done) // error checking
        {
            try {
                System.out.print("Select the numbers corresponding to the options below: (1)Book (2)Electronics \n");
                option = scanOption.nextInt();

                if (option == 1 || option == 2)
                    done = true;

                else
                    throw new Exception("*** Ony number 1 or 2 ****");
            } catch (InputMismatchException e) {
                scanOption.nextLine();
                System.out.println("*** Only numbers ***");
            }

            catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        }

        String productID;
        int Year;
        String name;
        String author;
        String publisher;
        String maker;
        double price;

        productID = Product.validateID(productList);
        Year = Product.validateYear();
        name = Product.validateName();
        price = Product.validatePrice();

        if (option == 1) {
            author = Book.validateAuthor();
            publisher = Book.validatePublisher();

            Book book = new Book(productID, name, price, Year, author, publisher);
            productList.add(book);

            // ----- Checking for the index of the value added -------
            int number = returnIndex(book, productList);
            // System.out.println("Index of the value added in array list: " + number);

            // This is the method for adding values as mentioned in the project specs -->
            // the values are index of the items in array list that contains the same
            // keyword in their names
            boolean validSpace = spaceCount(name);

            if (validSpace == true) {
                String[] tokens = name.toLowerCase().split(" "); // adding the keys in lowercase so it can find java and
                                                                 // JAVA
                for (String token : tokens) {
                    if (map.containsKey(token)) {
                        map.get(token).add(number);
                    } else {
                        map.put(token.toLowerCase(), new ArrayList<>()); // key and new array of value
                        map.get(token.toLowerCase()).add(number);
                    }
                }
            } else {
                if (map.containsKey(name.toLowerCase()))
                    map.get(name.toLowerCase()).add(number);

                else {
                    map.put(name.toLowerCase(), new ArrayList<>());
                    map.get(name).add(number);
                }
            }

            // Check for keys and values in hashmap using setEntry.

            /*
             * System.out.
             * println("***----------- Getting the output using entry set ------------- ***"
             * );
             * Set set = map.entrySet();
             * System.out.println("Set values: " + set);
             * 
             * 
             * System.out.
             * println("*** --------------------Getting the value using an advanced for loop through entry set------------------ ***"
             * );
             * for(Map.Entry<String,ArrayList<Integer>> entry: map.entrySet())
             * System.out.println(entry.getKey() + ": " + entry.getValue());
             */

            System.out.println(
                    "*** ----------------------------------------------------------------------------------------------------- ***");
            System.out.println("Adding this entry in list:" + " " + book.getID() + " " + book.getName() + " "
                    + book.getPrice() + " " + book.getYear() + " " + book.getAuthor() + " " + book.getPublisher());

        }

        if (option == 2) {
            maker = Electronics.validateMaker();
            Electronics electronic = new Electronics(productID, name, price, Year, maker);
            productList.add(electronic);

            int number = returnIndex(electronic, productList);
            System.out.println("Index of the value added in array list: " + number);

            boolean validSpace = spaceCount(name);
            if (validSpace == true) {
                String[] tokens = name.toLowerCase().split(" "); // adding the keys in lowercase so it can find java and
                                                                 // JAVA
                for (String token : tokens) {
                    if (map.containsKey(token)) {
                        map.get(token).add(number);
                    } else {
                        map.put(token.toLowerCase(), new ArrayList<>()); // key and new array of value
                        map.get(token).add(number);
                    }
                }
            }

            else {
                if (map.containsKey(name.toLowerCase())) {
                    map.get(name.toLowerCase()).add(number);
                } else {
                    map.put(name.toLowerCase(), new ArrayList<>());
                    map.get(name).add(number);
                }
            }

            // Check for keys and values in hashmap using setEntry.

            /*
             * System.out.
             * println("***----------- Getting the output using entry set ------------- ***"
             * );
             * Set set = map.entrySet();
             * System.out.println("Set values: " + set);
             * 
             * 
             * System.out.
             * println("*** --------------------Getting the value using an advanced for loop through entry set------------------ ***"
             * );
             * for(Map.Entry<String,ArrayList<Integer>> entry: map.entrySet())
             * System.out.println(entry.getKey() + ": " + entry.getValue());
             */

            System.out.print("Adding this entry in list: " + electronic.getID() + " " + electronic.getName() + " "
                    + electronic.getPrice() + " " + electronic.getYear() + " " + electronic.getMaker());
        }

        // printList(productList);

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

        /*
         * for(int i = 0; i < values.length; i++)
         * {
         * System.out.println(values[i]);
         * }
         */

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
                /*
                 * for(int i = 0; i < returnedList.size(); i++)
                 * {
                 * System.out.println(returnedList.get(i));
                 * }
                 */
            }

            if (validSpace == false) {
                if (map.containsKey(name.toLowerCase())) {
                    returnedList = (ArrayList<Integer>) map.get(name.toLowerCase());
                }
            }

            for (Integer p : returnedList) {
                if (ID.equals(productList.get(p).getID()) || ID.isEmpty()) {
                    // if(year.length() == 4|| year.isEmpty() ||(year.length() == 5 && values[2] ==
                    // 0 && productList.get(p).getYear() <= values[0]) ||(year.length() == 5 &&
                    // values[2] == 4 && productList.get(p).getYear() >= values[0]) ||(year.length()
                    // == 9 && values[2] == 4 && productList.get(p).getYear() >= values[0] &&
                    // productList.get(p).getYear()<= values[1]))

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

            /*
             * for(int i = 0; i < returnedList.size(); i++)
             * {
             * System.out.println("The index of name is: " + returnedList.get(i));
             * }
             */

        }

        if (name.isEmpty() == true) {

            for (int i = 0; i < productList.size(); i++) {
                if (ID.equals(productList.get(i).getID()) || ID.isEmpty()) {

                    // if(year.length() == 4|| year.isEmpty() ||(year.length() == 5 && values[2] ==
                    // 0 && productList.get(i).getYear() <= values[0]) ||(year.length() == 5 &&
                    // values[2] == 4 && productList.get(i).getYear() >= values[0]) ||(year.length()
                    // == 9 && values[2] == 4 && productList.get(i).getYear() >= values[0] &&
                    // productList.get(i).getYear()<= values[1]))
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

    /**
     * @param productList - The array list is printed here to check what values have
     *                    been added to it so far
     */
    public static void printList(ArrayList<Product> productList) {
        System.out.println("----- The items stored in the list are -----");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println(productList.get(i).toString());

        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------

    /**
     * @return product_ID - The method simply gets the user input as product ID and
     *         returns it to use it in search operation.
     *         I used this method straight from my assignment 1 without
     *         modifications
     */
    public static String search_ID() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Do you wish to enter the product ID: (1) Yes (2) No ");
        while (!scan.hasNextInt()) {
            scan.next(); // Read and discard offending non-int input
            System.out.print("Please enter one of the options: "); // Re-prompt
        }

        int option = scan.nextInt();

        String product_ID = scan.nextLine();

        if (option == 1) {
            do {

                System.out.println("Enter the product ID: ");

                product_ID = scan.nextLine();

                if (product_ID.length() == 6 && product_ID.matches("[0-9]+") == true) {
                    break;
                }

                else {
                    System.out.println("Wrong input.Please try again");
                }

            } while (product_ID.length() != 6 && product_ID.matches("[0-9]+") == false); // Checks if the value is empty
        } else {
            product_ID = "";
        }

        return product_ID;
    }

    /**
     * @return name - The method simply gets the user input for name and returns it
     *         to use it in search operation
     *         I used this method straight from my assignment - 1 without
     *         modifications
     */
    public static String search_Name() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Do you wish to enter product name: (1)Yes (2)No");

        while (!scan.hasNextInt()) {
            scan.next(); // Read and discard offending non-int input
            System.out.print("Please enter one of the options: "); // Re-prompt
        }
        int option = scan.nextInt();

        String product_name = scan.nextLine();

        if (option == 1) {
            System.out.println("Enter the product name: ");
            do {

                product_name = scan.nextLine();

                if (product_name.isEmpty() == false) {
                    break;
                }

            } while (product_name.isEmpty() == true);

        }

        else // this is for option 2.
        {
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
        Scanner scan = new Scanner(System.in);
        System.out.println("Do you wish to enter product year:\n(1)Yes (2)No");

        int option;

        outerloop: do {
            while (!scan.hasNextInt()) {
                scan.next(); // Read and discard offending non-int input
                System.out.print("Please enter one of the options: "); // Re-prompt
            }
            option = scan.nextInt();
            scan.nextLine();

            if (option == 1) {
                do {

                    System.out.println("Enter the product year: ");
                    product_year = scan.nextLine();

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
                    // System.out.println(substr1 + substr2 );
                    val[0] = Integer.parseInt(substr1);
                    val[2] = Integer.parseInt(substr2);
                    val[1] = 4;
                }
                // System.out.println("Yes, user entered info with - sign");
            }

            else {
                val[0] = Integer.parseInt(product_year);
            }

        }
        return val;
    }

    /**
     * @param item        - this is an object (type product) that is added to the
     *                    array-list
     * @param productList - The array list to which the object is added
     * @return number - the index where the object is found in the array-list
     *         This method checks the index of object that is added to the
     *         array-list. This is used in search operation and updating the
     *         hash-map
     */
    public static int returnIndex(Product item, ArrayList<Product> productList) {
        int number = 0;
        for (int i = 0; i < productList.size(); i++) {
            if (item.equals(productList.get(i))) {
                number = i;

            }
        }

        return number;
    }

    /**
     * @param productList
     *                    This method simply prints the output of productList in a
     *                    test file. Also, checks if the file exists before hand, if
     *                    not it creates a new file
     *                    It uses try and catch to create the file and return the
     *                    exception if file is not found
     *                    toString method is overriding method and modified to
     *                    return values in the format mentioned in assignment
     *                    specifications
     */
    public static void printOutput(ArrayList<Product> productList) {
        try {
            PrintWriter outputStream = new PrintWriter(new FileOutputStream("java.txt"));

            for (int i = 0; i < productList.size(); i++) {
                outputStream.println(productList.get(i).toString());
            }

            System.out.println("Successful write to file");

            outputStream.close();

        }

        catch (FileNotFoundException e) {
            System.out.println("Unable to open file");
            System.exit(0);
        }
    }
}
