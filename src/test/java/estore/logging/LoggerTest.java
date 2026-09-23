package estore.logging;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class LoggerTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void shouldWriteInformationToFileWithTimestampAndLevel() throws IOException {
        File logDirectory = temporaryFolder.newFolder("logs");
        Logger logger = new Logger(logDirectory.getAbsolutePath());

        logger.info("Catalog loaded");

        File[] logFiles = logDirectory.listFiles((dir, name) -> name.startsWith("log_") && name.endsWith(".txt"));
        assertNotNull(logFiles);
        assertEquals(1, logFiles.length);

        String content = new String(Files.readAllBytes(logFiles[0].toPath()), StandardCharsets.UTF_8);
        assertTrue(content.contains("INFO"));
        assertTrue(content.contains("Catalog loaded"));
        assertTrue(logFiles[0].getName().startsWith("log_"));
    }
}
