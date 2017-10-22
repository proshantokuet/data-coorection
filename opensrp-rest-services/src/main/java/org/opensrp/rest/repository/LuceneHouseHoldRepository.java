/**
 * @author Asifur
 */

package org.opensrp.rest.repository;

import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.HouseHold;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        name = "houdehold",
	    index = "function(rec) {" +
	    		" var doc=new Document();" + 
	    		" doc.add(rec.FWDIVISION,{\"field\":\"FWDIVISION\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWHOHGENDER,{\"field\":\"FWHOHGENDER\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.user_type,{\"field\":\"user_type\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWNHREGDATE,{\"field\":\"FWNHREGDATE\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWHOHFNAME,{\"field\":\"FWHOHFNAME\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWGOBHHID,{\"field\":\"FWGOBHHID\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWCOUNTRY,{\"field\":\"FWCOUNTRY\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWJIVHHID,{\"field\":\"FWJIVHHID\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWDISTRICT,{\"field\":\"FWDISTRICT\", \"store\":\"yes\"}); " +
	    		" doc.add(rec.FWUNION,{\"field\":\"FWUNION\", \"store\":\"yes\"});" +  
	    		" doc.add(rec.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.FWUPAZILLA,{\"field\":\"FWUPAZILLA\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.SUBMISSIONDATE,{\"field\":\"SUBMISSIONDATE\", \"store\":\"yes\"});" + 
	    		" doc.add(rec._id,{\"field\":\"id\", \"store\":\"yes\"});" +
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}"),
	@Index(
        name = "by_all_criteria",
        index = "function (doc) { if(doc.type !== 'HouseHold') return null;  var docl = new Array(); var ret = new Document(); if(doc.PROVIDERID){    var led = doc.PROVIDERID;    ret.add(led, {'field' : 'PROVIDERID'}); } if(doc.SUBMISSIONDATE){    var sed = doc.SUBMISSIONDATE;    ret.add(sed, {'field' : 'SUBMISSIONDATE','type' : 'long'}); } docl.push(ret); return docl; }")
})

@Repository
public class LuceneHouseHoldRepository extends CouchDbRepositorySupportWithLucene<HouseHold> {
	private static Logger logger = LoggerFactory.getLogger(LuceneHouseHoldRepository.class);
	@Autowired
	public LuceneHouseHoldRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(HouseHold.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	
	
	 public LuceneResult findDocsByProvider(String queryString) { 
	        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "houdehold"); 
	        query.setQuery(queryString); 
	        query.setStaleOk(false); 
	        return db.queryLucene(query); 
	    } 
		 public LuceneResult getData(String queryString,int skip,int limit) {		
				String sortField =  "\\" + "id"; 
		        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
		        LuceneQuery query = new LuceneQuery(designDoc.getId(), "houdehold"); 
		        query.setQuery(queryString); 
		        query.setStaleOk(true);
		        query.setSkip(skip);
		        query.setLimit(limit);
		        query.setSort(sortField);
		        return db.queryLucene(query); 
		   }

		 public int  getDataCount(String queryString) {		
		        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
		        LuceneQuery query = new LuceneQuery(designDoc.getId(), "houdehold"); 
		        query.setQuery(queryString); 
		        query.setStaleOk(true);   
		        
		        return db.queryLucene(query).getTotalRows(); 
		} 

}
