package refactored;

import java.util.HashMap;

import refactored.constants.Command;

import refactored.io.IProductDataExporter;
import refactored.io.IProductDataImporter;
import refactored.service.IProductService;

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

    IProductService productService = new ProductService();
    IProductDataExporter productDataExporter = new ProductDataExporter(productService);
    IProductDataImporter productDataImporter = new ProductDataImporter(productService);

    HashMap<Command, IOperationHandler> operations = new HashMap<Command, IOperationHandler>();
    operations.put(Command.ADD, new AddProductOperationHandler(productService));
    operations.put(Command.SEARCH, new SearchProductOperationHandler(productService));
    operations.put(Command.QUIT, new QuitOperationHandler(productDataExporter));

    IApplicationRunner applicationRunner = new ConsoleApplicationRunner(productDataImporter, operations);

    applicationRunner.start(args);
  }
}
