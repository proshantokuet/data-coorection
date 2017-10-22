/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.dto.BeneficiaryType.elco;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.DateTimeDuration.duration;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.*;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.opensrp.common.AllConstants.ELCOSchedulesConstantsImediate;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ELCOScheduleService {
	
	private static Logger logger = LoggerFactory.getLogger(ELCOScheduleService.class.toString());
	private final ScheduleTrackingService scheduleTrackingService;
	private HealthSchedulerService scheduler;
	private AllActions allActions;
	private ScheduleLogService scheduleLogService;
	
	@Autowired
	public ELCOScheduleService(HealthSchedulerService scheduler,ScheduleTrackingService scheduleTrackingService,AllActions allActions,ScheduleLogService scheduleLogService)
	{
		this.scheduler = scheduler;
		this.scheduleTrackingService = scheduleTrackingService;
		this.allActions = allActions;
		this.scheduleLogService = scheduleLogService;
		
	}
	
	public void enrollIntoMilestoneOfMisElco(String caseId, String date)
	{
	    logger.info(format("Enrolling Elco into MisElco schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, MIS_ELCO, date);
	}
	
	public void enrollIntoMilestoneOfPSRF(String caseId, String date,String provider,String instanceId)
	{
	    logger.info(format("Enrolling Elco into PSRF schedule. Id: {0}", caseId));
	    
		scheduler.enrollIntoSchedule(caseId, ELCO_SCHEDULE_PSRF, date);
		scheduleLogService.createNewScheduleLogandUnenrollImmediateSchedule(caseId, date, provider, instanceId, IMD_ELCO_SCHEDULE_PSRF, ELCO_SCHEDULE_PSRF, elco, duration);
		/*try{
			scheduler.unEnrollFromScheduleimediate(caseId, provider, IMD_ELCO_SCHEDULE_PSRF);
		}catch(Exception e){
			logger.info(format("Failed to UnEnrollFromSchedule PSRF"));
		}
		
		scheduleLogService.scheduleCloseAndSave(caseId, instanceId, provider, ELCO_SCHEDULE_PSRF, ELCO_SCHEDULE_PSRF, elco, AlertStatus.normal, new DateTime(), new DateTime().plusHours(duration));
		*/
	}
	public void unEnrollFromScheduleCensus(String caseId, String providerId, String scheduleName){
		//scheduler.unEnrollFromScheduleCensus(caseId, providerId, HH_SCHEDULE_CENSUS);
		try{
			scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, HH_SCHEDULE_CENSUS, new LocalDate());
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		
	}
	
	public void unEnrollFromScheduleOfPSRF(String caseId, String providerId, String scheduleName)
    {
        logger.info(format("Unenrolling Elco from PSRF schedule. Id: {0}", caseId));
        try{
        	//scheduler.unEnrollFromSchedule(caseId, providerId, ELCO_SCHEDULE_PSRF);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, ELCO_SCHEDULE_PSRF, new LocalDate());
        }catch(Exception e){
        	logger.info(format("Failed to UnEnrollFromSchedule PSRF"));
        }
        try{
        	//scheduler.unEnrollFromScheduleimediate(caseId, providerId, IMD_ELCO_SCHEDULE_PSRF);
        	scheduler.fullfillMilestoneAndCloseAlert(caseId, providerId, IMD_ELCO_SCHEDULE_PSRF, new LocalDate());
        }catch(Exception e){
        	logger.info(e.getMessage());
        }
        
    }
	
	private  Date getDateTime(){		
		InputStream input = ELCOScheduleService.class.getClassLoader().getResourceAsStream("imdediate-elco-psrf.json");
		String result = "";
	    try {
	        BufferedReader br = new BufferedReader(new InputStreamReader(input));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	        
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    Date todayDate = new Date();
	    try {
			JSONObject jsonObj = new JSONObject(result);			
			JSONArray milestones = jsonObj.getJSONArray("milestones");
			for (int k = 0; k < milestones.length(); k++) {
				JSONObject jsonObjs = new JSONObject(milestones.getJSONObject(k).getString("scheduleWindows").toString());
				String max = jsonObjs.getString("due").replace("[", "");
				max = max.replace("]", "").replace("Weeks", "").replaceAll("\"", "").trim();
				int weeks = Integer.parseInt(max);
				Date today = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(today);
				calendar.add(Calendar.WEEK_OF_YEAR,weeks);
				todayDate = calendar.getTime();				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
		return todayDate;
	}

	public void imediateEnrollIntoMilestoneOfPSRF(String caseId, String date,String provider,String instanceId)	
	{
	    logger.info(format("Enrolling Elco into PSRF schedule. Id: {0}", caseId));	  
	    scheduler.enrollIntoSchedule(caseId, ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF, date);	 
	    scheduleLogService.createImmediateScheduleAndScheduleLog(caseId, date, provider, instanceId, BeneficiaryType.elco, ELCO_SCHEDULE_PSRF, duration,ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF);
	    
	}
	
}
