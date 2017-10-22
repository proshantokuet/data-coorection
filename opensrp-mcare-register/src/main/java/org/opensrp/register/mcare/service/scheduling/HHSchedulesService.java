package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;

import java.util.List;

import org.joda.time.DateTime;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.service.EnrollmentRecord;
import org.motechproject.scheduletracking.api.service.EnrollmentsQuery;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HHSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(HHSchedulesService.class.toString());
	
	private HealthSchedulerService scheduler;
	private ScheduleLogService scheduleLogService;
	private AllActions allActions;
	private ScheduleTrackingService scheduleTrackingService;
	
	@Autowired
	public HHSchedulesService(HealthSchedulerService scheduler,AllActions allActions,ScheduleLogService scheduleLogService,ScheduleTrackingService scheduleTrackingService)
	{
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
		this.scheduleTrackingService = scheduleTrackingService;
	}

	public void enrollIntoMilestoneOfCensus(String entityId, String date,String provider,String instanceId)
	{
	    logger.info(format("Enrolling household into Census schedule. Id: {0}", entityId));	    
		scheduler.enrollIntoSchedule(entityId, HH_SCHEDULE_CENSUS, date);	
		
		scheduleLogService.scheduleCloseAndSave(entityId, instanceId, provider, HH_SCHEDULE_CENSUS, HH_SCHEDULE_CENSUS, BeneficiaryType.household, AlertStatus.normal, new DateTime(),  new DateTime().plusHours(duration));
		
		
	}
}
