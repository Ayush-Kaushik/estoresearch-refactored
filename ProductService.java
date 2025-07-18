
import java.util.ArrayList;
import java.util.Scanner;

public class ProductService {
    
    private ArrayList<Product> productList;
    
    public ProductService() {
        this.productList = new ArrayList<>();
    }
    
    public ProductService(ArrayList<Product> productList) {
        this.productList = productList;
    }
    
    /**
     * @return ID - the user input for product ID that is needed to be added to the
     *         array list
     *         It uses try and catch block to validate the user input. Also, it asks
     *         the user to re-enter ID incase it already exists in the array list
     */
    public String validateID() {
        Scanner scanID = new Scanner(System.in);
        boolean validID = false;
        String ID = "";

        while (!validID) {
            try {
                System.out.println("Enter the ID:");
                ID = scanID.nextLine();

                if (ID.length() == 6 && ID.matches("[0-9]+") == true) {
                    validID = true;
                }

                else {
                    throw new Exception("*** ID can only contain numbers of length 6 ***");
                }

                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getID().contains(ID) == true) {
                        validID = false;
                        throw new Exception("*** ID already exists in the list ***");
                    }
                }
            } catch (Exception e) {
                String message = e.getMessage();
                System.out.println(message);
            }
        }

        return ID;
    }
    
    public void addProduct(Product product) {
        productList.add(product);
    }
    
    public ArrayList<Product> getProductList() {
        return productList;
    }
    
    public Product findProductById(String id) {
        for (Product product : productList) {
            if (product.getID().equals(id)) {
                return product;
            }
        }
        return null;
    }
}
