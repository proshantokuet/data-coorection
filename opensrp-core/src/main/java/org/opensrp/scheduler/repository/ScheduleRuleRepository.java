package org.opensrp.scheduler.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.scheduler.ScheduleRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class ScheduleRuleRepository extends MotechBaseRepository<ScheduleRules>{
	
	@Autowired
    protected ScheduleRuleRepository(@Qualifier(AllConstants.OPENSRP_SCHEDULE_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(ScheduleRules.class, db);
    }
	public String submit(ScheduleRules scheduleRules){		
		try{			
			add(scheduleRules);
			return "1";
		}catch(Exception e){
			e.printStackTrace();
			return "0";
		}
	}
	
	
	@View(name = "all_rule", map = "function(doc) { if (doc.type === 'ScheduleRules') { emit(doc); } }")
    public List<ScheduleRules> allRule(){
    	return db.queryView(
				createQuery("all_rule")
						.includeDocs(true), ScheduleRules.class);
    }
	
	private static final String FUNCTION_DOC_EMIT_DOC_NAME = "function(doc) { if(doc.type === 'ScheduleRules') emit([doc.name], doc);}";
    @View(name = "by_Name", map = FUNCTION_DOC_EMIT_DOC_NAME)
    public ScheduleRules findByName(String name) {
    	try{
    		return  queryView("by_Name", ComplexKey.of(name)).get(0);
    	}catch(Exception e){
    		return null;
    	}
        
    } 
    
    private static final String FUNCTION_DOC_EMIT_DOC_ID = "function(doc) { if(doc.type === 'ScheduleRules') emit(doc._id, doc);}";
    @View(name = "by_Id", map = FUNCTION_DOC_EMIT_DOC_ID)
    public ScheduleRules findById(String id) {
    	try{
    		ComplexKey complexKey = ComplexKey.of(id);
    		ViewQuery query = createQuery("by_Id").designDocId(stdDesignDocumentId).key(complexKey).includeDocs(true);
    		return  db.queryView(query,ScheduleRules.class).get(0);
    	}catch(Exception e){
    		 return null;
    	}
        
    } 
    private static final String FUNCTION_DOC_EMIT_DOC_Id = "function(doc) { if(doc.type === 'ScheduleRules') emit([doc._id], doc);}";
    @View(name = "by_ID", map = FUNCTION_DOC_EMIT_DOC_Id)
    public ScheduleRules findByID(String id) {
       try{
    	   ComplexKey complexKey = ComplexKey.of(id);
    	   ViewQuery query = createQuery("by_ID").designDocId(stdDesignDocumentId).key(complexKey).includeDocs(true);
    	   return  db.queryView(query,ScheduleRules.class).get(0);
    	}catch(Exception e){
    	   return null;
    	}
        
    } 
   
}
