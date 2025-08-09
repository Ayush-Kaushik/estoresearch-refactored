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

    public String getAuthor() {
        return this.author;
    }

    public String getPublisher() {
        return this.publisher;
    }

    /**
     * @return special string format which is used to display the result of search
     */
    @Override
    public String toString() {
        return String.format(
                "type=book\nid=%s\nname=%s\nprice=%f\nyear=%d\nauthors=%s\npublisher=%s\n\n",
                getID(),
                getName(),
                getPrice(),
                getYear(),
                getAuthor(),
                getPublisher());
    }

    /**
     * @return "String formatted in accordance to the output to text file before
     *         quitting"
     */
    @Override
    public String datadump() {
        return String.format(
                "type=book\nid=%s\nname=%s\nprice=%f\nyear=%d\nauthors=%s\npublisher=%s\n\n",
                getID(),
                getName(),
                getPrice(),
                getYear(),
                getAuthor(),
                getPublisher());
    }
}