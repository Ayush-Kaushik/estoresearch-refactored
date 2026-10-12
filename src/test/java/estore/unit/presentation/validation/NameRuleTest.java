package estore.unit.presentation.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.presentation.validation.NameRule;

public class NameRuleTest {

    private final NameRule rule = new NameRule();

    @Test
    public void shouldAllowAnyNonEmptyName() {
        assertTrue(rule.validate("Laptop"));
        assertTrue(rule.validate(" "));
    }

    @Test
    public void shouldRejectNullAndEmptyNames() {
        assertFalse(rule.validate(null));
        assertFalse(rule.validate(""));
    }
}