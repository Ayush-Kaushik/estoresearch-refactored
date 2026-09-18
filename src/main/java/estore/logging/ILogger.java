package estore.logging;

/** Defines the output operations used by the application. */
public interface ILogger {
    void log(String message);

    void print(String message);
}