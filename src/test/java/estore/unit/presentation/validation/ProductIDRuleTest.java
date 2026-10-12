package estore.unit.presentation.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.presentation.validation.ProductIDRule;

public class ProductIDRuleTest {

    private final ProductIDRule rule = new ProductIDRule();

    @Test
    public void shouldAllowExactlySixDigits() {
        assertTrue(rule.validate("123456"));
        assertTrue(rule.validate("000001"));
    }

    @Test
    public void shouldRejectNullAndValuesThatAreNotExactlySixDigits() {
        assertFalse(rule.validate(null));
        assertFalse(rule.validate(""));
        assertFalse(rule.validate("12345"));
        assertFalse(rule.validate("1234567"));
        assertFalse(rule.validate("12A456"));
    }
}