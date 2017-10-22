package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.PNCService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PNCVisitThreeHandler implements FormSubmissionHandler {

	private PNCService pncService;

	@Autowired
	public PNCVisitThreeHandler(PNCService pncService) {
		this.pncService = pncService;
	}

	@Override
	public void handle(FormSubmission submission) {
		pncService.pncVisitThree(submission);
	}
}
