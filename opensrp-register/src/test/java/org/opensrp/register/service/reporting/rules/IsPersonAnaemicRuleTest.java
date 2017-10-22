package org.opensrp.register.service.reporting.rules;

import org.opensrp.common.util.EasyMap;
import org.opensrp.util.SafeMap;
import org.junit.Before;
import org.junit.Test;
import org.opensrp.register.service.reporting.rules.IsPersonAnaemicRule;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.opensrp.common.util.EasyMap.create;

public class IsPersonAnaemicRuleTest {

    private IsPersonAnaemicRule isPersonAnaemicRule;

    @Before
    public void setUp() throws Exception {
        isPersonAnaemicRule = new IsPersonAnaemicRule();
    }

    @Test
    public void shouldReturnTrueWhenHBLevelIsLessThanEleven() throws Exception {
        EasyMap easyMap = create("serviceProvidedPlace", "sub_center").put("hbLevel", "10.3");
        boolean rulePassed = isPersonAnaemicRule.apply(new SafeMap(easyMap.map()));

        assertTrue(rulePassed);
    }

    @Test
    public void shouldReturnFalseWhenHBLevelIsMoreThanEleven() throws Exception {
        EasyMap easyMap = create("serviceProvidedPlace", "sub_center").put("hbLevel", "11.3");
        boolean rulePassed = isPersonAnaemicRule.apply(new SafeMap(easyMap.map()));

        assertFalse(rulePassed);
    }
}
