/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.ANC4_current_formStatus;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4ANM;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4BLD;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4BLRVIS;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4CONVL;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DATE;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DBT;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DS1;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DS2;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DS3;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DS4;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DS5;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4DS6;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4HBP;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4HEAD;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4INT;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4KNWPRVDR;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4PROB;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4REMSTS;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4SWLNG;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWANC4THY;
import static org.opensrp.common.AllConstants.ANCVisitFourFields.FWHR_ANC4;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.ELCO;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1ANM;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1BLD;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1BLRVIS;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1CONVL;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DATE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DBT;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DS1;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DS2;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DS3;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DS4;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DS5;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1DS6;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1HBP;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1HEAD;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1INT;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1KNWPRVDR;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1PROB;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1REMSTS;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1SWLNG;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWANC1THY;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWBPC1ASSTLAB;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWBPC1BLDDNR;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWBPC1BLDGRP;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWBPC1FINARGMT;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWBPC1LOCOFDEL;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWBPC1TRNSPRT;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWCONFIRMATION;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWDANGERVALUE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWEDD;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWFLAGVALUE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWGESTATIONALAGE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWHRP;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWHR_ANC1;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWHR_PSR;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWNOTELIGIBLE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWSORTVALUE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWVG;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.anc1_current_formStatus;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.existing_ELCO;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.external_user_ID;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mauza;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_first_name;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_gobhhid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_husname;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_jivhhid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_mauza;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_valid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_wom_age;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_wom_bid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.mother_wom_nid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.user_type;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.ANC3_current_formStatus;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3ANM;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3BLD;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3BLRVIS;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3CONVL;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DATE;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DBT;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DS1;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DS2;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DS3;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DS4;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DS5;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3DS6;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3HBP;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3HEAD;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3INT;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3KNWPRVDR;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3PROB;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3REMSTS;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3SWLNG;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWANC3THY;
import static org.opensrp.common.AllConstants.ANCVisitThreeFields.FWHR_ANC3;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.ANC2_current_formStatus;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2ANM;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2BLD;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2BLRVIS;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2CONVL;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DATE;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DBT;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DS1;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DS2;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DS3;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DS4;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DS5;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2DS6;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2HBP;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2HEAD;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2INT;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2KNWPRVDR;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2PROB;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2REMSTS;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2SWLNG;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWANC2THY;
import static org.opensrp.common.AllConstants.ANCVisitTwoFields.FWHR_ANC2;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_DEAD;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.STS_GONE;
import static org.opensrp.common.AllConstants.ELCORegistrationFields.relationalid;
import static org.opensrp.common.AllConstants.HHRegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_GOBHHID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_HUSNAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_JiVitAHHID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_WOMBID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_WOMFNAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.FW_WOMNID;
import static org.opensrp.common.AllConstants.HHRegistrationFields.MOTHER_REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;
import static org.opensrp.common.AllConstants.PSRFFields.*;
import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.common.AllConstants;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.ANCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.BNFSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ANCService {

	private static Logger logger = LoggerFactory.getLogger(ANCService.class.toString());
	private AllElcos allElcos;
	private AllMothers allMothers;
	private ANCSchedulesService ancSchedulesService;
	private ActionService actionService;
	private ScheduleLogService scheduleLogService;
	private BNFSchedulesService bnfSchedulesService;
	private PNCService pncService;

	@Autowired
	public ANCService(AllElcos allElcos, AllMothers allMothers, ANCSchedulesService ancSchedulesService, ActionService actionService,
			ScheduleLogService scheduleLogService, BNFSchedulesService bnfSchedulesService, PNCService pncService) {
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.ancSchedulesService = ancSchedulesService;
		this.actionService = actionService;
		this.scheduleLogService = scheduleLogService;
		this.bnfSchedulesService = bnfSchedulesService;
		this.pncService = pncService;
	}

	public void registerANC(FormSubmission submission) {

		String motherId = submission.getField(AllConstants.ANCFormFields.MCARE_MOTHER_ID);

		Mother mother = allMothers.findByCaseId(motherId);
		if (!allElcos.exists(submission.entityId())) {
			logger.warn(format("Found mother without registered eligible couple. Ignoring: {0} for mother with id: {1} for ANM: {2}", submission.entityId(),
					motherId, submission.anmId()));
			return;
		}

		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.withPROVIDERID(submission.anmId());
		mother.withINSTANCEID(submission.instanceId());
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withSUBMISSIONDATE(DateUtil.getTimestampToday());
		mother.setTimeStamp(System.currentTimeMillis());
		Elco lco = allElcos.findByCaseId(mother.relationalid());

		mother.withmother_gobhhid(submission.getField(mother_gobhhid));
		mother.withmother_jivhhid(submission.getField(mother_jivhhid));
		mother.setFWWOMUNION(lco.FWWOMUNION());
		mother.setFWWOMWARD(lco.FWWOMWARD());
		mother.setFWWOMSUBUNIT(lco.FWWOMSUBUNIT());
		mother.setMother_mauza(submission.getField(mother_mauza));
		mother.withmother_wom_nid(submission.getField(mother_wom_nid));
		mother.withmother_wom_bid(submission.getField(mother_wom_bid));
		mother.withmother_wom_age(submission.getField(mother_wom_age));
		mother.withJmother_first_name(submission.getField(mother_first_name));
		mother.withmother_husname(submission.getField(mother_husname));

		addDetailsToMother(submission, mother,lco);

		allMothers.update(mother);
		
		ancSchedulesService.enrollMother(motherId, LocalDate.parse(submission.getField(MOTHER_REFERENCE_DATE)), submission.anmId(), submission.instanceId(),
				submission.getField(MOTHER_REFERENCE_DATE));
	}

	private void addDetailsToMother(FormSubmission submission, Mother mother,Elco elco) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();

		mother.details().put(REFERENCE_DATE, submission.getField(REFERENCE_DATE));
		mother.details().put(mother_valid, submission.getField(mother_valid));
		mother.details().put(FWVG, submission.getField(FWVG));
		mother.details().put(FWHRP, submission.getField(FWHRP));
		mother.details().put(FWHR_PSR, submission.getField(FWHR_PSR));
		mother.details().put(FWFLAGVALUE, submission.getField(FWFLAGVALUE));
		mother.details().put(FWSORTVALUE, submission.getField(FWSORTVALUE));
		mother.details().put(received_time, format.format(today).toString());
		mother.details().put("birthDate", elco.FWBIRTHDATE());
		List<Map<String, String>> psrfs =elco.PSRFDETAILS();
		int psrfsCount = psrfs.size()-1;
		Map<String, String> psrf = psrfs.get(psrfsCount);
		mother.details().put("LMP", psrf.get("FWPSRLMP"));
	}

	public void ancVisitOne(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle ANC-1 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		
		Map<String, String> ancVisitOne = create(FWANC1DATE, submission.getField(FWANC1DATE))
				.put(anc1_current_formStatus, submission.getField(anc1_current_formStatus)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWGESTATIONALAGE, submission.getField(FWGESTATIONALAGE)).put(FWEDD, submission.getField(FWEDD))
				.put(FWANC1REMSTS, submission.getField(FWANC1REMSTS)).put(FWANC1INT, submission.getField(FWANC1INT))
				.put(FWANC1KNWPRVDR, submission.getField(FWANC1KNWPRVDR)).put(FWANC1ANM, submission.getField(FWANC1ANM))
				.put(FWANC1HBP, submission.getField(FWANC1HBP)).put(FWANC1DBT, submission.getField(FWANC1DBT)).put(FWANC1THY, submission.getField(FWANC1THY))
				.put(FWANC1PROB, submission.getField(FWANC1PROB)).put(FWANC1HEAD, submission.getField(FWANC1HEAD))
				.put(FWBPC1LOCOFDEL, submission.getField(FWBPC1LOCOFDEL)).put(FWBPC1ASSTLAB, submission.getField(FWBPC1ASSTLAB))
				.put(FWBPC1TRNSPRT, submission.getField(FWBPC1TRNSPRT)).put(FWBPC1BLDGRP, submission.getField(FWBPC1BLDGRP))
				.put(FWBPC1BLDDNR, submission.getField(FWBPC1BLDDNR)).put(FWBPC1FINARGMT, submission.getField(FWBPC1FINARGMT))
				.put(mauza, submission.getField(mauza)).put(FWVG, submission.getField(FWVG)).put(FWHR_PSR, submission.getField(FWHR_PSR))
				.put(FWHRP, submission.getField(FWHRP)).put(existing_ELCO, submission.getField(existing_ELCO))
				.put(FWANC1BLRVIS, submission.getField(FWANC1BLRVIS)).put(FWANC1SWLNG, submission.getField(FWANC1SWLNG))
				.put(FWANC1CONVL, submission.getField(FWANC1CONVL)).put(FWANC1BLD, submission.getField(FWANC1BLD))
				.put(FWANC1DS1, submission.getField(FWANC1DS1)).put(FWANC1DS2, submission.getField(FWANC1DS2)).put(FWANC1DS3, submission.getField(FWANC1DS3))
				.put(FWANC1DS4, submission.getField(FWANC1DS4)).put(FWANC1DS5, submission.getField(FWANC1DS5)).put(FWANC1DS6, submission.getField(FWANC1DS6))
				.put(FWDANGERVALUE, submission.getField(FWDANGERVALUE)).put(FWNOTELIGIBLE, submission.getField(FWNOTELIGIBLE))
				.put(ELCO, submission.getField(ELCO)).put(FWHR_ANC1, submission.getField(FWHR_ANC1)).put(FWFLAGVALUE, submission.getField(FWFLAGVALUE))
				.put(FWSORTVALUE, submission.getField(FWSORTVALUE)).put(user_type, submission.getField(user_type))
				.put(external_user_ID, submission.getField(external_user_ID)).put(relationalid, submission.getField(relationalid))
				.put(FW_GOBHHID, submission.getField(FW_GOBHHID)).put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID))
				.put(FW_WOMBID, submission.getField(FW_WOMBID)).put(FW_WOMNID, submission.getField(FW_WOMNID))
				.put(FW_WOMFNAME, submission.getField(FW_WOMFNAME)).put(FW_HUSNAME, submission.getField(FW_HUSNAME))
				.put(MOTHER_REFERENCE_DATE, submission.getField(MOTHER_REFERENCE_DATE)).put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE)).put(END_DATE, submission.getField(END_DATE))
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(received_time, DateUtil.getTodayAsString()).map();
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withANCVisitOne(ancVisitOne);
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);
		ancSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		actionService.markAllAlertsAsInactive(submission.entityId());
		logger.info("submission.getField(FWANC1REMSTS):" + submission.getField(FWANC1REMSTS));
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitOne:" + e.getMessage());
		}
		logger.info("FWANC1REMSTS:" + submission.getField(FWANC1REMSTS));
		if (submission.getField(FWANC1REMSTS).equalsIgnoreCase(STS_GONE) || submission.getField(FWANC1REMSTS).equalsIgnoreCase(STS_DEAD)) {

			unEnrollBNFSchedule(submission.entityId(), submission.anmId(), submission.instanceId(), submission.getField("user_type"));
		}
	}

	public void ancVisitTwo(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle ANC-2 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		
		Map<String, String> ancVisitTwo = create(FWANC2DATE, submission.getField(FWANC2DATE))
				.put(ANC2_current_formStatus, submission.getField(ANC2_current_formStatus)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWGESTATIONALAGE, submission.getField(FWGESTATIONALAGE)).put(FWEDD, submission.getField(FWEDD))
				.put(FWANC2REMSTS, submission.getField(FWANC2REMSTS)).put(FWANC2INT, submission.getField(FWANC2INT))
				.put(FWANC2KNWPRVDR, submission.getField(FWANC2KNWPRVDR)).put(FWANC2PROB, submission.getField(FWANC2PROB))
				.put(FWBPC1LOCOFDEL, submission.getField(FWBPC1LOCOFDEL)).put(FWBPC1ASSTLAB, submission.getField(FWBPC1ASSTLAB))
				.put(FWBPC1TRNSPRT, submission.getField(FWBPC1TRNSPRT)).put(FWBPC1BLDGRP, submission.getField(FWBPC1BLDGRP))
				.put(FWBPC1BLDDNR, submission.getField(FWBPC1BLDDNR)).put(FWBPC1FINARGMT, submission.getField(FWBPC1FINARGMT))
				.put(FWANC2ANM, submission.getField(FWANC2ANM)).put(FWANC2HBP, submission.getField(FWANC2HBP)).put(FWANC2DBT, submission.getField(FWANC2DBT))
				.put(FWANC2THY, submission.getField(FWANC2THY)).put(FWANC2HEAD, submission.getField(FWANC2HEAD)).put(mauza, submission.getField(mauza))
				.put(FWVG, submission.getField(FWVG)).put(FWHR_PSR, submission.getField(FWHR_PSR)).put(FWHRP, submission.getField(FWHRP))
				.put(FWHR_ANC1, submission.getField(FWHR_ANC1)).put(existing_ELCO, submission.getField(existing_ELCO))
				.put(FWANC2BLRVIS, submission.getField(FWANC2BLRVIS)).put(FWANC2SWLNG, submission.getField(FWANC2SWLNG))
				.put(FWANC2CONVL, submission.getField(FWANC2CONVL)).put(FWANC2BLD, submission.getField(FWANC2BLD))
				.put(FWANC2DS1, submission.getField(FWANC2DS1)).put(FWANC2DS2, submission.getField(FWANC2DS2)).put(FWANC2DS3, submission.getField(FWANC2DS3))
				.put(FWANC2DS4, submission.getField(FWANC2DS4)).put(FWANC2DS5, submission.getField(FWANC2DS5)).put(FWANC2DS6, submission.getField(FWANC2DS6))
				.put(FWDANGERVALUE, submission.getField(FWDANGERVALUE)).put(FWNOTELIGIBLE, submission.getField(FWNOTELIGIBLE))
				.put(ELCO, submission.getField(ELCO)).put(FWHR_ANC2, submission.getField(FWHR_ANC2)).put(FWFLAGVALUE, submission.getField(FWFLAGVALUE))
				.put(FWSORTVALUE, submission.getField(FWSORTVALUE)).put(user_type, submission.getField(user_type))
				.put(external_user_ID, submission.getField(external_user_ID)).put(relationalid, submission.getField(relationalid))
				.put(FW_GOBHHID, submission.getField(FW_GOBHHID)).put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID))
				.put(FW_WOMBID, submission.getField(FW_WOMBID)).put(FW_WOMNID, submission.getField(FW_WOMNID))
				.put(FW_WOMFNAME, submission.getField(FW_WOMFNAME)).put(FW_HUSNAME, submission.getField(FW_HUSNAME))
				.put(MOTHER_REFERENCE_DATE, submission.getField(MOTHER_REFERENCE_DATE)).put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE)).put(END_DATE, submission.getField(END_DATE))
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(received_time, DateUtil.getTodayAsString()).map();
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withANCVisitTwo(ancVisitTwo);
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);
		ancSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		actionService.markAllAlertsAsInactive(submission.entityId());
		logger.info("submission.getField(FWANC1REMSTS):" + submission.getField(FWANC1REMSTS));
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitTwo:" + e.getMessage());
		}
		logger.info("FWANC1REMSTS:" + submission.getField(FWANC1REMSTS));
		if (submission.getField(FWANC2REMSTS).equalsIgnoreCase(STS_GONE) || submission.getField(FWANC2REMSTS).equalsIgnoreCase(STS_DEAD)) {

			unEnrollBNFSchedule(submission.entityId(), submission.anmId(), submission.instanceId(), submission.getField("user_type"));
		}
	}

	public void ancVisitThree(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle ANC-3 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		
		Map<String, String> ancVisitThree = create(FWANC3DATE, submission.getField(FWANC3DATE))
				.put(ANC3_current_formStatus, submission.getField(ANC3_current_formStatus)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWGESTATIONALAGE, submission.getField(FWGESTATIONALAGE)).put(FWEDD, submission.getField(FWEDD))
				.put(FWANC3REMSTS, submission.getField(FWANC3REMSTS)).put(FWANC3INT, submission.getField(FWANC3INT))
				.put(FWANC3KNWPRVDR, submission.getField(FWANC3KNWPRVDR)).put(FWANC3PROB, submission.getField(FWANC3PROB))
				.put(FWBPC1LOCOFDEL, submission.getField(FWBPC1LOCOFDEL)).put(FWBPC1ASSTLAB, submission.getField(FWBPC1ASSTLAB))
				.put(FWBPC1TRNSPRT, submission.getField(FWBPC1TRNSPRT)).put(FWBPC1BLDGRP, submission.getField(FWBPC1BLDGRP))
				.put(FWBPC1BLDDNR, submission.getField(FWBPC1BLDDNR)).put(FWBPC1FINARGMT, submission.getField(FWBPC1FINARGMT))
				.put(FWANC3ANM, submission.getField(FWANC3ANM)).put(FWANC3HBP, submission.getField(FWANC3HBP)).put(FWANC3DBT, submission.getField(FWANC3DBT))
				.put(FWANC3THY, submission.getField(FWANC3THY)).put(FWANC3HEAD, submission.getField(FWANC3HEAD)).put(mauza, submission.getField(mauza))
				.put(FWVG, submission.getField(FWVG)).put(FWHR_PSR, submission.getField(FWHR_PSR)).put(FWHR_ANC2, submission.getField(FWHR_ANC2))
				.put(FWHRP, submission.getField(FWHRP)).put(FWHR_ANC1, submission.getField(FWHR_ANC1)).put(existing_ELCO, submission.getField(existing_ELCO))
				.put(FWANC3BLRVIS, submission.getField(FWANC3BLRVIS)).put(FWANC3SWLNG, submission.getField(FWANC3SWLNG))
				.put(FWANC3CONVL, submission.getField(FWANC3CONVL)).put(FWANC3BLD, submission.getField(FWANC3BLD))
				.put(FWANC3DS1, submission.getField(FWANC3DS1)).put(FWANC3DS2, submission.getField(FWANC3DS2)).put(FWANC3DS3, submission.getField(FWANC3DS3))
				.put(FWANC3DS4, submission.getField(FWANC3DS4)).put(FWANC3DS5, submission.getField(FWANC3DS5)).put(FWANC3DS6, submission.getField(FWANC3DS6))
				.put(FWDANGERVALUE, submission.getField(FWDANGERVALUE)).put(FWNOTELIGIBLE, submission.getField(FWNOTELIGIBLE))
				.put(ELCO, submission.getField(ELCO)).put(FWHR_ANC3, submission.getField(FWHR_ANC3)).put(FWFLAGVALUE, submission.getField(FWFLAGVALUE))
				.put(FWSORTVALUE, submission.getField(FWSORTVALUE)).put(user_type, submission.getField(user_type))
				.put(external_user_ID, submission.getField(external_user_ID)).put(relationalid, submission.getField(relationalid))
				.put(FW_GOBHHID, submission.getField(FW_GOBHHID)).put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID))
				.put(FW_WOMBID, submission.getField(FW_WOMBID)).put(FW_WOMNID, submission.getField(FW_WOMNID))
				.put(FW_WOMFNAME, submission.getField(FW_WOMFNAME)).put(FW_HUSNAME, submission.getField(FW_HUSNAME))
				.put(MOTHER_REFERENCE_DATE, submission.getField(MOTHER_REFERENCE_DATE)).put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE)).put(END_DATE, submission.getField(END_DATE))
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(received_time, DateUtil.getTodayAsString()).map();
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withANCVisitThree(ancVisitThree);
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);
		ancSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		actionService.markAllAlertsAsInactive(submission.entityId());
		logger.info("submission.getField(FWANC1REMSTS):" + submission.getField(FWANC1REMSTS));
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitThree:" + e.getMessage());
		}
		logger.info("FWANC1REMSTS:" + submission.getField(FWANC1REMSTS));
		if (submission.getField(FWANC3REMSTS).equalsIgnoreCase(STS_GONE) || submission.getField(FWANC3REMSTS).equalsIgnoreCase(STS_DEAD)) {

			unEnrollBNFSchedule(submission.entityId(), submission.anmId(), submission.instanceId(), submission.getField("user_type"));
		}
	}

	public void ancVisitFour(FormSubmission submission) {
		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle ANC-4 as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}
		
		Map<String, String> ancVisitFour = create(FWANC4DATE, submission.getField(FWANC4DATE))
				.put(ANC4_current_formStatus, submission.getField(ANC4_current_formStatus)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWGESTATIONALAGE, submission.getField(FWGESTATIONALAGE)).put(FWEDD, submission.getField(FWEDD))
				.put(FWANC4REMSTS, submission.getField(FWANC4REMSTS)).put(FWANC4INT, submission.getField(FWANC4INT))
				.put(FWANC4KNWPRVDR, submission.getField(FWANC4KNWPRVDR)).put(FWANC4PROB, submission.getField(FWANC4PROB))
				.put(FWBPC1LOCOFDEL, submission.getField(FWBPC1LOCOFDEL)).put(FWBPC1ASSTLAB, submission.getField(FWBPC1ASSTLAB))
				.put(FWBPC1TRNSPRT, submission.getField(FWBPC1TRNSPRT)).put(FWBPC1BLDGRP, submission.getField(FWBPC1BLDGRP))
				.put(FWBPC1BLDDNR, submission.getField(FWBPC1BLDDNR)).put(FWBPC1FINARGMT, submission.getField(FWBPC1FINARGMT))
				.put(FWANC4ANM, submission.getField(FWANC4ANM)).put(FWANC4HBP, submission.getField(FWANC4HBP)).put(FWANC4DBT, submission.getField(FWANC4DBT))
				.put(FWANC4THY, submission.getField(FWANC4THY)).put(FWANC4HEAD, submission.getField(FWANC4HEAD)).put(mauza, submission.getField(mauza))
				.put(FWVG, submission.getField(FWVG)).put(FWHR_PSR, submission.getField(FWHR_PSR)).put(FWHR_ANC2, submission.getField(FWHR_ANC2))
				.put(FWHRP, submission.getField(FWHRP)).put(FWHR_ANC1, submission.getField(FWHR_ANC1)).put(FWHR_ANC3, submission.getField(FWHR_ANC3))
				.put(existing_ELCO, submission.getField(existing_ELCO)).put(FWANC4BLRVIS, submission.getField(FWANC4BLRVIS))
				.put(FWANC4SWLNG, submission.getField(FWANC4SWLNG)).put(FWANC4CONVL, submission.getField(FWANC4CONVL))
				.put(FWANC4BLD, submission.getField(FWANC4BLD)).put(FWANC4DS1, submission.getField(FWANC4DS1)).put(FWANC4DS2, submission.getField(FWANC4DS2))
				.put(FWANC4DS3, submission.getField(FWANC4DS3)).put(FWANC4DS4, submission.getField(FWANC4DS4)).put(FWANC4DS5, submission.getField(FWANC4DS5))
				.put(FWANC4DS6, submission.getField(FWANC4DS6)).put(FWDANGERVALUE, submission.getField(FWDANGERVALUE))
				.put(FWNOTELIGIBLE, submission.getField(FWNOTELIGIBLE)).put(ELCO, submission.getField(ELCO)).put(FWHR_ANC4, submission.getField(FWHR_ANC4))
				.put(FWFLAGVALUE, submission.getField(FWFLAGVALUE)).put(FWSORTVALUE, submission.getField(FWSORTVALUE))
				.put(user_type, submission.getField(user_type)).put(external_user_ID, submission.getField(external_user_ID))
				.put(relationalid, submission.getField(relationalid)).put(FW_GOBHHID, submission.getField(FW_GOBHHID))
				.put(FW_JiVitAHHID, submission.getField(FW_JiVitAHHID)).put(FW_WOMBID, submission.getField(FW_WOMBID))
				.put(FW_WOMNID, submission.getField(FW_WOMNID)).put(FW_WOMFNAME, submission.getField(FW_WOMFNAME))
				.put(FW_HUSNAME, submission.getField(FW_HUSNAME)).put(MOTHER_REFERENCE_DATE, submission.getField(MOTHER_REFERENCE_DATE))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE)).put(START_DATE, submission.getField(START_DATE))
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(END_DATE, submission.getField(END_DATE))
				.put(received_time, DateUtil.getTodayAsString()).map();
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withANCVisitFour(ancVisitFour);
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		mother.setTimeStamp(System.currentTimeMillis());
		allMothers.update(mother);
		ancSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ANC, new LocalDate());
		// actionService.markAllAlertsAsInactive(submission.entityId());
		try {
			long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
			ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
		} catch (Exception e) {
			logger.info("From ancVisitFour:" + e.getMessage());
		}

		if (submission.getField(FWANC4REMSTS).equalsIgnoreCase(STS_GONE) || submission.getField(FWANC4REMSTS).equalsIgnoreCase(STS_DEAD)) {

			unEnrollBNFSchedule(submission.entityId(), submission.anmId(), submission.instanceId(), submission.getField("user_type"));
		}
	}

	public void pregnancyVerificationForm(FormSubmission submission) {

	}

	public void ancClose(String entityId) {

		Mother mother = allMothers.findByCaseId(entityId);

		if (mother == null) {
			logger.warn("Tried to close case without registered mother for case ID: " + entityId);
			return;
		}

		allMothers.close(entityId);

		ancSchedulesService.unEnrollFromAllSchedules(entityId);
	}

	public void closeMother(Mother mother) {

		mother.setIsClosed(true);
		allMothers.update(mother);
		ancSchedulesService.unEnrollFromAllSchedules(mother.caseId());

		Elco elco = allElcos.findByCaseId(mother.relationalid());
		logger.info("Closing EC case along with PNC case. Ec Id: " + elco.caseId());
		elco.setIsClosed(true);
		allElcos.update(elco);
	}

	public void deleteBlankMother(FormSubmission submission) {
		try {
			String motherId = submission.getField(AllConstants.ANCFormFields.MCARE_MOTHER_ID);
			Mother mother = allMothers.findByCaseId(motherId);
			allMothers.remove(mother);
		} catch (Exception e) {
			logger.info("Unable to delete mother :" + e.getMessage());
		}
	}

	public void unEnrollBNFSchedule(String entityId, String provider, String instanceId, String user_type) {
		logger.info("user_type:" + user_type);
		if (user_type.equalsIgnoreCase("FD")) {
			Mother mother = allMothers.findByCaseId(entityId);
			closeMother(mother);
			bnfSchedulesService.unEnrollBNFSchedule(entityId, provider);
			pncService.closeMother(mother);
			scheduleLogService.closeScheduleAndScheduleLog(entityId, instanceId, SCHEDULE_BNF, provider);
		} else {
			logger.info("User type :" + user_type);
		}
	}
}
