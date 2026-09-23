package estore;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import estore.business.IProductService;
import estore.business.ProductSearchService;
import estore.business.ProductService;
import estore.data.CatalogFileReader;
import estore.data.IProductCatalogRepository;
import estore.data.InMemoryProductCatalogRepository;
import estore.logging.ILogger;
import estore.logging.Logger;
import estore.presentation.Console;
import estore.presentation.ConsoleMessages;
import estore.presentation.ConsolePrompts;
import estore.presentation.IConsole;
import estore.presentation.command.AddOperationHandler;
import estore.presentation.command.Command;
import estore.presentation.command.IOperationHandler;
import estore.presentation.command.QuitOperationHandler;
import estore.presentation.command.SearchOperationHandler;
import estore.presentation.command.ViewOperationHandler;
import estore.presentation.validation.MenuOptionRule;
import estore.presentation.validation.Rule;

public class EStoreSearch {

    private static final IProductCatalogRepository catalogRepository = new InMemoryProductCatalogRepository();
    private static final IProductService productService = new ProductService(catalogRepository);
    private static final IConsole console = new Console();
    private static final ILogger logger = new Logger();

    public static void main(String[] args) {

        if (args.length == 0) {
            logger.error("Please provide the catalog file name as a command line argument.");
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
            logger.error("Problem opening file: " + args[0]);
            System.exit(0);
        }
    }

    public static void runProgram() {
        String option = "-1";
        Map<Command, IOperationHandler> operationHandlers = createOperationHandlers();

        console.printMessage(ConsoleMessages.WELCOME_MESSAGE);

        while (!option.equals("3")) {
            do {
                console.printPrompt(ConsolePrompts.MAIN_MENU);

                option = console.readInput();
                Rule rule = new MenuOptionRule();

                if (!rule.validate(option)) {
                    console.printMessage(ConsoleMessages.INVALID_OPTION_SELECTED);
                    continue;
                }

                break;
            } while (true);

            Command command = Command.findByType(option);
            IOperationHandler operationHandler = operationHandlers.get(command);
            if (operationHandler != null) {
                operationHandler.execute();
            }

            console.printMessage(ConsoleMessages.HORIZONTAL_LINE);
        }
    }

    private static Map<Command, IOperationHandler> createOperationHandlers() {
        Map<Command, IOperationHandler> operationHandlers = new HashMap<Command, IOperationHandler>();
        operationHandlers.put(
            Command.ADD,
            new AddOperationHandler(productService, console));

        operationHandlers.put(
            Command.SEARCH,
            new SearchOperationHandler(new ProductSearchService(productService), console));

        operationHandlers.put(Command.VIEW, new ViewOperationHandler(catalogRepository, console));
        operationHandlers.put(Command.QUIT, new QuitOperationHandler(catalogRepository, logger));
        return operationHandlers;
    }
}
