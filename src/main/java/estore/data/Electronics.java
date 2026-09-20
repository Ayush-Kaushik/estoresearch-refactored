package estore.data;

public class Electronics extends Product {
    private String maker;

    public Electronics(String ID, String name, double price, int year, String maker) {
        super(ID, name, price, year);
        this.maker = maker;
    }

    public String getMaker() {
        return this.maker;
    }

    @Override
    public String toString() {
        return String.format(
                "type = \"electronics\"\nproductID = \"%s\"\nname = \"%s\"\nprice = \"%f\"\nyear = \"%d\"\nmaker = \"%s\"\n ",
                getID(), getName(), getPrice(), getYear(), getMaker());
    }
    
    @Override
    public String datadump() {
        return "item ID: " + getID() + ", " + "Name of item: " + getName() + ", " + "Year: " + getYear() + ", "
                + "Price: " + getPrice() + ", " + "Maker: " + getMaker();
    }

}
