package estore.data;

public abstract class Product {
    private String id;
    private String name;
    private double price;
    private int year;

    public Product(String id, String name, double price, int year) {
        this.id = id;
        this.price = price;
        this.year = year;
        this.name = name;
    }

    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item: " + ", " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice();
    }

    public String getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getYear() {
        return this.year;
    }
}
