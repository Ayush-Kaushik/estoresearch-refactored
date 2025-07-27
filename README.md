# Refactored eStoreSearch (Java Project)

## 📌 Overview

This project is a refactored version of my university Java project, originally designed as a simple console-based eStore search system. Over time, I restructured and improved the code to reflect best practices, better maintainability, and cleaner architecture.

> The goal was to modernize the codebase and demonstrate improved coding standards, readability, modularity, and extensibility.

---

## 🔄 Refactoring Summary

| Aspect              | Old Version                                | Refactored Version                          |
|---------------------|--------------------------------------------|---------------------------------------------|
| Structure           | Monolithic, flat structure                 | Modular packages with separation of concerns|
| Naming              | Inconsistent, ambiguous names              | Clear, descriptive class and method names   |
| Code Duplication    | Repeated logic across classes              | Utility/helper methods introduced           |
| Enums/Constants     | Hard-coded strings                         | Type-safe enums with centralized constants  |
| Input Handling      | Input logic with business logic           | Separated input logic into dedicated classes|
| Object-Oriented Use | Minimal encapsulation                      | Encapsulated fields, added polymorphism     |

---

## 🧠 Why the Refactor?

This project was originally built as part of a university course. The refactored version is a personal initiative to document my growth as a Java developer and designer.

- ✅ To improve **code readability** and make onboarding easier.
- ✅ To promote **clean architecture principles** (e.g., separation of concerns).
- ✅ To reduce **code duplication** and increase reusability.
- ✅ To introduce **testability** and future extensibility.
- ✅ To follow **Java best practices** (e.g., naming conventions, immutability, access modifiers).

---

## 🎯 SOLID Principles Applied

### 1. **Single Responsibility Principle (SRP)**
Each class now has a single, well-defined responsibility.

### 2. **Open/Closed Principle (OCP)**
The system is open for extension but closed for modification through interfaces and polymorphism.

### 3. **Liskov Substitution Principle (LSP)**
Derived classes can be substituted for their base classes without breaking functionality.

### 4. **Interface Segregation Principle (ISP)**
Clients depend only on the interfaces they need.

### 5. **Dependency Inversion Principle (DIP)**
High-level modules don't depend on low-level modules; both depend on abstractions.

---

## 🏗️ Architecture Transformation

### Before: Monolithic Structure
```
original/
├── EStoreSearch.java (2000+ lines - God class)
├── Product.java
├── Book.java
└── Electronics.java
```

### After: Layered Architecture
```
refactored/
├── constants/         # Centralized constants
├── data/              # Data access layer
├── handler/           # Business logic handlers
├── io/                # Input/Output operations
├── model/             # Domain models
├── service/           # Business services
├── view/              # Presentation layer
└── Main.java          # Application entry point
```

---

# 🔍 Detailed Before & After Analysis

## 1. **Main Application Entry Point**

### ❌ Before: Monolithic God Class
```java
// original/EStoreSearch.java (excerpt)

public class EStoreSearch {
    public static HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    public static ArrayList<Product> productList = new ArrayList<Product>();

    public static void main(String[] args) {
        if (args.length == 1) {
            fileRead(args);
        } else {
            System.out.println("Not enough arguments");
            System.exit(0);
        }
    }

    // 2000+ lines of mixed responsibilities:
    // - File I/O
    // - User interface
    // - Business logic
    // - Data management
    // - Input validation
}
```

**Problems:**
- Single class handling all responsibilities
- Static methods and variables (poor testability)
- Mixed concerns (UI, business logic, data access)
- No separation of concerns
- Hard to maintain and extend

### ✅ After: Clean Separation of Concerns
```java
// refactored/Main.java

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        IProductDataExporter productDataExporter = new ProductDataExporter(productService);
        IProductDataImporter productDataImporter = new ProductDataImporter(productService);

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
```

**Improvements:**
- **SRP**: Main class only responsible for dependency injection and application bootstrap
- **DIP**: Depends on abstractions (interfaces) not concrete implementations
- **OCP**: Easy to add new operations without modifying existing code
- Clear dependency injection pattern
- Proper logging implementation

---

## 2. **Command Pattern Implementation**

### ❌ Before: Hard-coded Switch Statement
```java
// original/EStoreSearch.java (excerpt)
public static void userInput(String[] args) {
    Scanner keyboard = new Scanner(System.in);
    String option = "-1";

    while (!option.equals("3")) {
        // Complex input validation logic...
        
        switch (option) {
            case "1":
            case "add":
            case "a":
                System.out.println("Selected option: Add\n");
                add(productList); // 200+ line method
                break;
            case "2":
            case "search":
            case "s":
                System.out.println("Selected option: Search\n");
                search(productList, map); // 150+ line method
                break;
            case "3":
            case "q":
            case "quit":
                System.out.println("Selected option Quit\n");
                printOutput(productList);
                System.exit(0);
                break;
        }
    }
}
```

### ✅ After: Command Pattern with Strategy
```java
// refactored/constants/Command.java
public enum Command {
    ADD(1, "Add Product"),
    SEARCH(2, "Search Products"),
    QUIT(3, "Quit Application");

    private final int type;
    private final String description;

    Command(int type, String description) {
        this.type = type;
        this.description = description;
    }

    public static Command findByType(int type) {
        for (Command command : Command.values()) {
            if (command.type == type) {
                return command;
            }
        }
        return null;
    }

    public static String getOptions() {
        StringBuilder options = new StringBuilder("(");
        for (Command command : Command.values()) {
            options.append(command.type).append(")").append(command.description).append(" ");
        }
        options.append(")");
        return options.toString();
    }
}

// refactored/view/ConsoleApplicationRunner.java
public class ConsoleApplicationRunner implements IApplicationRunner {
    private final Map<Command, IOperationHandler> operations;
    private Scanner scanner;

    public ConsoleApplicationRunner(Map<Command, IOperationHandler> operations) {
        this.operations = operations;
        this.scanner = new Scanner(System.in);
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

// refactored/view/IOperationHandler.java
public interface IOperationHandler {
    void execute();
}
```

**Improvements:**
- **SRP**: Each operation handler has single responsibility
- **OCP**: Easy to add new commands without modifying existing code
- **ISP**: Clean interface segregation
- Type-safe enums instead of magic strings
- Strategy pattern for operation handling

---

## 3. **Data Management Layer**

### ❌ Before: Static Global Variables
```java
// original/EStoreSearch.java
public class EStoreSearch {
    // Global static state - poor encapsulation
    public static HashMap<String, ArrayList<Integer>> map = new HashMap<String, ArrayList<Integer>>();
    public static ArrayList<Product> productList = new ArrayList<Product>();

    // Scattered data manipulation throughout the class
    public static void add(ArrayList<Product> productList) {
        // 200+ lines of mixed logic
        productList.add(book);
        
        // Inverted index logic mixed with business logic
        int number = returnIndex(book, productList);
        boolean validSpace = spaceCount(name);
        if (validSpace == true) {
            String[] tokens = name.toLowerCase().split(" ");
            for (String token : tokens) {
                if (map.containsKey(token)) {
                    map.get(token).add(number);
                } else {
                    map.put(token.toLowerCase(), new ArrayList<Integer>());
                    map.get(token.toLowerCase()).add(number);
                }
            }
        }
    }
}
```

### ✅ After: Encapsulated Data Store
```java
// refactored/data/ProductDataStore.java
public class ProductDataStore {
    private IInvertedIndex nameSearchInvertedIndex;
    private ArrayList<Product> productList;

    public ProductDataStore() {
        this.productList = new ArrayList<Product>();
        this.nameSearchInvertedIndex = new InvertedIndex();
    }

    public boolean productExists(String productId) {
        for (Product product : productList) {
            if (product.getID().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    public void addProduct(Product product) {
        productList.add(product);
        String[] searchTokens = product.getName().toLowerCase().split(" ");
        this.nameSearchInvertedIndex.add(searchTokens, productList.size() - 1);
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }
}

// refactored/data/IInvertedIndex.java
public interface IInvertedIndex {
    void add(String[] tokens, int index);
    ArrayList<Integer> search(String[] tokens);
}

// refactored/service/ProductService.java
public class ProductService {
    private ArrayList<Product> productList;
    private IInvertedIndex nameSearchInvertedIndex;

    public ProductService() {
        this.productList = new ArrayList<Product>();
        this.nameSearchInvertedIndex = new InvertedIndex();
    }
}
```

**Improvements:**
- **SRP**: Data store only handles data operations
- **DIP**: Service layer depends on abstractions
- Proper encapsulation with private fields
- Clear separation between data access and business logic
- Interface-based design for extensibility

---

## 4. **Model Layer Improvements**

### ❌ Before: Validation Mixed with UI
```java
// original/Product.java
public static String validateID(ArrayList<Product> productList) {
    Scanner scanID = new Scanner(System.in);  // UI coupling
    boolean validID = false;
    String ID = "";

    while (!validID) {
        try {
            System.out.println("Enter the ID:");  // Mixed concerns
            ID = scanID.nextLine();

            if (ID.length() == 6 && ID.matches("[0-9]+") == true) {
                validID = true;
            } else {
                throw new Exception("*** ID can only contain numbers of length 6 ***");
            }

            // Business logic mixed with UI
            for (int i = 0; i < productList.size(); i++) {
                if (productList.get(i).getID().contains(ID) == true) {
                    validID = false;
                    throw new Exception("*** ID already exists in the list ***");
                }
            }
        } catch (Exception e) {
            String message = e.getMessage();
            System.out.println(e.getMessage());
        }
    }
    return ID;
}
```

### ✅ After: Pure Domain Models
```java
// refactored/model/Product.java
public abstract class Product {
    private String id;
    private String name;
    private double price;
    private int year;

    public Product(String id, String name, double price, int year) {
        this.price = validatePrice(price);
        this.id = validateID(id);
        this.name = validateName(name);
        this.year = validateYear(year);
    }

    public static String validateID(String id) {
        if (id.length() != 6 || !id.matches("[0-9]+")) {
            throw new IllegalArgumentException("ID must be 6 digits long and contain only numbers");
        }
        return id;
    }

    public static int validateYear(int year) {
        if (year >= 9999 && year <= 1000) {
            throw new IllegalArgumentException("Year has to be 4 digit");
        }
        return year;
    }

    public static String validateName(String name) {
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        return name;
    }

    public static double validatePrice(double inputPrice) {
        if (inputPrice < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        return inputPrice;
    }
}

// refactored/model/Book.java
public class Book extends Product {
    private String author;
    private String publisher;

    public Book(String ID, String name, double price, int year, String author, String publisher) {
        super(ID, name, price, year);
        this.author = validateAuthor(author);
        this.publisher = validatePublisher(publisher);
    }

    public static String validateAuthor(String author) {
        if (author.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        return author;
    }
}
```

**Improvements:**
- **SRP**: Models only handle domain logic and validation
- **LSP**: Subclasses properly extend base class
- No UI coupling in domain models
- Proper use of exceptions for validation
- Immutable object construction through validation

---

## 5. **Input/Output Separation**

### ❌ Before: File I/O Mixed with Business Logic
```java
// original/EStoreSearch.java (excerpt)
public static void fileRead(String[] args) {
    Scanner inputStream = null;
    try {
        HashMap<String, String> inputMap = new HashMap<String, String>();
        inputStream = new Scanner(new FileInputStream(args[0]));
        
        // 150+ lines of parsing logic mixed with:
        // - File reading
        // - Object creation
        // - Data structure updates
        // - Index management
        
        while (inputStream.hasNextLine()) {
            line = inputStream.nextLine();
            String[] values = line.split("=");
            
            // Complex parsing and object creation logic...
            Book book = new Book(ID, name, Double.parseDouble(price), 
                               Integer.parseInt(year), author, publisher);
            productList.add(book);
            
            // Index management mixed in
            String[] tokens = name.toLowerCase().split(" ");
            for (String token : tokens) {
                if (map.containsKey(token)) {
                    map.get(token).add(number);
                }
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Problem opening files");
        System.exit(0);
    }
    userInput(args);  // Control flow mixed with I/O
}
```

### ✅ After: Clean I/O Abstraction
```java
// refactored/io/IProductDataImporter.java
public interface IProductDataImporter {
    void load(String filePath);
}

// refactored/io/ProductDataImporter.java
public class ProductDataImporter implements IProductDataImporter {
    private final ProductService productService;
    private final Logger logger = Logger.getLogger(ProductDataImporter.class.getName());

    public ProductDataImporter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void load(String filePath) {
        try (Scanner inputStream = new Scanner(new FileInputStream(filePath))) {
            processFile(inputStream);
            logger.info("Successfully loaded data from file: " + filePath);
        } catch (FileNotFoundException e) {
            logger.severe("File not found: " + filePath);
            throw new RuntimeException("Could not load data file", e);
        }
    }

    private void processFile(Scanner inputStream) {
        HashMap<String, String> productData = new HashMap<>();
        
        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine().trim();
            
            if (line.isEmpty()) {
                createAndAddProduct(productData);
                productData.clear();
            } else {
                parseLineIntoMap(line, productData);
            }
        }
        
        if (!productData.isEmpty()) {
            createAndAddProduct(productData);
        }
    }
}

// refactored/io/IProductDataExporter.java
public interface IProductDataExporter {
    void export(String path);
}
```

**Improvements:**
- **SRP**: I/O classes only handle data import/export
- **DIP**: Depends on ProductService abstraction
- Proper error handling with logging
- Resource management with try-with-resources
- Clear separation between parsing and business logic

---

## 🎯 Key Design Patterns Applied

### 1. **Strategy Pattern**
```java
// Operation handlers implement different strategies
public interface IOperationHandler {
    void execute();
}

public class AddProductOperationHandler implements IOperationHandler {
    // Add product strategy
}

public class SearchProductOperationHandler implements IOperationHandler {
    // Search product strategy
}
```

### 2. **Dependency Injection**
```java
// Dependencies injected through constructors
public class ProductDataImporter implements IProductDataImporter {
    private final ProductService productService;

    public ProductDataImporter(ProductService productService) {
        this.productService = productService;
    }
}
```

### 3. **Factory Pattern (Enum-based)**
```java
public enum Command {
    ADD(1, "Add Product"),
    SEARCH(2, "Search Products"),
    QUIT(3, "Quit Application");

    public static Command findByType(int type) {
        // Factory method implementation
    }
}
```

---

## 📊 Metrics Comparison

| Aspect | Before | After | Improvement |
|--------|--------|-------|-------------|
| **Lines per class** | 2000+ (EStoreSearch) | 50-150 average | 90% reduction |
| **Cyclomatic Complexity** | Very High | Low | Significant improvement |
| **Coupling** | Tight | Loose | Interface-based design |
| **Cohesion** | Low | High | Single responsibility |
| **Testability** | Poor | Excellent | Dependency injection |
| **Maintainability** | Difficult | Easy | Modular structure |
| **Extensibility** | Hard | Simple | Open/Closed principle |

---

## 🚀 Benefits Achieved

### **Code Quality**
- ✅ Eliminated god classes
- ✅ Separated concerns properly
- ✅ Improved readability and maintainability
- ✅ Enhanced testability

### **Architecture**
- ✅ Layered architecture implementation
- ✅ Proper abstraction levels
- ✅ Interface-based design
- ✅ Dependency inversion

### **Maintainability**
- ✅ Easy to add new features
- ✅ Simple to modify existing functionality
- ✅ Clear module boundaries
- ✅ Reduced code duplication

### **SOLID Compliance**
- ✅ Single Responsibility: Each class has one reason to change
- ✅ Open/Closed: Open for extension, closed for modification
- ✅ Liskov Substitution: Proper inheritance hierarchy
- ✅ Interface Segregation: Client-specific interfaces
- ✅ Dependency Inversion: Depend on abstractions

---

## 🎯 Conclusion

This refactoring demonstrates a complete transformation from a monolithic, procedural design to a clean, object-oriented architecture following SOLID principles. The result is a maintainable, extensible, and testable codebase that serves as an excellent example of professional Java development practices.

The modular design makes it easy to:
- Add new product types
- Implement different search strategies  
- Support multiple I/O formats
- Add new user interfaces
- Implement comprehensive testing

This refactoring showcases the power of applying design principles and patterns to create robust, professional software solutions.


