/**
 * @author proshanto
 * */
package org.opensrp.scheduler.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import static org.opensrp.common.AllConstants.OpenmrsTrackUuid.ENROLLMENT_TRACK_UUID;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.dto.ScheduleData;
import org.opensrp.scheduler.HookedEvent;
import org.opensrp.scheduler.ScheduleLog;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllReportActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportActionService {
	
	private AllReportActions allReportActions;	
	private  HookedEvent action;
	private final AllEnrollmentWrapper allEnrollments;
	private static Logger logger = LoggerFactory.getLogger(ReportActionService.class
			.toString());
	@Autowired
	public ReportActionService(AllReportActions allReportActions,AllEnrollmentWrapper allEnrollments)
	{
		this.allReportActions = allReportActions;
		this.allEnrollments = allEnrollments;
	}
	
	/**
	 * @desc This method called when motech trigger event and beneficiaryType should be except mother
	 * */
	public void updateScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long BTS,long timestamp){
		try{
			ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(BTS,caseID,scheduleName);
			if(schedule != null){			
				   if(!schedule.getCurrentWindow().equals(alertStatus) ){				   
					   this.updateDataScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, BTS, timestamp, schedule);
				   }else{				   
					   schedule.setRevision(schedule.getRevision());				  
					   schedule.data().get(0).put("expiryDate", expiryDate.toLocalDate().toString());	
					   schedule.setVisitCode(visitCode);
					   schedule.timestamp(timestamp);
				       allReportActions.update(schedule);
				   }
				   logger.info("Update ScheduleLog with id: "+caseID + " in elco or household or BNF type ");
			   }else{
				   this.alertForReporting(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, timestamp);
				   logger.info("Create ScheduleLog with id: "+caseID + " in elco or household or BNF type ");
			   }
			
		}catch(Exception e){
			logger.info("From updateScheduleLog:"+ e.getMessage());
		}
	}
	
	/**
	 * @desc This method called when motech trigger event and beneficiaryType should be  mother
	 * */
	public void updateScheduleLogMotherType(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long BTS,long timestamp){
		
	try{
		ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(BTS,caseID,scheduleName);
		List<Enrollment> el = null;
    	el = allEnrollments.findByEnrollmentByExternalIdAndScheduleName(caseID,scheduleName);
    	
    	
		if(schedule != null){			
			   if(!schedule.getVisitCode().equalsIgnoreCase(visitCode) ){
				   this.updateDataScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, BTS, timestamp, schedule);
			   }else if(schedule.getVisitCode().equalsIgnoreCase(visitCode)){				   
				   if(!schedule.getCurrentWindow().equals(alertStatus)){
					   this.updateDataScheduleLog(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, BTS, timestamp, schedule);
				   }else{
					   schedule.setRevision(schedule.getRevision());				  
					   schedule.data().get(0).put("expiryDate", expiryDate.toLocalDate().toString());	
					   schedule.setVisitCode(visitCode);
					   schedule.timestamp(timestamp);
				       allReportActions.update(schedule);
			       }
			   }else{
				   
			   }
			   logger.info("Update ScheduleLog with id: "+caseID + " in Mother type");
		   }else{
			   this.alertForReporting(beneficiaryType, caseID, instanceId, anmIdentifier, scheduleName, visitCode, alertStatus, startDate, expiryDate, currentWindowCloseDate, trackId, timestamp);
			   logger.info("Create ScheduleLog with id: "+caseID + " in elco or household or BNF type ");
		   }
		
		}catch(Exception e){
			logger.info("From updateScheduleLogMotherType:"+ e.getMessage());
		}
	}
    /**
     * @desc This method is used to create new scheduleLog when register form submit
     * */
	public void alertForReporting(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long timeStamp)
	{	  
		allReportActions.addAlert(new ScheduleLog(caseID, instanceId, anmIdentifier, ScheduleData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate),trackId,alertStatus,currentWindowCloseDate,startDate,expiryDate,scheduleName,timeStamp,visitCode));
	   
	}
	
	public void setEvent(HookedEvent action){
		this.action = action;
	}
	
	/**
	 * @desc update map data
	 * */
	public void updateDataScheduleLog(BeneficiaryType beneficiaryType, String caseID, String instanceId, String anmIdentifier, String scheduleName, String visitCode, AlertStatus alertStatus, DateTime startDate, DateTime expiryDate, DateTime currentWindowCloseDate,String trackId,long BTS,long timestamp ,ScheduleLog  schedule){
		Map<String, String > mapData = new HashMap<>();
		mapData.put("beneficiaryType", beneficiaryType.value());
    	mapData.put("scheduleName", scheduleName);
    	mapData.put("visitCode", visitCode);
    	mapData.put("alertStatus", alertStatus.value());
    	mapData.put("startDate", startDate.toLocalDate().toString());
    	mapData.put("expiryDate", expiryDate.toLocalDate().toString());	    	
    	schedule.data().add(mapData);
    	schedule.setRevision(schedule.getRevision());
    	schedule.currentWindow(alertStatus);
    	schedule.currentWindowStartDate(startDate);
    	schedule.currentWindowEndDate(expiryDate);			    	
    	schedule.timestamp(timestamp);
    	schedule.setVisitCode(visitCode);
    	allReportActions.update(schedule);
    	
    	List<Enrollment> el = null;
    	el = allEnrollments.findByEnrollmentByExternalIdAndScheduleName(caseID,scheduleName);
    	
    	for (Enrollment e : el){
    		Map<String, String> metadata = new HashMap<>();
    		metadata.put(ENROLLMENT_TRACK_UUID, schedule.trackId());
		 	e.setMetadata(metadata );
	    	List<Action> alertActions = new ArrayList<Action>();
	    	alertActions.add(new Action(caseID, anmIdentifier, ActionData.createAlert(beneficiaryType, scheduleName, visitCode, alertStatus, startDate, expiryDate)));
	    	action.scheduleSaveToOpenMRSMilestone(e,alertActions );
	    	
    	}
	}
	
	public void schedulefullfill(String caseID,String scheduleName,String instanceId,long timestamp){
		try{
			ScheduleLog  schedule = allReportActions.findByTimestampIdByCaseIdByname(timestamp,caseID,scheduleName);		
			int size = schedule.data().size();
			schedule.data().get(size-1).put("fullfillmentDate",new LocalDate().toString());		
			schedule.data().get(size-1).put("fullfuillBySubmission",instanceId);		
			schedule.setRevision(schedule.getRevision());
			schedule.timestamp(timestamp);
			allReportActions.update(schedule);
			logger.info("Schedule fullfill with id: "+caseID );
		}catch(Exception e){
			logger.info("From schedulefullfill:"+e.getMessage());
		}
		
	}
	
	
}
