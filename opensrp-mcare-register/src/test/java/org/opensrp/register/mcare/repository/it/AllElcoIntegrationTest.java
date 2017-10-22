package org.opensrp.register.mcare.repository.it;

import static org.opensrp.common.AllConstants.ELCORegistrationFields.FWPSRPREGSTS;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ektorp.CouchDbInstance;
import org.ektorp.ViewResult;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.ektorp.impl.StdObjectMapperFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class AllElcoIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
	private AllElcos allElcos;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	private AllActions allActions;
	private AllChilds allChilds;
	private AllMothers allMothers;
	
    @Before
    public void setUp() throws Exception {
    	//allHouseHolds.removeAll();
    	//allElcos.removeAll();
       HttpClient httpClient = new StdHttpClient.Builder() 
        //.host("localhost") 
       	.host("localhost")
        .port(5984) 
        .username("Admin").password("mPower@1234")
        .socketTimeout(1000) 
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allMothers = new AllMothers(2, stdCouchDbConnector);
    	//initMocks(this);
    }
   
    
   
    
    // data cleaning
    // woman who has inactive psrf schedule but not in elco so delete them
    @SuppressWarnings("unused")
	@Ignore@Test
    public void shouldRemoveAction(){
    	
    	List<Action> actions = allActions.findByScheduleNameAndIsActive(false, "ELCO PSRF");
    	System.err.println("actions:"+actions.size());
    	int i=0;
    	int j=0;
    	for (Action action : actions) {    		
    		Elco elco = allElcos.findByCaseId(action.caseId());	    		
	    		if(elco ==null ){
	    			j++;
	    			allActions.remove(action);
	    		}else{	    			
	    			if(elco.PSRFDETAILS().isEmpty()){
	    				i++;
	    				allActions.remove(action);
	    			}else{
	    				
	    			}
    		
	    		}
		}
    	System.err.println("CNT:"+i +"Mj:"+j);
    	
    }
    
    // delete elco which has no provider
  @Test
    public void deleteAndUpdateElco(){
    	// _count need to remove from view
    	List<Elco> elcos = allElcos.getAll();
    	int i=0;
    	int cnt=0;
    	for (Elco elco : elcos) {
			if(elco.PROVIDERID()==null){
				i++;
				allElcos.remove(elco);
				
			}else{
				 List<Map<String, String>> psrfs = elco.PSRFDETAILS();					 
		 		 try{
		 			 
		 		 for (int j = 0; j < psrfs.size(); j++) { 			  
		 			psrfs.get(j).put("timeStamp", ""+System.currentTimeMillis());
		 			psrfs.get(j).put("clientVersion", DateTimeUtil.getTimestampOfADate(psrfs.get(j).get("today")).toString());
		 			 
				  }
		 		
		 		  elco.setTimeStamp(DateTimeUtil.getTimestampOfADate(elco.TODAY()));
		 		  allElcos.update(elco);
		 		}catch(Exception e){
		 			System.err.println("MSG:"+e.getMessage());
		 			System.err.println("Case::"+elco.caseId());
		 		}
			}
			
		}
    	System.err.println("Count :"+i+"FORM:"+elcos.size());
    } 
  
}
