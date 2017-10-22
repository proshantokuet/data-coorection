package org.opensrp.dashboard.repository;

import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.dashboard.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class AllUser extends MotechBaseRepository<User> {
	
	private static Logger logger = LoggerFactory.getLogger(AllUser.class);
	
	@Autowired
	public AllUser(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
	    @Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(User.class, db);
		this.db.setRevisionLimit(revisionLimit);
	}
	
	@View(name = "by_user_name", map = "function(doc) { if (doc.type === 'User' && doc.user_name) { emit(doc.user_name, doc); } }")
	public User findUserByUserName(String username) {
		logger.info("inside AllUsers.findUserByUserName()");
		List<User> users = db.queryView(createQuery("by_user_name").key(username).includeDocs(true), User.class);
		if (users == null || users.isEmpty()) {
			logger.info("user with username- " + username + " not found.");
			return null;
		}
		return users.get(0);
	}
	
	@View(name = "by_id", map = "function(doc) { if (doc.type === 'User' && doc._id) { emit(doc._id, doc); } }")
	public User findUserById(String userId) {
		List<User> users = db.queryView(createQuery("by_id").key(userId).includeDocs(true), User.class);
		if (users == null || users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
}
