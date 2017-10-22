package org.opensrp.web.listener;

import java.util.concurrent.TimeUnit;

import org.opensrp.register.mcare.OpenSRPScheduleConstants;
import org.opensrp.scheduler.RepeatingSchedule;
import org.opensrp.scheduler.TaskSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    public static final String APPLICATION_ID = "org.springframework.web.context.WebApplicationContext:/opensrp";

    private TaskSchedulerService scheduler;
    
    private RepeatingSchedule formSchedule;
    private RepeatingSchedule anmReportScheduler;
    private RepeatingSchedule mctsReportScheduler;
    private RepeatingSchedule openmrsScheduleSyncerScheduler;
    private RepeatingSchedule dataTransmission;
    
    @Autowired
    public ApplicationStartupListener(TaskSchedulerService scheduler, 
    		@Value("#{opensrp['form.poll.time.interval']}") int formPollInterval,
    		@Value("#{opensrp['mcts.poll.time.interval.in.minutes']}") int mctsPollIntervalInHours,
    		@Value("#{opensrp['openmrs.scheduletracker.syncer.interval-min']}") int openmrsSchSyncerMin
    		) {
        this.scheduler = scheduler;
        formSchedule = new RepeatingSchedule(OpenSRPScheduleConstants.FORM_SCHEDULE_SUBJECT, 0, TimeUnit.MINUTES, formPollInterval, TimeUnit.MINUTES);
        //anmReportScheduler = new RepeatingSchedule(OpenSRPScheduleConstants.ANM_REPORT_SCHEDULE_SUBJECT, 10, TimeUnit.MINUTES, 6, TimeUnit.HOURS);
        //mctsReportScheduler = new RepeatingSchedule(OpenSRPScheduleConstants.MCTS_REPORT_SCHEDULE_SUBJECT, 10, TimeUnit.MINUTES, mctsPollIntervalInHours, TimeUnit.HOURS);
        //openmrsScheduleSyncerScheduler = new RepeatingSchedule(OpenmrsConstants.SCHEDULER_TRACKER_SYNCER_SUBJECT, 0, TimeUnit.MINUTES, 1, TimeUnit.MINUTES);
       // dataTransmission = new RepeatingSchedule(ETLConstant.DATA_TRANSMISSION_SUBJECT, 0, TimeUnit.MINUTES, formPollInterval, TimeUnit.MINUTES);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (APPLICATION_ID.equals(contextRefreshedEvent.getApplicationContext().getId())) {
        	System.out.println("Starting job formSchedule SUBJECT:" + formSchedule.SUBJECT + " START_DELAY:" + formSchedule.START_DELAY 
        			+ " REPEAT_INTERVAL:" + formSchedule.REPEAT_INTERVAL+ " START_DELAY_UNIT:" + formSchedule.START_DELAY_UNIT+ " REPEAT_INTERVAL_UNIT:" + formSchedule.REPEAT_INTERVAL_UNIT );
            scheduler.startJob(formSchedule);
            //scheduler.startJob(anmReportScheduler);
            //scheduler.startJob(mctsReportScheduler);
            //scheduler.startJob(openmrsScheduleSyncerScheduler);
            //scheduler.startJob(dataTransmission);
        }
    }
}




