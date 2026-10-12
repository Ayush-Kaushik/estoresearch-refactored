package estore.unit.presentation.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import estore.presentation.validation.SearchProductYearRule;

public class SearchProductYearRuleTest {

    private final SearchProductYearRule rule = new SearchProductYearRule();

    @Test
    public void shouldAllowSingleYearAndOpenEndedRanges() {
        assertTrue(rule.validate("2024"));
        assertTrue(rule.validate("-2024"));
        assertTrue(rule.validate("2024-"));
    }

    @Test
    public void shouldAllowClosedYearRange() {
        assertTrue(rule.validate("1990-2024"));
    }

    @Test
    public void shouldRejectInvalidYearFormats() {
        assertFalse(rule.validate(null));
        assertFalse(rule.validate("24"));
        assertFalse(rule.validate("20245"));
        assertFalse(rule.validate("2024-25"));
        assertFalse(rule.validate("2024--2025"));
        assertFalse(rule.validate("abcd"));
    }
}