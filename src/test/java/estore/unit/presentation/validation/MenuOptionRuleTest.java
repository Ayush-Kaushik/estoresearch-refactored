package estore.unit.presentation.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.presentation.validation.MenuOptionRule;

public class MenuOptionRuleTest {

    private final MenuOptionRule rule = new MenuOptionRule();

    @Test
    public void shouldAllowEverySupportedMenuOption() {
        String[] selections = {"1", "2", "3", "4", "add", "search", "quit", "metadata", "a", "s", "q", "m"};

        for (String selection : selections) {
            assertTrue(selection, rule.validate(selection));
        }
    }

    @Test
    public void shouldIgnoreCaseAndSurroundingWhitespace() {
        assertTrue(rule.validate(" ADD "));
        assertTrue(rule.validate("Search"));
    }

    @Test
    public void shouldRejectUnsupportedSelections() {
        assertFalse(rule.validate(null));
        assertFalse(rule.validate(""));
        assertFalse(rule.validate("5"));
        assertFalse(rule.validate("unknown"));
    }
}