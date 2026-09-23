package estore.data;

public class Book extends Product {
    private String author;
    private String publisher;

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

    @Override
    public String toString() {
        return String.format(
                "type = \"book\"\nproductID = \"%s\"\nname = \"%s\"\nprice = \"%f\"\nyear = \"%d\"\nauthors = \"%s\"\npublisher = \"%s\"\n ",
                getID(), getName(), getPrice(), getYear(), getAuthor(), getPublisher());
    }

    @Override
    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item:" + ", " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice() + ", " + "Author: " + getAuthor() + ", " + "Publisher: " + getPublisher();
    }
}