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
public class ENCCVisitThreeHandler implements FormSubmissionHandler {

	private ChildService childService;

	@Autowired
	public ENCCVisitThreeHandler(ChildService childService) {
		this.childService = childService;
	}

	@Override
	public void handle(FormSubmission submission) {
		childService.enccThree(submission);
	}
}
