package org.opensrp.register.service.reporting.rules;

import org.opensrp.util.SafeMap;
import org.opensrp.register.service.reporting.ChildImmunization;
import org.opensrp.service.reporting.rules.IRule;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.ChildImmunizationFields.MEASLES_BOOSTER_VALUE;
import static org.opensrp.common.AllConstants.ChildImmunizationFields.MEASLES_VALUE;

@Component
public class IsMeaslesBoosterImmunizationGivenRule implements IRule {

    @Override
    public boolean apply(SafeMap reportFields) {
        return new ChildImmunization().isImmunizedWith(MEASLES_BOOSTER_VALUE, reportFields);
    }
}
