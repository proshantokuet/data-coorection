package org.opensrp.form.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllFormSubmissions extends MotechBaseRepository<FormSubmission> {
    @Autowired
    public AllFormSubmissions(@Qualifier(AllConstants.OPENSRP_FORM_DATABASE_CONNECTOR) CouchDbConnector db) {
        super(FormSubmission.class, db);
    }

    public boolean exists(String instanceId) {
        return findByInstanceId(instanceId) != null;
    }

    @GenerateView
    public FormSubmission findByInstanceId(String instanceId) {
        List<FormSubmission> submissions = queryView("by_instanceId", instanceId);
        if (submissions == null || submissions.isEmpty()) {
            return null;
        }
        return submissions.get(0);
    }

    @View(name = "formSubmission_by_server_version", map = "function(doc) { if (doc.type === 'FormSubmission') { emit([doc.serverVersion], null); } }")
    public List<FormSubmission> findByServerVersion(long serverVersion) {
        ComplexKey startKey = ComplexKey.of(serverVersion + 1);
        ComplexKey endKey = ComplexKey.of(Long.MAX_VALUE);
        return db.queryView(createQuery("formSubmission_by_server_version").startKey(startKey).endKey(endKey).includeDocs(true), FormSubmission.class);
    }

    public List<FormSubmission> allFormSubmissions(long serverVersion, Integer batchSize) {
        ComplexKey startKey = ComplexKey.of(serverVersion + 1);
        ComplexKey endKey = ComplexKey.of(Long.MAX_VALUE);
        ViewQuery query = createQuery("formSubmission_by_server_version")
                .startKey(startKey)
                .endKey(endKey)
                .includeDocs(true);

        if (batchSize != null) {
            query.limit(batchSize);
        }
        return db.queryView(query, FormSubmission.class);
    }

    
    @View(
            name = "formSubmission_by_anm",
            map = "function(doc) { if (doc.type === 'FormSubmission' && doc.anmId) { emit(doc.anmId, null); } }")
    public List<FormSubmission> findByANMID(String anmId) {
       
    	return db.queryView(
				createQuery("formSubmission_by_anm").key(anmId)
						.includeDocs(true), FormSubmission.class);
    }

    
	@View(
            name = "formSubmission_by_anm_and_server_version",
            map = "function(doc) { if (doc.type === 'FormSubmission') { emit([doc.anmId, doc.serverVersion], null); } }")
    public List<FormSubmission> findByANMIDAndServerVersion(String anmId, long version, Integer batchSize) {
       
    	  ComplexKey startKey = ComplexKey.of(anmId, version + 1);
          ComplexKey endKey = ComplexKey.of(anmId, Long.MAX_VALUE);
        
        ViewQuery query = createQuery("formSubmission_by_anm_and_server_version")
                .startKey(startKey)
                .endKey(endKey)
                .includeDocs(true);
        if (batchSize != null) {
            query.limit(batchSize);
        }
        return db.queryView(query, FormSubmission.class);
    }

	@View(
            name = "formSubmission_by_anm_and_usertype_and_server_version",
            map = "function(doc) {if(doc.formInstance.form.fields.length > 0 && doc.type==='FormSubmission') {for(var field in doc.formInstance.form.fields) {if(doc.formInstance.form.fields[field].name ==='user_type'){emit([doc.anmId,doc.formInstance.form.fields[field].value, doc.serverVersion], null);}}}}")
    public List<FormSubmission> findByANMIDAndUserTypeAndServerVersion(String anmId, String userType, long version, Integer batchSize) {
       
    	  ComplexKey startKey = null;
          ComplexKey endKey = null;
        
            if(userType != null && !userType.isEmpty())
            {
            	startKey = ComplexKey.of(anmId, userType, version + 1);
            	endKey = ComplexKey.of(anmId, userType, Long.MAX_VALUE);
            }
            /*else {
            	startKey = ComplexKey.of(anmId, version + 1);
            	endKey = ComplexKey.of(anmId, Long.MAX_VALUE);
            }*/
            
        ViewQuery query = createQuery("formSubmission_by_anm_and_usertype_and_server_version")
                .startKey(startKey)
                .endKey(endKey)
                .includeDocs(true);
        if (batchSize != null) {
            query.limit(batchSize);
        }
        return db.queryView(query, FormSubmission.class);
    }
	
	@View(
            name = "formSubmission_by_both_user_fwa_after_90",
            map = "function(doc) { if(doc.formInstance.form.fields.length > 0 && doc.type==='FormSubmission') { for(var field in doc.formInstance.form.fields) { if(doc.formInstance.form.fields[field].name ==='user_type'){ if (doc.formInstance.form.fields[field].value==='FD'){ emit([doc.anmId, doc.serverVersion], null); } else if (doc.formInstance.form.fields[field].value==='FWA' && doc.serverVersion > 1456358400000){ emit([doc.anmId, doc.serverVersion], null); } } } } }")
    public List<FormSubmission> findBothUserSubmissionFWAAfter90(String anmId, long version, Integer batchSize) {
       
    	ComplexKey startKey = ComplexKey.of(anmId, version + 1);
        ComplexKey endKey = ComplexKey.of(anmId, Long.MAX_VALUE);
        
        ViewQuery query = createQuery("formSubmission_by_both_user_fwa_after_90")
                .startKey(startKey)
                .endKey(endKey)
                .includeDocs(true);
        if (batchSize != null) {
            query.limit(batchSize);
        }
        return db.queryView(query, FormSubmission.class);
        
		/*ViewQuery query = createQuery("formSubmission_by_both_user_fwa_after_90").includeDocs(true);
		
		if (batchSize != null) {
            query.limit(batchSize);
        }
        return db.queryView(query, FormSubmission.class);*/
    }
	
	 @View(name = "form_by_entity", map = "function(doc) { if (doc.type === 'FormSubmission' && doc.entityId) { emit(doc.entityId,null ); } }")
	 public List<FormSubmission> getByEntityId(String entityId){
		 
	    return db.queryView(
					createQuery("form_by_entity").key(entityId)
							.includeDocs(true), FormSubmission.class);
	 }
	 
	
}
