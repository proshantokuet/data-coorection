package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.ServiceProvidedAtSub_CenterRule;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.mapOf;

public class ServiceProvidedAtSub_CenterRuleTest {

    private ServiceProvidedAtSub_CenterRule rule;

    @Before
    public void setUp() throws Exception {
        rule = new ServiceProvidedAtSub_CenterRule();
    }

    @Test
    public void shouldReturnTrueWhenServiceProvidedPlaceIsSubCenter() throws Exception {
        boolean rulePassed = rule.apply(new SafeMap(mapOf("serviceProvidedPlace", "sub_center")));

        assertTrue(rulePassed);
    }

    @Test
    public void shouldReturnFalseWhenServiceProvidedPlaceIsNotSubCenter() throws Exception {
        boolean rulePassed = rule.apply(new SafeMap(mapOf("serviceProvidedPlace", "phc")));

        assertFalse(rulePassed);

        rulePassed = rule.apply(new SafeMap(mapOf("serviceProvidedPlace", "elsewhere")));

        assertFalse(rulePassed);

        rulePassed = rule.apply(new SafeMap());

        assertFalse(rulePassed);
    }
}
