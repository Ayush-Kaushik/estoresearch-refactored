import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class Product {

    private String id;
    private String name;
    private double price;
    private int year;

    /**
     * @param id    - product id received from add method, used to create member of
     *              instance object of electronic/book subclass
     * @param name  - name received from add method, used to create member of
     *              instance object of electronic/book subclass
     * @param price - price received from add method, used to create member of
     *              instance object of electronic/book subclass
     * @param year  - year received from add method, used to create member of
     *              instance object of electronic/book subclass
     */
    public Product(String id, String name, double price, int year) {
        this.id = id;
        this.price = price;
        this.year = year;
        this.name = name;
    }

    /**
     * @param productList - The array list is used to check if there is a product ID
     *                    entered by user that already exists
     * @return ID - the user input for product ID that is needed to be added to the
     *         array list
     *         It uses try and catch block to validate the user input. Also, it asks
     *         the user to re-enter ID incase it already exists in the array list
     */
    public static String validateID(ArrayList<Product> productList) {
        Scanner scanID = new Scanner(System.in);
        boolean validID = false;
        String ID = "";

        while (!validID) {
            try {
                System.out.println("Enter the ID:");
                ID = scanID.nextLine();

                if (ID.length() == 6 && ID.matches("[0-9]+") == true) {
                    validID = true;
                }

                else {
                    throw new Exception("*** ID can only contain numbers of length 6 ***");
                }

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getID().contains(ID) == true) {
                        validID = false;
                        throw new Exception("*** ID already exists in the list ***");
                    }
                }
            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        }

        return ID;
    }

    /**
     * @return year - returns the user input for year that is needed to be added to
     *         the array list
     *         It uses try and catch block to validate the user input
     */
    public static int validateYear() {
        Scanner scanYear = new Scanner(System.in);
        int year = 0;
        boolean validateYear = false;

        while (!validateYear) {
            try {
                System.out.println("Enter year:");
                year = scanYear.nextInt();

                if (year <= 9999 && year >= 1000) {
                    validateYear = true;
                }

                else {
                    throw new Exception("**** Year has to be 4 digit ****");
                }
            } catch (InputMismatchException e) {
                scanYear.nextLine();
                System.out.println("*** Only numbers ***");
            }

            catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }

        }
        return year;
    }

    /**
     * @return name - It simply returns the user input in the form of String
     *         Since name can include numbers, alphabets and in some cases signs.
     *         This method does not require any error checking.
     */
    public static String validateName(string name) {
        Scanner scanName = new Scanner(System.in);
        boolean validName = false;
        String name = "";

        while (!validName) {
            try {
                System.out.println("Enter the name of the item:");
                name = scanName.nextLine();

                if (name.isEmpty() == true) {
                    validName = false;
                    throw new Exception("*** User must input a name ***");
                } else {
                    validName = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return name;
    }

    /**
     * @return Price - If user input is valid, it converts the user input in the
     *         form of a string into float.
     */
    public static double validatePrice(double price) {
        if (price < 0) {
            throw new ArgumentException("Price cannot be negative");
        }

        return price;
    }

    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item: " + ", " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice();
    }

    /**
     * @param ID_ -product (String) ID received from add method, used to create
     *            member of instance object of electronic/book subclass
     */
    public void setID(String ID_) {
        this.ID = ID_;
    }

    /**
     * @return this.ID - this returns the member (product ID) of instance object of
     *         type Electronic or Book
     */
    public String getID() {

        return this.ID;

    }

    /**
     * @param name_ - name (String) received from add method, used to create member
     *              of instance object of electronic/book subclass
     */
    public void setName(String name_) {
        this.name = name_;
    }

    /**
     * @return this.name - this returns the member (name of item) of instance object
     *         of type Electronic or Book
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param price - this receives a variable from add method which is used to
     *              create member of instance object of book or electronics
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return this.price - this returns the member (Price of item - double data
     *         type) of instance object of type Electronic or Book
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param year_ - this receives a variable from add method which is used to
     *              create member of instance object of book or electronics
     */
    public void setYear(int year_) {
        this.year = year_;
    }

    /**
     * @return this.year - this returns the member (year of the item) of instance
     *         object of type Electronic or Book
     */
    public int getYear() {
        return this.year;
    }

}
