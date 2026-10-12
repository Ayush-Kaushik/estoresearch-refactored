package estore.unit.presentation.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.presentation.validation.PriceRule;

public class PriceRuleTest {

    private final PriceRule rule = new PriceRule();

    @Test
    public void shouldAllowEmptyOptionalPrice() {
        assertTrue(rule.validate(null));
        assertTrue(rule.validate(""));
    }

    @Test
    public void shouldAllowPositiveNumericPrice() {
        assertTrue(rule.validate("1"));
        assertTrue(rule.validate("12.50"));
    }

    @Test
    public void shouldRejectZeroNegativeAndNonNumericPrices() {
        assertFalse(rule.validate("0"));
        assertFalse(rule.validate("-1.25"));
        assertFalse(rule.validate("free"));
    }
}