/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.PNCService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PNCVisitTwoHandler implements FormSubmissionHandler {

	private PNCService pncService;

	@Autowired
	public PNCVisitTwoHandler(PNCService pncService) {
		this.pncService = pncService;
	}

	@Override
	public void handle(FormSubmission submission) {
		pncService.pncVisitTwo(submission);
	}
}
