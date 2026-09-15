package estore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/** Shared Cucumber state and console-process adapter for one scenario. */
public class EStoreSearchWorld {

    private static final EStoreSearchWorld INSTANCE = new EStoreSearchWorld();
    private static Path suiteDirectory;

    private Path scenarioDirectory;
    private Path catalogFile;
    private final List<String> stdinLines = new ArrayList<String>();
    private final List<String> catalogProducts = new ArrayList<String>();
    private Process process;
    private String output = "";
    private boolean outputCaptured;

    public static EStoreSearchWorld current() {
        return INSTANCE;
    }

    public static void startSuite() throws IOException {
        suiteDirectory = Files.createTempDirectory("estore-suite-");
    }

    public static void stopSuite() throws IOException {
        INSTANCE.stopProcess();
        deleteRecursively(suiteDirectory);
        suiteDirectory = null;
    }

    public void startScenario() throws IOException {
        stopProcess();
        scenarioDirectory = Files.createTempDirectory(suiteDirectory, "scenario-");
        catalogFile = scenarioDirectory.resolve("catalog.txt");
        stdinLines.clear();
        catalogProducts.clear();
        output = "";
        outputCaptured = false;
    }

    public void stopScenario() throws IOException {
        stopProcess();
        deleteRecursively(scenarioDirectory);
        scenarioDirectory = null;
        catalogFile = null;
    }

    public void createEmptyCatalog() throws IOException {
        requireScenario();
        Files.write(catalogFile, new byte[0]);
    }

    public void createCatalogWithProducts() throws IOException {
        createEmptyCatalog();
    }

    public void addBookProduct() {
        catalogProducts.add(product("book", "book-" + (catalogProducts.size() + 1))
                + "authors={Test Author}\n" + "publisher={Test Publisher}\n");
    }

    public void addElectronicsProduct() {
        catalogProducts.add(product("electronics", "electronics-" + (catalogProducts.size() + 1))
                + "maker={Test Maker}\n");
    }

    private String product(String type, String id) {
        return "type=\"" + type + "\"\n"
                + "productID=\"" + id + "\"\n"
                + "name=\"Test Product " + (catalogProducts.size() + 1) + "\"\n"
                + "price=\"10.00\"\n"
                + "year=\"2024\"\n";
    }

    public void startProgram() throws IOException {
        requireScenario();
        StringBuilder contents = new StringBuilder();
        for (String product : catalogProducts) {
            contents.append(product).append("\n");
        }
        Files.write(catalogFile, contents.toString().getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        String artifact = System.getProperty("estore.artifact");
        if (artifact == null || artifact.isEmpty()) {
            throw new IllegalStateException("The packaged E-Search artifact is not configured");
        }
        ProcessBuilder processBuilder = new ProcessBuilder(
            "java", "-jar", artifact, catalogFile.toAbsolutePath().toString());
        processBuilder.directory(scenarioDirectory.toFile());
        processBuilder.redirectErrorStream(true);

        process = processBuilder.start();
    }

    public void viewMetadata() {
        typeLine("4");
        typeLine("3");
    }

    public void typeLine(String line) {
        stdinLines.add(line);
    }

    public void addBook(String productId, String year, String name, String price, String authors, String publisher) {
        typeLine("1");
        typeLine("1");
        typeLine(productId);
        typeLine(year);
        typeLine(name);
        typeLine(price);
        typeLine(authors);
        typeLine(publisher);
    }

    public void searchByName(String name) {
        typeLine("2");
        typeLine("2");
        typeLine("1");
        typeLine(name);
        typeLine("2");
    }

    public void quit() {
        typeLine("3");
    }

    public String getOutput() throws IOException, InterruptedException {
        captureOutput();
        return output;
    }

    private void captureOutput() throws IOException, InterruptedException {
        if (outputCaptured) {
            return;
        }
        if (process == null) {
            throw new IllegalStateException("The operator has not started E-Search");
        }

        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        InputStream processOutput = process.getInputStream();
        Thread outputReader = new Thread(() -> readOutput(processOutput, captured));
        outputReader.start();

        OutputStream input = process.getOutputStream();
        for (String line : stdinLines) {
            int outputBeforeInput = captured.size();
            input.write((line + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
            input.flush();
            waitForOutput(captured, outputBeforeInput);
        }
        input.close();

        if (!process.waitFor(15, TimeUnit.SECONDS)) {
            process.destroyForcibly();
        }
        outputReader.join(1000);
        output = captured.toString(StandardCharsets.UTF_8.name());
        outputCaptured = true;
    }

    private static void readOutput(InputStream input, ByteArrayOutputStream captured) {
        byte[] buffer = new byte[1024];
        int read;
        try {
            while ((read = input.read(buffer)) != -1) {
                synchronized (captured) {
                    captured.write(buffer, 0, read);
                    captured.notifyAll();
                }
            }
        } catch (IOException ignored) {
            // The process may close its output while the scenario is tearing down.
        }
    }

    private static void waitForOutput(ByteArrayOutputStream captured, int previousSize)
            throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        synchronized (captured) {
            while (captured.size() <= previousSize && System.nanoTime() < deadline) {
                captured.wait(50);
            }
        }
    }

    private void stopProcess() {
        if (process != null && process.isAlive()) {
            process.destroy();
            try {
                if (!process.waitFor(5, TimeUnit.SECONDS)) {
                    process.destroyForcibly();
                }
            } catch (InterruptedException exception) {
                process.destroyForcibly();
                Thread.currentThread().interrupt();
            }
        }
        process = null;
    }

    private void requireScenario() {
        if (scenarioDirectory == null) {
            throw new IllegalStateException("Scenario has not been initialized");
        }
    }

    private static void deleteRecursively(Path path) throws IOException {
        if (path == null || !Files.exists(path)) {
            return;
        }
        try {
            Files.walk(path)
                    .sorted(java.util.Comparator.reverseOrder())
                    .forEach(item -> {
                        try {
                            Files.delete(item);
                        } catch (IOException exception) {
                            throw new RuntimeException(exception);
                        }
                    });
        } catch (RuntimeException exception) {
            if (exception.getCause() instanceof IOException) {
                throw (IOException) exception.getCause();
            }
            throw exception;
        }
    }
}
