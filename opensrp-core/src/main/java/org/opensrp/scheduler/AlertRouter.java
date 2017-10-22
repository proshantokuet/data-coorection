package org.opensrp.scheduler;

import java.util.ArrayList;
import java.util.List;

import org.motechproject.scheduler.domain.MotechEvent;
import org.motechproject.scheduletracking.api.events.constants.EventSubjects;
import org.motechproject.scheduletracking.api.service.ScheduleTrackingService;
import org.motechproject.server.event.annotations.MotechListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.opensrp.common.AllConstants.BnfFollowUpVisitFields;
import org.opensrp.common.AllConstants.ELCOSchedulesConstantsImediate;
import static org.opensrp.common.AllConstants.BnfFollowUpVisitFields.SCHEDULE_BNF_IME;

/**
 * The class that maintains the actions against alerts by {@link ScheduleTrackingService}
 */
@Component
public class AlertRouter {
    private List<Route> routes;
    private static Logger logger = LoggerFactory.getLogger(AlertRouter.class.toString());


    public AlertRouter() {
        routes = new ArrayList<>();
    }

    public Route addRoute(Matcher scheduleMatcher, Matcher milestoneMatcher, Matcher windowMatcher, HookedEvent action) {
        Route route = new Route(scheduleMatcher, milestoneMatcher, windowMatcher, action);
        routes.add(route);
        return route;
    }

    public Route addRoute(Route route) {
        routes.add(route);
        return route;
    }
    
    @MotechListener(subjects = {EventSubjects.MILESTONE_ALERT})
    public void handle(MotechEvent realEvent) {
        logger.info("Handling motech event : " + realEvent);
        MilestoneEvent event = new MilestoneEvent(realEvent);
        if(event.scheduleName().equalsIgnoreCase(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF)){
    		event.scheduleName().contentEquals(ELCOSchedulesConstantsImediate.ELCO_SCHEDULE_PSRF);
    		event.milestoneName().contentEquals(ELCOSchedulesConstantsImediate.ELCO_SCHEDULE_PSRF);
    	}
        
        for (Route route : routes) {
        	
        	 if (route.isSatisfiedBy(parseScheduleName(event.scheduleName()), parseScheduleName(event.milestoneName()), event.windowName())) {
                 route.invokeAction(event);
                 return;
             }
        }

        throw new NoRoutesMatchException(event);
    }
    public String parseScheduleName(String scheduleName){
    	if(scheduleName.equalsIgnoreCase(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF)){
    		return scheduleName.replace(ELCOSchedulesConstantsImediate.IMD_ELCO_SCHEDULE_PSRF, ELCOSchedulesConstantsImediate.ELCO_SCHEDULE_PSRF);
    	}else{
    		return scheduleName.replace(SCHEDULE_BNF_IME, BnfFollowUpVisitFields.SCHEDULE_BNF);
    	}
    	
    }
}
