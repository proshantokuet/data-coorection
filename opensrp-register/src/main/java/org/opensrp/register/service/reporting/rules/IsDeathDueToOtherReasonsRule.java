package org.opensrp.register.service.reporting.rules;

import org.opensrp.service.reporting.rules.IRule;
import org.opensrp.util.SafeMap;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.ChildCloseFormFields.OTHERS_LIST;
import static org.opensrp.common.AllConstants.ECCloseFields.MATERNAL_DEATH_CAUSE_FIELD_NAME;

@Component
public class IsDeathDueToOtherReasonsRule implements IRule {

    @Override
    public boolean apply(SafeMap reportFields) {
        return OTHERS_LIST.contains(reportFields.get(MATERNAL_DEATH_CAUSE_FIELD_NAME));
    }
}

