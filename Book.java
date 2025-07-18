import java.util.Scanner;

public class Book extends Product {

    private String author;
    private String publisher;

    /**
     * @param ID   - receives a variable (String ID) from add method which is used
     *             to create member of instance object of book class
     * @param name - receives a variable (String name) from add method which is used
     *             to create member of instance object of book class
     */
    public Book(
            final string ID,
            final string name,
            final double price,
            final int year,
            final string author,
            final string publisher) {
        super(ID, name, price, year);

        this.author = validateAuthor(author);
        this.publisher = publisher;
    }

    /**
     * @param author - receives a variable (String author) from add method which is
     *               used to create member of instance object of book class
     * @return author - If user input is valid, it returns user input - author name
     */
    public static String validateAuthor(final string author) {
        if (author.isEmpty()) {
            throw new ArgumentException("Author cannot be empty");
        }

        return author;
    }

    /**
     * @param publisher - receives a variable (String publisher) from add method
     *                  which is used to create member of instance object of book
     *                  class
     * @return publisher - If user input is valid, it returns user input - publisher
     *         name
     */
    public static String validatePublisher(final String publisher) {

        if (publisher.isEmpty()) {
            throw new ArgumentException("Publisher cannot be empty");
        }

        return publisher;
    }

    /**
     * @param author_ - this receives a variable from add method which is used to
     *                create member of instance object of book class
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * @return this.author - this returns the member of instance object of type Book
     *         created
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * @param publisher_ - this receives a variable from add method which is used to
     *                   create member of instance object of book class
     */
    public void setPublisher(final String publisher_) {
        this.publisher = publisher_;
    }

    /**
     * @return publisher - this returns the member of instance object of type
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
