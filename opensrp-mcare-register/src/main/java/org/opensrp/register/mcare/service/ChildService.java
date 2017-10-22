/**
 * @author julkar nain
 */
package org.opensrp.register.mcare.service;

import static java.text.MessageFormat.format;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1BFINTN;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1BTHD;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DATE;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DELCOMP;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DRYWM;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSCONVL;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSDIFBRTH;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSFOULUMBS;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSFVRCLD;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSLETH;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSLIMBLUE;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1DSSKNYLW;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1HDCOV;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1PRLCTL;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1STS;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1TEMP;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.FWENC1UMBS;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.encc1_current_formStatus;
import static org.opensrp.common.AllConstants.ENCCVisitOneFields.referenceDate;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3BFINTN;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3BTHD;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DATE;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DELCOMP;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DRYWM;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSCONVL;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSDIFBRTH;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSFOULUMBS;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSFVRCLD;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSLETH;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSLIMBLUE;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3DSSKNYLW;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3HDCOV;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3PRLCTL;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3STS;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3TEMP;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.FWENC3UMBS;
import static org.opensrp.common.AllConstants.ENCCVisitThreeFields.encc3_current_formStatus;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2BFINTN;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2BTHD;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DATE;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DELCOMP;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DRYWM;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSCONVL;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSDIFBRTH;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSFOULUMBS;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSFVRCLD;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSLETH;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSLIMBLUE;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2DSSKNYLW;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2HDCOV;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2PRLCTL;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2STS;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2TEMP;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.FWENC2UMBS;
import static org.opensrp.common.AllConstants.ENCCVisitTwoFields.encc2_current_formStatus;
import static org.opensrp.common.AllConstants.HHRegistrationFields.END_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.REFERENCE_DATE;
import static org.opensrp.common.AllConstants.HHRegistrationFields.START_DATE;
import static org.opensrp.common.AllConstants.PSRFFields.clientVersion;
import static org.opensrp.common.AllConstants.PSRFFields.timeStamp;
import static org.opensrp.common.util.EasyMap.create;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_2;
import static org.opensrp.register.mcare.OpenSRPScheduleConstants.ChildScheduleConstants.SCHEDULE_ENCC_3;

import java.util.Map;

import org.joda.time.LocalDate;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.service.scheduling.ChildSchedulesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildService {

	private static Logger logger = LoggerFactory.getLogger(ChildService.class
			.toString());
	
	private AllChilds allChilds;
	private ChildSchedulesService childSchedulesService;
	
	@Autowired
	public ChildService(AllChilds allChilds, ChildSchedulesService childSchedulesService)
	{
		this.allChilds = allChilds;
		this.childSchedulesService = childSchedulesService;
	}
	
	public void enccOne(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());

		if (child == null) {
			logger.warn(format(
					"Failed to handle ENCC-1 as there is no Child enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		Map<String, String> enccOne = create(FWENC1DATE, submission.getField(FWENC1DATE))
				.put(FWENC1STS, submission.getField(FWENC1STS))
				.put(FWENC1BFINTN, submission.getField(FWENC1BFINTN))
				.put(FWENC1PRLCTL, submission.getField(FWENC1PRLCTL))
				.put(FWENC1DRYWM, submission.getField(FWENC1DRYWM))
				.put(FWENC1HDCOV, submission.getField(FWENC1HDCOV))
				.put(FWENC1BTHD, submission.getField(FWENC1BTHD))
				.put(FWENC1UMBS, submission.getField(FWENC1UMBS))
				.put(FWENC1DSFVRCLD, submission.getField(FWENC1DSFVRCLD))
				.put(FWENC1TEMP, submission.getField(FWENC1TEMP))
				.put(FWENC1DSFOULUMBS, submission.getField(FWENC1DSFOULUMBS))
				.put(FWENC1DSLIMBLUE, submission.getField(FWENC1DSLIMBLUE))
				.put(FWENC1DSSKNYLW, submission.getField(FWENC1DSSKNYLW))
				.put(FWENC1DSLETH, submission.getField(FWENC1DSLETH))
				.put(FWENC1DSDIFBRTH, submission.getField(FWENC1DSDIFBRTH))
				.put(FWENC1DSCONVL, submission.getField(FWENC1DSCONVL))
				.put(FWENC1DELCOMP, submission.getField(FWENC1DELCOMP))
				.put(encc1_current_formStatus, submission.getField(encc1_current_formStatus))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE))
				.put("received_time", DateUtil.getTodayAsString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(END_DATE, submission.getField(END_DATE)).map();	

		child.withENCCVisitOne(enccOne);
		child.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		child.withTODAY(submission.getField(REFERENCE_DATE));
		child.setTimeStamp(System.currentTimeMillis());
		allChilds.update(child);
		childSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ENCC, new LocalDate());
		childSchedulesService.enrollENCCVisit(submission.entityId(), SCHEDULE_ENCC_2, LocalDate.parse(child.getDetail(referenceDate)));
		
	}

	public void enccTwo(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());

		if (child == null) {
			logger.warn(format(
					"Failed to handle ENCC-2 as there is no Child enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		Map<String, String> enccTwo = create(FWENC2DATE, submission.getField(FWENC2DATE))
				.put(FWENC2STS, submission.getField(FWENC2STS))
				.put(FWENC2BFINTN, submission.getField(FWENC2BFINTN))
				.put(FWENC2PRLCTL, submission.getField(FWENC2PRLCTL))
				.put(FWENC2DRYWM, submission.getField(FWENC2DRYWM))
				.put(FWENC2HDCOV, submission.getField(FWENC2HDCOV))
				.put(FWENC2BTHD, submission.getField(FWENC2BTHD))
				.put(FWENC2UMBS, submission.getField(FWENC2UMBS))
				.put(FWENC2DSFVRCLD, submission.getField(FWENC2DSFVRCLD))
				.put(FWENC2TEMP, submission.getField(FWENC2TEMP))
				.put(FWENC2DSFOULUMBS, submission.getField(FWENC2DSFOULUMBS))
				.put(FWENC2DSLIMBLUE, submission.getField(FWENC2DSLIMBLUE))
				.put(FWENC2DSSKNYLW, submission.getField(FWENC2DSSKNYLW))
				.put(FWENC2DSLETH, submission.getField(FWENC2DSLETH))
				.put(FWENC2DSDIFBRTH, submission.getField(FWENC2DSDIFBRTH))
				.put(FWENC2DSCONVL, submission.getField(FWENC2DSCONVL))
				.put(FWENC2DELCOMP, submission.getField(FWENC2DELCOMP))
				.put(encc2_current_formStatus, submission.getField(encc2_current_formStatus))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE))
				.put("received_time", DateUtil.getTodayAsString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(END_DATE, submission.getField(END_DATE)).map();	

		child.withENCCVisitTwo(enccTwo);
		child.withTODAY(submission.getField(REFERENCE_DATE));
		child.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		child.setTimeStamp(System.currentTimeMillis());
		allChilds.update(child);		
		childSchedulesService.fullfillMilestone(submission.entityId(), submission.anmId(), SCHEDULE_ENCC, new LocalDate());	
		childSchedulesService.enrollENCCVisit(submission.entityId(), SCHEDULE_ENCC_3, LocalDate.parse(child.getDetail(referenceDate)));
		
	}

	public void enccThree(FormSubmission submission) {
		Child child = allChilds.findByCaseId(submission.entityId());

		if (child == null) {
			logger.warn(format(
					"Failed to handle ENCC-3 as there is no Child enroll with ID: {0}",
					submission.entityId()));
			return;
		}
		
		Map<String, String> enccThree = create(FWENC3DATE, submission.getField(FWENC3DATE))
				.put(FWENC3STS, submission.getField(FWENC3STS))
				.put(FWENC3BFINTN, submission.getField(FWENC3BFINTN))
				.put(FWENC3PRLCTL, submission.getField(FWENC3PRLCTL))
				.put(FWENC3DRYWM, submission.getField(FWENC3DRYWM))
				.put(FWENC3HDCOV, submission.getField(FWENC3HDCOV))
				.put(FWENC3BTHD, submission.getField(FWENC3BTHD))
				.put(FWENC3UMBS, submission.getField(FWENC3UMBS))
				.put(FWENC3DSFVRCLD, submission.getField(FWENC3DSFVRCLD))
				.put(FWENC3TEMP, submission.getField(FWENC3TEMP))
				.put(FWENC3DSFOULUMBS, submission.getField(FWENC3DSFOULUMBS))
				.put(FWENC3DSLIMBLUE, submission.getField(FWENC3DSLIMBLUE))
				.put(FWENC3DSSKNYLW, submission.getField(FWENC3DSSKNYLW))
				.put(FWENC3DSLETH, submission.getField(FWENC3DSLETH))
				.put(FWENC3DSDIFBRTH, submission.getField(FWENC3DSDIFBRTH))
				.put(FWENC3DSCONVL, submission.getField(FWENC3DSCONVL))
				.put(FWENC3DELCOMP, submission.getField(FWENC3DELCOMP))
				.put(encc3_current_formStatus, submission.getField(encc3_current_formStatus))
				.put(REFERENCE_DATE, submission.getField(REFERENCE_DATE))
				.put(START_DATE, submission.getField(START_DATE))
				.put("received_time", DateUtil.getTodayAsString())
				.put(timeStamp, ""+System.currentTimeMillis())
				.put(clientVersion, DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)).toString())
				.put(END_DATE, submission.getField(END_DATE)).map();	

		child.withENCCVisitThree(enccThree);
		child.withClientVersion(DateTimeUtil.getTimestampOfADate(submission.getField(REFERENCE_DATE)));
		child.withTODAY(submission.getField(REFERENCE_DATE));
		child.setTimeStamp(System.currentTimeMillis());
		allChilds.update(child);		
		childSchedulesService.unEnrollFromSchedule(submission.entityId(), submission.anmId(), SCHEDULE_ENCC);
		
	}

}
