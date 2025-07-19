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

        return name.trim();
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
        return "item ID: " + getID() + ", " + "Name of item: " + ", " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice();
    }

    public void setID(String ID_) {
        this.id = ID_;
    }

    public String getID() {

        return this.id;

    }

    public void setName(String name_) {
        this.name = name_;
    }

    /**
     * @return this.name - this returns the member (name of item) of instance object
     *         of type Electronic or Book
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param price - this receives a variable from add method which is used to
     *              create member of instance object of book or electronics
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return this.price - this returns the member (Price of item - double data
     *         type) of instance object of type Electronic or Book
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * @param year_ - this receives a variable from add method which is used to
     *              create member of instance object of book or electronics
     */
    public void setYear(int year_) {
        this.year = year_;
    }

    /**
     * @return this.year - this returns the member (year of the item) of instance
     *         object of type Electronic or Book
     */
    public int getYear() {
        return this.year;
    }

}
