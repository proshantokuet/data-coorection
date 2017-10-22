/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.ChildService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ENCCVisitTwoHandler implements FormSubmissionHandler {

	private ChildService childService;

	@Autowired
	public ENCCVisitTwoHandler(ChildService childService) {
		this.childService = childService;
	}

	@Override
	public void handle(FormSubmission submission) {
		childService.enccTwo(submission);
	}
}
