package org.opensrp.register.mcare.service.reporting;

import org.opensrp.service.reporting.IReporter;
import org.opensrp.service.reporting.IReporterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.opensrp.common.AllConstants.FormEntityTypes.*;

@Component
public class ReporterFactory implements IReporterFactory {

	private HouseHoldReporter houseHoldReporter;

	@Autowired
	public ReporterFactory(HouseHoldReporter houseHoldReporter) {
		this.houseHoldReporter = houseHoldReporter;
	}

	public IReporter reporterFor(String entityType) {
		if (entityType.equalsIgnoreCase(HOUSE_HOLD_TYPE))
			return houseHoldReporter;
		/*
		 * if (entityType.equalsIgnoreCase(MOTHER_TYPE)) return motherReporter;
		 * if (entityType.equalsIgnoreCase(ELIGIBLE_COUPLE_TYPE)) return
		 * eligibleCoupleReporter;
		 */
		return null;
	}
}
