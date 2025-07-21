package refactored.view;

import refactored.io.IProductDataExporter;

public class QuitOperationHandler implements IOperationHandler {

  private IProductDataExporter ProductDataExporter;

  public QuitOperationHandler(IProductDataExporter productDataExporter) {
    this.ProductDataExporter = productDataExporter;
  }

  public void execute() {
    // TODO: pass the file name as an argument to the export method
    this.ProductDataExporter.export("temp/path"); // arguments[0]
    System.exit(0);
  }
}