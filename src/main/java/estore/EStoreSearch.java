package estore;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import estore.business.IProductService;
import estore.business.ProductSearchService;
import estore.business.ProductService;
import estore.command.AddOperationHandler;
import estore.command.Command;
import estore.command.IOperationHandler;
import estore.command.QuitOperationHandler;
import estore.command.SearchOperationHandler;
import estore.command.ViewOperationHandler;
import estore.data.InMemoryProductCatalogRepository;
import estore.logging.ConsoleLogger;
import estore.logging.ILogger;
import estore.presentation.ConsoleView;
import estore.presentation.validation.MenuOptionRule;
import estore.presentation.validation.Rule;

public class EStoreSearch {

    private static final InMemoryProductCatalogRepository catalogRepository = new InMemoryProductCatalogRepository();
    private static final IProductService productService = new ProductService(catalogRepository);
    private static final ConsoleView consoleView = new ConsoleView();
    private static final ILogger logger = new ConsoleLogger();

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.log("Please provide the catalog file name as a command line argument.");
            System.exit(0);
        }

        readCatalog(args);
        runProgram();
    }

    public static void readCatalog(String[] args) {
        try {
            CatalogFileReader catalogFileReader = new CatalogFileReader(catalogRepository);
            catalogFileReader.read(args[0]);
        } catch (FileNotFoundException e) {
            logger.log("Problem opening file: " + args[0]);
            System.exit(0);
        }
    }

    public static void runProgram() {
        String option = "-1";
        Map<Command, IOperationHandler> operationHandlers = createOperationHandlers();

        while (!option.equals("3")) {
            do {
                logger.log("\n-------Welcome to E-Search --------");
                logger.print(
                        "Select the numbers or type command corresponding to the options below:" + "\n" +
                                "(1)Add" + "\n" +
                                "(2)Search" + "\n" +
                                "(3)Quit" + "\n" +
                                "(4)View Metadata" + "\n");

                option = consoleView.readInput();
                Rule rule = new MenuOptionRule();

                if (!rule.validate(option)) {
                    logger.log("*** Invalid option selected ***");
                    continue;
                }

                break;
            } while (true);

            Command command = Command.findByType(option);
            IOperationHandler operationHandler = operationHandlers.get(command);
            if (operationHandler != null) {
                operationHandler.execute();
            }
        }
    }

    private static Map<Command, IOperationHandler> createOperationHandlers() {
        Map<Command, IOperationHandler> operationHandlers = new HashMap<Command, IOperationHandler>();
        operationHandlers.put(
                Command.ADD,
                new AddOperationHandler(productService, consoleView, logger));

        operationHandlers.put(
            Command.SEARCH, 
            new SearchOperationHandler(new ProductSearchService(productService), consoleView, logger));

        operationHandlers.put(Command.VIEW, new ViewOperationHandler(catalogRepository, logger));
        operationHandlers.put(Command.QUIT, new QuitOperationHandler(catalogRepository, logger));
        return operationHandlers;
    }
}
