package estore.unit;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import estore.presentation.Console;

public class ConsoleTest {

    @Test
    public void shouldKeepTestDelimiterOutOfVisibleMessages() throws Exception {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8.name()));

            Console console = new Console();
            console.printMessage("Hello");

            String stdout = output.toString(StandardCharsets.UTF_8.name());
            assertEquals("Hello" + System.lineSeparator(), stdout);
        } finally {
            System.setOut(originalOut);
        }
    }
}
