/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_DELIVERY_PLAN;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_EDD;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_TEST_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_LAB;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_TT_1;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.opensrp.common.util.DateUtil;
import org.opensrp.scheduler.HealthSchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PNCSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(PNCSchedulesService.class.toString());
	private static final String[] NON_ANC_SCHEDULES = {SCHEDULE_EDD, SCHEDULE_LAB, SCHEDULE_TT_1, SCHEDULE_IFA_1,
        SCHEDULE_HB_TEST_1, SCHEDULE_DELIVERY_PLAN};
	private HealthSchedulerService scheduler;
	
	@Autowired
	public PNCSchedulesService(HealthSchedulerService scheduler){
		this.scheduler = scheduler;
	}

    public void enrollPNCRVForMother(String caseId, LocalDate referenceDateForSchedule) {
       
        enrollIntoCorrectMilestoneOfPNCRVCare(caseId, referenceDateForSchedule);
    }
    private void enrollIntoCorrectMilestoneOfPNCRVCare(String entityId, LocalDate referenceDateForSchedule) {
        String milestone=null;

        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.ONE.toPeriod())) {
            milestone = SCHEDULE_PNC_1;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.FIVE.toPeriod())) {
            milestone = SCHEDULE_PNC_2;
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Days.SEVEN.toPeriod().plusDays(1))) {
            milestone = SCHEDULE_PNC_3;
        } else{
        	
        }

        logger.info(format("Enrolling PNC with Entity id:{0} to PNC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_PNC, milestone, referenceDateForSchedule.toString());
    }
    
    public void enrollPNCForMother(String entityId, String sch_name, LocalDate referenceDateForSchedule) {
        logger.info(format("Enrolling PNC with Entity id:{0} to PNC schedule, milestone: {1}.", entityId, sch_name));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_PNC, sch_name, referenceDateForSchedule.toString());
    }
    
    public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("Fullfill Milestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Not a fullfillMilestone :"+e.getMessage());
    	}
    }
    
    public void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling PNC with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    
    public void unEnrollFromAllSchedules(String entityId) {
    	scheduler.unEnrollFromAllSchedules(entityId);
    }
}
