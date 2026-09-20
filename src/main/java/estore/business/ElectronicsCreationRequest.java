package estore.business;

public class ElectronicsCreationRequest implements ProductCreationRequest {
    private final String productId;
    private final String name;
    private final double price;
    private final int year;
    private final String maker;

    public ElectronicsCreationRequest(String productId, String name, double price, int year, String maker) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.year = year;
        this.maker = maker;
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

    public String getMaker() {
        return maker;
    }
}
