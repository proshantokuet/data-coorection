package org.opensrp.scheduler.repository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ektorp.BulkDeleteDocument;
import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.dto.ActionData;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.ScheduleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllActions extends MotechBaseRepository<Action> {
    private static Logger logger = LoggerFactory.getLogger(AllActions.class.toString());

    @Autowired
    public AllActions(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(Action.class, db);
    }

    @View(name = "action_by_anm_and_time", map = "function(doc) { if (doc.type === 'Action') { emit([doc.anmIdentifier, doc.timeStamp], null); } }")
    public List<Action> findByANMIDAndTimeStamp(String anmIdentifier, long timeStamp) {
        ComplexKey startKey = ComplexKey.of(anmIdentifier, timeStamp + 1);
        ComplexKey endKey = ComplexKey.of(anmIdentifier, Long.MAX_VALUE);
        return db.queryView(createQuery("action_by_anm_and_time").startKey(startKey).endKey(endKey).includeDocs(true), Action.class);
    }
    
    @View(name = "action_by_schedule_isActionActive_and_time", map = "function(doc) { if (doc.type === 'Action') { emit([doc.data.scheduleName,doc.isActionActive, doc.timeStamp], null); } }")
    public List<Action> findByScheduleIsActivenAndTimeStamp(String schedule,boolean isActive, long start,long end) {
        ComplexKey startKey = ComplexKey.of(schedule,isActive, start);
        ComplexKey endKey = ComplexKey.of(schedule,isActive, end);
        return db.queryView(createQuery("action_by_schedule_isActionActive_and_time").startKey(startKey).endKey(endKey).includeDocs(true), Action.class);
    }

    @View(name = "action_by_anm_entityId_scheduleName",
            map = "function(doc) { " +
                    "if(doc.type === 'Action' && doc.actionTarget === 'alert' && doc.anmIdentifier && doc.caseID && doc.data && doc.data.scheduleName) {" +
                    "emit([doc.anmIdentifier, doc.caseID, doc.data.scheduleName], null)} " +
                    "}")
    public List<Action> findAlertByANMIdEntityIdScheduleName(String anmIdentifier, String caseID, String scheduleName) {
        ComplexKey key = ComplexKey.of(anmIdentifier, caseID, scheduleName);
        return db.queryView(createQuery("action_by_anm_entityId_scheduleName").key(key).includeDocs(true), Action.class);
        
    }

    @View(name = "action_by_caseId_and_schedule_and_time", map = "function(doc) { if (doc.type === 'Action') { emit([doc.caseID, doc.data.scheduleName, doc.timeStamp], null); } }")
    public List<Action> findByCaseIdScheduleAndTimeStamp(String caseId, String schedule, DateTime start, DateTime end) {
        ComplexKey startKey = ComplexKey.of(caseId, schedule, start.getMillis() + 1);
        ComplexKey endKey = ComplexKey.of(caseId, schedule, end.getMillis());
        return db.queryView(createQuery("action_by_caseId_and_schedule_and_time").startKey(startKey).endKey(endKey).includeDocs(true), Action.class);
    }
    
    public void deleteAllByTarget(String target) {
        deleteAll(findByActionTarget(target));
    }

    public void markAllAsInActiveFor(String caseId) {
        List<Action> actions = findByCaseID(caseId);
        for (Action action : actions) {
            action.markAsInActive();
        }
        db.executeBulk(actions);
    }

    @GenerateView
    private List<Action> findByActionTarget(String target) {
        return queryView("by_actionTarget", target);
    }

    @GenerateView
    public List<Action> findByCaseID(String caseId) {
        return queryView("by_caseID", caseId);
    }

    private void deleteAll(List<Action> actions) {
        ArrayList<BulkDeleteDocument> deleteDocuments = new ArrayList<>();
        for (Action action : actions) {
            deleteDocuments.add(BulkDeleteDocument.of(action));
        }
        db.executeBulk(deleteDocuments);
    }

    public void addOrUpdateAlert(Action alertAction) {
        List<Action> existingAlerts = findAlertByANMIdEntityIdScheduleName(alertAction.anmIdentifier(), alertAction.caseId(), alertAction.data().get("scheduleName"));
        
        if (existingAlerts.size() > 1) {
            logger.warn(MessageFormat.format("Found more than one alert for the combination of anmId: {0}, entityId: {1} and scheduleName : {2}. Alerts : {3}",
                    alertAction.anmIdentifier(), alertAction.caseId(), alertAction.data().get("scheduleName"), existingAlerts));
        }
       
        for (Action existingAlert : existingAlerts) {
            safeRemove(existingAlert);
        }
        add(alertAction);
    }

    public void markAlertAsInactiveFor(String anmIdentifier, String caseId, String scheduleName) {
        List<Action> existingAlerts = findAlertByANMIdEntityIdScheduleName(anmIdentifier, caseId, scheduleName);
        if (existingAlerts.size() > 1) {
            logger.warn(MessageFormat.format("Found more than one alert for the combination of anmId: {0}, entityId: {1} and scheduleName : {2}. Alerts : {3}",
                    anmIdentifier, caseId, scheduleName, existingAlerts));
        }
        for (Action existingAlert : existingAlerts) {
            existingAlert.markAsInActive();
        }
        db.executeBulk(existingAlerts);
    }
    
    public List<Action> findByScheduleNameAndIsActive(boolean isActive, String schedule) {
        ComplexKey startKey = ComplexKey.of(isActive, schedule);
        ComplexKey endKey = ComplexKey.of(isActive, schedule);
        return db.queryView(createQuery("action_by_anm_active_scheduleName").startKey(startKey).endKey(endKey).includeDocs(true), Action.class);
    }
}
