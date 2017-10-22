/**
 * @author proshanto
 * */

package org.opensrp.rest.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.opensrp.common.AllConstants;
import org.opensrp.register.mcare.domain.Mother;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.github.ldriscoll.ektorplucene.CouchDbRepositorySupportWithLucene;
import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneQuery;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;
import com.github.ldriscoll.ektorplucene.designdocument.LuceneDesignDocument;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.FullText;
import com.github.ldriscoll.ektorplucene.designdocument.annotation.Index;

@FullText({
    @Index(
        name = "mother",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.TODAY,{\"field\":\"TODAY\", \"store\":\"yes\"});" +
	    		" doc.add(rec.mother_first_name,{\"field\":\"mother_first_name\", \"store\":\"yes\"});" +
	    		" doc.add(rec.mother_husname,{\"field\":\"mother_husname\", \"store\":\"yes\"});" +
	    		" doc.add(rec.mother_wom_nid,{\"field\":\"mother_wom_nid\", \"store\":\"yes\"});" +
	    		" doc.add(rec.mother_wom_bid,{\"field\":\"mother_wom_bid\", \"store\":\"yes\"});" +
	    		" doc.add(rec.mother_wom_age,{\"field\":\"mother_wom_age\", \"store\":\"yes\"});" +
	    		" doc.add(rec.FWWOMUPAZILLA,{\"field\":\"FWWOMUPAZILLA\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.FWWOMDISTRICT,{\"field\":\"FWWOMDISTRICT\", \"store\":\"yes\"});"+ 
	    		" doc.add(rec.FWWOMUNION,{\"field\":\"FWWOMUNION\", \"store\":\"yes\"});"+ 	    		   		
	    		" doc.add(rec.SUBMISSIONDATE,{\"field\":\"SUBMISSIONDATE\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.PROVIDERID,{\"field\":\"PROVIDERID\", \"store\":\"yes\"});" +
	    		" doc.add(rec._id,{\"field\":\"id\", \"store\":\"yes\"});" +
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" +
	    		" return doc;" +
	    		"}"),
	@Index(
        name = "by_all_criteria",
        index = "function (doc) { if(doc.type !== 'Mother') return null;  var docl = new Array(); var ret = new Document(); if(doc.PROVIDERID){    var led = doc.PROVIDERID;    ret.add(led, {'field' : 'PROVIDERID'}); } docl.push(ret); return docl; }")
})

@Repository
public class LuceneMotherRepository extends CouchDbRepositorySupportWithLucene<Mother>{
	@Autowired
	public LuceneMotherRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(Mother.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "mother"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        return db.queryLucene(query); 
    } 
	 public LuceneResult getData(String queryString,int skip,int limit) {		
			String sortField =  "\\" + "id"; 
	        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "mother"); 
	        query.setQuery(queryString); 
	        query.setStaleOk(true);
	        query.setSkip(skip);
	        query.setLimit(limit);
	        query.setSort(sortField);
	        return db.queryLucene(query); 
	   }

	 public int  getDataCount(String queryString) {		
	        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);        
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "mother"); 
	        query.setQuery(queryString); 
	        query.setStaleOk(true);   
	        
	        return db.queryLucene(query).getTotalRows(); 
	} 
	 public List<Integer> getByCriteria(String anmIdentifier) {
			// create a simple query against the view/search function that we've created
			
			Query qf = new Query(FilterType.AND);
			
			if(anmIdentifier!= null && !anmIdentifier.isEmpty() && !anmIdentifier.equalsIgnoreCase("")){
				qf.eq("PROVIDERID", anmIdentifier);
			}

			if(qf.query() == null || qf.query().isEmpty()){
				throw new RuntimeException("Atleast one search filter must be specified");
			}
				
			/*LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
	        LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_all_criteria");*/ 
			
			LuceneQuery query = new LuceneQuery("Mother", "by_all_criteria");
			
			System.out.println("Query: "+qf.query());
			
	        query.setQuery(qf.query()); 
	        query.setStaleOk(false); 
	        query.setIncludeDocs(true);
	       
	        LuceneResult result = db.queryLucene(query);	        
	        List<Integer> count = new ArrayList<Integer>();
	        int countNewborn = 0, countLiveBirth = 0, countStillBirth = 0;

			for (Row r : result.getRows()) {
				HashMap<String, Object> doc = r.getDoc();
				//System.out.println("Lucene HH doc: "+doc);
				Mother ro = null;
				try {
					ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for (int i=0; i < ro.bnfVisitDetails().size(); i++){	
					String FWBNFSTS = "", FWBNFCHLDVITSTS = "";
					try {
						FWBNFSTS = ro.bnfVisitDetails().get(i).get("FWBNFSTS");					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						FWBNFCHLDVITSTS = ro.bnfVisitDetails().get(i).get("FWBNFCHLDVITSTS");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(FWBNFSTS.equalsIgnoreCase("3"))
						countNewborn++;
					if(FWBNFCHLDVITSTS.equalsIgnoreCase("1"))
						countLiveBirth++;
					if(FWBNFCHLDVITSTS.equalsIgnoreCase("0"))
						countStillBirth++;
				}			
			}	
			count.add(countNewborn);
			count.add(countLiveBirth);
			count.add(countStillBirth);
	        return count;
	 }
}
