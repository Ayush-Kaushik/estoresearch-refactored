import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;

public class EStore {

    private ProductDataImporter productDataImporter;
    private IProductInputHandler productInputHandler;
    private ProductDataExporter productDataExporter;

    public HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    public ArrayList<Product> productList = new ArrayList<Product>();

    public EStore(
            IProductInputHandler productInputHandler,
            ProductDataImporter fileLoader,
            ProductDataExporter productDataExporter) {
        this.productDataImporter = fileLoader;
        this.productInputHandler = productInputHandler;
        this.productDataExporter = productDataExporter;
    }

    public void start(String[] arguments) {
        if (arguments.length != 1) {
            System.out.println("Info: Input file not provided, store will start without loading existing data");
        } else {
            System.out.println("Info: Loading input file");
            this.productDataImporter.load(arguments[0]);
        }

        userInput(arguments);
    }

    public void userInput(String[] args) {
        Scanner keyboard = new Scanner(System.in); // Created an instance of scanner object called scanner

        String option = "-1"; // options in main command

        while (!option.equals("3")) {
            boolean done = false;
            while (!done) {
                try {
                    System.out.println("\n-------Welcome to E-Search --------");
                    System.out.print(
                            "Select the numbers or type command corresponding to the options below: (1)Add (2)Search (3)Quit\n");
                    option = keyboard.nextLine();

                    option = option.toLowerCase().trim();
                    // System.out.println(option);

                    if (option.equals("1") || option.equals("2") || option.equals("3") || option.equals("q")
                            || option.equals("quit") || option.equals("add") || option.equals("search")
                            || option.equals("a") || option.equals("s")) {
                        done = true;
                    }

                    else {
                        done = false;
                        throw new Exception("*** enter only numbers or commands ***");
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }

            switch (option) {
                case "1":
                case "add":
                case "a":
                    System.out.println("Selected option: Add\n");
                    this.productInputHandler.addProduct();
                    break;

                case "2":
                case "search":
                case "s":
                    System.out.println("Selected option: Search\n");
                    this.productInputHandler.search();
                    break;

                case "3":
                case "q":
                case "quit":
                    System.out.println("Selected option Quit\n");
                    this.productDataExporter.export(args[0]);
                    System.exit(0);
                    break;
            }

        }

    }

}
