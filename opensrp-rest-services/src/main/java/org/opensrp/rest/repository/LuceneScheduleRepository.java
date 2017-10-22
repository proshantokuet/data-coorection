/**
 * @author Asifur
 */
package org.opensrp.rest.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.opensrp.common.AllConstants;
import org.opensrp.scheduler.ScheduleLog;
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
        name = "scheduleLog",
	    index = "function(rec) {" +
	    		" var doc=new Document();" +
	    		" doc.add(rec.anmIdentifier,{\"field\":\"anmIdentifier\", \"store\":\"yes\"});" +
	    		" doc.add(rec.caseID,{\"field\":\"caseID\", \"store\":\"yes\"});"+ 	    		   		
	    		" doc.add(rec.visitCode,{\"field\":\"visitCode\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.currentWindow,{\"field\":\"currentWindow\", \"store\":\"yes\"});" + 
	    		" doc.add(rec.scheduleName,{\"field\":\"scheduleName\", \"store\":\"yes\"});" +
	    		" doc.add(rec.timeStamp,{\"field\":\"timeStamp\", \"store\":\"yes\"});" +
	    		" doc.add(rec.type,{\"field\":\"type\", \"store\":\"yes\"});" + 
	    		" return doc;" +
	    		"}"),
	@Index(
	        name = "by_all_criteria",
	        index = "function (doc) { if(doc.type !== 'ScheduleLog') return null;  var docl = new Array(); var ret = new Document(); if(doc.anmIdentifier){    var led = doc.anmIdentifier;    ret.add(led, {'field' : 'anmIdentifier'}); } if(doc.scheduleName){    var ped = doc.scheduleName;    ret.add(ped, {'field' : 'scheduleName'}); } if(doc.timeStamp){    var sed = doc.timeStamp;    ret.add(sed, {'field' : 'timeStamp','type' : 'long'}); } docl.push(ret); return docl; }")
})

@Repository
public class LuceneScheduleRepository extends CouchDbRepositorySupportWithLucene<ScheduleLog>{
	@Autowired
	public LuceneScheduleRepository(@Value("#{opensrp['couchdb.atomfeed-db.revision-limit']}") int revisionLimit,
			@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)LuceneAwareCouchDbConnector db) {
		super(ScheduleLog.class, db);
		this.db.setRevisionLimit(revisionLimit);
		initStandardDesignDocument();
	}
	public LuceneResult findDocsByProvider(String queryString) { 
        LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "scheduleLog"); 
        query.setQuery(queryString); 
        query.setStaleOk(false); 
        
        LuceneResult result = db.queryLucene(query);
        
        List<ScheduleLog> ol = new ArrayList<>();
		for (Row r : result.getRows()) {
			HashMap<String, Object> doc = r.getDoc();
			ScheduleLog ro = null;
			try {
				ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ol.add(ro);
		}	
		
		//System.out.println("LuceneResult: "+ol);
				
        return result; 
    } 
	
	public LuceneResult getByCrite(long start,long end,String anmIdentifier,String scheduleName) {
		// create a simple query against the view/search function that we've created
		
		Query qf = new Query(FilterType.AND);
		
		if(start!=0 && end!=0){
			qf.betwen("timeStamp", start, end);
		}
		
		if(anmIdentifier!= null && !anmIdentifier.isEmpty() && !anmIdentifier.equalsIgnoreCase("")){
			qf.eq("anmIdentifier", anmIdentifier);
		}
		
		if(scheduleName!= null && !scheduleName.isEmpty() && !scheduleName.equalsIgnoreCase("")){
			qf.eq("scheduleName", scheduleName);
		}
		
		if(qf.query() == null || qf.query().isEmpty()){
			throw new RuntimeException("Atleast one search filter must be specified");
		}
			
		/*LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_all_criteria");*/ 
		
		LuceneQuery query = new LuceneQuery("ScheduleLog", "by_all_criteria");
		
		//System.out.println("Query: "+qf.query());
		
        query.setQuery(qf.query()); 
        query.setStaleOk(false); 
        query.setIncludeDocs(true);
        
        LuceneResult result = db.queryLucene(query);
        List<ScheduleLog> ol = new ArrayList<>();
		for (Row r : result.getRows()) {
			HashMap<String, Object> doc = r.getDoc();			
			ScheduleLog ro = null;
			try {
				ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}						
			ol.add(ro);
		}	        
        return result; 
	}
	
	public int getByCriteria(long start,long end,String anmIdentifier,String scheduleName) {
		// create a simple query against the view/search function that we've created
		
		Query qf = new Query(FilterType.AND);
		
		if(start!=0 && end!=0){
			qf.betwen("timeStamp", start, end);
		}
		
		if(anmIdentifier!= null && !anmIdentifier.isEmpty() && !anmIdentifier.equalsIgnoreCase("")){
			qf.eq("anmIdentifier", anmIdentifier);
		}
		
		if(scheduleName!= null && !scheduleName.isEmpty() && !scheduleName.equalsIgnoreCase("")){
			qf.eq("scheduleName", scheduleName);
		}
		
		if(qf.query() == null || qf.query().isEmpty()){
			throw new RuntimeException("Atleast one search filter must be specified");
		}
			
		/*LuceneDesignDocument designDoc = db.get(LuceneDesignDocument.class, stdDesignDocumentId);
        LuceneQuery query = new LuceneQuery(designDoc.getId(), "by_all_criteria");*/ 
		
		LuceneQuery query = new LuceneQuery("ScheduleLog", "by_all_criteria");
		
		//System.out.println("Query: "+qf.query());
		
        query.setQuery(qf.query()); 
        query.setStaleOk(false); 
        query.setIncludeDocs(true);
        
        LuceneResult result = db.queryLucene(query);
        Set<String> tenp = new HashSet<String>();
        List<String> ninp = new ArrayList<String>();
        int count = 0;
        
		for (Row r : result.getRows()) {
			HashMap<String, Object> doc = r.getDoc();
			
			ScheduleLog ro = null;
			try {
				ro = new ObjectMapper().readValue(new JSONObject(doc).toString(), type);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			

			for (int i=0; i < ro.data().size(); i++){	
				//System.out.println("Lucene Object: "+ro.data().toString());
				//System.out.println("Lucene visitCode: "+ro.data().get(i).toString());
				ninp.add(ro.data().get(i).get("visitCode"));
			}
			//System.out.println("VisitCode: "+ninp.toString());
			tenp.addAll(ninp);
			//System.out.println("Lucene count: "+tenp.size());
			count+=tenp.size();
		}	        
        return count; 
	}
}
