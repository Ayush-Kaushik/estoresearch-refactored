package estore.e2e;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import io.cucumber.java.an.E;

public class EStoreSearchWorld {

    private EStoreSearchProcess application;
    private Path scenarioDirectory;
    private Path catalogFile;

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
        this.application = new EStoreSearchProcess(artifact);
        this.scenarioDirectory = Files.createTempDirectory("estore-scenario-");
        
        // Note: This does not create the file, it merely creates the placeholder location
        // for where the file will exist
        this.catalogFile = scenarioDirectory.resolve("catalog.txt");
    }

    public void startApplication() throws IOException {
        String file = catalogFile.toString();
        application.start(file);
    }

    public void stopApplication() {
        application.stop();
    }

    public EStoreSearchProcess getApplication() {
        return this.application;
    }

    public Path getCatalogFile() {
        return this.catalogFile;
    }
}