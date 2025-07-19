public class Electronics extends Product {

    private String maker;

    public Electronics(String ID, String name, double price, int year, String maker) {
        super(ID, name, price, year);

        this.maker = maker;
    }

    public static String validateMaker(String maker) {
        if (maker.trim().isEmpty()) {
            throw new IllegalArgumentException("Maker cannot be empty");
        }

        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getMaker() {
        return this.maker;
    }

    /**
     * @return "String formatted in accordance to the output to text file before
     *         quitting"
     */
    @Override
    public String toString() {
        return String.format(
                "type = \"electronics\"\nproductID = \"%s\"\nname = \"%s\"\nprice = \"%f\"\nyear = \"%d\"\nmaker = \"%s\"\n ",
                getID(), getName(), getPrice(), getYear(), getMaker());
    }

    /**
     * @return special string format which is used to display the result of search
     */
    @Override
    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item: " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice() + ", " + "Maker: " + getMaker();
    }

}
