package org.opensrp.scheduler.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.BeneficiaryType;
import org.opensrp.dto.ScheduleData;
import org.opensrp.scheduler.ScheduleLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllReportActions extends MotechBaseRepository<ScheduleLog> {
	
	private static Logger logger = LoggerFactory.getLogger(AllReportActions.class.toString());
	
	@Autowired
	public AllReportActions(@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db)
	{
		 super(ScheduleLog.class, db);
	}
	
	@GenerateView
    public List<ScheduleLog> findByCaseID(String caseID) {
        return queryView("by_caseID", caseID);
    }
	
	public void addAlert(ScheduleLog alertAction) {
        add(alertAction);
    }
	
	@View(name = "all_visits_scheduled_by_server" , 
			 map = "function(doc) { " +
	                    "if(doc.type === 'ScheduleLog' && doc.actionTarget === 'alert' && doc.anmIdentifier && doc.caseID && doc.data && doc.data.scheduleName) {" +
	                    "emit([doc.anmIdentifier, doc.data.scheduleName], null)} " +
	                    "}")
	public Map<String, ScheduleLog> findAllSchedulesForVisits(String anmIdentifier, String scheduleName, LocalDate startDate, LocalDate endDate)
	{
		Map<String, ScheduleLog> schedulesMap = new HashMap<String,ScheduleLog>();
		 ComplexKey key = ComplexKey.of(anmIdentifier, scheduleName);
		
		 List<ViewResult.Row> rows = db.queryView(createQuery("all_visits_scheduled_by_server").key(key)
				 .cacheOk(true)).getRows();
		 
		 for(ViewResult.Row row : rows)
		 {
			 if(!schedulesMap.containsKey(row.getKey()))
			 {
				 
				 ScheduleData actionData = ScheduleData.createAlert(
						 				 BeneficiaryType.from(row.getValueAsNode().findValue("data").get("beneficiaryType").asText()),
										 row.getValueAsNode().findValue("data").get("scheduleName").toString(),
										 row.getValueAsNode().findValue("data").get("visitCode").toString(),
										 AlertStatus.from(row.getValueAsNode().findValue("data").get("alertStatus").asText()),
										 DateTime.parse(row.getValueAsNode().findValue("data").get("startDate").asText()),
										 DateTime.parse(row.getValueAsNode().findValue("data").get("expiryDate").asText())
						 				);
				 
				 //schedulesMap.put(row.getKey(), new ScheduleLog(row.getValueAsNode().findValue("caseID").toString(), row.getValueAsNode().findValue("instanceId").toString(),row.getValueAsNode().findValue("anmIdentifier").toString(), actionData,row.getValueAsNode().findValue("caseID").asLong()));
			 }
		 }
				
		 return schedulesMap;
	}
	private static final String FUNCTION_DOC_EMIT_DOC_TIMESTAMP_CASEID_NAME = "function(doc) { if(doc.type === 'ScheduleLog') emit([doc.timeStamp,doc.caseID,doc.scheduleName], doc.caseID);}";
	@View(name = "by_timestamp_id_bycaseId_by_name", map = FUNCTION_DOC_EMIT_DOC_TIMESTAMP_CASEID_NAME)
    public ScheduleLog findByTimestampIdByCaseIdByname(long timeStamp,String caseId,String name) {
        List<ScheduleLog> scheduleLog = queryView("by_timestamp_id_bycaseId_by_name", ComplexKey.of(timeStamp,caseId,name));
        if (scheduleLog == null || scheduleLog.isEmpty()) {
			return null;
		}
		return scheduleLog.get(0);        
    }
	private static final String FUNCTION_DOC_EMIT_DOC_INSTANCEID_CASEID_NAME = "function(doc) { if(doc.type === 'ScheduleLog') emit([doc.instanceId,doc.caseID,doc.scheduleName], doc.caseID);}";
	@View(name = "by_instance_id_bycaseId_by_name", map = FUNCTION_DOC_EMIT_DOC_INSTANCEID_CASEID_NAME)
    public ScheduleLog findByInstanceIdByCaseIdByname(String instanceId,String caseId,String name) {
        List<ScheduleLog> scheduleLog = queryView("by_instance_id_bycaseId_by_name", ComplexKey.of(instanceId,caseId,name));
        if (scheduleLog == null || scheduleLog.isEmpty()) {
			return null;
		}
		return scheduleLog.get(0);        
    }
	
	private static final String FUNCTION_DOC_EMIT_DOC__CASEID_NAME = "function(doc) { if(doc.type === 'ScheduleLog') emit([doc.caseID,doc.scheduleName], doc.caseID);}";
	@View(name = "by_bycaseId_by_name", map = FUNCTION_DOC_EMIT_DOC__CASEID_NAME)
    public ScheduleLog findByyCaseIdByname(String caseId,String name) {
        List<ScheduleLog> scheduleLog = queryView("by_bycaseId_by_name", ComplexKey.of(caseId,name));
        if (scheduleLog == null || scheduleLog.isEmpty()) {
			return null;
		}
		return scheduleLog.get(0);        
    }
    

}
