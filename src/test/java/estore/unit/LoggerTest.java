package estore.unit;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.junit.Test;

import estore.logging.Logger;

public class LoggerTest {

    @Test
    public void shouldWriteLogEntriesAndShutdownThreadCleanly() throws IOException, InterruptedException {
        Path tempDirectory = Files.createTempDirectory("estore-log-test");
        Logger logger = new Logger(tempDirectory.toString());

        logger.info("App started");
        logger.error("Something failed");
        logger.close();

        try (Stream<Path> stream = Files.list(tempDirectory)) {
            assertTrue(stream.anyMatch(path -> path.getFileName().toString().startsWith("log_")));
        }

        Path logFile = null;
        try (Stream<Path> stream = Files.list(tempDirectory)) {
            logFile = stream.filter(path -> path.getFileName().toString().startsWith("log_"))
                    .findFirst()
                    .orElseThrow(() -> new AssertionError("Expected log file to be created"));
        }

        byte[] bytes = Files.readAllBytes(logFile);
        String content = new String(bytes, StandardCharsets.UTF_8);
        assertTrue(content.contains("App started"));
        assertTrue(content.contains("Something failed"));
    }
}
