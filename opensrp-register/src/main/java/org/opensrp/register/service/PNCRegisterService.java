package org.opensrp.register.service;

import org.opensrp.register.domain.EligibleCouple;
import org.opensrp.register.domain.Mother;
import org.opensrp.register.PNCRegister;
import org.opensrp.register.PNCRegisterEntry;
import org.opensrp.register.repository.AllEligibleCouples;
import org.opensrp.register.repository.AllMothers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static ch.lambdaj.Lambda.*;
import static org.opensrp.common.AllConstants.ANCFormFields.REGISTRATION_DATE;
import static org.opensrp.common.AllConstants.DeliveryOutcomeFields.*;
import static org.opensrp.common.AllConstants.ECRegistrationFields.HOUSEHOLD_ADDRESS;
import static org.opensrp.common.AllConstants.FamilyPlanningFormFields.*;
import static org.opensrp.common.AllConstants.PNCVisitFormFields.DISCHARGE_DATE;
import static org.hamcrest.Matchers.equalTo;

@Service
public class PNCRegisterService {
    private final AllMothers allMothers;
    private final AllEligibleCouples allEligibleCouples;

    @Autowired
    public PNCRegisterService(AllMothers allMothers,
                              AllEligibleCouples allEligibleCouples) {
        this.allMothers = allMothers;
        this.allEligibleCouples = allEligibleCouples;
    }

    public PNCRegister getRegisterForANM(String anmIdentifier) {
        ArrayList<PNCRegisterEntry> pncRegisterEntries = new ArrayList<>();
        List<Mother> mothers = allMothers.findAllOpenPNCsForANM(anmIdentifier);
        Collection<String> ecIDs = selectDistinct(collect(mothers, on(Mother.class).ecCaseId()));
        List<String> ecIdsList = new ArrayList<>();
        ecIdsList.addAll(ecIDs);
        List<EligibleCouple> ecs = allEligibleCouples.findAll(ecIdsList);
        for (Mother mother : mothers) {
            EligibleCouple ec = selectUnique(ecs,
                    having(on(EligibleCouple.class).caseId(), equalTo(mother.ecCaseId())));
            PNCRegisterEntry pncRegisterEntry = new PNCRegisterEntry()
                    .withRegistrationDate(mother.getDetail(REGISTRATION_DATE))
                    .withThayiCardNumber(mother.thayiCardNumber())
                    .withWifeName(ec.wifeName())
                    .withHusbandName(ec.husbandName())
                    .withWifeDOB(ec.wifeDOB())
                    .withAddress(ec.getDetail(HOUSEHOLD_ADDRESS))
                    .withDateOfDelivery(mother.dateOfDelivery().toString())
                    .withPlaceOfDelivery(mother.getDetail(DELIVERY_PLACE))
                    .withTypeOfDelivery(mother.getDetail(DELIVERY_TYPE))
                    .withDischargeDate(mother.getDetail(DISCHARGE_DATE))
                    .withFPMethodName(ec.getDetail(CURRENT_FP_METHOD_FIELD_NAME))
                    .withFPMethodDate(ec.getDetail(FP_METHOD_CHANGE_DATE_FIELD_NAME))
                    .withChildrenDetails(mother.childrenDetails())
                    .withPNCVisits(mother.pncVisits())
                    .withDeliveryComplications(mother.getDetail(DELIVERY_COMPLICATIONS));
            pncRegisterEntries.add(pncRegisterEntry);
        }
        return new PNCRegister(pncRegisterEntries);
    }
}
