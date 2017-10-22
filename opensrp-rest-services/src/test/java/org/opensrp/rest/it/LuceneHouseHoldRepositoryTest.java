package org.opensrp.rest.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.support.OpenCouchDbDocument;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.opensrp.common.AllConstants;
import org.opensrp.dto.register.HHRegisterEntryDTO;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.repository.AllBaseEntities;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:test-applicationContext-opensrp-rest-services.xml")
@Ignore
public class LuceneHouseHoldRepositoryTest {
	
	private LuceneAwareCouchDbConnector connector; 
    private LuceneHouseHoldRepository repo;
	//private AllHouseHolds repo;
    
/*
	@Autowired
	@Qualifier(AllConstants.OPENSRP_DATABASE_LUCENE_CONNECTOR)
	LuceneAwareCouchDbConnector connector;

	@Autowired
	private LuceneHouseHoldRepository repo;*/

    @Before
    public void setUp() throws IOException { 
        HttpClient httpClient = new StdHttpClient.Builder() 
                .host("localhost") 
                .port(5984) 
               .socketTimeout(100000) 
               .username("Admin").password("mPower@1234") 
                .build(); 
        CouchDbInstance instance = new StdCouchDbInstance(httpClient); 
 
        connector = new LuceneAwareCouchDbConnector("opensrp", instance); 
        connector.createDatabaseIfNotExists(); 
        repo = new  LuceneHouseHoldRepository(2, connector);
        //repo = new AllHouseHolds(2, connector);
        //createDocuments(); 
    } 
 
 
    @After 
    public void tearDown() { 
       // deleteDocuments(); 
        connector = null; 
    } 
 
    private void createDocuments() { 
        updateDocument("test1", "field1", "test"); 
        updateDocument("test2", "field1", "test"); 
        updateDocument("test2", "field2", "here"); 
    } 
 
    private void deleteDocuments() { 
        deleteDocument("test1"); 
        deleteDocument("test2"); 
    } 
 
    private void updateDocument(String name, String field, String value) { 
        final OpenCouchDbDocument doc; 
        if (connector.contains(name)) { 
            doc = connector.get(OpenCouchDbDocument.class, name); 
        } else { 
            doc = new OpenCouchDbDocument(); 
            doc.setId(name); 
            connector.create(doc); 
        } 
 
        doc.setAnonymous(field, value); 
        connector.update(doc); 
    } 
 
    private void deleteDocument(String name) { 
        if (connector.contains(name)) { 
            OpenCouchDbDocument doc = connector.get(OpenCouchDbDocument.class, name); 
            connector.delete(doc); 
        } 
    }
    /*@Test
    public void convertDateToTimestampMills(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   	
    	Date day= null;
    	String date = "2016-04-09";
		try {
			day = dateFormat.parse(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
		System.out.println(day.getTime());
		
	}*/
   @Test 
    public void testInit() throws ParseException { 
    	//String makeQueryString ="PROVIDERID:proshanto" + " AND " + "FWUPAZILLA:GAIBANDHA SADAR" + " AND " + "user_type:FWA"+ " AND SUBMISSIONDATE:[2014-02-01 TO 2017-03-30]" ;
    	
    	
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = Calendar.getInstance().getTime();
    	Date dates = dateFormat.parse(dateFormat.format(today));
    	Date date = dateFormat.parse("2016-05-01");
    	long start = date.getTime();
    	long end = dates.getTime();
    	System.out.println("ddd:"+end);
    	System.out.println("ss:"+start);
    	//String makeQueryString ="PROVIDERID:proshanto" + " AND " + "user_type:FWA"+ " AND SUBMISSIONDATE:["+start+" TO "+end+"]" ;
    	String makeQueryString ="type:Household AND PROVIDERID:rojina AND SUBMISSIONDATE:1463421600000";
    	LuceneResult result = repo.findDocsByProvider(makeQueryString);
    	//System.out.println(result.toString());
    	System.out.println(result.getRows().size() + " -with specific type");
    	System.out.println(result.getTotalRows());
    	makeQueryString ="PROVIDERID:rojina AND SUBMISSIONDATE:1463421600000";
    	result = repo.findDocsByProvider(makeQueryString);
    	System.out.println(result.getRows().size() + " -without specific type");
		
        assertNotNull("Expecting a non null result", result); 
        assertTrue("Should only have one result", result.getRows().size() >=0); 
    } 
    /*@Test 
    public void testRawkey() throws ParseException {
    	List<HouseHold> fetchedHH = repo.allHHsCreatedLastFourMonthsByLocation("[\"Gaibandha\"]", "[\"Gaibandha\",{}]");
    	System.out.println("Number of fetched rows- " + fetchedHH.size());

    	List<HouseHold> fetchedHHTemp = repo.allHHsCreatedLastFourMonthsByLocation("[\"Gaibandha\",\"GAIBANDHA%20SADAR\"]", "[\"Gaibandha\",\"GAIBANDHA%20SADAR\"{}]");
    	System.out.println("Number of fetched rows- " + fetchedHHTemp.size());
    }*/
    
    
    
   
}
