package org.opensrp.register.mcare.repository.it;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.codehaus.jackson.node.ArrayNode;
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
import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.common.util.DateTimeUtil;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.mcare.domain.Child;
import org.opensrp.register.mcare.domain.Elco;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.domain.Mother;
import org.opensrp.register.mcare.repository.AllChilds;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.scheduler.Action;
import org.opensrp.scheduler.repository.AllActions;
import org.opensrp.scheduler.service.AllEnrollmentWrapper;
import org.springframework.beans.factory.annotation.Autowired;

/*@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-applicationContext-opensrp-register-mcare.xml")*/
public class AllHouseHoldsIntegrationTest {

	@Autowired
    private AllHouseHolds allHouseHolds;
	@Autowired
	private AllElcos allElcos;
	private CouchDbInstance dbInstance;
	private StdCouchDbConnector stdCouchDbConnector;
	private StdCouchDbConnector stdCouchDbConnectorForm;
	private StdCouchDbConnector stdCouchDbConnectorEnrollment;
	@Autowired
	private AllFormSubmissions allFormSubmissions;
	@Autowired	
	private AllMothers allMothers;
	@Autowired
	private AllChilds allChilds;
	@Autowired
	private AllActions allActions;
	private AllEnrollmentWrapper allEnrollmentWrapper;
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
		stdCouchDbConnectorForm = new StdCouchDbConnector("opensrp-form", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnectorEnrollment = new StdCouchDbConnector("motech-scheduletracking-api", dbInstance, new StdObjectMapperFactory());
		stdCouchDbConnector.createDatabaseIfNotExists(); 
		allHouseHolds = new AllHouseHolds(2, stdCouchDbConnector);
		allElcos = new AllElcos(2, stdCouchDbConnector);
		allFormSubmissions = new AllFormSubmissions(stdCouchDbConnectorForm);
		allMothers = new AllMothers(2, stdCouchDbConnector);
		allChilds = new AllChilds(2, stdCouchDbConnector);
		allActions = new AllActions(stdCouchDbConnector);
		allEnrollmentWrapper = new AllEnrollmentWrapper(stdCouchDbConnectorEnrollment);
    	//initMocks(this);
    }
    
  
    
    @Ignore@Test
    public void updateHouseHold(){
 	   List<HouseHold> houseHolds = allHouseHolds.getAll();
 	   int i=0;
 	   for (HouseHold houseHold : houseHolds) {
 		houseHold.setTimeStamp(System.currentTimeMillis());
 		allHouseHolds.update(houseHold);
 		i++;
 		System.err.println("I::"+i);
 	   }
 	   
    }
   // remove hh which has no provider and update setTimeStamp
   // Data cleaning
  @Ignore@Test
   public void shouldRemoveAndUpdateTimeStampHHWithNoProvider(){
   	int i=0; // _count need to remove from view
   	List<HouseHold> households = allHouseHolds.getAll();
   	for (HouseHold houehold : households) {
			if(houehold.PROVIDERID()==null){
				i++;
				allHouseHolds.remove(houehold);
				
			}else{
				try{
				houehold.setTimeStamp(DateTimeUtil.getTimestampOfADate(houehold.START()));
				i++;
		 		allHouseHolds.update(houehold);
		 		System.err.println("I::"+i);
				}catch(Exception e){
					System.err.println("MSG:"+e.getMessage());
					System.err.println("houehold:"+houehold.caseId());
				}
			}
   	}
   	System.err.println("CNT:"+i);
   }
   
  //for provider update 
   @Test
   public void updateDocByMouzaPara(){	 
	 String mouzaPara = "Bachhati - Dara Para";
	 String updateMouzaPara = "Bachhati - Dara Para";
	 List<HouseHold> households=  allHouseHolds.getByMouzaPara(mouzaPara);
	 System.err.println("households:"+households.size());
	 int i=0;
	 int e = 0;
	 String updatedProvider = "jahanara";
	
	 for (HouseHold houseHold : households) {	
		 
		 updateMouzapara(houseHold.caseId(),updateMouzaPara,updatedProvider,mouzaPara); 
		 houseHold.withFWMAUZA_PARA(updatedProvider);
		 houseHold.withPROVIDERID(updatedProvider);
		 try{
			 allHouseHolds.update(houseHold);
		 }catch(Exception ee){
			 System.err.println("HH:"+houseHold.caseId());
		 }
		 
		 actionUpdate(houseHold.caseId(),updatedProvider);
		 List<Elco> elcos = allElcos.getByRelationalId(houseHold.caseId());
		
		 if(elcos!=null){
		 for (Elco elco : elcos) {
			updateMouzapara(elco.caseId(),updateMouzaPara,updatedProvider,mouzaPara);
			elco.withFWWOMMAUZA_PARA(updateMouzaPara);
			elco.withPROVIDERID(updatedProvider);
			try{
				allElcos.update(elco);
			}catch(Exception eee){
				System.err.println("elco:"+elco.caseId());
			}
			
			actionUpdate(elco.caseId(),updatedProvider);
			
			Mother mother = allMothers.findByCaseId(elco.caseId());
			if(mother!=null){
				updateMouzapara(mother.caseId(),updateMouzaPara,updatedProvider,mouzaPara);
				mother.setMother_mauza(updateMouzaPara);
				mother.withPROVIDERID(updatedProvider);
				try{
					allMothers.update(mother);	
				}catch(Exception eeee){
					System.err.println("elco:"+mother.caseId());
				}
				actionUpdate(mother.caseId(),updatedProvider);
				
				List<Child> childs = allChilds.getByRelationalId(mother.caseId());
				if(childs!=null){
					for (Child child : childs) {
						updateMouzapara(child.caseId(),updateMouzaPara,updatedProvider,mouzaPara);
						child.withPROVIDERID(updatedProvider);
						try{
						allChilds.update(child);
						}catch(Exception eeeee){
							System.err.println("mother:"+child.caseId());
						}
						actionUpdate(child.caseId(),updatedProvider);
					}
				
				}else{
					System.err.println("MMM:"+elco.caseId());
				}
				
			}
		 }
		 }else{
			 System.err.println("DD:"+houseHold.caseId());
		 }
            
		
	 }
	 
	   
   }
  
   public void actionUpdate(String caseId,String updatedProvider){
	   List<Action> actions =  allActions.findByCaseID(caseId);       
       for (Action action : actions) {
      	 action.setAnmIdentifier(updatedProvider);
      	 try{
      	 allActions.update(action);
      	 }catch(Exception e){
      		System.err.println("elco:"+caseId); 
      	 }
       }
   }
   public void updateMouzapara(String entityId,String updateMouzaPara,String provider,String mouzaPara){
	 List<FormSubmission> formSubmissions = allFormSubmissions.getByEntityId(entityId);
	 for (FormSubmission formSubmission : formSubmissions) {
		 
		if("Pachgachi Shantiram - Majhi Para".equalsIgnoreCase(mouzaPara) || "Pachgachi Shantiram - Akondo Para".equalsIgnoreCase(mouzaPara)  ){ 
		Map<String,String> fieldsMap = formSubmission.getFormInstance().form().getFieldsAsMap();		 
		List<FormField> fields = formSubmission.getFormInstance().form().fields();	
		for (FormField formField : fields) {
			String name =formField.name();
			if(name.equalsIgnoreCase("existing_Mauzapara")){
				formField.setValue(updateMouzaPara);
			}
			if(name.equalsIgnoreCase("existing_location")){
				formField.setValue(updateMouzaPara);
			}
		}		
		fieldsMap.put("FWMAUZA_PARA", updateMouzaPara);
		fieldsMap.put("existing_location", updateMouzaPara);
		// subforms update
		List<SubFormData> subFormDatas = formSubmission.getFormInstance().form().getSub_forms();
		for (SubFormData subFormData : subFormDatas) {
			List<Map<String, String>> instances = subFormData.instances();
			for (Map<String, String> map : instances) {
				map.put("FWWOMMAUZA_PARA", updateMouzaPara);
				//System.err.println("map:"+map);
			}
		}		
			//System.err.println("formSubmission:"+formSubmission.toString());
			formSubmission.setAnmId(provider);		
		}else{
			System.err.println("Not Updated...");
			//System.err.println("formSubmission:"+formSubmission.toString());
			formSubmission.setAnmId(provider);
			
		}
		try{
			allFormSubmissions.update(formSubmission);
		}catch(Exception e){
			System.err.println("entityId::"+entityId);
		}
		
	 }
	   
   }
   @Ignore@Test
   public void deleteDocByEntityId(){
	   String csvFile = "/opt/multimedia/export/ANCSUBMIT_1500974436027.csv";
       BufferedReader br = null;
       String line = "";
       String cvsSplitBy = ",";
       String caseId = "00004627-2f6d-443d-a162-4bbce38661fb";
       /*try {
           br = new BufferedReader(new FileReader(csvFile));
           while ((line = br.readLine()) != null) {               
               String[] country = line.split(cvsSplitBy);
               HouseHold household = allHouseHolds.findByCaseId("caseId");
               Elco elco = allElcos.findByCaseId("caseId");
               Mother mother = allMothers.findByCaseId("caseId");
               Child child = allChilds.findByCaseId("caseId");
               List<Action> actions =  allActions.findByCaseID("caseId");
               List<FormSubmission> forms = allFormSubmissions.getByEntityId("");
               System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

           }

       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       } finally {
           if (br != null) {
               try {
                   br.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
       }
       */
       HouseHold houseHold=  allHouseHolds.findByCaseId(caseId);       
        System.err.println("HH:"+houseHold.caseId());
        removeForm(houseHold.caseId());
        deleteAction(houseHold.caseId());
		allHouseHolds.remove(houseHold);
		
		 
		 List<Elco> elcos = allElcos.getByRelationalId(houseHold.caseId());
		 if(elcos!=null){
			 for (Elco elco : elcos) {
				 removeForm(elco.caseId());
				 deleteAction(elco.caseId());
				 allElcos.remove(elco);
				 System.err.println("elco:"+elco.caseId());
				 Mother mother = allMothers.findByCaseId(elco.caseId());
				 if(mother!=null){
					removeForm(mother.caseId());
					deleteAction(mother.caseId());
					allMothers.remove(mother);
					System.err.println("mother:"+elco.caseId());
					List<Child> childs = allChilds.getByRelationalId(mother.caseId());
					if(childs!=null){
						for (Child child : childs) {
							removeForm(child.caseId());
							deleteAction(child.caseId());
							allChilds.remove(child);						
							
						}
					}else{
						System.err.println("MMMM:"+elco.caseId());
					}
					
					
				}
			 }
		 
		 }else{
			 System.err.println("DD:"+houseHold.caseId());
		 }
		 
		 
   }
   
  public void removeForm(String entityId){
	  List<FormSubmission> formSubmissions = allFormSubmissions.getByEntityId(entityId);      
     for (FormSubmission formSubmission : formSubmissions) {
    	 allFormSubmissions.remove(formSubmission);
     }
  }
  public void deleteAction(String caseId ){
	  List<Action> actions =  allActions.findByCaseID(caseId);       
      for (Action action : actions) {     	
     	 allActions.remove(action);
      }
  }
  
  public void enrolment(){
	  System.err.println("allEnrollmentWrapper:"+allEnrollmentWrapper);
	 List<Enrollment> allEnrollmentWrappers = allEnrollmentWrapper.getByExternalId("11156827-46a0-42d6-a413-c8cd218f156f");
	 for (Enrollment enrollment : allEnrollmentWrappers) {
		System.err.println(""+enrollment.getCurrentMilestoneName());
	}
  }
   
}

