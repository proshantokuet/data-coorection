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
import org.junit.Test;
import org.opensrp.rest.repository.LuceneScheduleRepository;
import org.opensrp.rest.services.LuceneScheduleService;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;

public class LuceneScheduleServiceTest {

	
    private LuceneScheduleService luceneServ; 
    private LuceneScheduleRepository luceneScheduleRepository;
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
        luceneScheduleRepository = new  LuceneScheduleRepository(2, connector); 
        //createDocuments(); 
        convertDateStringToTimestampMills = new ConvertDateStringToTimestampMills();
        luceneServ = new LuceneScheduleService(luceneScheduleRepository, convertDateStringToTimestampMills);
    } 
    
    @Test 
    public void testCount() throws ParseException {    	
    	//System.out.println(luceneServ.getSchedulCount("", "","amena","ELCO PSRF") + " -schedule count1 from DB");
    	//System.out.println(luceneServ.getSchedulCount("2016-11-01", "2016-12-31","amena","ELCO PSRF") + " -schedule count2 from DB");
    	//System.out.println(luceneServ.getSchedulCount("2017-01-01", "2017-01-31","amena","ELCO PSRF") + " -schedule count3 from DB");
    } 


}
