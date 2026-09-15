package estore.dataaccess;

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

    public String getAuthor() {
        return this.author;
    }

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
