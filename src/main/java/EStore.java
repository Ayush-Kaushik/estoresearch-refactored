import handler.IProductInputHandler;
import io.ProductDataExporter;
import io.ProductDataImporter;

public class EStore {

    private ProductDataImporter productDataImporter;
    private IProductInputHandler productInputHandler;

    public EStore(
            IProductInputHandler productInputHandler,
            ProductDataImporter fileLoader,
            ProductDataExporter productDataExporter) {
        this.productDataImporter = fileLoader;
        this.productInputHandler = productInputHandler;
    }

    public void start(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("Info: Input file not provided, store will start without loading existing data");
        } else {
            System.out.println("Info: Loading input file");
            this.productDataImporter.load(arguments[0]);
        }

        this.productInputHandler.start(arguments);
    }
}
