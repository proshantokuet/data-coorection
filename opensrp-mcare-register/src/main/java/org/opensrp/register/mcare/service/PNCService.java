/**
 * @author julkar nain
 */

package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWCONFIRMATION;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.external_user_ID;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_mauza;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_wom_age;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.relationalid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.user_type;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFCHILDNAME;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFCHLDVITSTS;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFDOB;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFDTOO;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFGEN;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFNAME;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFNAMECHECK;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFSTS;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.FWBNFWOMVITSTS;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_LB;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_SB;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_WD;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.DeliveryOutcomeFields.CHILD_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_GOBHHID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_LOCATIONID;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.FW_WOMFNAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_HUSNAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_JiVitAHHID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_WOMBID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_WOMNID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1DATE;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1DELCOMP;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1DELTYPE;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1DNGRSIGN;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1FVR;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1INT;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1KNWPRVDR;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1REMSTS;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.FWPNC1TEMP;
import static org.opensrp.common.AllConstants.PNCVisitOneFields.pnc1_current_formStatus;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3DATE;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3DELCOMP;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3DNGRSIGN;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3FVR;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3INT;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3KNWPRVDR;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3REMSTS;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.FWPNC3TEMP;
import static org.opensrp.common.AllConstants.PNCVisitThreeFields.pnc3_current_formStatus;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2DATE;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2DELCOMP;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2DNGRSIGN;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2FVR;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2INT;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2KNWPRVDR;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2REMSTS;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.FWPNC2TEMP;
import static org.opensrp.common.AllConstants.PNCVisitTwoFields.pnc2_current_formStatus;
import static org.opensrp.common.AllConstants.PSRFFields.*;
import static org.opensrp.common.AllConstants.UserType.FD;

import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_PNC_3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.HttpUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ELCOScheduleService;
import org.opensrp.register.mcare.service.scheduling.PNCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PNCService {

	private static Logger logger = LoggerFactory.getLogger(ANCService.class.toString());
	private AllElcos allElcos;
	private AllMothers allMothers;
	private AllChilds allChilds;
	private ELCOScheduleService elcoSchedulesService;
	private PNCSchedulesService pncSchedulesService;
	private ChildSchedulesService childSchedulesService;
	private ScheduleLogService scheduleLogService;
	private static String FEVER_SMS_URL;
	private String womanNID = "";
	private String womanBID = "";
	private String feverTemp = "";
	private String identifier = "text=";

	@Autowired
	public PNCService(AllElcos allElcos, AllMothers allMothers, AllChilds allChilds, ELCOScheduleService elcoSchedulesService,
			PNCSchedulesService pncSchedulesService, ChildSchedulesService childSchedulesService, ScheduleLogService scheduleLogService,
			@Value("#{opensrp['FEVER_SMS_URL']}") String fever_sms_url) {
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.allChilds = allChilds;
		this.elcoSchedulesService = elcoSchedulesService;
		this.pncSchedulesService = pncSchedulesService;
		this.childSchedulesService = childSchedulesService;
		this.scheduleLogService = scheduleLogService;
		this.FEVER_SMS_URL = fever_sms_url;
	}

	public PNCService(@Value("#{opensrp['FEVER_SMS_URL']}") String fever_sms_url) {
		this.FEVER_SMS_URL = fever_sms_url;
	}

	public void deliveryOutcome(FormSubmission submission) {
		String pattern = "yyyy-MM-dd";
		// DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);

		DateTime dateTime = DateTime.parse(submission.getField(FWBNFDTOO));
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		String referenceDate = fmt.print(dateTime);

		if (submission.getField(FWBNFDTOO) != null) {

			Mother mother = allMothers.findByCaseId(submission.entityId());

			if (mother == null) {
				logger.warn(format("Failed to handle PNC as there is no Mother enroll with ID: {0}", submission.entityId()));
				return;
			}

			Elco elco = allElcos.findByCaseId(mother.relationalid());

			if (elco != null) {
				logger.info("Closing EC case. Ec Id: " + elco.caseId());
				elco.setIsClosed(false);
				elco.withTODAY(submission.getField(REFERENCE_DATE));
				allElcos.update(elco);
				
				elcoSchedulesService.imediateEnrollIntoMilestoneOfPSRF(elco.caseId(), elco.TODAY(), elco.PROVIDERID(), elco.INSTANCEID());
			}

			if (submission.getField(FWBNFSTS).equals(STS_WD)) {
				logger.info("Closing Mother as the mother died during delivery. Mother Id: " + mother.caseId());
				closeMother(mother);
			} else if (submission.getField(FWBNFSTS).equals(STS_LB)) {
				logger.info("Generating schedule for Mother when Child is Live Birth. Mother Id: " + mother.caseId());
				logger.info("FWBNFWOMVITSTS:" + submission.getField(FWBNFWOMVITSTS));
				if (submission.getField(FWBNFWOMVITSTS).equalsIgnoreCase("0")) {
					logger.info("Mother died");
				} else {
					//User type conditions
					if(submission.getField("user_type").equalsIgnoreCase(FD)){					
						pncSchedulesService.enrollPNCRVForMother(submission.entityId(), LocalDate.parse(referenceDate));
						logger.info("Generating schedule for Child when Child is Live Birth. Mother Id: " + mother.caseId());
					}
				}
				SubFormData subFormData = submission.getSubFormByName(CHILD_REGISTRATION_SUB_FORM_NAME);
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat formats = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date today = Calendar.getInstance().getTime();
				for (Map<String, String> childFields : subFormData.instances()) {

					Child child = allChilds.findByCaseId(childFields.get(ID)).withINSTANCEID(submission.instanceId()).withPROVIDERID(submission.anmId())
							.withLOCATIONID(submission.getField(FW_LOCATIONID)).withTODAY(submission.getField(REFERENCE_DATE))
							.withSTART(submission.getField(START_DATE)).withEND(submission.getField(END_DATE))
							.withSUBMISSIONDATE(DateUtil.getTimestampToday())
							.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)))
							.withDistrict(mother.FWWOMDISTRICT())
							.withUpazilla(mother.FWWOMUPAZILLA())
							.withUnion(mother.getFWWOMUNION())
							.withUnit(mother.getFWWOMSUBUNIT())
							.withMouzaPara(mother.getMother_mauza())
							.setTimeStamp(System.currentTimeMillis())
							.setIsClosed(false);

					child.details().put(relationalid, childFields.get(relationalid));

					child.details().put(FW_WOMNID, submission.getField(FW_WOMNID));
					child.details().put(FW_WOMBID, submission.getField(FW_WOMBID));
					child.details().put(FW_GOBHHID, submission.getField(FW_GOBHHID));
					child.details().put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID));
					child.details().put(FW_WOMFNAME, submission.getField(FW_WOMFNAME));
					child.details().put(FW_HUSNAME, submission.getField(FW_HUSNAME));
					child.details().put(mother_wom_age, mother.mother_wom_age());
					child.details().put(mother_mauza, mother.getMother_mauza());
					child.details().put(FWBNFGEN, childFields.get(FWBNFGEN));
					child.details().put(FWBNFCHLDVITSTS, childFields.get(FWBNFCHLDVITSTS));
					child.details().put(received_time, formats.format(today).toString());
					child.details().put(FWBNFNAMECHECK, childFields.get(FWBNFNAMECHECK));
					child.details().put(FWBNFNAME, childFields.get(FWBNFNAME));
					child.details().put(FWBNFCHILDNAME, childFields.get(FWBNFCHILDNAME));
					child.details().put(FWBNFDOB, childFields.get(FWBNFDOB));
					child.details().put(external_user_ID, childFields.get(external_user_ID));
					child.details().put("referenceDate", referenceDate);
					child.details().put("ward",  mother.getFWWOMWARD());
					allChilds.update(child);
					logger.info("FWBNFCHLDVITSTS:" + childFields.get(FWBNFCHLDVITSTS));
					if (childFields.get(FWBNFCHLDVITSTS).equalsIgnoreCase("0")) {
						logger.info("Child died");
					} else {
						// user type conditions
						if(submission.getField("user_type").equalsIgnoreCase(FD)){
							childSchedulesService.enrollENCCForChild(childFields.get(ID), LocalDate.parse(referenceDate));
						}
					}
				}
			} else if (submission.getField(FWBNFSTS).equals(STS_SB)) {
				if (submission.getField("user_type").equalsIgnoreCase(FD)) {
					logger.info("Generating schedule for Mother when Child is Still Birth. Mother Id: " + mother.caseId());
					pncSchedulesService.enrollPNCRVForMother(submission.entityId(), LocalDate.parse(referenceDate));
				} else {
					logger.info("FWA submit form for Still Birth so nothing happend ");
				}
			}
		}
	}

	public void pncVisitOne(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());
		if (mother == null) {
			logger.warn(format("Failed to handle PNC-1 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		womanBID = submission.getField(FW_WOMBID);
		womanNID = submission.getField(FW_WOMBID);
		feverTemp = submission.getField(FWPNC1TEMP);
		Map<String, String> pncVisitOne = create(FWPNC1DATE, submission.getField(FWPNC1DATE)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWPNC1REMSTS, submission.getField(FWPNC1REMSTS)).put(FWPNC1INT, submission.getField(FWPNC1INT))
				.put(FWPNC1KNWPRVDR, submission.getField(FWPNC1KNWPRVDR)).put(FWPNC1FVR, submission.getField(FWPNC1FVR)).put(FWPNC1TEMP, feverTemp)
				.put(FWPNC1DNGRSIGN, submission.getField(FWPNC1DNGRSIGN)).put(FWPNC1DELTYPE, submission.getField(FWPNC1DELTYPE))
				.put(FWPNC1DELCOMP, submission.getField(FWPNC1DELCOMP)).put(FW_GOBHHID, submission.getField(FW_GOBHHID))
				.put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID)).put(FW_WOMBID, womanBID).put(FW_WOMNID, womanNID)
				.put(FW_WOMFNAME, submission.getField(FW_WOMFNAME)).put(FW_HUSNAME, submission.getField(FW_HUSNAME))
				.put(FWBNFDTOO, submission.getField(FWBNFDTOO)).put(FWBNFSTS, submission.getField(FWBNFSTS))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE)).put(START_DATE, submission.getField(START_DATE))
				.put(END_DATE, submission.getField(END_DATE)).put(pnc1_current_formStatus, submission.getField(pnc1_current_formStatus))
				.put(relationalid, submission.getField(relationalid)).put(user_type, submission.getField(user_type))
				.put(external_user_ID, submission.getField(external_user_ID))
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(received_time, DateUtil.getTodayAsString()).map();

		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withPNCVisitOne(pncVisitOne);
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);
		pncSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_PNC, new LocalDate());
		String pattern = "yyyy-MM-dd";
		DateTime dateTime = DateTime.parse(mother.getbnfVisitDetails(FWBNFDTOO));
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		String referenceDate = fmt.print(dateTime);
		pncSchedulesService.enrollPNCForMother(submission.entityId(), SCHEDULE_PNC_2, LocalDate.parse(referenceDate));
		
		if (Double.parseDouble(feverTemp) >= 100.4) {
			if (!womanBID.equalsIgnoreCase(""))
				identifier += "b " + womanBID;
			else if (!womanNID.equalsIgnoreCase(""))
				identifier += "n " + womanNID;
			sendFeverSMS(identifier);
		}
	}

	public void pncVisitTwo(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle PNC-2 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		womanBID = submission.getField(FW_WOMBID);
		womanNID = submission.getField(FW_WOMBID);
		feverTemp = submission.getField(FWPNC2TEMP);
		Map<String, String> pncVisitTwo = create(FWPNC2DATE, submission.getField(FWPNC2DATE)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWPNC2REMSTS, submission.getField(FWPNC2REMSTS)).put(FWPNC2INT, submission.getField(FWPNC2INT))
				.put(FWPNC2KNWPRVDR, submission.getField(FWPNC2KNWPRVDR)).put(FWPNC2FVR, submission.getField(FWPNC2FVR)).put(FWPNC2TEMP, feverTemp)
				.put(FWPNC2DNGRSIGN, submission.getField(FWPNC2DNGRSIGN)).put(FWPNC2DELCOMP, submission.getField(FWPNC2DELCOMP))
				.put(FW_GOBHHID, submission.getField(FW_GOBHHID)).put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID)).put(FW_WOMBID, womanBID)
				.put(FW_WOMNID, womanNID).put(FW_WOMFNAME, submission.getField(FW_WOMFNAME)).put(FW_HUSNAME, submission.getField(FW_HUSNAME))
				.put(FWBNFDTOO, submission.getField(FWBNFDTOO)).put(FWBNFSTS, submission.getField(FWBNFSTS))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE)).put(START_DATE, submission.getField(START_DATE))
				.put(END_DATE, submission.getField(END_DATE)).put(pnc2_current_formStatus, submission.getField(pnc2_current_formStatus))
				.put(relationalid, submission.getField(relationalid)).put(user_type, submission.getField(user_type))
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(external_user_ID, submission.getField(external_user_ID))
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(received_time, DateUtil.getTodayAsString()).map();

		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withPNCVisitTwo(pncVisitTwo);
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);		
		pncSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_PNC, new LocalDate());
	
		String pattern = "yyyy-MM-dd";		
		DateTime dateTime = DateTime.parse(mother.getbnfVisitDetails(FWBNFDTOO));
		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		String referenceDate = fmt.print(dateTime);
		pncSchedulesService.enrollPNCForMother(submission.entityId(), SCHEDULE_PNC_3, LocalDate.parse(referenceDate));
		
		if (Double.parseDouble(feverTemp) >= 100.4) {
			if (!womanBID.equalsIgnoreCase(""))
				identifier += "b " + womanBID;
			else if (!womanNID.equalsIgnoreCase(""))
				identifier += "n " + womanNID;
			sendFeverSMS(identifier);
		}
	}

	public void pncVisitThree(FormSubmission submission) {

		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle PNC-3 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		womanBID = submission.getField(FW_WOMBID);
		womanNID = submission.getField(FW_WOMBID);
		feverTemp = submission.getField(FWPNC3TEMP);
		Map<String, String> pncVisitThree = create(FWPNC3DATE, submission.getField(FWPNC3DATE)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWPNC3REMSTS, submission.getField(FWPNC3REMSTS)).put(FWPNC3INT, submission.getField(FWPNC3INT))
				.put(FWPNC3KNWPRVDR, submission.getField(FWPNC3KNWPRVDR)).put(FWPNC3FVR, submission.getField(FWPNC3FVR)).put(FWPNC3TEMP, feverTemp)
				.put(FWPNC3DNGRSIGN, submission.getField(FWPNC3DNGRSIGN)).put(FWPNC3DELCOMP, submission.getField(FWPNC3DELCOMP))
				.put(FW_GOBHHID, submission.getField(FW_GOBHHID)).put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID)).put(FW_WOMBID, womanBID)
				.put(FW_WOMNID, womanNID).put(FW_WOMFNAME, submission.getField(FW_WOMFNAME)).put(FW_HUSNAME, submission.getField(FW_HUSNAME))
				.put(FWBNFDTOO, submission.getField(FWBNFDTOO)).put(FWBNFSTS, submission.getField(FWBNFSTS))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE)).put(START_DATE, submission.getField(START_DATE))
				.put(END_DATE, submission.getField(END_DATE)).put(pnc3_current_formStatus, submission.getField(pnc3_current_formStatus))
				.put(relationalid, submission.getField(relationalid)).put(user_type, submission.getField(user_type))
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(external_user_ID, submission.getField(external_user_ID))
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(received_time, DateUtil.getTodayAsString()).map();
		
		mother.withPNCVisitThree(pncVisitThree);
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);
		pncSchedulesService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), SCHEDULE_PNC);
		
		if (Double.parseDouble(feverTemp) >= 100.4) {
			if (!womanBID.equalsIgnoreCase(""))
				identifier += "b " + womanBID;
			else if (!womanNID.equalsIgnoreCase(""))
				identifier += "n " + womanNID;
			sendFeverSMS(identifier);
		}
	}

	public void pncClose(String entityId) {

		Mother mother = allMothers.findByCaseId(entityId);

		if (mother == null) {
			logger.warn("Tried to close case without registered mother for case ID: " + entityId);
			return;
		}

		allMothers.close(entityId);

		pncSchedulesService.unEnrollFromAllSchedules(entityId);
	}

	public void closeMother(Mother mother) {

		mother.setIsClosed(true);
		allMothers.update(mother);
		pncSchedulesService.unEnrollFromAllSchedules(mother.caseId());

		Elco elco = allElcos.findByCaseId(mother.relationalid());
		if (elco != null) {
			logger.info("Closing EC case along with PNC case. Ec Id: " + elco.caseId());
			elco.setIsClosed(true);
			allElcos.update(elco);
		}
	}

	public void deleteBlankChild(FormSubmission submission) {
		SubFormData subFormData = submission.getSubFormByName(CHILD_REGISTRATION_SUB_FORM_NAME);
		for (Map<String, String> childFields : subFormData.instances()) {
			Child child = allChilds.findByCaseId(childFields.get(ID));
			if (child != null) {
				allChilds.remove(child);
			}
		}

	}

	public void sendFeverSMS(String identifier) {
		System.out.println("hello" + FEVER_SMS_URL);
		System.out.println("sending feversms for woman identifier: " + identifier + " ,response:" + HttpUtil.get(FEVER_SMS_URL, identifier, "", "").body());
	}

}
