package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.CurrentFPMethodIsCondomRule;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class CurrentFPMethodIsCondomRuleTest {

    CurrentFPMethodIsCondomRule currentFPMethodIsCondomRule;

    @Before
    public void setUp() {
        currentFPMethodIsCondomRule = new CurrentFPMethodIsCondomRule();
    }

    @Test
    public void shouldReturnFalseWhenCurrentFPMethodOfECIsNotCondom() {
        boolean didRuleSucceed = currentFPMethodIsCondomRule.apply(new SafeMap().put("currentMethod", "ocp"));

        assertFalse(didRuleSucceed);
    }

    @Test
    public void shouldReturnTrueIfCurrentFPMethodOfTheECIsCondom() {
        boolean didRuleSucceed = currentFPMethodIsCondomRule.apply(new SafeMap().put("currentMethod", "condom"));

        assertTrue(didRuleSucceed);
    }
}
