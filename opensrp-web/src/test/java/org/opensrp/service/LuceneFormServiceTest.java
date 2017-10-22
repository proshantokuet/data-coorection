package org.opensrp.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.rest.repository.LuceneFormRepository;
import org.opensrp.rest.services.LuceneFormService;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;

public class LuceneFormServiceTest {
	
    private LuceneFormService luceneServ; 
    private LuceneFormRepository luceneFormRepository;
    private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
    private LuceneAwareCouchDbConnector connector; 
 
    @Before
    public void setUp() throws IOException { 
    	System.out.println("setup for DataCountServiceTest");
        HttpClient httpClient = new StdHttpClient.Builder() 
                .host("localhost") 
                .port(5984) 
                .socketTimeout(1000) 
               .username("Admin").password("mPower@1234") 
                .build(); 
        CouchDbInstance instance = new StdCouchDbInstance(httpClient); 
 
        connector = new LuceneAwareCouchDbConnector("opensrp", instance); 
        connector.createDatabaseIfNotExists(); 
        luceneFormRepository = new  LuceneFormRepository(2, connector); 
        //createDocuments(); 
        convertDateStringToTimestampMills = new ConvertDateStringToTimestampMills();
        luceneServ = new LuceneFormService(luceneFormRepository, convertDateStringToTimestampMills);
    } 
    
   @Ignore @Test 
    public void testCount() throws ParseException {    	
    	//luceneServ.someFunc();
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date start = dateFormat.parse("2016-05-01");
    	Date end = dateFormat.parse("2016-12-31");
    	System.out.println(start.getTime() + " -- " + end.getTime());
    	//System.out.println(luceneServ.getFormCount("", "","amena","new_household_registration") + " -form count from DB");
    	//System.out.println(luceneServ.getFormCount("2016-05-01", "2016-12-31","amena","new_household_registration") + " -form count from DB");
    } 

}
