/**
 * @author Asifur
 */

package org.opensrp.register.mcare.service.handler;

import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.service.ELCOService;
import org.opensrp.service.formSubmission.handler.FormSubmissionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MISCensusHandler implements FormSubmissionHandler {

	private ELCOService eLCOService;
	
	@Autowired
    public MISCensusHandler(ELCOService eLCOService) {
        this.eLCOService = eLCOService;
    }
	
	@Override
	public void handle(FormSubmission submission) {
		eLCOService.misCensus(submission);
	}
}