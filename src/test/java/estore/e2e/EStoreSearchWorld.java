package estore.e2e;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class EStoreSearchWorld {

    private EStoreSearchProcess application;
    private Path scenarioDirectory;
    private Path catalogFile;
    private List<ApplicationInteraction> interactions;

    /**
     * This is invoked by the hook to run before each scenario
     * It creates a temp scenario directory with estore-scenario prefix
     * Then it creates a temporary empty catalog file inside that specific scenario
     * The file is then shared among step definitions pertainiing to the specific scenario
     * It also picks the artifact which is run as a part of scenario
     * @throws IOException
     */
    public void startScenario() throws IOException {

        String artifact = System.getProperty("estore.artifact");
        interactions = new ArrayList<ApplicationInteraction>();
        
        this.application = new EStoreSearchProcess(artifact, interactions);
        this.scenarioDirectory = Files.createTempDirectory("estore-scenario-");
        
        // Note: This does not create the file, it merely creates the placeholder location
        // for where the file will exist
        this.catalogFile = scenarioDirectory.resolve("catalog.txt");
    }

    public void startApplication() throws IOException, TimeoutException {
        String file = catalogFile.toString();
        application.start(file);
    }

    public void stopApplication() {
        if (application != null) {
            application.stop();
        }
    }

    public void capturePendingInteractions() throws IOException, TimeoutException {
        if (application != null && application.hasPendingInputs()) {
            application.capturePendingInteractions();
        }
    }

    public void stopScenario(String scenarioName) throws IOException {
        stopApplication();
        printScenarioOutput(scenarioName);
        if (scenarioDirectory == null || !Files.exists(scenarioDirectory)) {
            return;
        }

        Files.walkFileTree(scenarioDirectory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                Files.deleteIfExists(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path directory, IOException exception) throws IOException {
                if (exception != null) {
                    throw exception;
                }
                Files.deleteIfExists(directory);
                return FileVisitResult.CONTINUE;
            }
        });
        scenarioDirectory = null;
        catalogFile = null;
        application = null;
    }

    private void printScenarioOutput(String scenarioName) {
        System.out.println("\n===== Scenario output: " + scenarioName + " =====");
        if (interactions != null) {
            for (ApplicationInteraction interaction : interactions) {
                System.out.println("--- Interaction " + interaction.getSequence() + " ---");
                if (interaction.getInput() != null) {
                    System.out.println("Input: " + interaction.getInput());
                }
                System.out.print(interaction.getOutput());
                if (!interaction.getOutput().endsWith("\n")) {
                    System.out.println();
                }
            }
        }
        System.out.println("===== End scenario output: " + scenarioName + " =====");
    }

    public EStoreSearchProcess getApplication() {
        return this.application;
    }

    public Path getCatalogFile() {
        return this.catalogFile;
    }
}