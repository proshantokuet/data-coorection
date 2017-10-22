package org.opensrp.dashboard.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.dashboard.domain.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllPrivileges extends MotechBaseRepository<Privilege> {
	
	private static Logger logger = LoggerFactory.getLogger(AllPrivileges.class);
	
	@Autowired
	public AllPrivileges(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Privilege.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	@View(name = "all_privileges", map = "function(doc) { if (doc.type === 'Privilege') { emit(doc.name, doc); } }")
	public List<Privilege> privileges() {
		return db.queryView(createQuery("all_privileges").includeDocs(true), Privilege.class);
	}
	
	@View(name = "privilege_by_name", map = "function(doc) { if (doc.type === 'Privilege' && doc.name) { emit(doc.name, doc); } }")
	public Privilege privilegeByName(String name) {
		logger.info("inside repository class.");
		List<Privilege> privileges = db.queryView(createQuery("privilege_by_name").key(name).includeDocs(true),
		    Privilege.class);
		if (privileges == null || privileges.isEmpty()) {
			return null;
		}
		return privileges.get(0);
	}
	
	@View(name = "privilege_by_id", map = "function(doc) { if (doc.type === 'Privilege' && doc._id) { emit(doc._id, doc); } }")
	public Privilege privilegeById(String id) {
		logger.info("inside repository class.");
		List<Privilege> privileges = db.queryView(createQuery("privilege_by_id").key(id).includeDocs(true), Privilege.class);
		if (privileges == null || privileges.isEmpty()) {
			return null;
		}
		return privileges.get(0);
	}
	
}
