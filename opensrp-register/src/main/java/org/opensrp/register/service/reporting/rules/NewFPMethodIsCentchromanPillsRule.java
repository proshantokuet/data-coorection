package org.opensrp.register.service.reporting.rules;

import org.opensrp.service.reporting.rules.IRule;
import org.opensrp.util.SafeMap;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.CENTCHROMAN_FP_METHOD_VALUE;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.NEW_FP_METHOD_FIELD_NAME;

@Component
public class NewFPMethodIsCentchromanPillsRule implements IRule {

    @Override
    public boolean apply(SafeMap reportFields) {
        return CENTCHROMAN_FP_METHOD_VALUE.equalsIgnoreCase(reportFields.get(NEW_FP_METHOD_FIELD_NAME));
    }
}

