/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.dto.BeneficiaryType.elco;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_3;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC_4;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_DELIVERY_PLAN;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_EDD;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_HB_TEST_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_IFA_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_LAB;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_TT_1;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Weeks;
import org.opensrp.common.util.DateUtil;
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
public class ANCSchedulesService {
	
	private static Logger logger = LoggerFactory.getLogger(ANCSchedulesService.class.toString());
	private static final String[] NON_ANC_SCHEDULES = {SCHEDULE_EDD, SCHEDULE_LAB, SCHEDULE_TT_1, SCHEDULE_IFA_1,
        SCHEDULE_HB_TEST_1, SCHEDULE_DELIVERY_PLAN};
	private HealthSchedulerService scheduler;
	private AllActions allActions;
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public ANCSchedulesService(HealthSchedulerService scheduler,AllActions allActions,ScheduleLogService scheduleLogService){
		this.scheduler = scheduler;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
	}

    public void enrollMother(String caseId, LocalDate referenceDateForSchedule,String provider,String instanceId,String startDate) {
        /*for (String schedule : NON_ANC_SCHEDULES) {
        	scheduler.enrollIntoSchedule(caseId, schedule, referenceDateForSchedule);
        }*/
        enrollIntoCorrectMilestoneOfANCCare(caseId, referenceDateForSchedule,provider,instanceId,startDate);
    }
    /**
     * Create ANC Schedule depends on LMP Date
     * @param entityId form entity Id
     * @param referenceDateForSchedule LMP Date convert to Local Date
     * @param provider FW  user name
     * @param instanceId form instance id
     * @param startDate LMP Date as String format
     * 
     * */
    private void enrollIntoCorrectMilestoneOfANCCare(String entityId, LocalDate referenceDateForSchedule,String provider,String instanceId,String startDate) {
        String milestone=null;        
        DateTime ancStartDate = null;
        DateTime ancExpireDate = null;
        AlertStatus alertStaus = null;
        Date date = null;        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
			date = format.parse(startDate);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        DateTime start = new DateTime(date);
        if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(24).toPeriod().minusDays(6))) {
            milestone = SCHEDULE_ANC_1;           
            ancStartDate = new DateTime(start);
            
            if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod().minusDays(6)))
                alertStaus = AlertStatus.normal;
            else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(8).toPeriod().minusDays(1)))
            	alertStaus = AlertStatus.upcoming;
            else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(23).toPeriod().minusDays(1)))
            	alertStaus = AlertStatus.urgent;
            else alertStaus = AlertStatus.expired;

            ancExpireDate = new DateTime(start).plusDays(DateTimeDuration.anc1);
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod().minusDays(6))) {
            milestone = SCHEDULE_ANC_2;  
            
            if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(24).toPeriod().minusDays(1)))
                alertStaus = AlertStatus.upcoming;
            else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(31).toPeriod().minusDays(1)))
            	alertStaus = AlertStatus.urgent;
            else alertStaus = AlertStatus.expired;
          
            ancStartDate = new DateTime(start).plusDays(DateTimeDuration.anc2Start);
            ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.anc3End);
        } else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod().minusDays(6))) {
            milestone = SCHEDULE_ANC_3; 
            
            if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(32).toPeriod().minusDays(1)))
                alertStaus = AlertStatus.upcoming;
            else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(35).toPeriod().minusDays(1)))
            	alertStaus = AlertStatus.urgent;
            else alertStaus = AlertStatus.expired;
            
            ancStartDate = new DateTime(start).plusDays(DateTimeDuration.anc3Start);
            ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.anc3End);
        } else if(DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(94).toPeriod().minusDays(5))) {
            milestone = SCHEDULE_ANC_4;
            
            if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(36).toPeriod().minusDays(1)))
                alertStaus = AlertStatus.upcoming;
            else if (DateUtil.isDateWithinGivenPeriodBeforeToday(referenceDateForSchedule, Weeks.weeks(93).toPeriod()))
            	alertStaus = AlertStatus.urgent;
            else alertStaus = AlertStatus.expired;
            
            ancStartDate = new DateTime(start).plusDays(DateTimeDuration.anc4Start);
            ancExpireDate = new DateTime(ancStartDate).plusDays(DateTimeDuration.anc4End);
        } else{
        	
        }

        logger.info(format("Enrolling ANC with Entity id:{0} to ANC schedule, milestone: {1}.", entityId, milestone));
        scheduler.enrollIntoSchedule(entityId, SCHEDULE_ANC, milestone, referenceDateForSchedule.toString());
        
        scheduleLogService.scheduleCloseAndSave(entityId, instanceId, provider, SCHEDULE_ANC, milestone, BeneficiaryType.mother, alertStaus, ancStartDate, ancExpireDate);
        
        
    }
    
    public void fullfillSchedule(String caseID, String scheduleName, String instanceId, long timestamp){
    	try{
	    	scheduleLogService.fullfillSchedule(caseID, scheduleName, instanceId, timestamp);
	    	logger.info("fullfillSchedule a Schedule with id : "+caseID);
    	}catch(Exception e){
    		logger.info("Does not fullfill a schedule:"+e.getMessage());
    	}
    }
    
    public void unEnrollFromAllSchedules(String entityId) {
        scheduler.unEnrollFromAllSchedules(entityId);
    }

    private void unEnrollFromSchedule(String entityId, String anmId, String scheduleName) {
        logger.info(format("Un-enrolling ANC with Entity id:{0} from schedule: {1}", entityId, scheduleName));
        scheduler.unEnrollFromSchedule(entityId, anmId, scheduleName);
    }
    public void fullfillMilestone(String entityId, String providerId, String scheduleName,LocalDate completionDate ){
    	try{
    		scheduler.fullfillMilestone(entityId, providerId, scheduleName, completionDate);
    		logger.info("fullfillMilestone with id: :"+entityId);
    	}catch(Exception e){
    		logger.info("Does not a fullfillMilestone :"+e.getMessage());
    	}
    }
    public void enrollANCSchedule(){
    	
    }

}
