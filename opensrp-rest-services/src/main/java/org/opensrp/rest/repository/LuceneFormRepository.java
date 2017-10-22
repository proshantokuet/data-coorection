/**
 * @author Asifur
 */
package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.form.domain.FormSubmission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.designdocument.LuceneDesignDocument;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;

@FullText({
    @Index(
        name = "formSubmission",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.anmId,{\"field\":\"anmId\", \"store\":\"yes\"});" +
	    		" doc.add(rec.formName,{\"field\":\"formName\", \"store\":\"yes\"});"+ 	    		   		
	    		" doc.add(rec.serverVersion,{\"field\":\"serverVersion\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.instanceId,{\"field\":\"instanceId\", \"store\":\"yes\"});" +
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneFormRepository extends CouchDbRepositorySupportWithLucene<FormSubmission>{
	@Autowired
	public LuceneFormRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_Form_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(FormSubmission.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "formSubmission"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 
}
