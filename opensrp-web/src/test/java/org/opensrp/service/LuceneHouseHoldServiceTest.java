package org.opensrp.service;

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
import java.util.GregorianCalendar;
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
import org.opensrp.common.util.DateUtil;
import org.opensrp.dto.register.HHRegisterEntryDTO;
import org.opensrp.repository.AllBaseEntities;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.ldriscoll.ektorplucene.LuceneAwareCouchDbConnector;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;

public class LuceneHouseHoldServiceTest {	
    private LuceneHouseHoldService luceneServ; 
    private LuceneHouseHoldRepository luceneHouseHoldRepository;
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
        luceneHouseHoldRepository = new  LuceneHouseHoldRepository(2, connector); 
        //createDocuments(); 
        convertDateStringToTimestampMills = new ConvertDateStringToTimestampMills();
        luceneServ = new LuceneHouseHoldService(luceneHouseHoldRepository, convertDateStringToTimestampMills);
    } 
    
    @Ignore@Test 
    public void testCount() throws ParseException {    	
    	//luceneServ.someFunc();
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date start = dateFormat.parse("2016-05-01");
    	Date end = dateFormat.parse("2016-05-31");
    	System.out.println(start.getTime() + " -- " + end.getTime());
    	//System.out.println(luceneServ.getHouseholdCountForChart("2016-05-01", "2016-05-31") + " -hh count from 1st to 31st May");
    	System.out.println(luceneServ.getHouseholdCount("2016-05-01", "2016-12-31") + " -hh count from DB");
    	
    	/*List<String> startAndEndOfWeeks = DateUtil.getWeekBoundariesForDashboard();
    	
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(0), startAndEndOfWeeks.get(1)) + " week1");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(2), startAndEndOfWeeks.get(3))+ " week2");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(4), startAndEndOfWeeks.get(5))+ " week3");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(6), startAndEndOfWeeks.get(7))+ " week4");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(8), startAndEndOfWeeks.get(9))+ " week5");
    	
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(10), startAndEndOfWeeks.get(11)) + " _1week1");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(12), startAndEndOfWeeks.get(13)) + " _1week2");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(14), startAndEndOfWeeks.get(15)) + " _1week3");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(16), startAndEndOfWeeks.get(17)) + " _1week4");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(18), startAndEndOfWeeks.get(19)) + " _1week5");
    	
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(20), startAndEndOfWeeks.get(21)) + " _2week1");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(22), startAndEndOfWeeks.get(23)) + " _2week2");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(24), startAndEndOfWeeks.get(25)) + " _2week3");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(26), startAndEndOfWeeks.get(27)) + " _2week4");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(28), startAndEndOfWeeks.get(29)) + " _2week5");
    	
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(30), startAndEndOfWeeks.get(31)) + " _3week1");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(32), startAndEndOfWeeks.get(33)) + " _3week2");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(34), startAndEndOfWeeks.get(35)) + " _3week3");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(36), startAndEndOfWeeks.get(37)) + " _3week4");
    	System.out.println(luceneServ.getHouseholdCountForChart(startAndEndOfWeeks.get(38), startAndEndOfWeeks.get(39)) + " _3week5");
		    	
    	LuceneHouseHoldService a = null;
    	assertNotNull(a);*/
    } 
}
