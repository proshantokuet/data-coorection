package org.opensrp.register.service.reporting.rules;

import org.opensrp.service.reporting.rules.IRule;
import org.opensrp.util.SafeMap;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.CURRENT_FP_METHOD_FIELD_NAME;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.MALE_STERILIZATION_FP_METHOD_VALUE;

@Component
public class CurrentFPMethodIsMaleSterilizationRule implements IRule {

    @Override
    public boolean apply(SafeMap reportFields) {
        return MALE_STERILIZATION_FP_METHOD_VALUE.equalsIgnoreCase(reportFields.get(CURRENT_FP_METHOD_FIELD_NAME));
    }
}

