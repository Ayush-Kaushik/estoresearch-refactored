package refactored.model;

public class Book extends Product {

    private String author;
    private String publisher;

    public Book(
            final String ID,
            final String name,
            final double price,
            final int year,
            final String author,
            final String publisher) {
        super(ID, name, price, year);

        this.author = author;
        this.publisher = publisher;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setPublisher(final String publisher_) {
        this.publisher = publisher_;
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
