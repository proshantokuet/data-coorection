/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.handler;

import java.util.Map;

import org.opensrp.common.util.EasyMap;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.opensrp.service.formSubmission.handler.IHandlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import static org.opensrp.common.AllConstants.Form.*;

@Service
public class HandlerMapper implements IHandlerMapper {

	private final Map<String, FormSubmissionHandler> handlerMap;
	
	@Autowired
	public HandlerMapper(  
			AllFormSubmissions formSubmissionsRepository,
			HHRegistrationHandler hhRegistrationHandler,
			ELCOHandler elcoHandler,PSRFHandler psrfHandler,
			ANCVisitOneHandler ancVisitOneHandler,
			ANCVisitTwoHandler ancVisitTwoHandler,
			ANCVisitThreeHandler ancVisitThreeHandler,
			ANCVisitFourHandler ancVisitFourHandler,
			BNFHandler bnfHandler,
			PNCVisitOneHandler pncVisitOneHandler,
			PNCVisitTwoHandler pncVisitTwoHandler,
			PNCVisitThreeHandler pncVisitThreeHandler,
			ENCCVisitOneHandler enccVisitOneHandler,
			ENCCVisitTwoHandler enccVisitTwoHandler,
			ENCCVisitThreeHandler enccVisitThreeHandler,
			MISCensusHandler misCensusHandler,
			MISELCOHandler misELCOHandler
			)
	{
		handlerMap = EasyMap.create(HH_REGISTRATION, (FormSubmissionHandler) hhRegistrationHandler)
				     .put(ELCO_REGISTRATION, elcoHandler)
				     .put(PSRF_FORM, psrfHandler)
				     .put(ANC_REMINDER_VISIT_1, ancVisitOneHandler)
				     .put(ANC_REMINDER_VISIT_2, ancVisitTwoHandler)
				     .put(ANC_REMINDER_VISIT_3, ancVisitThreeHandler)
				     .put(ANC_REMINDER_VISIT_4, ancVisitFourHandler)
				     .put(BNF_FORM, bnfHandler)
				     .put(PNC_REMINDER_VISIT_1, pncVisitOneHandler)
				     .put(PNC_REMINDER_VISIT_2, pncVisitTwoHandler)
				     .put(PNC_REMINDER_VISIT_3, pncVisitThreeHandler)
				     .put(ENCC_REMINDER_VISIT_1, enccVisitOneHandler)
				     .put(ENCC_REMINDER_VISIT_2, enccVisitTwoHandler)
				     .put(ENCC_REMINDER_VISIT_3, enccVisitThreeHandler)
				     .put(MIS_Census, misCensusHandler)
				     .put(MIS_ELCO, misELCOHandler)
                     .map();
	}

	@Override
	public Map<String, FormSubmissionHandler> handlerMapper() {
		return handlerMap;
	}

}