import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Logger;

import refactored.constants.Command;

import refactored.io.IProductDataExporter;
import refactored.io.IProductDataImporter;
import refactored.service.IProductService;
import refactored.factory.IProductFactory;
import refactored.factory.ProductFactory;

import refactored.io.ProductDataExporter;
import refactored.io.ProductDataImporter;
import refactored.service.ProductService;
import refactored.view.ConsoleApplicationRunner;
import refactored.view.IApplicationRunner;
import refactored.view.IOperationHandler;
import refactored.view.AddProductOperationHandler;
import refactored.view.QuitOperationHandler;
import refactored.view.SearchProductOperationHandler;
import refactored.view.ViewProductOperationHandler;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    IProductService productService = new ProductService();
    IProductFactory productFactory = new ProductFactory(Logger.getLogger(Main.class.getName()));

    IProductDataExporter productDataExporter = new ProductDataExporter(productService,
        Logger.getLogger(Main.class.getName()));
    IProductDataImporter productDataImporter = new ProductDataImporter(
        productService,
        Logger.getLogger(Main.class.getName()),
        productFactory);

    HashMap<Command, IOperationHandler> operations = new HashMap<Command, IOperationHandler>();
    operations.put(Command.ADD, new AddProductOperationHandler(productService, scanner));
    operations.put(Command.SEARCH, new SearchProductOperationHandler(productService));
    operations.put(Command.QUIT, new QuitOperationHandler(productDataExporter));
    operations.put(Command.VIEW, new ViewProductOperationHandler(productService));

    IApplicationRunner applicationRunner = new ConsoleApplicationRunner(productDataImporter, operations);

    applicationRunner.start(args);
  }
}
