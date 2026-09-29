package estore.logging;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/** Writes application events to a timestamped file logger. */
public class Logger implements ILogger {
    private static final DateTimeFormatter TIMESTAMP_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter FILE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private final Path logDirectory;
    private final Path logFile;
    private final BlockingQueue<String> queue;
    private final Thread worker;
    private final AtomicBoolean running;

    public Logger() {
        this(System.getProperty("user.dir"));
    }

    public Logger(String directory) {
        this.logDirectory = Paths.get(directory);
        this.logFile = resolveLogFile(this.logDirectory);
        this.queue = new LinkedBlockingQueue<String>();
        this.running = new AtomicBoolean(true);
        this.worker = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running.get() || !queue.isEmpty()) {
                    try {
                        String entry = queue.poll(250, TimeUnit.MILLISECONDS);
                        if (entry == null) {
                            continue;
                        }
                        writeLogEntry(entry);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }, "estore-logger-worker");
        this.worker.setDaemon(true);
        this.worker.start();
    }

    @Override
    public void log(String message) {
        info(message);
    }

    @Override
    public void print(String message) {
        info(message);
    }

    @Override
    public void info(String message) {
        writeLog("INFO", message);
    }

    @Override
    public void debug(String message) {
        writeLog("DEBUG", message);
    }

    @Override
    public void trace(String message) {
        writeLog("TRACE", message);
    }

    @Override
    public void error(String message) {
        writeLog("ERROR", message);
    }

    @Override
    public void close() {
        if (!running.compareAndSet(true, false)) {
            return;
        }

        drainQueue();
        worker.interrupt();

        try {
            worker.join(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void drainQueue() {
        while (!queue.isEmpty()) {
            String entry = queue.poll();
            if (entry != null) {
                writeLogEntry(entry);
            }
        }
    }

    private Path resolveLogFile(Path directory) {
        try {
            Files.createDirectories(directory);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to create log directory", e);
        }

        String fileName = "log_" + LocalDateTime.now().format(FILE_FORMATTER) + ".txt";
        return directory.resolve(fileName);
    }

    private void writeLog(String level, String message) {
        if (!running.get()) {
            return;
        }

        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMATTER);
        String source = getCallerClassName();
        String entry = timestamp + " [" + source + "] " + level + " - " + message;
        queue.offer(entry);
    }

    private void writeLogEntry(String entry) {
        try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.UTF_8,
                java.nio.file.StandardOpenOption.CREATE,
                java.nio.file.StandardOpenOption.APPEND)) {
            writer.write(entry);
            writer.newLine();
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write to log file: " + logFile, e);
        }
    }

    private String getCallerClassName() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : trace) {
            String className = element.getClassName();
            if (!Objects.equals(className, Logger.class.getName())
                    && !Objects.equals(className, Thread.class.getName())) {
                return className;
            }
        }
        return "unknown";
    }
}
