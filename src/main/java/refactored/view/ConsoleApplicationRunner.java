package refactored.view;

import java.util.Scanner;
import java.util.Map;
import java.util.logging.Logger;

import refactored.constants.Command;
import refactored.io.IProductDataImporter;

public class ConsoleApplicationRunner implements IApplicationRunner {

  private final Map<Command, IOperationHandler> operations;
  private IProductDataImporter productDataImporter;
  private Scanner scanner;
  private Logger logger;

  public ConsoleApplicationRunner(
      IProductDataImporter productDataImporter,
      Map<Command, IOperationHandler> operations) {
    this.operations = operations;
    this.productDataImporter = productDataImporter;
    this.scanner = new Scanner(System.in);
    this.logger = Logger.getLogger(ConsoleApplicationRunner.class.getName());
  }

  private Command getCommand() {
    while (true) {
      System.out.println("Enter command " + Command.getOptions());

      try {
        String selection = scanner.nextLine();
        int command = Integer.parseInt(selection.trim());

        Command commandType = Command.findByType(command);

        if (commandType == null) {
          System.out.println("Invalid command");
          continue;
        } else {
          return commandType;
        }

      } catch (Exception exception) {
        System.out.println("Invalid command");
        continue;
      }
    }
  }

  public void start(String[] arguments) {
    if (arguments.length != 1) {
      logger.warning("Input file not provided, store will start without loading existing data.");
    } else {
      logger.info("Loading input file.");
      productDataImporter.importData(arguments[0]);
    }

    System.out.println("\n------- Welcome to EStoreSearch --------");

    while (true) {
      Command command = getCommand();
      IOperationHandler operationHandler = operations.get(command);
      operationHandler.execute();
    }
  }
}