import java.util.logging.Logger;

import handler.IProductInputHandler;
import io.ProductDataExporter;
import io.ProductDataImporter;

public class EStore {

    private ProductDataImporter productDataImporter;
    private IProductInputHandler productInputHandler;
    private Logger logger;

    public EStore(
            IProductInputHandler productInputHandler,
            ProductDataImporter fileLoader,
            ProductDataExporter productDataExporter) {
        this.productDataImporter = fileLoader;
        this.productInputHandler = productInputHandler;
        this.logger = Logger.getLogger(EStore.class.getName());
    }

    public void start(String[] arguments) {
        if (arguments.length != 1) {
            logger.warning("Input file not provided, store will start without loading existing data.");
        } else {
            logger.info("Loading input file.");
            this.productDataImporter.load(arguments[0]);
        }

        this.productInputHandler.start(arguments);
    }
}
