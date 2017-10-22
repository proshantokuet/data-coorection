package org.opensrp.connector.openmrs.service;

import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.SCHEDULE_BNF_IME;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.domain.MilestoneFulfillment;
import org.opensrp.common.AllConstants.BnfFollowUpVisitFields;
import org.opensrp.common.AllConstants.ELCOSchedulesConstantsImediate;
import org.opensrp.connector.HttpUtil;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants;
import org.opensrp.scheduler.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenmrsSchedulerService extends OpenmrsService{
	
	private static final String TRACK_URL = "ws/rest/v1/scheduletracker/track";
	private static final String TRACK_MILESTONE_URL = "ws/rest/v1/scheduletracker/trackmilestone";
	private static final String SCHEDULE_URL = "ws/rest/v1/scheduletracker/schedule";
	private static final String MILESTONE_URL = "ws/rest/v1/scheduletracker/milestone";
	private static Logger logger = LoggerFactory.getLogger(OpenmrsSchedulerService.class.toString());

	private OpenmrsUserService userService;
	private PatientService patientService;

	public OpenmrsUserService getUserService() {
		return userService;
	}

	public void setUserService(OpenmrsUserService userService) {
		this.userService = userService;
	}

	public PatientService getPatientService() {
		return patientService;
	}

	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}
	
	@Autowired
	public OpenmrsSchedulerService(OpenmrsUserService userService,PatientService patientService) {
		this.userService = userService;
		this.patientService = patientService;
	}

    public OpenmrsSchedulerService(String openmrsUrl, String user, String password) {
    	super(openmrsUrl, user, password);
    }
    public String parseScheduleName(String scheduleName){
    	if(scheduleName.equalsIgnoreCase(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF)){
    		return scheduleName.replace(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF, ELCOSchedulesConstantsImediate.ELCO_SCHEDULE_PSRF);
    	}else{
    		return scheduleName.replace(SCHEDULE_BNF_IME, BnfFollowUpVisitFields.SCHEDULE_BNF);
    	}
    	
    }
	
    public JSONObject createTrack(Enrollment e, List<Action> alertActions,String motherId) 
	{
    	try{
	    	JSONObject t = new JSONObject();    	
			
			try{
				JSONObject po = patientService.getPatientByIdentifier(e.getExternalId());
				t.put("beneficiary", po.getJSONObject("person").getString("uuid"));
			}catch(Exception eee){
				if(!motherId.equalsIgnoreCase("")){
				JSONObject po = patientService.getPatientByIdentifier(motherId);
				t.put("beneficiary", po.getJSONObject("person").getString("uuid"));
				logger.info("Patient UUID: "+po.getJSONObject("person").getString("uuid"));
				
				}
				logger.info(eee.getMessage());
			}		
			t.put("beneficiaryRole", alertActions!=null&&alertActions.size()>0?alertActions.get(0).data().get("beneficiaryType"):null);
			t.put("schedule", parseScheduleName(e.getScheduleName()));
			String hr = StringUtils.leftPad(e.getPreferredAlertTime().getHour().toString(),2,"0");
			String mn = StringUtils.leftPad(e.getPreferredAlertTime().getMinute().toString(),2,"0");
			t.put("preferredAlertTime", hr+":"+mn+":00");
			t.put("referenceDate", OPENMRS_DATE.format(e.getStartOfSchedule().toDate()));
			t.put("referenceDateType", "MANUAL");
			t.put("dateEnrolled", OPENMRS_DATE.format(e.getEnrolledOn().toDate()));		
			t.put("currentMilestone", parseScheduleName(e.getCurrentMilestoneName()));
			t.put("status", e.getStatus().name());
			String trackuuid = null;
			JSONObject to = null;
			
			if(!e.getMetadata().containsKey("openmrsTrackUuid")){				
				logger.info("track post json:"+t.toString());
				try{
					to = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_URL, "", t.toString(), OPENMRS_USER, OPENMRS_PWD).body());
					trackuuid = to.getString("uuid");
				}catch(Exception ex){
					logger.info(""+ex.toString());
				}
				
			}else{				
				trackuuid = e.getMetadata().get(OpenmrsConstants.ENROLLMENT_TRACK_UUID);
				logger.info("ENROLLMENT_TRACK_UUID:"+e.getMetadata().get(OpenmrsConstants.ENROLLMENT_TRACK_UUID));
			}
			JSONArray tmarr = new JSONArray();
			for (Action ac : alertActions) {
				
				if(ac.actionType().equalsIgnoreCase("createAlert")){
					JSONObject tm = fromActionToTrackMilestone(false, ac, trackuuid, e, alertActions);
					try{
					JSONObject tmo = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_MILESTONE_URL, "", tm.toString(), OPENMRS_USER, OPENMRS_PWD).body());
					}catch(Exception ext){
						logger.info(ext.toString());
					}
									
				}
				
			}
			
			for (MilestoneFulfillment f : e.getFulfillments()) {
				if(getCreatedAction(f.getMilestoneName(), alertActions) == null){
					JSONObject tm = fromMilestoneToTrackMilestone(false, f, trackuuid, e, alertActions);
					JSONObject tmo = null;
					try{
					 tmo = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_MILESTONE_URL, "", tm.toString(), OPENMRS_USER, OPENMRS_PWD).body());
					}catch(Exception extm){
						logger.info(extm.toString());
					}
					tmarr.put(tmo);
				}
			}
			if(tmarr.length()!=0 ){	
				to.put("trackMilestones", tmarr);
			}
			return to;
    	}catch(Exception ep){
    		ep.printStackTrace();
    		logger.info(ep.getMessage());
    		return null;
    	}
	}
	
    public void createTrackMilestone(List<Action> alertActions,String trackuuid,Enrollment e){
    	try{
    	JSONArray tmarr = new JSONArray();
			for (Action ac : alertActions) {
				if(ac.actionType().equalsIgnoreCase("createAlert")){
					
					JSONObject tm = fromActionToTrackMilestone(false, ac, trackuuid, e, alertActions);
					
					JSONObject tmo = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_MILESTONE_URL, "", tm.toString(), OPENMRS_USER, OPENMRS_PWD).body());
					
					tmarr.put(tmo);
					
				}
			}
    	}catch(Exception ex){
    		logger.info(":"+ex.getMessage());
    	}
		
    }
	public JSONObject updateTrack(Enrollment e, List<Action> alertActions) throws JSONException, ParseException
	{
		JSONObject t = new JSONObject();
		t.put("beneficiaryRole", alertActions!=null&&alertActions.size()>0?alertActions.get(0).data().get("beneficiaryType"):null);
		String hr = StringUtils.leftPad(e.getPreferredAlertTime().getHour().toString(),2,"0");
		String mn = StringUtils.leftPad(e.getPreferredAlertTime().getMinute().toString(),2,"0");
		t.put("preferredAlertTime", hr+":"+mn+":00");
		t.put("currentMilestone", parseScheduleName(e.getCurrentMilestoneName()));
		t.put("status", e.getStatus().name());
		
		JSONObject to = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_URL+"/"+e.getMetadata().get(OpenmrsConstants.ENROLLMENT_TRACK_UUID), "", t.toString(), OPENMRS_USER, OPENMRS_PWD).body());
		String trackuuid = to.getString("uuid");
		
		JSONArray tmarr = new JSONArray();
		for (Action ac : alertActions) {
			if(ac.actionType().equalsIgnoreCase("createAlert")){
				String milestone = ac.data().get("visitCode");
				JSONArray j = new JSONObject(HttpUtil.get(getURL()+"/"+TRACK_MILESTONE_URL, "v=full&track="+trackuuid+"&milestone="+milestone, OPENMRS_USER, OPENMRS_PWD).body()).getJSONArray("results");
				JSONObject tm = fromActionToTrackMilestone(j.length()>0, ac, trackuuid, e, alertActions);
	
				String uuid = j.length()>0?j.getJSONObject(0).getString("uuid"):"";
				JSONObject tmo = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_MILESTONE_URL+"/"+uuid, "", tm.toString(), OPENMRS_USER, OPENMRS_PWD).body());
			
				tmarr.put(tmo);
			}
		}
		for (MilestoneFulfillment f : e.getFulfillments()) {
			if(getCreatedAction(f.getMilestoneName(), alertActions) == null){
				String milestone = f.getMilestoneName();
				JSONArray j = new JSONObject(HttpUtil.get(getURL()+"/"+TRACK_MILESTONE_URL, "v=full&track="+trackuuid+"&milestone="+milestone, OPENMRS_USER, OPENMRS_PWD).body()).getJSONArray("results");
				JSONObject tm = fromMilestoneToTrackMilestone(j.length()>0, f, trackuuid, e, alertActions);
				
				String uuid = j.length()>0?j.getJSONObject(0).getString("uuid"):"";
				JSONObject tmo = new JSONObject(HttpUtil.post(getURL()+"/"+TRACK_MILESTONE_URL+"/"+uuid, "", tm.toString(), OPENMRS_USER, OPENMRS_PWD).body());
			
				tmarr.put(tmo);
			}
		}
		
		t.put("trackMilestones", tmarr);
		return t;
	}
	
	private JSONObject fromActionToTrackMilestone(boolean isUpdate, Action ac, String trackUuid, Enrollment e, List<Action> alertActions) throws JSONException, ParseException {
		JSONObject tm = new JSONObject();
		String milestone = ac.data().get("visitCode");
		if(!isUpdate){
			JSONObject pr = userService.getPersonByUser(ac.anmIdentifier());
			tm.put("track", trackUuid);
			tm.put("milestone", parseScheduleName(milestone) );
			tm.put("alertRecipient", pr.getString("uuid"));
			tm.put("alertRecipientRole", "PROVIDER");
		}
		Action close = getClosedAction(milestone, alertActions);
		MilestoneFulfillment m = getMilestone(milestone, e);
		String fdate = m == null?null:OPENMRS_DATE.format(m.getFulfillmentDateTime().toDate());
		if(fdate == null){
			fdate = close==null?null:OPENMRS_DATE.format(new SimpleDateFormat("dd-MM-yyyy").parse(close.data().get("completionDate")));
		}
		tm.put("fulfillmentDate", fdate);
		tm.put("status", ac.data().get("alertStatus")+(close==null?"":"-completed"));
		//tm.put("status", e.getStatus().name());
		//TODO tm.put("reasonClosed", ac.data().get(""));
		tm.put("alertStartDate", ac.data().get("startDate"));
		tm.put("alertExpiryDate", ac.data().get("expiryDate"));
		tm.put("isActive", ac.getIsActionActive());
		tm.put("actionType", "PROVIDER ALERT");
		return tm;
	}
	
	private JSONObject fromMilestoneToTrackMilestone(boolean isUpdate, MilestoneFulfillment m, String trackUuid, Enrollment e, List<Action> alertActions) throws JSONException, ParseException {
		JSONObject tm = new JSONObject();
		if(!isUpdate){
			JSONObject pr = userService.getPersonByUser("daemon");
			tm.put("track", trackUuid);
			tm.put("milestone", m.getMilestoneName());
			tm.put("alertRecipient", pr.getString("uuid"));
			tm.put("alertRecipientRole", "PROVIDER");
		}

		Action close = getClosedAction(m.getMilestoneName(), alertActions);
		String fdate = m == null?null:OPENMRS_DATE.format(m.getFulfillmentDateTime().toDate());
		if(fdate == null){
			fdate = close==null?null:OPENMRS_DATE.format(new SimpleDateFormat("dd-MM-yyyy").parse(close.data().get("completionDate")));
		}
		tm.put("fulfillmentDate", fdate);
		tm.put("status", "FULFILLED");
		//TODO tm.put("reasonClosed", ac.data().get(""));
		tm.put("alertStartDate", OPENMRS_DATE.format(new Date(0L)));
		tm.put("alertExpiryDate", OPENMRS_DATE.format(new Date(0L)));
		tm.put("isActive", false);
		tm.put("actionType", "PROVIDER ALERT MISSING");
		return tm;
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
	
	private Action getCreatedAction(String milestone, List<Action> actions){
		for (Action a : actions) {
			if(a.data().get("visitCode") != null && a.data().get("visitCode").equalsIgnoreCase(milestone)
					&& a.actionType().equalsIgnoreCase("createAlert")){
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
}