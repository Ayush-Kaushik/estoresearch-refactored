import java.util.logging.Logger;
import java.util.HashMap;

import main.refactored.constants.Command;
import main.refactored.io.ProductDataExporter;
import main.refactored.io.ProductDataImporter;
import main.refactored.service.ProductService;
import main.refactored.view.ConsoleApplicationRunner;
import main.refactored.view.IApplicationRunner;
import main.refactored.view.IOperationHandler;
import main.refactored.view.AddProductOperationHandler;
import main.refactored.view.QuitOperationHandler;
import main.refactored.view.SearchProductOperationHandler;

public class Main {

  public static void main(String[] args) {

    ProductService productService = new ProductService();
    ProductDataExporter productDataExporter = new ProductDataExporter(productService);
    ProductDataImporter productDataImporter = new ProductDataImporter(productService);

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
