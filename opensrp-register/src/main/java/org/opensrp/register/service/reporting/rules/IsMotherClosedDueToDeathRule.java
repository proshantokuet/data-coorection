package org.opensrp.register.service.reporting.rules;

import org.opensrp.service.reporting.rules.IRule;
import org.opensrp.util.SafeMap;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.ChildCloseFormFields.CLOSE_REASON_FIELD_NAME;
import static org.opensrp.common.AllConstants.PNCCloseFields.DEATH_OF_MOTHER_VALUE;

@Component
public class IsMotherClosedDueToDeathRule implements IRule {

    @Override
    public boolean apply(SafeMap reportFields) {
        return DEATH_OF_MOTHER_VALUE.equalsIgnoreCase(reportFields.get(CLOSE_REASON_FIELD_NAME));
    }
}
