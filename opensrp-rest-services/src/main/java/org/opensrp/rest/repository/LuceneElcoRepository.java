/**
 * @author proshanto
 */

package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Elco;
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
        name = "elco",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMDIVISION,{\"field\":\"FWWOMDIVISION\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWPSRPREGSTS,{\"field\":\"FWPSRPREGSTS\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMDISTRICT,{\"field\":\"FWWOMDISTRICT\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMUPAZILLA,{\"field\":\"FWWOMUPAZILLA\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMUNION,{\"field\":\"FWWOMUNION\", \"store\":\"yes\"});" +
	    		" doc.add(rec.WomanREGDATE,{\"field\":\"WomanREGDATE\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMFNAME,{\"field\":\"FWWOMFNAME\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.FWWOMNID,{\"field\":\"FWWOMNID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWHUSNAME,{\"field\":\"FWHUSNAME\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWWOMBID,{\"field\":\"FWWOMBID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.GOBHHID,{\"field\":\"GOBHHID\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.JiVitAHHID,{\"field\":\"JiVitAHHID\", \"store\":\"yes\"});" +	 
	    		" doc.add(rec.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" +	 
	    		" doc.add(rec.SUBMISSIONDATE,{\"field\":\"SUBMISSIONDATE\", \"store\":\"yes\"});" + 
	    		" doc.add(rec._id,{\"field\":\"id\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" +
	    		" return doc;" +
	    		"}")
})

@Repository
public class LuceneElcoRepository extends CouchDbRepositorySupportWithLucene<Elco>{

	@Autowired
	public LuceneElcoRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Elco.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "elco"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 
	 public LuceneResult getData(String queryString,int skip,int limit) {		
			String sortField =  "\\" + "id"; 
	        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "elco"); 
	        query.setQuery(queryString); 
	        query.setStaleOk(true);
	        query.setSkip(skip);
	        query.setLimit(limit);
	        query.setSort(sortField);
	        return db.queryLucene(query); 
	   }

	 public int  getDataCount(String queryString) {		
	        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "elco"); 
	        query.setQuery(queryString); 
	        query.setStaleOk(true);   
	        
	        return db.queryLucene(query).getTotalRows(); 
	} 

}
