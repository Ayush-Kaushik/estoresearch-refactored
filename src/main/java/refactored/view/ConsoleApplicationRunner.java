package refactored.view;

import java.util.Scanner;
import java.util.Map;

import refactored.constants.Command;

public class ConsoleApplicationRunner implements IApplicationRunner {

  private final Map<Command, IOperationHandler> operations;
  private Scanner scanner;

  public ConsoleApplicationRunner(Map<Command, IOperationHandler> operations) {
    this.operations = operations;
    this.scanner = new Scanner(System.in);
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

      } catch (NumberFormatException exception) {
        System.out.println("Invalid command");
        continue;
      }
    }
  }

  public void start(String[] arguments) {
    System.out.println("\n------- Welcome to EStoreSearch --------");

    while (true) {
      Command command = getCommand();
      IOperationHandler operationHandler = operations.get(command);
      operationHandler.execute();
    }
  }
}