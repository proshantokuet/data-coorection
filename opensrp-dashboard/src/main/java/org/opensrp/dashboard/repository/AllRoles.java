package org.opensrp.dashboard.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.dashboard.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllRoles extends MotechBaseRepository<Role> {
	
	private static Logger logger = LoggerFactory.getLogger(AllRoles.class);
	
	@Autowired
	public AllRoles(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Role.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	//@GenerateView
	public Role findByUserName(String userName) {
		/*List<Role> roles = queryView("by_userName", userName);
		if (roles == null || roles.isEmpty()) {
			return null;
		}
		return roles.get(0);*/
		return null;
	}
	
	//@View(name = "all_user_with_role", map = "function(doc) { if (doc.type === 'Role' && doc.status ==='Active') { emit(doc.userName); } }")
	public List<Role> roles() {
		/*return db.queryView(
				createQuery("all_user_with_role")
						.includeDocs(true), Role.class);*/
		return null;
	}
	
	@View(name = "role_by_name", map = "function(doc) { if (doc.type === 'Role' && doc.name) { emit(doc.name); } }")
	public Role findRoleByName(String name) {
		logger.info("inside Allroles.findRoleByName");
		List<Role> roles = db.queryView(createQuery("role_by_name").key(name).includeDocs(true), Role.class);
		if (roles == null || roles.isEmpty()) {
			return null;
		}
		return roles.get(0);
	}
	
	@View(name = "role_by_id", map = "function(doc) { if (doc.type === 'Role' && doc._id) { emit(doc._id, doc); } }")
	public Role findRoleById(String id) {
		logger.info("inside Allroles.findRoleById()");
		List<Role> roles = db.queryView(createQuery("role_by_id").key(id).includeDocs(true), Role.class);
		if (roles == null || roles.isEmpty()) {
			return null;
		}
		return roles.get(0);
	}
	
}
