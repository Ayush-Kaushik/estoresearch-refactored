package estore.presentation;

import java.util.List;

import estore.business.ProductSearchService;
import estore.business.SearchQuery;
import estore.data.Product;
import estore.logging.ILogger;
import estore.presentation.validation.NameRule;
import estore.presentation.validation.ProductIDRule;

public class ProductSearchView {
    private final ProductSearchService productSearchService;
    private final ConsoleView consoleView;
    private final ILogger logger;

    public ProductSearchView(ProductSearchService productSearchService, ConsoleView consoleView, ILogger logger) {
        this.productSearchService = productSearchService;
        this.consoleView = consoleView;
        this.logger = logger;
    }

    public void search() {
        logger.log("Enter the specs and find the matching product");

        String id = readId();
        String name = readName();
        String year = readYear();

        List<Product> products = productSearchService.search(new SearchQuery(id, name, year));
        for (Product product : products) {
            logger.log(product.datadump());
        }
    }

    private String readId() {
        logger.log("Do you wish to enter the product ID: (1) Yes (2) No ");
        int option = readIntegerOption();

        if (option == 1) {
            return consoleView.readValidatedInput(
                    "Enter the product ID: \n",
                    new ProductIDRule(),
                    "Wrong input.Please try again");
        }

        return "";
    }

    private String readName() {
        logger.log("Do you wish to enter product name: (1)Yes (2)No");
        int option = readIntegerOption();

        if (option == 1) {
            return consoleView.readValidatedInput(
                    "Enter the product name: \n",
                    new NameRule(),
                    "Wrong input.Please try again");
        }

        return "";
    }

    private String readYear() {
        String productYear = "";
        logger.log("Do you wish to enter product year:\n(1)Yes (2)No");

        int option;
        outerloop: do {
            option = readIntegerOption();

            if (option == 1) {
                do {
                    logger.log("Enter the product year: ");
                    productYear = consoleView.readInput();
                    if (!productYear.isEmpty() && !productYear.matches("[a-zA-Z]+")) {
                        break outerloop;
                    }
                } while (productYear.isEmpty() && productYear.matches("[a-zA-Z]+"));
            }

            if (option == 2) {
                productYear = "";
                break;
            }

            logger.log("Wrong input");
        } while (option != 1 || option != 2);

        return productYear;
    }

    private int readIntegerOption() {
        while (true) {
            try {
                return Integer.parseInt(consoleView.readInput().trim());
            } catch (NumberFormatException exception) {
                logger.print("Please enter one of the options: ");
            }
        }
    }
}
