/**
 * @author proshanto
 * */
package org.opensrp.register.mcare.service.scheduling;

import static java.text.MessageFormat.format;
import static org.opensrp.dto.AlertStatus.normal;
import static org.opensrp.dto.BeneficiaryType.mother;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.MilestoneFulfillment;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants;
import org.opensrp.connector.openmrs.service.OpenmrsSchedulerService;
import org.opensrp.connector.openmrs.service.OpenmrsService;
import org.opensrp.connector.openmrs.service.OpenmrsUserService;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.Defination;
import org.opensrp.scheduler.HealthSchedulerService;
import org.opensrp.scheduler.Rule;
import org.opensrp.scheduler.ScheduleLog;
import org.opensrp.scheduler.ScheduleRules;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.repository.AllReportActions;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.opensrp.scheduler.service.ReportActionService;
import org.opensrp.scheduler.repository.ScheduleRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service

public class ScheduleLogService extends OpenmrsService{	
	private static Logger logger = LoggerFactory.getLogger(ScheduleLogService.class.toString());
	private ReportActionService reportActionService;
	private final AllEnrollmentWrapper allEnrollments;
	private AllReportActions allReportActions;
	private AllActions allActions;
	private OpenmrsUserService userService;
	private HealthSchedulerService scheduler;
	private OpenmrsSchedulerService openmrsSchedulerService;
	private AllMothers allMothers;
	private ScheduleRuleRepository scheduleRuleRepository;
	
	@Autowired
	public ScheduleLogService(ReportActionService reportActionService,AllEnrollmentWrapper allEnrollments,AllReportActions allReportActions,AllActions allActions,OpenmrsUserService userService,HealthSchedulerService scheduler,OpenmrsSchedulerService openmrsSchedulerService,AllMothers allMothers,ScheduleRuleRepository scheduleRuleRepository){
		this.reportActionService = reportActionService;
		this.allEnrollments = allEnrollments;
		this.allReportActions = allReportActions;
		this.allActions = allActions;
		this.userService = userService;
		this.scheduler = scheduler;
		this.openmrsSchedulerService = openmrsSchedulerService;
		this.allMothers = allMothers;
		this.scheduleRuleRepository = scheduleRuleRepository;
	}
	
	/**
	 * @author proshanto
	 * @desc This method save scheduleLog
	 * @param beneficiaryType Type of Beneficiary
	 * @param caseID  Beneficiary CaseId
	 * @param instanceId 
	 * @param anmIdentifier user name of field worker
	 * @param  scheduleName 
	 * @param alertStatus type of current window status
	 * @param visitCode current milestone name
	 * @param startDate Schedule start date
	 * @param expiryDate Schedule expired date
	 * 
	 * @return nothing to return
	 * 
	 * 
	 * */
	
	public void saveScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate,String immediateScheduleName,long timeStamp){
		List<Enrollment> el = null;
		List<Action> alertActions = new ArrayList<Action>();
		if(!immediateScheduleName.equalsIgnoreCase("")){
			el =this.findEnrollmentByCaseIdAndScheduleName(caseID,immediateScheduleName);			 
			
		}else{
			el =this.findEnrollmentByCaseIdAndScheduleName(caseID,scheduleName);
			
		}
		String trackId = "";
		String motherId = "";
		
		if(mother.equals(beneficiaryType)){
			Mother mother = allMothers.findByCaseId(caseID);			
			motherId = mother.getRelationalid();
		}
		
		
		for (Enrollment e : el){
			try{
				alertActions.add(new Action(caseID, anmIdentifier, ActionData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate)));
				trackId = this.saveEnrollDataToOpenMRSTrack(e,alertActions,motherId);
			}catch(Exception ex){
				logger.info(""+ex.toString());
			}
		}
		
		if(trackId.equalsIgnoreCase("")){
			trackId = "";
		}		
		try{
			reportActionService.alertForReporting(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate,null,trackId,timeStamp);
			logger.info("ScheduleLog created with id case id :"+caseID);			
			
		}catch(Exception e){
			logger.info("ScheduleLog Does not create:"+e.getMessage());
		}
		
	}
	
	public  List<Enrollment> findEnrollmentByCaseIdAndScheduleName(String caseID,String scheduleName ){
		return  allEnrollments.findByEnrollmentByExternalIdAndScheduleName(caseID,scheduleName);
	}
	
	
	public String saveEnrollDataToOpenMRSTrack(Enrollment el,List<Action> alertActions,String motherId){
	
		try {			
			JSONObject t = openmrsSchedulerService.createTrack(el, alertActions,motherId);
			//e.setStatus(EnrollmentStatus.COMPLETED);
			Map<String, String> metadata = new HashMap<>();
			metadata.put(OpenmrsConstants.ENROLLMENT_TRACK_UUID, t.getString("uuid"));
			el.setMetadata(metadata );
			openmrsSchedulerService.updateTrack(el, alertActions);
			logger.info("Openmrs Track Created...::");
			return t.getString("uuid");
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			logger.info("Log:"+e2.toString());
			return "";
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			logger.info("Log:"+e2.toString());
			return "";
		}
		
		
		
	}
	 private ActionData alert(String schedule, String milestone) {
	        return ActionData.createAlert(mother, schedule, milestone, normal, DateTime.now(), DateTime.now().plusDays(3));
	    }
	public void closeSchedule(String caseId,String instanceId,long timestamp,String name){
		        
        try{
        	ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(timestamp,caseId,name);
    		schedule.setRevision(schedule.getRevision());
            schedule.scheduleCloseDate(new DateTime());
            schedule.closeById(instanceId);
            schedule.setIsActionActive(false);
        	allReportActions.update(schedule);
        	logger.info("ScheduleLog close with id case id :"+caseId +" InstanceId: "+instanceId);
        }catch(Exception e){
        	logger.info("ScheduleLog Data not found.:"+e.getMessage());
        }
		
	}
	public void closeScheduleAndScheduleLog(String caseId,String instanceId,String scheduleName,String provider){
		try{
			List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, scheduleName);
			if(beforeNewActions.size() > 0){ 
				this.closeSchedule(caseId,instanceId,beforeNewActions.get(0).timestamp(),scheduleName);
			}
			
		}catch(Exception e){
			 logger.info("From closeScheduleAndScheduleLog:"+e.getMessage());
		}
	}
	
	public void saveActionDataToOpenMrsMilestoneTrack( Enrollment el,List<Action> alertActions) throws ParseException{
		try {
			openmrsSchedulerService.createTrack(el, alertActions,"");
			logger.info("Data sent to Track MileStone ");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private Action getClosedAction(String milestone, List<Action> actions){
		for (Action a : actions) {
			if(a.data().get("visitCode") != null && a.data().get("visitCode").equalsIgnoreCase(milestone)
					&& a.actionType().equalsIgnoreCase("closeAlert")){
				return a;
			}
		}
		return null;
	}
	private MilestoneFulfillment getMilestone(String milestone, Enrollment e) {
		for (MilestoneFulfillment m : e.getFulfillments()) {
			if(m.getMilestoneName().equalsIgnoreCase(milestone)){
				return m;
			}
		}
		return null;
	}
	
	public void fullfillSchedule(String caseID, String scheduleName, String instanceId,long timestamp){
		reportActionService.schedulefullfill(caseID, scheduleName, instanceId, timestamp);
		
	}
	
	/**
	 * @desc This method create new  Immediate scheduleLog and Schedule for PSRF & BNF
	 * @param entityId form entiry id
	 * @param instanceId form instanceId
	 * @param provider 
	 * @param ScheduleName  current ScheduleName
	 * @param milestoneName current milestoneName
	 * @param beneficiaryType various beneficiaryType such as elco ,household
	 * @param alertStaus Schedule alert status
	 * @param startDate Schedule startDate
	 * @param expiredDate Schedule expiredDate
	 * @return nothing
	 * */
	public void createImmediateScheduleAndScheduleLog(String caseId, String date,String provider,String instanceId,BeneficiaryType beneficiaryType,String scheduleName,Integer durationInHour,String ImmediateScheduleName){
		try{
			allActions.addOrUpdateAlert(new Action(caseId, provider, ActionData.createAlert(beneficiaryType, scheduleName, scheduleName, AlertStatus.upcoming, new DateTime(), new DateTime().plusHours(durationInHour))));	    
			List<Action> existingAlerts = allActions.findAlertByANMIdEntityIdScheduleName(provider, caseId, scheduleName);
			if(existingAlerts.size() > 0){ 
				this.saveScheduleLog(beneficiaryType, caseId, instanceId, provider, scheduleName, scheduleName, AlertStatus.upcoming, new DateTime(), new DateTime().plusHours(durationInHour), ImmediateScheduleName,existingAlerts.get(0).timestamp());
				logger.info("Create a Schedule Log with id :"+caseId);
			}
				
		}catch(Exception e){
		    logger.info("From createImmediateScheduleAndScheduleLog:"+e.getMessage());
		}		
	}
	
	/**
	 * @desc This method create new  scheduleLog and unreroll or complete  Immediate Schedule
	 * @param entityId form entiry id
	 * @param instanceId form instanceId
	 * @param provider 
	 * @param ScheduleName  current ScheduleName
	 * @param milestoneName current milestoneName
	 * @param beneficiaryType various beneficiaryType such as elco ,household
	 * @param alertStaus Schedule alert status
	 * @param startDate Schedule startDate
	 * @param expiredDate Schedule expiredDate
	 * @return nothing
	 * */
	
	public void createNewScheduleLogandUnenrollImmediateSchedule(String caseId, String date,String provider,String instanceId,String immediateScheduleName,String scheduleName,BeneficiaryType beneficiaryType,Integer durationInHour){
		try{
			//scheduler.unEnrollFromScheduleimediate(caseId, provider, immediateScheduleName);
			scheduler.fullfillMilestoneAndCloseAlert(caseId, provider, immediateScheduleName, new LocalDate());
		}catch(Exception e){
			logger.info(format("Failed to COmplete Immediate BNF Schedule:"+ e.getMessage()));
		}
		
		this.scheduleCloseAndSave(caseId, instanceId, provider, scheduleName, scheduleName, beneficiaryType, AlertStatus.normal, new DateTime(), new DateTime().plusHours(durationInHour));
		
	}
	
	/**
	 * @desc This method close related previous scheduleLog and create new ScheduleLog 
	 * @param entityId form entiry id
	 * @param instanceId form instanceId
	 * @param provider 
	 * @param ScheduleName  current ScheduleName
	 * @param milestoneName current milestoneName
	 * @param beneficiaryType various beneficiaryType such as elco ,household
	 * @param alertStaus Schedule alert status
	 * @param startDate Schedule startDate
	 * @param expiredDate Schedule expiredDate
	 * @return nothing
	 * */
	public void scheduleCloseAndSave(String entityId,String instanceId,String provider,String ScheduleName,String milestoneName,BeneficiaryType beneficiaryType,AlertStatus alertStaus, DateTime startDate, DateTime expiredDate){
		try{
			List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(provider, entityId, ScheduleName);
			if(beforeNewActions.size() > 0){ 
			 this.closeSchedule(entityId,instanceId,beforeNewActions.get(0).timestamp(),ScheduleName);
			 logger.info("close a schedule with id : "+entityId);
			}
			
		}catch(Exception e){
			logger.info("From scheduleCloseAndSave for close : "+e.getMessage());
		}
		try{
			allActions.addOrUpdateAlert(new Action(entityId, provider, ActionData.createAlert(beneficiaryType, ScheduleName, milestoneName, alertStaus, startDate,  expiredDate)));
			logger.info(format("create psrf from psrf to psrf..", entityId));
			List<Action> afterNewActions = allActions.findAlertByANMIdEntityIdScheduleName(provider, entityId, ScheduleName);
			if(afterNewActions.size() > 0){ 
				this.saveScheduleLog(beneficiaryType, entityId, instanceId, provider, ScheduleName, milestoneName, alertStaus, startDate, expiredDate,ScheduleName,afterNewActions.get(0).timestamp());
				logger.info("create a schedule with id : "+entityId);
			}
			
		}catch(Exception e){
			logger.info("From scheduleCloseAndSave for save: "+e.getMessage());
		}
	}
	
	public String getScheduleRuleForPSRFInHH(String name){
		ScheduleRules scheduleRule = scheduleRuleRepository.findByName(name);	
		String fieldName = "";
		if(scheduleRule != null){			
			for (int i = 0; i < scheduleRule.getRule().size(); i++) {				
				if(scheduleRule.getRule().get(i).getEndFormName().equalsIgnoreCase("psrf_form")){
					for (int j = 0; j < scheduleRule.getRule().get(i).getDefination().size(); j++) {
						if(scheduleRule.getRule().get(i).getDefination().get(j).getName().equalsIgnoreCase("elco")){
							fieldName= scheduleRule.getRule().get(i).getDefination().get(j).getValue();
						}
					}
				}
				
			}
		}
		
		return fieldName;
	}
	
	public String getScheduleRuleForCensus(String name){
		ScheduleRules scheduleRule = scheduleRuleRepository.findByName(name);	
		String fieldName = "";
		if(scheduleRule != null){			
			for (int i = 0; i < scheduleRule.getRule().size(); i++) {				
				if(scheduleRule.getRule().get(i).getEndFormName().equalsIgnoreCase("Cencus")){
					for (int j = 0; j < scheduleRule.getRule().get(i).getDefination().size(); j++) {
						if(scheduleRule.getRule().get(i).getDefination().get(j).getName().equalsIgnoreCase("submission")){
							fieldName= scheduleRule.getRule().get(i).getDefination().get(j).getValue();
						}
					}
				}
				
			}
		}
		
		return fieldName;
	}
	
	public void ancScheduleUnEnroll(String entityId, String  providerId,String scheduleName){
		scheduler.unEnrollFromSchedule(entityId, providerId, scheduleName);
	}
}
