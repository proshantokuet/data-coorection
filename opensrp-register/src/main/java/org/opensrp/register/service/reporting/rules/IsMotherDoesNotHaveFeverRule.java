package org.opensrp.register.service.reporting.rules;

import org.opensrp.service.reporting.rules.IRule;
import org.opensrp.util.SafeMap;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.EMPTY_STRING;
import static org.opensrp.common.AllConstants.PNCVisitFormFields.HAS_FEVER_FIELD;

@Component
public class IsMotherDoesNotHaveFeverRule implements IRule {

    @Override
    public boolean apply(SafeMap reportFields) {
        return EMPTY_STRING.equalsIgnoreCase(reportFields.get(HAS_FEVER_FIELD));
    }
}
