package estore.presentation;

import java.util.List;

import estore.business.ProductAdditionService;
import estore.dataaccess.Product;
import estore.presentation.validation.NameRule;
import estore.presentation.validation.PriceRule;
import estore.presentation.validation.ProductIDRule;
import estore.presentation.validation.YearRule;

/** Presents the prompts and results for adding a product. */
public class ProductAddView {
    private final ProductAdditionService productAdditionService;
    private final List<Product> products;
    private final ConsoleView consoleView;

    public ProductAddView(
        ProductAdditionService productAdditionService, 
        List<Product> products, 
        ConsoleView consoleView) {
        this.productAdditionService = productAdditionService;
        this.products = products;
        this.consoleView = consoleView;
    }

    public void add() {
        boolean done = false;
        int option = -1;

        while (!done) {
            try {
                System.out.print("Select the numbers corresponding to the options below: (1)Book (2)Electronics \n");
                option = Integer.parseInt(consoleView.readInput());

                if (option == 1 || option == 2) {
                    done = true;
                } else {
                    System.out.println("*** Invalid option ***");
                    continue;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        String productId = readProductId();
        int year = Integer.parseInt(consoleView.readValidatedInput(
            "Enter year:\n", new YearRule(), "**** Year has to be 4 digit ****"));
        String name = consoleView.readValidatedInput(
            "Enter the name of the item:\n", new NameRule(), "*** User must input a name ***");
        String priceInput = consoleView.readValidatedInput(
            "Enter the price of item or leave blank: ", new PriceRule(), "*** Enter numbers values only ***");
        double price = priceInput.isEmpty() ? 0.0 : Double.parseDouble(priceInput);

        if (option == 1) {
            String author = readAuthor();
            String publisher = readPublisher();
            productAdditionService.addBook(productId, year, name, price, author, publisher);

            System.out.println("*** ----------------------------------------------------------------------------------------------------- ***");
            System.out.println("Adding this entry in list:" + " " + productId + " " + name + " "
                    + price + " " + year + " " + author + " " + publisher);
        }

        if (option == 2) {
            String maker = readMaker();
            productAdditionService.addElectronics(productId, year, name, price, maker);

            int number = products.size() - 1;
            System.out.println("Index of the value added in array list: " + number);
            System.out.print("Adding this entry in list: " + productId + " " + name + " "
                    + price + " " + year + " " + maker);
        }
    }

    private String readAuthor() {
        return readOptionalValue("Enter the author of item or leave blank: ");
    }

    private String readPublisher() {
        return readOptionalValue("Enter the Publisher of item or leave blank: ");
    }

    private String readMaker() {
        return readOptionalValue("Enter the Maker of item or leave blank: ");
    }

    private String readOptionalValue(String prompt) {
        try {
            System.out.println(prompt);
            return consoleView.readInput();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    private String readProductId() {
        while (true) {
            String productId = consoleView.readValidatedInput(
                    "Enter the ID:\n", new ProductIDRule(), "*** ID can only contain numbers of length 6 ***");
            if (containsProductId(productId)) {
                System.out.println("*** ID already exists in the list ***");
            } else {
                return productId;
            }
        }
    }

    private boolean containsProductId(String productId) {
        for (Product product : products) {
            if (product.getID().contains(productId)) {
                return true;
            }
        }
        
        return false;
    }
}