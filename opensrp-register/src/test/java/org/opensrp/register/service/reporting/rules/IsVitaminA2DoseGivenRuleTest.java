package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsVitaminA2DoseGivenRule;

import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.create;
import static org.junit.Assert.assertFalse;


public class IsVitaminA2DoseGivenRuleTest {
    private IsVitaminA2DoseGivenRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new IsVitaminA2DoseGivenRule();
    }

    @Test
    public void shouldReturnTrueIfVitaminA2DoseIsGiven() {
        SafeMap safeMap = new SafeMap(create("vitaminADose", "2").map());

        boolean didRuleApply = rule.apply(safeMap);
        assertTrue(didRuleApply);
    }

    @Test
    public void shouldReturnFalseIfVitaminA2DoseIsNotGiven() {

        boolean didRuleApply = rule.apply(new SafeMap(create("vitaminADose", "1").map()));
        assertFalse(didRuleApply);

        didRuleApply = rule.apply(new SafeMap(create("vitaminADose", "").map()));
        assertFalse(didRuleApply);
    }
}
