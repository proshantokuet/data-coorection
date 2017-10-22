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
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.register.mcare.service.DataExportService;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class MotherIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
	private AllElcos allElcos;
	private AllMothers allMothers;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	private AllChilds allChilds;
	
	private AllActions allActions;
	
    @Before
    public void setUp() throws Exception {
    	//allHouseHolds.removeAll();
    	//allElcos.removeAll();
       HttpClient httpClient = new StdHttpClient.Builder() 
        //.host("localhost") 
       	.host("localhost")
        .port(5984) 
        .socketTimeout(1000) 
        .username("Admin").password("mPower@1234")
        .build(); 
		dbInstance = new StdCouchDbInstance(httpClient); 
		
		stdCouchDbConnector = new StdCouchDbConnector("opensrp", dbInstance, new StdObjectMapperFactory());
		 
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allMothers = new AllMothers(2, stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
    	//initMocks(this);
    }
    
  //Data cleaning
  @Ignore@Test
    public void shouldRemoveAction(){
    	DataExportService dataExportService =  new DataExportService();
    	//Post Natal Care Reminder Visit
    	//Ante Natal Care Reminder Visit
    	List<Action> actions = allActions.findByScheduleNameAndIsActive(false, "Post Natal Care Reminder Visit");
    	System.err.println("actions:"+actions.size());
    	int i=0;
    	int j=0;
    	for (Action action : actions) {    		
    		Mother mother = allMothers.findByCaseId(action.caseId());
	    		String visitCode = action.data().get("visitCode"); 
	    		
	    		int visitNumber = dataExportService.getLimit(visitCode);
	    		
	    		if(mother ==null ){
	    			j++;
	    			allActions.remove(action);
	    		}else{
	    			Map<String, String> anc = dataExportService.getANCNumber(mother, visitNumber-1);
	    			if(  anc!=null ||!anc.isEmpty()){
	    				i++;
	    				allActions.remove(action);
	    			}else{
	    				System.err.println("ANC:"+anc);
	    			}
    		
	    		}
		}
    	System.err.println("CNT:"+i +"Mj:"+j);
    	
    }
    
    // for data cleaning
    @Ignore@Test
    public void shouldRemoveMotherWithNoProvider(){
    	List<Mother> mothers = allMothers.getAll();
    	int i=0;
    	int cnt = 0;
    	String FWWOMDISTRICT = "";
    	String FWWOMUPAZILLA ="";
    	for (Mother mother : mothers) {
    		
    		if(mother.PROVIDERID()==null){
    			i++;
    			allMothers.remove(mother);
    			/*List<FormSubmission> formSubmissions = allFormSubmissions.findByEntity(mother.caseId());
    			// mother created from bnf or psrf submission where psrf or bnf valid  but there mother is invalid
				for (FormSubmission formSubmission : formSubmissions) {
					allFormSubmissions.remove(formSubmission);
					System.err.println("FormName:"+formSubmission.formName()+ "Provider: "+formSubmission.anmId()+ "      Case:"+formSubmission.entityId());
				}
				cnt += allFormSubmissions.findByEntity(mother.caseId()).size();*/
				System.err.println(""+i);
    		}else{
    			
    			try{
            		Elco elco = allElcos.findByCaseId(mother.getRelationalid());
            		
            		mother.details().put("birthDate", elco.FWBIRTHDATE());
            		 List<Map<String, String>> psrfs =elco.PSRFDETAILS();
            		 int psrfsCount = psrfs.size()-1;
            		 Map<String, String> psrf = psrfs.get(psrfsCount);
            		 mother.details().put("LMP", psrf.get("FWPSRLMP"));
            		 
            		if(elco !=null){
            			if(elco.FWWOMDISTRICT()!=null){
            				FWWOMDISTRICT = elco.FWWOMDISTRICT();
            			}else{
            				FWWOMDISTRICT = "";
            			}
            			if(elco.FWWOMUPAZILLA()!=null ){
            				FWWOMUPAZILLA =elco.FWWOMUPAZILLA();
            			}else{
            				FWWOMUPAZILLA = "";
            			}
            			
            		}
            		
            		/*if(mother.getFWWOMUNION() ==null){
        				mother.setFWWOMUNION("");
        			}*/
        			
            		 List<Map<String, String>> bnfs =mother.bnfVisitDetails();
            	 		System.err.println("bnfs.size():"+bnfs.size());
            		  for (int j = 0; j < bnfs.size(); j++) {
            			  System.err.println(""+i);
            			  bnfs.get(j).put("timeStamp", ""+System.currentTimeMillis());
            			  bnfs.get(j).put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.TODAY()).toString());
            		  }
            		
        			mother.withFWWOMDISTRICT(FWWOMDISTRICT);
                	mother.withFWWOMUPAZILLA(FWWOMUPAZILLA);
                	mother.setTimeStamp(DateTimeUtil.getTimestampOfADate(mother.TODAY()));
            		allMothers.update(mother);
            		
            	}catch(Exception e){
            		allMothers.remove(mother);
            		e.printStackTrace();
            		System.out.println("mother:"+e.getMessage());
            		System.out.println("caseId:"+mother.caseId());
            	}
    		}
			
		}
    	System.out.println("CNT:"+i+"FormCNT:"+cnt);
    	
    }
    
    
   
    
   @Ignore @Test
    public void updateMother(){
 	   List<Mother> mothers = allMothers.getAll();
 	   for (Mother mother : mothers) {
 		  List<Map<String, String>> psrfs =mother.bnfVisitDetails();
 		try{
 		for (int i = 0; i < psrfs.size(); i++) {
			psrfs.get(i).put("timeStamp", ""+System.currentTimeMillis());
			psrfs.get(i).put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.TODAY()).toString());
		}
 		 
 		
 		
 		//mother.withClientVersion(DateTimeUtil.getTimestampOfADate(mother.TODAY()));
 		 //mother.setTimeStamp(System.currentTimeMillis());
 		if(mother.TODAY()!=null){
			mother.withClientVersion(DateTimeUtil.getTimestampOfADate(mother.TODAY()));
		}else{
			mother.withClientVersion(0);
		}
		if(!mother.ancVisitOne().isEmpty()){
			mother.ancVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitOne().get("today")).toString());
			mother.ancVisitOne().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}
		
		if(!mother.ancVisitTwo().isEmpty()){
			mother.ancVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitTwo().get("today")).toString());
			mother.ancVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}
		
		if(!mother.ancVisitThree().isEmpty()){
			mother.ancVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitThree().get("today")).toString());
			mother.ancVisitThree().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}

		if(!mother.ancVisitFour().isEmpty()){
			mother.ancVisitFour().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.ancVisitFour().get("today")).toString());
			mother.ancVisitFour().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}
		
		if(!mother.pncVisitOne().isEmpty()){
			mother.pncVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitOne().get("today")).toString());
			mother.pncVisitOne().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}
		
		if(!mother.pncVisitTwo().isEmpty()){
			mother.pncVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitTwo().get("today")).toString());
			mother.pncVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}
		if(!mother.pncVisitThree().isEmpty()){
			mother.pncVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.pncVisitThree().get("today")).toString());
			mother.pncVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
			
			
		}
		 List<Map<String, String>> bnfs =mother.bnfVisitDetails();
		 for (int j = 0; j < bnfs.size(); j++) {
			 bnfs.get(j).put("timeStamp", ""+System.currentTimeMillis());
			 bnfs.get(j).put("clientVersion", DateTimeUtil.getTimestampOfADate(mother.TODAY()).toString());
			}
		mother.setTimeStamp(System.currentTimeMillis());
 		 allMothers.update(mother);
 		 
 		}catch(Exception e){
 			System.err.println(""+e.getMessage());
 			System.err.println(""+mother.caseId());
 		}
 	   }
 	   
    }
 
 
  
  
 
  
@Ignore @Test
 public void deleteUpdateChild(){
 	List<Child> childs = allChilds.getAll();
 	for (Child child : childs) {
 		
 		if(child.PROVIDERID()==null || child.PROVIDERID().isEmpty() ){
 			System.err.println("DD:"+child.PROVIDERID());
 			allChilds.remove(child);
 		}else{
 			child.setTimeStamp(System.currentTimeMillis());
			allChilds.update(child);
 		}
			
	}
 	
 }
 @Ignore @Test
  public void childUpdate(){
		List<Child> clilds = allChilds.getAll();
		int i =0;
		for (Child child : clilds) {
			try{
			Mother mother = allMothers.findByCaseId(child.details().get("relationalid"));
			if(child.TODAY()!=null){			
			child.withClientVersion(DateTimeUtil.getTimestampOfADate(child.TODAY()));
			}else{
				child.withClientVersion(0);
			}
			try{
				child.withDistrict(mother.FWWOMDISTRICT());
			}catch(Exception e){
				child.withDistrict("");
			}
			try{
				child.withUpazilla(mother.FWWOMUPAZILLA());
			}catch(Exception e){
				child.withUpazilla("");
			}
			
			try{
				child.withUnion(mother.getFWWOMUNION());
			}catch(Exception e){
				child.withUnion("");
			}
			try{
				child.withUnit(mother.getFWWOMSUBUNIT());
			}catch(Exception e){
				child.withUnit("");
			}
			
			try{
				child.withMouzaPara(mother.getMother_mauza());
			}catch(Exception e){
				child.withMouzaPara("");
			}
			try{
			child.details().put("ward", mother.getFWWOMWARD());
			}catch(Exception e){
				child.details().put("ward", "");
			}
			
			allChilds.update(child);
			System.err.println("I:"+i+1);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
  @Ignore@Test
  public void childENCCUpdate(){
		List<Child> childs = allChilds.getAll();
		int i =0;
		for (Child child : childs) {
			try{
			
			
			if(!child.enccVisitOne().isEmpty()){
				child.enccVisitOne().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitOne().get("today")).toString());
				child.enccVisitOne().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!child.enccVisitTwo().isEmpty()){
				child.enccVisitTwo().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitTwo().get("today")).toString());
				child.enccVisitTwo().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}
			
			if(!child.enccVisitThree().isEmpty()){
				child.enccVisitThree().put("clientVersion", DateTimeUtil.getTimestampOfADate(child.enccVisitThree().get("today")).toString());
				child.enccVisitThree().put("timeStamp", ""+System.currentTimeMillis());
				
				
			}

			
			
			//child.setTimeStamp(System.currentTimeMillis());
			
			allChilds.update(child);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
  
  
    
   
}
