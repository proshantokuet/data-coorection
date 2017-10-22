package org.opensrp.register.mcare.repository;

import java.io.File;
import java.util.List;

import org.ektorp.CouchDbConnector;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Acl;
import org.opensrp.register.mcare.domain.Exports;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.google.gson.annotations.Expose;

@Repository
public class AllExports  extends MotechBaseRepository<Exports>{
	private String multimediaDirPath;
	@Autowired
	public AllExports(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Value("#{opensrp['multimedia.directory.name']}") String multimediaDirName,@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Exports.class, db);
		this.db.setRevisionLimit(revisionLimit);
		this.multimediaDirPath = multimediaDirName;
	}
	
	
	@View(name = "by_user", map = "function(doc) { if (doc.type === 'Exports' && doc._id) { emit(doc.user, doc); } }")
	public List<Exports> getExportsByUser(String session_id) {
		List<Exports> exports = db.queryView(createQuery("by_user").key(session_id).descending(true).limit(5).includeDocs(true), Exports.class);
		return exports;
	}
	@View(name = "by_id", map = "function(doc) { if (doc.type === 'Exports' && doc._id) { emit(doc._id, doc); } }")
	public Exports getExportsById(String id) {
		List<Exports> export = db.queryView(createQuery("by_id").key(id).includeDocs(true), Exports.class);
		if (export == null || export.isEmpty()) {
			return null;
		}
		return export.get(0);
	}
	
	public void save(Exports exports){
		this.save(exports);
	}
	
	public void delete(Exports export){
		this.remove(export);
		try{
			System.out.println("Export Report Name: "+export.getReportName());
			File file = new File(multimediaDirPath+"/export/"+export.getReportName());
			file.delete();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
