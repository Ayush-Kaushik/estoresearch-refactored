package estore.presentation;

import java.util.List;

import estore.business.ProductSearchService;
import estore.business.SearchQuery;
import estore.data.Product;
import estore.logging.ILogger;
import estore.presentation.validation.NameRule;
import estore.presentation.validation.ProductIDRule;
import estore.presentation.validation.SearchProductYearRule;

public class ProductSearchView {
    private final ProductSearchService productSearchService;
    private final IConsole console;
    private final ILogger logger;

    public ProductSearchView(
            ProductSearchService productSearchService,
            IConsole console,
            ILogger logger) {
        this.productSearchService = productSearchService;
        this.console = console;
        this.logger = logger;
    }

    public void search() {
        logger.info("Starting product search workflow");
        console.printMessage(ConsoleMessages.SEARCH_SPECIFICATIONS);

        String id = readId();
        String name = readName();
        String year = readYear();

        List<Product> products = productSearchService.search(new SearchQuery(id, name, year));
        logger.info("Search returned " + products.size() + " product(s)");
        for (Product product : products) {
            console.printMessage(product.datadump());
        }
    }

    private String readId() {
        console.printMessage(ConsoleMessages.ENTER_ID_OPTION);
        int option = readIntegerOption();

        if (option == 1) {
            return console.readValidatedInput(
                    ConsolePrompts.PRODUCT_ID,
                    new ProductIDRule(),
                    ConsoleMessages.WRONG_INPUT_MESSAGE);
        }

        return "";
    }

    private String readName() {
        console.printMessage(ConsoleMessages.ENTER_NAME_OPTION);
        int option = readIntegerOption();

        if (option == 1) {
            return console.readValidatedInput(
                    ConsolePrompts.PRODUCT_NAME,
                    new NameRule(),
                    ConsoleMessages.WRONG_INPUT_MESSAGE);
        }

        return "";
    }

    private String readYear() {
        int option;
        String productYear = "";

        console.printMessage(ConsoleMessages.ENTER_YEAR_OPTION);

        do {
            option = readIntegerOption();

            if (option != 1 && option != 2) {
                console.printMessage(ConsoleMessages.WRONG_INPUT);
                continue;
            }

            if (option == 1) {

                productYear = console.readValidatedInput(
                        ConsolePrompts.PRODUCT_YEAR,
                        new SearchProductYearRule(),
                        ConsoleMessages.WRONG_INPUT_MESSAGE);
            }

            if (option == 2) {
                productYear = "";
            }

        } while (option != 1 && option != 2);

        return productYear;
    }

    private int readIntegerOption() {
        while (true) {
            try {
                return Integer.parseInt(console.readInput().trim());
            } catch (NumberFormatException exception) {
                console.printPrompt(ConsolePrompts.INVALID_OPTION_PROMPT);
            }
        }
    }
}
