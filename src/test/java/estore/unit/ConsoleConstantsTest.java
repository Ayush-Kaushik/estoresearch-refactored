package estore.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import estore.presentation.ConsoleMessages;
import estore.presentation.ConsolePrompts;

public class ConsoleConstantsTest {

    @Test
    public void shouldExposeSharedConsoleConstants() {
        assertEquals("Catalog metadata", ConsoleMessages.CATALOG_METADATA);
        assertEquals("\n-------Welcome to E-Search --------", ConsoleMessages.WELCOME_MESSAGE);
        assertEquals(
                "Select the numbers or type command corresponding to the options below:\n(1)Add\n(2)Search\n(3)Quit\n(4)View Metadata\n",
                ConsolePrompts.MAIN_MENU);
    }
}
