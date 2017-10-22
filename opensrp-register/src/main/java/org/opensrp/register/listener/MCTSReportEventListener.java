package org.opensrp.register.listener;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace;
import static org.opensrp.common.AllConstants.DEFAULT_DATE_FORMAT;

import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.server.event.annotations.MotechListener;
import org.opensrp.common.util.DateUtil;
import org.opensrp.register.DrishtiScheduleConstants;
import org.opensrp.register.domain.MCTSReport;
import org.opensrp.register.service.MCTSSMSReportService;
import org.opensrp.register.service.MCTSSMSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MCTSReportEventListener {
    private static Logger logger = LoggerFactory.getLogger(MCTSReportEventListener.class.toString());
    private MCTSSMSService mctssmsService;
    private MCTSSMSReportService mctsSMSReportService;
    private static final ReentrantLock lock = new ReentrantLock();

    @Autowired
    public MCTSReportEventListener(MCTSSMSService mctssmsService, MCTSSMSReportService mctsSMSReportService) {
        this.mctssmsService = mctssmsService;
        this.mctsSMSReportService = mctsSMSReportService;
    }

    @MotechListener(subjects = DrishtiScheduleConstants.MCTS_REPORT_SCHEDULE_SUBJECT)
    public void fetchReports(MotechEvent event) {
        if (!lock.tryLock()) {
            logger.warn("Not fetching MCTS Reports. It is already in progress.");
            return;
        }
        try {
            logger.info("Fetching MCTS Reports");

            List<MCTSReport> reports = mctsSMSReportService.fetch(DateUtil.today().toString(DEFAULT_DATE_FORMAT));
            if (reports.isEmpty()) {
                logger.info("No new MCTS report found.");
                return;
            }

            sendSMS(reports);

            logger.info(format("Sent {0} new MCTS  found.", reports.size()));
        } catch (Exception e) {
            logger.error(MessageFormat.format("{0} occurred while trying to fetch forms. Message: {1} with stack trace {2}",
                    e.toString(), e.getMessage(), getFullStackTrace(e)));
        } finally {
            lock.unlock();
        }
    }

    private void sendSMS(List<MCTSReport> reports) {
        for (MCTSReport report : reports) {
            mctssmsService.send(report.reportText());
            mctsSMSReportService.markReportAsSent(report);
        }
    }
}
