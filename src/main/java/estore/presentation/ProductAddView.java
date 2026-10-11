package estore.presentation;

import estore.business.BookCreationRequest;
import estore.business.ElectronicsCreationRequest;
import estore.business.IProductService;
import estore.business.ProductFactory;
import estore.logging.ILogger;
import estore.presentation.validation.NameRule;
import estore.presentation.validation.PriceRule;
import estore.presentation.validation.ProductIDRule;
import estore.presentation.validation.YearRule;

public class ProductAddView {
    private final IProductService productService;
    private final IConsole console;
    private final ILogger logger;

    public ProductAddView(
            IProductService productService,
            IConsole console,
            ILogger logger) {
        this.productService = productService;
        this.console = console;
        this.logger = logger;
    }

    public void add() {
        logger.info("Starting product addition workflow");
        int option = -1;

        do {
            console.printPrompt(ConsolePrompts.PRODUCT_TYPE_MENU);
            option = Integer.parseInt(console.readInput());

            if (option != 1 && option != 2) {
                console.printMessage(ConsoleMessages.INVALID_OPTION);
                continue;
            }
        } while (option != 1 && option != 2);

        String productId = readProductId();
        int year = Integer.parseInt(console.readValidatedInput(
                ConsolePrompts.YEAR_PROMPT,
                new YearRule(),
                "Year has to be 4 digit"));

        String name = console.readValidatedInput(
                ConsolePrompts.NAME_PROMPT,
                new NameRule(),
                "User must input a name");

        String priceInput = console.readValidatedInput(
                ConsolePrompts.PRICE_PROMPT,
                new PriceRule(),
                "User must input decimal numbers");

        double price = priceInput.isEmpty() ? 0.0 : Double.parseDouble(priceInput);

        if (option == 1) {
            BookCreationRequest request = createBookCreationRequest(productId, year, name, price);
            logger.info("Adding book product with id=" + productId);
            productService.addProduct(ProductFactory.createProduct(request));
        }

        if (option == 2) {
            ElectronicsCreationRequest request = createElectronicsCreationRequest(productId, year, name, price);
            logger.info("Adding electronics product with id=" + productId);
            productService.addProduct(ProductFactory.createProduct(request));
        }
    }

    public BookCreationRequest createBookCreationRequest(String productId, int year, String name, double price) {
        String author = readAuthor();
        String publisher = readPublisher();
        return new BookCreationRequest(productId, name, price, year, author, publisher);
    }

    public ElectronicsCreationRequest createElectronicsCreationRequest(String productId, int year, String name,
            double price) {
        String maker = readMaker();
        return new ElectronicsCreationRequest(productId, name, price, year, maker);
    }

    private String readAuthor() {
        return readOptionalValue(ConsolePrompts.AUTHOR_PROMPT);
    }

    private String readPublisher() {
        return readOptionalValue(ConsolePrompts.PUBLISHER_PROMPT);
    }

    private String readMaker() {
        return readOptionalValue(ConsolePrompts.MAKER_PROMPT);
    }

    private String readOptionalValue(String prompt) {
        console.printPrompt(prompt);
        return console.readInput();
    }

    private String readProductId() {
        return console.readValidatedInput(
                ConsolePrompts.ID_PROMPT,
                new ProductIDRule(),
                "ID can only contain numbers of length 6");
    }
}