package org.opensrp.scheduler.service;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.joda.time.DateTime;
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.motechproject.scheduletracking.api.repository.AllEnrollments;
import org.motechproject.scheduletracking.api.repository.AllSchedules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class AllEnrollmentWrapper extends AllEnrollments{
	@Autowired
    private AllSchedules allSchedules;
	
	@Autowired
	public AllEnrollmentWrapper(@Qualifier("scheduleTrackingDbConnector") CouchDbConnector db) {
		super(db);
	}

	 private static final String FUNCTION_DOC_EMIT_DOC_STATUS_AND_ENROLLED_ON = "function(doc) { if(doc.type === 'Enrollment') emit([doc.status,doc.enrolledOn], doc._id);}";

	    @View(name = "by_status_date_enrolled", map = FUNCTION_DOC_EMIT_DOC_STATUS_AND_ENROLLED_ON)
	    public List<Enrollment> findByEnrollmentDate(String status, DateTime start, DateTime end) {
	        List<Enrollment> enrollments = queryView("by_status_date_enrolled", ComplexKey.of(status, start, end));
	        return populateWithSchedule(enrollments);
	    }
	    
	    private static final String FUNCTION_DOC_EMIT_LAST_UPDATE = "function(doc) { if(doc.type === 'Enrollment') emit(doc.metadata.lastUpdate, doc._id);}";
	    
	    @View(name = "by_last_update", map = FUNCTION_DOC_EMIT_LAST_UPDATE)
	    public List<Enrollment> findByLastUpDate(DateTime start, DateTime end) {
	        List<Enrollment> enrollments = db.queryView(createQuery("by_last_update").startKey(start).endKey(end).includeDocs(true), Enrollment.class);
	    	return populateWithSchedule(enrollments);
	    }
	    private List<Enrollment> populateWithSchedule(List<Enrollment> enrollments) {
	        for (Enrollment enrollment : enrollments)
	            populateSchedule(enrollment);
	        return enrollments;
	    }

	    private Enrollment populateSchedule(Enrollment enrollment) {
	        enrollment.setSchedule(allSchedules.getByName(enrollment.getScheduleName()));
	        return enrollment;
	    }
	    
	    private static final String FUNCTION_DOC_EMIT_DOC_EXTERNALID_AND_SCHEDULENAME = "function(doc) { if(doc.type === 'Enrollment') emit([doc.externalId,doc.scheduleName,doc.status], doc._id);}";
	    @View(name = "by_externalId_scheduleName", map = FUNCTION_DOC_EMIT_DOC_EXTERNALID_AND_SCHEDULENAME)
	    public List<Enrollment> findByEnrollmentByExternalIdAndScheduleName(String externalId, String ScheduleName) {
	        List<Enrollment> enrollments = queryView("by_externalId_scheduleName", ComplexKey.of(externalId, ScheduleName,"ACTIVE"));
	        return populateWithSchedule(enrollments);
	    }
	    
	   
		 public List<Enrollment> getByExternalId(String externalId){		
			 List<Enrollment> enrollments =  db.queryView(
						createQuery("by_external_id").key(externalId)
								.includeDocs(true), Enrollment.class);
			 if (enrollments == null || enrollments.isEmpty()) {
					return null;
				}
			return enrollments;
		 }
}
