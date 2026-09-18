package estore.logging;

import java.io.PrintStream;

/** Writes application output to a console stream. */
public class ConsoleLogger implements ILogger {
    private final PrintStream output;

    public ConsoleLogger() {
        this(System.out);
    }

    public ConsoleLogger(PrintStream output) {
        this.output = output;
    }

    @Override
    public void log(String message) {
        output.println(message);
    }

    @Override
    public void print(String message) {
        output.print(message);
    }
}