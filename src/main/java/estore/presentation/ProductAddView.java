package estore.presentation;

import java.util.List;

import estore.business.ProductAdditionService;
import estore.dataaccess.Product;
import estore.logging.ILogger;
import estore.presentation.validation.NameRule;
import estore.presentation.validation.PriceRule;
import estore.presentation.validation.ProductIDRule;
import estore.presentation.validation.YearRule;

/** Presents the prompts and results for adding a product. */
public class ProductAddView {
    private final ProductAdditionService productAdditionService;
    private final List<Product> products;
    private final ConsoleView consoleView;
    private final ILogger logger;

    public ProductAddView(
        ProductAdditionService productAdditionService, 
        List<Product> products, 
        ConsoleView consoleView,
        ILogger logger) {
        this.productAdditionService = productAdditionService;
        this.products = products;
        this.consoleView = consoleView;
        this.logger = logger;
    }

    public void add() {
        boolean done = false;
        int option = -1;

        while (!done) {
            try {
                logger.print("Select the numbers corresponding to the options below: (1)Book (2)Electronics \n");
                option = Integer.parseInt(consoleView.readInput());

                if (option == 1 || option == 2) {
                    done = true;
                } else {
                    logger.log("*** Invalid option ***");
                    continue;
                }
            } catch (Exception e) {
                logger.log(e.getMessage());
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

            logger.log("*** ----------------------------------------------------------------------------------------------------- ***");
            logger.log("Adding this entry in list:" + " " + productId + " " + name + " "
                    + price + " " + year + " " + author + " " + publisher);
        }

        if (option == 2) {
            String maker = readMaker();
            productAdditionService.addElectronics(productId, year, name, price, maker);

            int number = products.size() - 1;
            logger.log("Index of the value added in array list: " + number);
            logger.print("Adding this entry in list: " + productId + " " + name + " "
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
            logger.log(prompt);
            return consoleView.readInput();
        } catch (Exception e) {
            logger.log(e.getMessage());
            return "";
        }
    }

    private String readProductId() {
        while (true) {
            String productId = consoleView.readValidatedInput(
                    "Enter the ID:\n", new ProductIDRule(), "*** ID can only contain numbers of length 6 ***");
            if (containsProductId(productId)) {
                logger.log("*** ID already exists in the list ***");
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