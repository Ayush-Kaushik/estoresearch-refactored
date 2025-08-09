package refactored.model;

public abstract class Product {

    private String id;
    private String name;
    private double price;
    private int year;

    public Product(String id, String name, double price, int year) {
        this.price = validatePrice(price);
        this.id = validateID(id);
        this.name = validateName(name);
        this.year = validateYear(year);
    }

    public static String validateID(String id) {
        if (id.length() != 6 || !id.matches("[0-9]+")) {
            throw new IllegalArgumentException("ID must be 6 digits long and contain only numbers");
        }

        return id;
    }

    public static int validateYear(int year) {
        if (year >= 9999 && year <= 1000) {
            throw new IllegalArgumentException("Year has to be 4 digit");
        }

        return year;
    }

    public static String validateName(String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        return name;
    }

    public static double validatePrice(double inputPrice) {

        if (inputPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return inputPrice;
    }

    public static double validatePrice(String inputPrice) {
        if (inputPrice.isEmpty()) {
            return 0.0;
        }

        if (inputPrice.contains("[a-zA-Z]+")) {
            throw new IllegalArgumentException("Enter decimal values only");
        }

        double price = Double.parseDouble(inputPrice);

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        return price;
    }

    public String datadump() {
        return String.format("type=Product\nid=%s\nname=%s\nyear=%d\nprice=%f\n\n",
                getID(),
                getName(),
                getYear(),
                getPrice());
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
