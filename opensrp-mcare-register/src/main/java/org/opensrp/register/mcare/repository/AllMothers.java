package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Mother;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllMothers extends MotechBaseRepository<Mother> {

	private static Logger logger = LoggerFactory.getLogger(AllMothers.class);

	@Autowired
	public AllMothers(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Mother.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Mother findByCaseId(String caseId) {
		List<Mother> mothers = queryView("by_caseId", caseId);
		if (mothers == null || mothers.isEmpty()) {
			return null;
		}
		return mothers.get(0);
	}

	public void close(String caseId) {
		Mother mother = findByCaseId(caseId);
		update(mother.setIsClosed(true));
	}

	@View(name = "all_open_mothers_for_provider", map = "function(doc) { if (doc.type === 'Mother' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Mother> allOpenMothersForProvider(String providerId) {
		return db.queryView(createQuery("all_open_mothers_for_provider").key(providerId).includeDocs(true), Mother.class);
	}

	@View(name = "all_open_mothers_for_provider", map = "function(doc) { if (doc.type === 'Mother' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Mother> allOpenMothers() {
		return db.queryView(createQuery("all_open_mothers_for_provider").includeDocs(true), Mother.class);
	}

	@View(name = "created_in_between_2_dates", map = "function(doc) { if(doc.type === 'Mother' && doc.type && doc.clientVersion) { emit( [doc.type, doc.clientVersion], null); } }")
	public List<Mother> allMothersCreatedBetween2Dates(String type, long startKey, long endKey) {
		ComplexKey start = ComplexKey.of(type, startKey);
		ComplexKey end = ComplexKey.of(type, endKey);
		List<Mother> mothers = db.queryView(createQuery("created_in_between_2_dates").startKey(start).endKey(end).includeDocs(true), Mother.class);
		return mothers;
	}

	// map reduce query
    @View(name = "motherCount", map = "function(doc) { if (doc.type === 'Mother') { emit(doc.id); } }",reduce="_count")
    public int countMothers() {
        //System.out.println("Mother time start:"+System.currentTimeMillis());
        //return db.queryView(createQuery("motherCount")).getRows().get(0).getValueAsInt();  
    	ViewResult result =  db.queryView(createQuery("motherCount")); 
        int  count = 0;
        if(!result.getRows().isEmpty()){
            count = result.getRows().get(0).getValueAsInt();
        }
        return count;
    }
    
	/*
	 * @View(name = "all_households", map =
	 * "function(doc) { if (doc.type === 'HouseHold') { emit(doc.PROVIDERID, doc.CASEID); } }"
	 * ) public List<HouseHold> findAllHouseHolds() { return
	 * db.queryView(createQuery("all_households").includeDocs(true),
	 * HouseHold.class); }
	 * 
	 * @View(name = "all_open_hhs_for_provider", map =
	 * "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }"
	 * ) public List<HouseHold> allOpenHHsForProvider(String providerId) {
	 * return db.queryView(
	 * createQuery("all_open_hhs_for_provider").key(providerId)
	 * .includeDocs(true), HouseHold.class); }
	 * 
	 * @View(name = "all_hhs_prev_7_days", map =
	 * "function(doc) { if (doc.type === 'HouseHold' && doc.PROVIDERID && doc.TODAY) { emit(doc.PROVIDERID, doc.TODAY); } }"
	 * ) public List<HouseHold> allHHsVisited7Days(String providerId) {
	 * 
	 * LocalDate today = LocalDate.now();
	 * 
	 * List<HouseHold> houseHolds = db.queryView(
	 * createQuery("all_hhs_prev_7_days") .startKey(today.minusDays(100))
	 * .endKey(today) //.key(providerId) .includeDocs(true), HouseHold.class);
	 * 
	 * return houseHolds; }
	 */
}
