package original;

import java.util.Scanner;

public class Book extends Product {
    private String author;
    private String publisher;

    /**
     * @param ID        - product (String) ID received from superclass Product
     * @param name      - name (String) received from superclass Product
     * @param price     - price (double) received from superclass Product
     * @param year      - year (int) received from superclass Product
     * @param author    - receives a variable (String author) from add method which
     *                  is used to create member of instance object of book class
     * @param publisher - this receives a variable (String publisher) from add
     *                  method which is used to create member of instance object of
     *                  book class
     */
    public Book(String ID, String name, double price, int year, String author, String publisher) {
        super(ID, name, price, year);
        this.author = author;
        this.publisher = publisher;
    }

    /**
     * @return author - If user input is valid, it returns user input - author name
     *         This method uses try and catch blocks to error check.
     */
    public static String validateAuthor() {
        String author = "";
        Scanner scanAuthor = new Scanner(System.in);
        boolean validAuthor = false;

        try {
            System.out.println("Enter the author of item or leave blank: ");
            author = scanAuthor.nextLine();
            validAuthor = true;

            if (author.isEmpty() || !author.isEmpty()) {
                validAuthor = true;
            }

            else {
                throw new Exception("Not valid input. Try again");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return author;
    }

    /**
     * @return publisher - If user input is valid, it returns user input - publisher
     *         name
     *         This method uses try and catch blocks to error check.
     */
    public static String validatePublisher() {
        String publisher = "";
        Scanner scanPublisher = new Scanner(System.in);
        boolean validPublisher = false;

        try {
            System.out.println("Enter the Publisher of item or leave blank: ");
            publisher = scanPublisher.nextLine();

            if (publisher.isEmpty() || publisher.isEmpty() == false) {
                validPublisher = true;
            }

            else {
                throw new Exception("Not valid input.");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return publisher;

    }

    // ------------------- SET AND GET AUTHOR ---------------//
    /**
     * @param author_ - this receives a variable from add method which is used to
     *                create member of instance object of book class
     */
    public void setAuthor(String author_) {
        this.author = author_;
    }

    /**
     * @return this.author - this returns the member of instance object of type Book
     *         created
     */
    public String getAuthor() {
        return this.author;
    }

    // ----------------- SET AND GET PUBLISHER -------------//

    /**
     * @param publisher_ - this receives a variable from add method which is used to
     *                   create member of instance object of book class
     */
    public void setPublisher(String publisher_) {
        this.publisher = publisher_;
    }

    /**
     * @return this.publisher - this returns the member of instance object of type
     *         Book created
     */
    public String getPublisher() {
        return this.publisher;
    }

    /**
     * @return special string format which is used to write output to text file
     */
    @Override
    public String toString() {
        return String.format(
                "type = \"book\"\nproductID = \"%s\"\nname = \"%s\"\nprice = \"%f\"\nyear = \"%d\"\nauthors = \"%s\"\npublisher = \"%s\"\n ",
                getID(), getName(), getPrice(), getYear(), getAuthor(), getPublisher());
    }

    /**
     * @return special string format which is used to display the result of search
     */
    @Override
    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item:" + ", " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice() + ", " + "Author: " + getAuthor() + ", " + "Publisher: " + getPublisher();
    }

}
