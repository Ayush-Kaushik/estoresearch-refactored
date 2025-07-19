import handler.ConsoleProductInputHandler;
import handler.IProductInputHandler;
import io.ProductDataExporter;
import io.ProductDataImporter;
import service.ProductService;

public class Main {

  public static void main(String[] args) {
    ProductService productService = new ProductService();
    ProductDataExporter productDataExporter = new ProductDataExporter(productService);
    ProductDataImporter productDataImporter = new ProductDataImporter(productService);
    IProductInputHandler productInputHandler = new ConsoleProductInputHandler(productService, productDataExporter);

    EStore eStore = new EStore(productInputHandler, productDataImporter, productDataExporter);
    eStore.start(args);
  }
}
