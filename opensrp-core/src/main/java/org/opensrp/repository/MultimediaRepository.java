package org.opensrp.repository;

import java.util.List;

import org.ektorp.ComplexKey;
import org.ektorp.CouchDbConnector;
import org.ektorp.ViewQuery;
import org.ektorp.ViewResult;
import org.ektorp.support.GenerateView;
import org.ektorp.support.View;
import org.motechproject.dao.MotechBaseRepository;
import org.opensrp.common.AllConstants;
import org.opensrp.domain.Multimedia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class MultimediaRepository extends MotechBaseRepository<Multimedia> {

	@Autowired
	protected MultimediaRepository(
			@Qualifier(AllConstants.OPENSRP_DATABASE_CONNECTOR) CouchDbConnector db) {
		super(Multimedia.class, db);
	}

	@GenerateView
	public Multimedia findByCaseId(String entityId) {
		List<Multimedia> files = queryView("by_caseId", entityId);
		if (files == null || files.isEmpty()) {
			return null;
		}
		return files.get(0);
	}

	@View(name = "all_multimedia_files", map = "function(doc) { if (doc.type === 'Multimedia' && doc.providerId) { emit(doc.providerId, doc); } }")
	public List<Multimedia> all(String providerId) {
		return db.queryView(createQuery("all_multimedia_files").key(providerId)
				.includeDocs(true), Multimedia.class);
	}
	
	@View(name="find_by_caseId_fileCategory", map="function(doc) { if (doc.type === 'Multimedia' && doc.providerId) { emit([doc.caseId, doc.fileCategory], doc); } }")
	public List<Multimedia> findByCaseIdAndFileCategory(String entityId, String fileCategory)
	{
		/*return db.queryView(createQuery("find_by_caseId_fileCategory").key(new String[]{entityId,fileCategory})
				.includeDocs(true), Multimedia.class);*/
		
		ComplexKey complexKey = ComplexKey.of(entityId,fileCategory);
		
		 ViewQuery query = createQuery("find_by_caseId_fileCategory")
	                .designDocId(stdDesignDocumentId)
	                .key(complexKey)
	                .includeDocs(true);
		 
		 ViewResult r = db.queryView(query);
		 
	     return  db.queryView(query, Multimedia.class);
	     //   return r.getRows().get(0).getValueAsInt();
	}

}
