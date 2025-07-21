package main.original;

import java.util.Scanner;

import main.original.Product;

public class Electronics extends Product {
    private String maker;

    /**
     * @param ID    - product (String) ID received from add method, used to create
     *              member of instance object of electronic/book subclass
     * @param name  - name (String) received from add method, used to create member
     *              of instance object of electronic/book subclass
     * @param price - price (double) received from add method, used to create member
     *              of instance object of electronic/book subclass
     * @param year  - year (int) received from add method, used to create member of
     *              instance object of electronic/book subclass
     * @param maker - this receives a variable from add method which is used to
     *              create member (maker) of instance object of electronics class
     * 
     *              Constructor for Electronics class
     */
    public Electronics(String ID, String name, double price, int year, String maker) {
        super(ID, name, price, year);
        this.maker = maker;
    }

    /**
     * @return maker - method gets input from user and returns a String (maker name)
     *         This method checks if the user input is empty or if it has some value
     *         and simply returns it
     *         return could be an empty string too.
     */
    public static String validateMaker() {
        String maker = "";
        Scanner scanMaker = new Scanner(System.in);
        boolean validMaker;

        try {
            System.out.println("Enter the Maker of item or leave blank: ");
            maker = scanMaker.nextLine();

            if (maker.isEmpty() || !maker.isEmpty()) {
                validMaker = true;
            }

            else {
                throw new Exception("Not valid input. Try again");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return maker;
    }

    // ----------------- SET AND GET MAKER -------------//

    /**
     * @param maker - this receives a variable from add method which is used to
     *              create member of instance object of electronics class
     */
    public void setMaker(String maker) {
        this.maker = maker;
    }

    /**
     * @return this.maker - this returns the member of instance object of type
     *         Electronic created
     */
    public String getMaker() {
        return this.maker;
    }

    /**
     * @return "String formatted in accordance to the output to text file before
     *         quitting"
     */
    @Override
    public String toString() {
        return String.format(
                "type = \"electronics\"\nproductID = \"%s\"\nname = \"%s\"\nprice = \"%f\"\nyear = \"%d\"\nmaker = \"%s\"\n ",
                getID(), getName(), getPrice(), getYear(), getMaker());
    }

    /**
     * @return special string format which is used to display the result of search
     */
    @Override
    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item: " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice() + ", " + "Maker: " + getMaker();
    }

}
