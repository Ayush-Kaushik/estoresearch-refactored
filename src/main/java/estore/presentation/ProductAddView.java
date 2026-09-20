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
    private final ConsoleView consoleView;
    private final ILogger logger;

    public ProductAddView(
            IProductService productService,
            ConsoleView consoleView,
            ILogger logger) {
        this.productService = productService;
        this.consoleView = consoleView;
        this.logger = logger;
    }

    public void add() {
        int option = -1;

        do{
            logger.print("Select the numbers corresponding to the options below: (1)Book (2)Electronics \n");
            option = Integer.parseInt(consoleView.readInput());

            if(option != 1 || option != 2) {
                logger.log("*** Invalid option ***");
                continue;
            }
        }while(option != 1 || option != 2);

        String productId = readProductId();
        int year = Integer.parseInt(consoleView.readValidatedInput(
                "Enter year:\n", new YearRule(), "Year has to be 4 digit"));
        String name = consoleView.readValidatedInput(
                "Enter the name of the item:\n", new NameRule(), "User must input a name");
        String priceInput = consoleView.readValidatedInput(
                "Enter the price of item or leave blank: ", new PriceRule(), "User must input decimal numbers");
        double price = priceInput.isEmpty() ? 0.0 : Double.parseDouble(priceInput);

        if (option == 1) {
            BookCreationRequest request = createBookCreationRequest(productId, year, name, price);
            productService.addProduct(ProductFactory.createProduct(request));
        }

        if (option == 2) {
            ElectronicsCreationRequest request = createElectronicsCreationRequest(productId, year, name, price);
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
        return readOptionalValue("Enter the author of item or leave blank: ");
    }

    private String readPublisher() {
        return readOptionalValue("Enter the Publisher of item or leave blank: ");
    }

    private String readMaker() {
        return readOptionalValue("Enter the Maker of item or leave blank: ");
    }

    private String readOptionalValue(String prompt) {
        return consoleView.readInput();
    }

    private String readProductId() {
        return consoleView.readValidatedInput(
             "Enter the ID:\n", 
             new ProductIDRule(),
             "ID can only contain numbers of length 6");
    }
}