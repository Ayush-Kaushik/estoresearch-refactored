package estore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Shared state for Cucumber scenarios. Runs {@code estore.EStoreSearch} as a
 * child process so {@code System.exit} on quit does not kill the test JVM.
 */
public class EStoreSearchWorld {

    private Path catalogFile;
    private Path workingDirectory;
    private final List<String> stdinLines = new ArrayList<String>();
    private String output = "";
    private Integer exitCode;
    private boolean started;

    public void createEmptyCatalog() throws IOException {
        workingDirectory = Files.createTempDirectory("estore-it-");
        catalogFile = workingDirectory.resolve("catalog.txt");
        Files.write(catalogFile, new byte[0]);
    }

    public void startProgram() {
        started = true;
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

    public void runIfNeeded() throws IOException, InterruptedException {
        if (output.length() > 0 || exitCode != null) {
            return;
        }
        if (!started) {
            throw new IllegalStateException("The operator has not started E-Search");
        }
        if (catalogFile == null) {
            throw new IllegalStateException("No catalog file was created");
        }

        String classpath = System.getProperty("java.class.path");
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-cp", classpath, "estore.EStoreSearch", catalogFile.toAbsolutePath().toString());
        processBuilder.directory(workingDirectory.toFile());
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        final ByteArrayOutputStream captured = new ByteArrayOutputStream();
        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InputStream stream = process.getInputStream();
                    byte[] chunk = new byte[1024];
                    int read;
                    while ((read = stream.read(chunk)) != -1) {
                        captured.write(chunk, 0, read);
                    }
                } catch (IOException ignored) {
                    // Process closed the stream.
                }
            }
        });
        reader.setDaemon(true);
        reader.start();

        writeStdin(process.getOutputStream());

        boolean finished = process.waitFor(15, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            try {
                reader.join(1000);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
            }
            output = captured.toString(StandardCharsets.UTF_8.name());
            throw new IllegalStateException("E-Search did not exit within 15 seconds. Output so far:\n" + output);
        }
        try {
            reader.join(1000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        output = captured.toString(StandardCharsets.UTF_8.name());
        exitCode = process.exitValue();
    }

    public String getOutput() {
        return output;
    }

    private void writeStdin(OutputStream stdin) throws IOException {
        StringBuilder script = new StringBuilder();
        for (String line : stdinLines) {
            script.append(line).append(System.lineSeparator());
        }
        stdin.write(script.toString().getBytes(StandardCharsets.UTF_8));
        stdin.close();
    }
}
