/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWCONFIRMATION;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWEDD;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.FWGESTATIONALAGE;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.external_user_ID;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.relationalid;
import static org.opensrp.common.AllConstants.ANCVisitOneFields.user_type;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.*;
import static org.opensrp.common.AllConstants.CommonFormFields.ID;
import static org.opensrp.common.AllConstants.DeliveryOutcomeFields.CHILD_REGISTRATION_SUB_FORM_NAME;
import static org.opensrp.common.AllConstants.HHRegistrationFields.MOTHER_REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.received_time;
import static org.opensrp.common.AllConstants.PSRFFields.clientVersion;
import static org.opensrp.common.AllConstants.PSRFFields.timeStamp;
import static org.opensrp.common.AllConstants.UserType.FD;

import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_ANC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.MotherScheduleConstants.SCHEDULE_BNF;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.common.AllConstants;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.scheduling.ANCSchedulesService;
import org.opensrp.register.mcare.service.scheduling.BNFSchedulesService;
import org.opensrp.register.mcare.service.scheduling.ELCOScheduleService;
import org.opensrp.register.mcare.service.scheduling.ScheduleLogService;
import org.opensrp.scheduler.service.ActionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BNFService {

	private static Logger logger = LoggerFactory.getLogger(BNFService.class.toString());
	private ActionService actionService;
	private AllElcos allElcos;
	private AllMothers allMothers;
	private BNFSchedulesService bnfSchedulesService;
	private PNCService pncService;
	private ScheduleLogService scheduleLogService;
	private ANCSchedulesService ancSchedulesService;
    private ELCOScheduleService elcoScheduleService;

	@Autowired
	public BNFService(AllElcos allElcos, AllMothers allMothers, BNFSchedulesService bnfSchedulesService, PNCService pncService,
			ScheduleLogService scheduleLogService, ActionService actionService, ANCSchedulesService ancSchedulesService, ELCOScheduleService elcoScheduleService) {
		this.allElcos = allElcos;
		this.allMothers = allMothers;
		this.bnfSchedulesService = bnfSchedulesService;
		this.pncService = pncService;
		this.scheduleLogService = scheduleLogService;
		this.actionService = actionService;
		this.ancSchedulesService = ancSchedulesService;
		this.elcoScheduleService = elcoScheduleService;
	}

	public void registerBNF(FormSubmission submission) {
		String motherId = submission.getField(AllConstants.ANCFormFields.MCARE_MOTHER_ID);
		
		Mother mother = allMothers.findByCaseId(motherId);
		Elco elco = allElcos.findByCaseId(mother.relationalid());
		if (!allElcos.exists(submission.entityId())) {
			logger.warn(format("Found mother without registered eligible couple. Ignoring: {0} for mother with id: {1} for ANM: {2}", submission.entityId(),
					motherId, submission.anmId()));
			return;
		}
		mother.withFWWOMDISTRICT(elco.FWWOMDISTRICT());
		mother.withFWWOMUPAZILLA(elco.FWWOMUPAZILLA());
		allMothers.update(mother);
		
		bnfSchedulesService.enrollBNF(motherId, LocalDate.parse(submission.getField(MOTHER_REFERENCE_DATE)), submission.anmId(), submission.instanceId(),
				submission.getField(MOTHER_REFERENCE_DATE));

	}

	public void bnfFollowUpVisit(FormSubmission submission) {

		Mother mother = allMothers.findByCaseId(submission.entityId());

		if (mother == null) {
			logger.warn(format("Failed to handle BNF as there is no Mother enroll with ID: {0}", submission.entityId()));
			return;
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		Map<String, String> bnfVisit = create(FWBNFDATE, submission.getField(FWBNFDATE))
				.put(bnf_current_formStatus, submission.getField(bnf_current_formStatus)).put(FWCONFIRMATION, submission.getField(FWCONFIRMATION))
				.put(FWGESTATIONALAGE, submission.getField(FWGESTATIONALAGE)).put(FWEDD, submission.getField(FWEDD))
				.put(FWBNFSTS, submission.getField(FWBNFSTS)).put(FWDISPLAYTEXT1, submission.getField(FWDISPLAYTEXT1))
				.put(FWBNFWOMVITSTS, submission.getField(FWBNFWOMVITSTS)).put(FWBNFDTOO, submission.getField(FWBNFDTOO))
				.put(FWBNFLB, submission.getField(FWBNFLB)).put(FWBNFSMSRSN, submission.getField(FWBNFSMSRSN)).put(user_type, submission.getField(user_type))
				.put(external_user_ID, submission.getField(external_user_ID)).put(received_time, format.format(today).toString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(relationalid, submission.getField(relationalid)).map();

		SubFormData subFormData = submission.getSubFormByName(CHILD_REGISTRATION_SUB_FORM_NAME);

		for (Map<String, String> childFields : subFormData.instances()) {
			bnfVisit.put(FWBNFGEN, childFields.get(FWBNFGEN));
			bnfVisit.put(FWBNFCHLDVITSTS, childFields.get(FWBNFCHLDVITSTS));
		}

		mother.bnfVisitDetails().add(bnfVisit);
		mother.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		mother.withTODAY(submission.getField(REFERENCE_DATE));
		allMothers.update(mother);

		logger.info("submission.getField(FWBNFSTS):" + submission.getField(FWBNFSTS));
		if (submission.getField(FWBNFSTS).equalsIgnoreCase(STS_LB) || submission.getField(FWBNFSTS).equalsIgnoreCase(STS_SB)) {
			if (submission.getField("user_type").equalsIgnoreCase(FD)) {
				pncService.deliveryOutcome(submission);
				bnfSchedulesService.unEnrollBNFSchedule(submission.entityId(), submission.anmId());
				scheduleLogService.closeScheduleAndScheduleLog(submission.entityId(), submission.instanceId(), SCHEDULE_BNF, submission.anmId());

				/**
				 * Close Corresponding ANC schedule
				 * */
				scheduleLogService.ancScheduleUnEnroll(submission.entityId(), submission.anmId(), SCHEDULE_ANC);
				actionService.markAllAlertsAsInactive(submission.entityId());
				try {
					long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
					ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
				} catch (Exception e) {
					logger.info("From ancVisitOne:" + e.getMessage());
				}
			} else {
				logger.info("FWA submit live birth or still birth , so nothing hapened & BNF schedule continue.");
				bnfSchedulesService.enrollIntoMilestoneOfBNF(submission.entityId(), submission.getField(REFERENCE_DATE), submission.anmId(),
						submission.instanceId());
			}
			
			//pncService.deliveryOutcome(submission);

		} else if (submission.getField(FWBNFSTS).equalsIgnoreCase(STS_GONE) || submission.getField(FWBNFSTS).equalsIgnoreCase(STS_WD)) {
			if (submission.getField("user_type").equalsIgnoreCase(FD)) {
				pncService.deleteBlankChild(submission);
				bnfSchedulesService.unEnrollBNFSchedule(submission.entityId(), submission.anmId());
				pncService.closeMother(mother);
				scheduleLogService.closeScheduleAndScheduleLog(submission.entityId(), submission.instanceId(), SCHEDULE_BNF, submission.anmId());
				/**
				 * Close Corresponding ANC schedule
				 * */
				scheduleLogService.ancScheduleUnEnroll(submission.entityId(), submission.anmId(), SCHEDULE_ANC);
				actionService.markAllAlertsAsInactive(submission.entityId());
				try {
					long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
					ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
				} catch (Exception e) {
					logger.info("From ancVisitOne:" + e.getMessage());
				}
			} else {
				pncService.deleteBlankChild(submission);
				logger.info("FWA says mother gone or died , so nothing hapened & BNF schedule continue.");
				bnfSchedulesService.enrollIntoMilestoneOfBNF(submission.entityId(), submission.getField(REFERENCE_DATE), submission.anmId(),
						submission.instanceId());
			}

		} else if(submission.getField(FWBNFSTS).equalsIgnoreCase(STS_FALSE_PREGNANCY_IDENTIFICATION)) {
			if (submission.getField("user_type").equalsIgnoreCase(FD)) {
					pncService.deleteBlankChild(submission);
					bnfSchedulesService.unEnrollBNFSchedule(submission.entityId(), submission.anmId());
					pncService.closeMother(mother);
					scheduleLogService.closeScheduleAndScheduleLog(submission.entityId(), submission.instanceId(), SCHEDULE_BNF, submission.anmId());
					/**
					 * Close Corresponding ANC schedule
					 * */
					scheduleLogService.ancScheduleUnEnroll(submission.entityId(), submission.anmId(), SCHEDULE_ANC);
                    elcoScheduleService.imediateEnrollIntoMilestoneOfPSRF(submission.entityId(), submission.getField(REFERENCE_DATE), submission.anmId(),
                            submission.instanceId());
					try {
						long timestamp = actionService.getActionTimestamp(submission.anmId(), submission.entityId(), SCHEDULE_ANC);
						ancSchedulesService.fullfillSchedule(submission.entityId(), SCHEDULE_ANC, submission.instanceId(), timestamp);
					} catch (Exception e) {
						logger.info("From ancVisitOne:" + e.getMessage());
					}
			} else {
					pncService.deleteBlankChild(submission);
					logger.info("FWA says false pregnancy , so nothing hapened & BNF schedule continue.");
					bnfSchedulesService.enrollIntoMilestoneOfBNF(submission.entityId(), submission.getField(REFERENCE_DATE), submission.anmId(),
							submission.instanceId());
			}
			

		} else {
			pncService.deleteBlankChild(submission);
			logger.info("FWA submit BNF form , so nothing hapened & BNF schedule continue.");
			bnfSchedulesService.enrollIntoMilestoneOfBNF(submission.entityId(), submission.getField(REFERENCE_DATE), submission.anmId(),
					submission.instanceId());
		}

	}
	
	public Mother getElcoById(String id){
		return allMothers.get(id);
	}

}
