package estore.unit.presentation.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.presentation.validation.AddProductYearRule;

public class AddProductYearRuleTest {

    private final AddProductYearRule rule = new AddProductYearRule();

    @Test
    public void shouldAllowExactlyFourDigits() {
        assertTrue(rule.validate("2024"));
        assertTrue(rule.validate("0000"));
    }

    @Test
    public void shouldRejectNullAndNonFourDigitValues() {
        assertFalse(rule.validate(null));
        assertFalse(rule.validate(""));
        assertFalse(rule.validate("24"));
        assertFalse(rule.validate("20245"));
        assertFalse(rule.validate("20a4"));
    }
}