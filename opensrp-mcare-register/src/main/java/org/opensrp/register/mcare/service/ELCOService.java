/**
 * The ELCOService class implements ELCO registry, Census Enrollment and PSRF schedule. 
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.*;
import static org.opensrp.common.AllConstants.Form.ELCO_REGISTRATION;
import static org.opensrp.common.AllConstants.Form.MIS_Census;
import static org.opensrp.common.AllConstants.HHRegistrationFields.ELCO_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FWCENDATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FWPSRDATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_UPAZILLA;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.existing_location;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISBCSOURCE;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISCONDGIVENDATE;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISCONDGIVENNO;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISELCODATE;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISIMPT;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISINJGIVENDATE;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISINJNEXTDATE;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISINJNEXTDATECALC;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISOPT;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISPILLGIVENDATE;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISPILLGIVENNO;
import static org.opensrp.common.AllConstants.PSRFFields.FWMISPMDATE;
import static org.opensrp.common.AllConstants.PSRFFields.FWNOTELIGIBLE;
import static org.opensrp.common.AllConstants.PSRFFields.FWPMISBIRTHCTRL;
import static org.opensrp.common.AllConstants.PSRFFields.FW_CONFIRMATION;
import static org.opensrp.common.AllConstants.PSRFFields.FW_FLAGVALUE;
import static org.opensrp.common.AllConstants.PSRFFields.FW_HRP;
import static org.opensrp.common.AllConstants.PSRFFields.FW_HR_PSR;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRANM;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRDATE;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRDBT;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSREVRPREG;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRHBP;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRHGT;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRHHLAT;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRHHRICE;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRHUSPREGWTD;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRLMP;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRMUAC;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRNBDTH;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPHONE;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPHONENUM;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPRCHECKS;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPREGSTS;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPREGTWYRS;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPREGWTD;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPRMC;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPRSB;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRPRVPREGCOMP;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRSTS;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRTHY;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRTOTBIRTH;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRVDGMEM;
import static org.opensrp.common.AllConstants.PSRFFields.FW_PSRWOMEDU;
import static org.opensrp.common.AllConstants.PSRFFields.FW_SORTVALUE;
import static org.opensrp.common.AllConstants.PSRFFields.FW_VG;
import static org.opensrp.common.AllConstants.PSRFFields.clientVersion;
import static org.opensrp.common.AllConstants.PSRFFields.current_formStatus;
import static org.opensrp.common.AllConstants.PSRFFields.mis_elco_current_formStatus;
import static org.opensrp.common.AllConstants.PSRFFields.timeStamp;
import static org.opensrp.common.AllConstants.UserType.FD;
import static org.opensrp.common.AllConstants.PSRFFields.*;

import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ELCOSchedulesConstants.ELCO_SCHEDULE_PSRF;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.HHSchedulesConstants.HH_SCHEDULE_CENSUS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.scheduling.ELCOScheduleService;
import org.opensrp.register.mcare.service.scheduling.HHSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ELCOService {
	private static Logger logger = LoggerFactory.getLogger(ELCOService.class.toString());

	private AllHouseHolds allHouseHolds;
	private AllElcos allEcos;
	private HHSchedulesService hhSchedulesService;
	private ELCOScheduleService elcoScheduleService;
	private ANCService ancService;
	private BNFService bnfService;
	private ScheduleLogService scheduleLogService;
	private AllActions allActions;

	@Autowired
	public ELCOService(AllHouseHolds allHouseHolds, AllElcos allEcos, HHSchedulesService hhSchedulesService, ELCOScheduleService elcoScheduleService,
			ANCService ancService, BNFService bnfService, ScheduleLogService scheduleLogService, AllActions allActions) {
		this.allHouseHolds = allHouseHolds;
		this.allEcos = allEcos;
		this.hhSchedulesService = hhSchedulesService;
		this.elcoScheduleService = elcoScheduleService;
		this.ancService = ancService;
		this.bnfService = bnfService;
		this.scheduleLogService = scheduleLogService;
		this.allActions = allActions;

	}

	public void registerELCO(FormSubmission submission) {

		SubFormData subFormData = submission.getSubFormByName(ELCO_REGISTRATION_SUB_FORM_NAME);

		for (Map<String, String> elcoFields : subFormData.instances()) {
			
			String UPAZILA = elcoFields.get(FW_WOMUPAZILLA);
			if(UPAZILA!=null && UPAZILA.contains("+")) UPAZILA.replace("+", " ");
			
			Elco elco = allEcos.findByCaseId(elcoFields.get(ID)).withINSTANCEID(submission.instanceId()).withPROVIDERID(submission.anmId())
					.withTODAY(submission.getField(REFERENCE_DATE))
					.withSUBMISSIONDATE(DateUtil.getTimestampToday())
					.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)))
					.setTimeStamp(System.currentTimeMillis())
					.withexternal_user_ID(submission.getField(external_user_ID))
					.withuser_type(submission.getField(user_type))					
					.withFWWOMUPAZILLA(UPAZILA);

			addDetailsToElco(submission, subFormData, elco);
			
			if(elco.details().containsKey(FWPSRPREGSTS)){
	    		if(elco.details().get("FWPSRPREGSTS") ==null){	    			
	    			elco.withFWPSRPREGSTS("");
	    		}else{
	    			elco.withFWPSRPREGSTS(elco.details().get("FWPSRPREGSTS"));
	    		}
	    		
	    	}else{
	    		elco.withFWPSRPREGSTS("");
	    	}
			
			if (elcoFields.containsKey(FW_WOMFNAME)) {
				allEcos.update(elco);
				logger.info("Elco updated");
			} else {
				allEcos.remove(elco);
				logger.info("Elco removed");
			}

			String fieldName = "FWWOMFNAME";
			logger.info("FieldName:" + fieldName);
			if (!fieldName.equalsIgnoreCase("")) {
				if (elcoFields.containsKey(fieldName)) {
					if (!elcoFields.get(fieldName).equalsIgnoreCase("") || elcoFields.get(fieldName) != null) {
						elcoScheduleService.imediateEnrollIntoMilestoneOfPSRF(elcoFields.get(ID), submission.getField(REFERENCE_DATE), submission.anmId(),
								submission.instanceId());
					}
				} else {

					logger.info("Variable not found which is defined in rule defination for elco");
				}
			}
		}

		if (submission.formName().equalsIgnoreCase(ELCO_REGISTRATION)) {

			HouseHold houseHold = allHouseHolds.findByCaseId(submission.entityId());

			if (houseHold == null) {
				logger.warn(format("Failed to handle Census form as there is no household registered with ID: {0}", submission.entityId()));
				return;
			}
			
			String UPAZILLA = submission.getField(FW_UPAZILLA);
			if(UPAZILLA!=null && UPAZILLA.contains("+")) UPAZILLA.replace("+", " ");	

			addELCODetailsToHH(submission, subFormData, houseHold);

			houseHold.withPROVIDERID(submission.anmId());
			houseHold.withINSTANCEID(submission.instanceId());
			houseHold.withFWUPAZILLA(UPAZILLA);

			houseHold.details().put(existing_ELCO, submission.getField(existing_ELCO));
			houseHold.details().put(new_ELCO, submission.getField(new_ELCO));
			houseHold.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
			allHouseHolds.update(houseHold);

			logger.info("Expected value leading non zero and found FWCENSTAT : " + submission.getField("FWCENSTAT"));
			if (submission.getField("FWCENSTAT").equalsIgnoreCase("7")) {
				// user type condition
				if(submission.getField("user_type").equalsIgnoreCase(FD)){
					elcoScheduleService.unEnrollFromScheduleCensus(submission.entityId(), submission.anmId(), "");
					try {
						List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
								HH_SCHEDULE_CENSUS);
						if (beforeNewActions.size() > 0) {
							scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
									HH_SCHEDULE_CENSUS);
							logger.info("Create a Schedule Log with id : " + submission.entityId());
						}
	
					} catch (Exception e) {
						logger.info("From registerELCO: " + e.getMessage());
					}
				
				}

			} else {
				hhSchedulesService.enrollIntoMilestoneOfCensus(submission.entityId(), submission.getField(FWCENDATE), submission.anmId(),
						submission.instanceId());
			}
		}
	}

	public void misCensus(FormSubmission submission) {

		if (submission.formName().equalsIgnoreCase(MIS_Census)) {

			Elco elco = allEcos.findByCaseId(submission.entityId());

			if (elco == null) {
				logger.warn(format("Failed to handle MISCensus form as there is no elco registered with ID: {0}", submission.entityId()));
				return;
			}
			elco.details().put("MisToday", submission.getField(REFERENCE_DATE));
			elco.details().put("MisStart", submission.getField(START_DATE));
			elco.details().put("MisEnd", submission.getField(END_DATE));
			elco.details().put("FWMISCENSUSDATE", submission.getField("FWMISCENSUSDATE"));
			elco.details().put("FWCOUPLENUM", submission.getField("FWCOUPLENUM"));
			elco.details().put("FWTETSTAT", submission.getField("FWTETSTAT"));
			elco.details().put("FWMARRYDATE", submission.getField("FWMARRYDATE"));
			elco.details().put("FWCHILDALIVEB", submission.getField("FWCHILDALIVEB"));
			elco.details().put("FWCHILDALIVEG", submission.getField("FWCHILDALIVEG"));
			elco.details().put("ELCO", submission.getField("ELCO"));
			elco.details().put("GOBHHID", submission.getField("GOBHHID"));
			elco.details().put("JiVitAHHID", submission.getField("JiVitAHHID"));
			elco.details().put("FWCOUNTRY", submission.getField("FWCOUNTRY"));
			elco.details().put("FWDIVISION", submission.getField("FWDIVISION"));
			elco.details().put("FWDISTRICT", submission.getField("FWDISTRICT"));
			elco.details().put("FWUPAZILLA", submission.getField("FWUPAZILLA"));
			elco.details().put("FWUNION", submission.getField("FWUNION"));
			elco.details().put("FWWARD", submission.getField("FWWARD"));
			elco.details().put("FWSUBUNIT", submission.getField("FWSUBUNIT"));
			elco.details().put("FWMAUZA_PARA", submission.getField("FWMAUZA_PARA"));
			elco.details().put("FWNHHHGPS", submission.getField("FWNHHHGPS"));
			elco.details().put("FWMISCENTTDOSE", submission.getField("FWMISCENTTDOSE"));
			elco.details().put("mis_census_current_formStatus", submission.getField("mis_census_current_formStatus"));

			allEcos.update(elco);
		}
	}

	public void misElco(FormSubmission submission) {

		Elco elco = allEcos.findByCaseId(submission.entityId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		if (elco == null) {
			logger.warn(format("Failed to handle MIS ELCO form as there is no ELCO registered with ID: {0}", submission.entityId()));
			return;
		}

		Map<String, String> misElco = create(FWMISELCODATE, submission.getField(FWMISELCODATE)).put(START_DATE, submission.getField(START_DATE))
				.put(END_DATE, submission.getField(END_DATE)).put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(FWPMISBIRTHCTRL, submission.getField(FWPMISBIRTHCTRL)).put(FWMISBCSOURCE, submission.getField(FWMISBCSOURCE))
				.put(FW_WOMMAUZA_PARA, submission.getField(FW_WOMMAUZA_PARA)).put(FW_GOBHHID, submission.getField(FW_GOBHHID))
				.put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID)).put(FW_WOMFNAME, submission.getField(FW_WOMFNAME))
				.put(FW_HUSNAME, submission.getField(FW_HUSNAME)).put(FW_WOMNID, submission.getField(FW_WOMNID)).put(FW_WOMBID, submission.getField(FW_WOMBID))
				.put(FW_WOMAGE, submission.getField(FW_WOMAGE)).put(ELCO, submission.getField(ELCO))
				.put(existing_location, submission.getField(existing_location)).put(FWMISINJGIVENDATE, submission.getField(FWMISINJGIVENDATE))
				.put(FWMISINJNEXTDATECALC, submission.getField(FWMISINJNEXTDATECALC)).put(FWMISINJNEXTDATE, submission.getField(FWMISINJNEXTDATE))
				.put(FWMISPMDATE, submission.getField(FWMISPMDATE)).put(FWMISPILLGIVENNO, submission.getField(FWMISPILLGIVENNO))
				.put(FWMISPILLGIVENDATE, submission.getField(FWMISPILLGIVENDATE)).put(FWMISCONDGIVENNO, submission.getField(FWMISCONDGIVENNO))
				.put(FWMISCONDGIVENDATE, submission.getField(FWMISCONDGIVENDATE)).put(FWMISOPT, submission.getField(FWMISOPT))
				.put(FWMISIMPT, submission.getField(FWMISIMPT)).put(mis_elco_current_formStatus, submission.getField(mis_elco_current_formStatus))
				.put(received_time, format.format(today).toString()).map();

		elco.MISDETAILS().add(misElco);

		allEcos.update(elco);

		elcoScheduleService.enrollIntoMilestoneOfMisElco(submission.entityId(), submission.getField(FWMISELCODATE));
	}

	private void addDetailsToElco(FormSubmission submission, SubFormData subFormData, Elco elco) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		elco.details().put(relationalid, subFormData.instances().get(0).get(relationalid));
		elco.details().put(FW_DISPLAY_AGE, subFormData.instances().get(0).get(FW_DISPLAY_AGE));
		elco.details().put(REFERENCE_DATE, submission.getField(REFERENCE_DATE));
		elco.details().put(START_DATE, submission.getField(START_DATE));
		elco.details().put(END_DATE, submission.getField(END_DATE));
		elco.details().put(received_time, format.format(today).toString());
	}

	private void addELCODetailsToHH(FormSubmission submission, SubFormData subFormData, HouseHold houseHold) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();

		for (Map<String, String> elcoFields : subFormData.instances()) {			
			String UPAZLA = elcoFields.get(FW_WOMUPAZILLA);
			if(UPAZLA!=null && UPAZLA.contains("+")) UPAZLA.replace("+", " ");

			Map<String, String> elco = create(ID, elcoFields.get(ID)).put(FW_TODAY, submission.getField(REFERENCE_DATE))
					.put(START_DATE, submission.getField(START_DATE)).put(END_DATE, submission.getField(END_DATE)).put(FW_GOBHHID, elcoFields.get(FW_GOBHHID))
					.put(FW_JiVitAHHID, elcoFields.get(FW_JiVitAHHID)).put(FW_CENDATE, submission.getField(FW_CENDATE))
					.put(FW_CENSTAT, submission.getField(FW_CENSTAT)).put(existing_ELCO, submission.getField(existing_ELCO))
					.put(new_ELCO, submission.getField(new_ELCO)).put(ELCO, submission.getField(ELCO)).put(WomanREGDATE, elcoFields.get(WomanREGDATE))
					.put(form_name, submission.getField(form_name)).put(FW_WOMFNAME, elcoFields.get(FW_WOMFNAME)).put(FW_WOMLNAME, elcoFields.get(FW_WOMLNAME))
					.put(FW_WOMANYID, elcoFields.get(FW_WOMANYID)).put(FW_WOMNID, elcoFields.get(FW_WOMNID))
					.put(FW_WOMRETYPENID, elcoFields.get(FW_WOMRETYPENID)).put(FW_WOMBID, elcoFields.get(FW_WOMBID))
					.put(FW_WOMRETYPEBID, elcoFields.get(FW_WOMRETYPEBID)).put(FW_HUSNAME, elcoFields.get(FW_HUSNAME))
					.put(FW_GENDER, elcoFields.get(FW_GENDER)).put(FW_BIRTHDATE, elcoFields.get(FW_BIRTHDATE)).put(FW_WOMAGE, elcoFields.get(FW_WOMAGE))
					.put(FW_DISPLAY_AGE, elcoFields.get(FW_DISPLAY_AGE)).put(FW_CWOMSTRMEN, elcoFields.get(FW_CWOMSTRMEN))
					.put(FWCWOMSTER, elcoFields.get(FWCWOMSTER)).put(FW_CWOMHUSALV, elcoFields.get(FW_CWOMHUSALV))
					.put(FW_CWOMHUSSTR, elcoFields.get(FW_CWOMHUSSTR)).put(FW_CWOMHUSLIV, elcoFields.get(FW_CWOMHUSLIV))
					.put(FW_ELIGIBLE, elcoFields.get(FW_ELIGIBLE)).put(FW_ELIGIBLE2, elcoFields.get(FW_ELIGIBLE2))
					.put(FW_WOMCOUNTRY, elcoFields.get(FW_WOMCOUNTRY)).put(FW_WOMDIVISION, elcoFields.get(FW_WOMDIVISION))
					.put(FW_WOMDISTRICT, elcoFields.get(FW_WOMDISTRICT)).put(FW_WOMUPAZILLA, UPAZLA)
					.put(FW_WOMUNION, elcoFields.get(FW_WOMUNION)).put(FW_WOMWARD, elcoFields.get(FW_WOMWARD))
					.put(FW_WOMSUBUNIT, elcoFields.get(FW_WOMSUBUNIT)).put(FW_WOMMAUZA_PARA, elcoFields.get(FW_WOMMAUZA_PARA))
					.put(FW_WOMGOBHHID, elcoFields.get(FW_WOMGOBHHID)).put(FW_WOMGPS, elcoFields.get(FW_WOMGPS)).put(profileImagePath, "")
					.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
					.put(timeStamp, ""+System.currentTimeMillis())
					.put(received_time, format.format(today).toString()).put(nidImagePath, "").map();

			houseHold.ELCODETAILS().add(elco);

		}
	}

	public void addPSRFDetailsToELCO(FormSubmission submission) {

		Elco elco = allEcos.findByCaseId(submission.entityId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		if (elco == null) {
			logger.warn(format("Failed to handle PSRF form as there is no ELCO registered with ID: {0}", submission.entityId()));
			return;
		}

		Map<String, String> psrf = create(FW_PSRDATE, submission.getField(FW_PSRDATE)).put(FW_CONFIRMATION, submission.getField(FW_CONFIRMATION))
				.put(FW_PSRSTS, submission.getField(FW_PSRSTS)).put(FW_PSRLMP, submission.getField(FW_PSRLMP))
				.put(FW_PSRPREGSTS, submission.getField(FW_PSRPREGSTS)).put(FW_PSRPREGWTD, submission.getField(FW_PSRPREGWTD))
				.put(FW_PSRHUSPREGWTD, submission.getField(FW_PSRHUSPREGWTD)).put(FW_PSREVRPREG, submission.getField(FW_PSREVRPREG))
				.put(FW_PSRTOTBIRTH, submission.getField(FW_PSRTOTBIRTH)).put(FW_PSRNBDTH, submission.getField(FW_PSRNBDTH))
				.put(FW_PSRPRSB, submission.getField(FW_PSRPRSB)).put(FW_PSRPRMC, submission.getField(FW_PSRPRMC))
				.put(FW_PSRPREGTWYRS, submission.getField(FW_PSRPREGTWYRS)).put(FW_PSRPRVPREGCOMP, submission.getField(FW_PSRPRVPREGCOMP))
				.put(FW_PSRPRCHECKS, submission.getField(FW_PSRPRCHECKS)).put(FW_PSRVDGMEM, submission.getField(FW_PSRVDGMEM))
				.put(FW_PSRWOMEDU, submission.getField(FW_PSRWOMEDU)).put(FW_PSRHHLAT, submission.getField(FW_PSRHHLAT))
				.put(FW_PSRHHRICE, submission.getField(FW_PSRHHRICE)).put(FW_PSRANM, submission.getField(FW_PSRANM))
				.put(FW_PSRHBP, submission.getField(FW_PSRHBP)).put(FW_PSRDBT, submission.getField(FW_PSRDBT)).put(FW_PSRTHY, submission.getField(FW_PSRTHY))
				.put(FW_PSRHGT, submission.getField(FW_PSRHGT)).put(FW_PSRMUAC, submission.getField(FW_PSRMUAC))
				.put(FW_PSRPHONE, submission.getField(FW_PSRPHONE)).put(FW_PSRPHONENUM, submission.getField(FW_PSRPHONENUM))
				.put(FW_VG, submission.getField(FW_VG)).put(FW_HRP, submission.getField(FW_HRP)).put(FW_HR_PSR, submission.getField(FW_HR_PSR))
				.put(FW_FLAGVALUE, submission.getField(FW_FLAGVALUE)).put(FW_SORTVALUE, submission.getField(FW_SORTVALUE))
				.put(START_DATE, submission.getField(START_DATE)).put(END_DATE, submission.getField(END_DATE))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE)).put(existing_ELCO, submission.getField(existing_ELCO))
				.put(FWNOTELIGIBLE, submission.getField(FWNOTELIGIBLE)).put(ELCO, submission.getField(ELCO)).put(FW_ELIGIBLE, submission.getField(FW_ELIGIBLE))
				.put(current_formStatus, submission.getField(current_formStatus))
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(received_time, format.format(today).toString()).map();
		
		
		elco.PSRFDETAILS().add(psrf);
		elco.details().put(FW_PSRPREGSTS, submission.getField(FW_PSRPREGSTS));
		elco.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		elco.setTimeStamp(System.currentTimeMillis());
		allEcos.update(elco);
		logger.info("Expected value leading zero and found submission.getField(FW_PSRSTS): " + submission.getField(FW_PSRSTS));
		logger.info("Expected value leading no zero and found submission.getField(FW_PSRPREGSTS): " + submission.getField(FW_PSRPREGSTS));

		if (submission.getField(FW_PSRSTS) != null)
			if (submission.getField(FW_PSRPREGSTS) != null && submission.getField(FW_PSRPREGSTS).equalsIgnoreCase("1")
					&& submission.getField(FW_PSRSTS).equals("01")) {

				elco.setIsClosed(true);
				allEcos.update(elco);
				elcoScheduleService.unEnrollFromScheduleOfPSRF(submission.entityId(), submission.anmId(), "");
				try {
					List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
							ELCO_SCHEDULE_PSRF);
					if (beforeNewActions.size() > 0) {
						scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
								ELCO_SCHEDULE_PSRF);
					}

				} catch (Exception e) {
					logger.info("From addPSRFDetailsToELCO:" + e.getMessage());
				}
				// user type condition
				if(submission.getField("user_type").equalsIgnoreCase(FD)){
					ancService.registerANC(submission);
					bnfService.registerBNF(submission);
				
				}
				
			} else if (submission.getField(FW_PSRSTS).equalsIgnoreCase("02") || (submission.getField(FW_PSRSTS).equalsIgnoreCase("01"))) {
				ancService.deleteBlankMother(submission);
				elcoScheduleService.enrollIntoMilestoneOfPSRF(submission.entityId(), submission.getField(FWPSRDATE), submission.anmId(),
						submission.instanceId());
			} else {
				ancService.deleteBlankMother(submission);
				
				// user type condition
				if(submission.getField("user_type").equalsIgnoreCase(FD)){					
				
					elcoScheduleService.unEnrollFromScheduleOfPSRF(submission.entityId(), submission.anmId(), "");
					try {
						List<Action> beforeNewActions = allActions.findAlertByANMIdEntityIdScheduleName(submission.anmId(), submission.entityId(),
								ELCO_SCHEDULE_PSRF);
						if (beforeNewActions.size() > 0) {
							scheduleLogService.closeSchedule(submission.entityId(), submission.instanceId(), beforeNewActions.get(0).timestamp(),
									ELCO_SCHEDULE_PSRF);
						}
					} catch (Exception e) {
						logger.info("From addPSRFDetailsToELCO:" + e.getMessage());
					}
				
				}
				
			}
	}
}
