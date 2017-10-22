package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsChildClosedDueToDeathRule;

import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.create;
import static org.junit.Assert.assertFalse;


public class IsChildClosedDueToDeathRuleTest {
    private IsChildClosedDueToDeathRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new IsChildClosedDueToDeathRule();
    }

    @Test
    public void shouldReturnTrueIfCloseReasonIsDeath() {
        SafeMap safeMap = new SafeMap(create("closeReason", "death_of_child").map());

        boolean didRuleApply = rule.apply(safeMap);
        assertTrue(didRuleApply);
    }

    @Test
    public void shouldReturnFalseIfDeathReasonIsNotDeath() {

        boolean didRuleApply = rule.apply(new SafeMap(create("closeReason", "permanent_relocation").map()));
        assertFalse(didRuleApply);

    }
}
