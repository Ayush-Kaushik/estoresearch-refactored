package estore.logging;

/** Defines the logging operations used by the application. */
public interface ILogger extends AutoCloseable {
    void log(String message);

    void print(String message);

    void info(String message);

    void debug(String message);

    void trace(String message);

    void error(String message);

    @Override
    default void close() {
        // Default no-op for implementations that do not need explicit shutdown.
    }
}