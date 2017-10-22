/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.HHService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HHRegistrationHandler implements FormSubmissionHandler {
	
	private HHService hhService;
	
	@Autowired
	public HHRegistrationHandler(HHService hhService)
	{
		this.hhService = hhService;
	}

	@Override
	public void handle(FormSubmission submission) {
		
		hhService.registerHouseHold(submission);
	}
}
