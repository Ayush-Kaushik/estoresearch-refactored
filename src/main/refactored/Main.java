import java.util.logging.Logger;
import java.util.HashMap;

import constants.Command;
import io.ProductDataExporter;
import io.ProductDataImporter;
import service.ProductService;
import view.ConsoleApplicationRunner;
import view.IApplicationRunner;
import view.IOperationHandler;
import view.AddProductOperationHandler;
import view.QuitOperationHandler;
import view.SearchProductOperationHandler;

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
