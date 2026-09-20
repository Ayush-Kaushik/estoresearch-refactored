package estore.business;

public class BookCreationRequest implements ProductCreationRequest {
    private final String productId;
    private final String name;
    private final double price;
    private final int year;
    private final String author;
    private final String publisher;

    public BookCreationRequest(String productId, String name, double price, int year, String author, String publisher) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.year = year;
        this.author = author;
        this.publisher = publisher;
    }

    @Override
    public String getProductId() {
        return productId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getYear() {
        return year;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }
}
