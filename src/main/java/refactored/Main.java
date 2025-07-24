package refactored;

import java.util.logging.Logger;
import java.util.HashMap;

import refactored.constants.Command;
import refactored.io.IProductDataExporter;
import refactored.io.IProductDataImporter;
import refactored.io.ProductDataExporter;
import refactored.io.ProductDataImporter;
import refactored.service.ProductService;
import refactored.view.ConsoleApplicationRunner;
import refactored.view.IApplicationRunner;
import refactored.view.IOperationHandler;
import refactored.view.AddProductOperationHandler;
import refactored.view.QuitOperationHandler;
import refactored.view.SearchProductOperationHandler;

public class Main {

  public static void main(String[] args) {

    ProductService productService = new ProductService();
    IProductDataExporter productDataExporter = new ProductDataExporter(productService);
    IProductDataImporter productDataImporter = new ProductDataImporter(productService);

    HashMap<Command, IOperationHandler> operations = new HashMap<Command, IOperationHandler>();
    operations.put(Command.ADD, new AddProductOperationHandler(productService));
    operations.put(Command.SEARCH, new SearchProductOperationHandler(productService));
    operations.put(Command.QUIT, new QuitOperationHandler(productDataExporter));

    IApplicationRunner applicationRunner = new ConsoleApplicationRunner(operations);
    Logger logger = Logger.getLogger(Main.class.getName());

    if (args.length != 1) {
      logger.warning("Input file not provided, store will start without loading existing data.");
    } else {
      logger.info("Loading input file.");
      productDataImporter.load(args[0]);
    }

    applicationRunner.start(args);
  }
}
