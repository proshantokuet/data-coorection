package org.opensrp.register.mcare.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Elco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllChilds extends MotechBaseRepository<Child> {

	@Autowired
	public AllChilds(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Child.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}

	@GenerateView
	public Child findByCaseId(String caseId) {
		List<Child> childs = queryView("by_caseId", caseId);
		if (childs == null || childs.isEmpty()) {
			return null;
		}
		return childs.get(0);
	}

	@View(name = "all_open_childs_for_provider", map = "function(doc) { if (doc.type === 'Child' && doc.PROVIDERID) { emit(doc.PROVIDERID); } }")
	public List<Child> allOpenChilds() {
		return db.queryView(createQuery("all_open_childs_for_provider").includeDocs(true), Child.class);
	}

	@View(name = "created_in_between_2_dates", map = "function(doc) { if(doc.type === 'Child' && doc.type && doc.clientVersion) { emit( [doc.type, doc.clientVersion], null); } }")
	public List<Child> allChildsCreatedBetween2Dates(String type, long startKey, long endKey) {
		ComplexKey start = ComplexKey.of(type, startKey);
		ComplexKey end = ComplexKey.of(type, endKey);
		List<Child> childs = db.queryView(createQuery("created_in_between_2_dates").startKey(start).endKey(end).includeDocs(true), Child.class);
		return childs;
	}

	public boolean exists(String caseId) {
		return findByCaseId(caseId) != null;
	}

	 @View(name = "child_by_relationalId", map = "function(doc) { if (doc.type === 'Elco' && doc.details.relationalid) { emit(doc.details.relationalid,null ); } }")
	 public List<Child> getByRelationalId(String relationalId){		
		 List<Child> elcos =  db.queryView(
					createQuery("child_by_relationalId").key(relationalId)
							.includeDocs(true), Child.class);
		 if (elcos == null || elcos.isEmpty()) {
				return null;
			}
		return elcos;
	 }
}
