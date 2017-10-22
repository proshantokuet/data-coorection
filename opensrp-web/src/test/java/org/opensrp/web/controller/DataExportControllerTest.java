package org.opensrp.web.controller;

import static org.mockito.MockitoAnnotations.initMocks;

import java.io.FileWriter;
import java.io.IOException;

import org.ektorp.ViewResult;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.service.DataExportService;
import org.springframework.mock.web.MockHttpServletResponse;

public class DataExportControllerTest {

@Mock
private DataExportController controller;

@Mock
private DataExportService dataExportService;

@Mock
private AllHouseHolds allHouseHolds;

@Before
public void setUp() throws Exception {
	initMocks(this);
    this.controller = new DataExportController();
}

@Ignore@Test
public void test() throws JSONException{
	String multimediaDirPath = "/opt/multimedia";
	String reportName = "BD";
	System.out.println("Path:..............:"+multimediaDirPath);
	MockHttpServletResponse response = new MockHttpServletResponse(); 
	response.setContentType("text/csv");		
	response.setHeader("Content-disposition",
			"attachment; " + "filename=" + reportName);

	ViewResult hhs=allHouseHolds.allHHsCreatedBetween2Date("HouseHold",1451584800000L, 1485799200000L,"FWA");
	
	FileWriter writer;
	try {
		writer = new FileWriter(multimediaDirPath +"/export/" + reportName);			
		 writer.append("FWA WORKER ID"); 
		 writer.append(',');
	     writer.append("today_newhh_FW"); 
		 writer.append(',');
	     writer.append("start_newhh_FW"); 
		 writer.append(',');
	     writer.append("end_newhh_FW"); 
		 writer.append(',');
	     writer.append("FWNHREGDATE"); 
		 writer.append(',');
	     writer.append("FWGOBHHID"); 
		 writer.append(',');
	     writer.append("FWJIVHHID"); 
		 writer.append(',');
	     writer.append("FWUNION"); 
		 writer.append(',');
	     writer.append("FWWARD"); 
		 writer.append(',');
	     writer.append("FWSUBUNIT"); 
		 writer.append(','); //10
	     writer.append("FWMAUZA_PARA"); 
		 writer.append(',');
	     writer.append("FWHOHFNAME"); 
		 writer.append(',');
	     writer.append("FWHOHBIRTHDATE"); 
		 writer.append(',');
	     writer.append("FWHOHGENDER"); 
		 writer.append(',');
	     writer.append("FWNHHMBRNUMB"); 
		 writer.append(',');
	     writer.append("FWNHHMWRA"); 
		 writer.append(',');
	     writer.append("REGDATE"); 
		 writer.append(',');
	     writer.append("FWWOMFNAME"); 
		 writer.append(',');
	     writer.append("FWBIRTHDATE"); 
		 writer.append(',');
	     writer.append("FWWOMAGE"); 
		 writer.append(','); //20
	     writer.append("FWCWOMSTRMEN"); 
		 writer.append(',');
	     writer.append("FWCWOMHUSLIV"); 
		 writer.append(',');
	     writer.append("FWCWOMHUSALV"); 
		 writer.append(',');
	     writer.append("FWCWOMHUSSTR"); 
		 writer.append(',');
	     writer.append("FWELIGIBLE"); 
		 writer.append(',');
	     writer.append("FWWOMANYID"); 
		 writer.append(',');
	     writer.append("FWWOMNID"); 
		 writer.append(',');
	     writer.append("FWWOMBID"); 
		 writer.append(',');
	     writer.append("FWHUSNAME");  
		 writer.append(','); 
	     writer.append("Latitude"); 
		 writer.append(','); //30
	     writer.append("Longitude"); 
	     writer.append(',');
	     writer.append("ELCO");
		 writer.append(',');
	     writer.append("RECEIVED TIME AT SERVER"); 
		 writer.append(',');
	     writer.append("FD WORKER ID"); 
		 writer.append(',');       
	     writer.append("INSTANCE ID"); 
		 writer.append(',');
	     writer.append("ENTITY ID"); 
	     writer.append('\n'); //36
	     
		int count =hhs.getSize(); 
		System.out.println("count: "+count);
	     for (int i = 0; i <count; i++) {
				if(hhs.getRows().get(i).getValue().equalsIgnoreCase("")){
					System.err.println("Error.........................");
				}
				String[] s=hhs.getRows().get(i).getValue().split(",");
				int elco = 0;
				
				String temp = s[0].replace("\"", "").replace("[", "");
				//System.out.println("Loop val: "+ temp);
				
				if(temp != null && !temp.isEmpty() && !temp.equalsIgnoreCase(""))
					elco = Integer.parseInt(temp);
				//String jsonArray = vr.getRows().get(i).getValue().trim();
			    //System.out.println(hhs.getRows().get(i).getValue());
				for(int y=1;y<=16;y++){
					if(s[y] != null && !s[y].isEmpty() && !s[y].equalsIgnoreCase("")){
						writer.append(s[y]);
						writer.append(',');
					}
					else {
						writer.append("");
						writer.append(',');
					}
				}
				if(elco>0){		
					for(int y=21;y<=30;y++){
						if(s[y] != null && !s[y].isEmpty() && !s[y].equalsIgnoreCase("")){
							writer.append(s[y]);
							writer.append(',');
						}
						else {
							writer.append("");
							writer.append(',');
						}
					}
					writer.append(String.valueOf(s[31]));
					writer.append(',');
					writer.append(String.valueOf(s[32]));
					writer.append(',');
					writer.append(s[33]);						
					
					String gps = s[34];
					if(gps != null && !gps.isEmpty() && !gps.equalsIgnoreCase("")){
						String[] location = gps.split(" ");   							
			            if (location.length > 1 ) {
			            	System.out.println("Location: "+ location[0].replace("\"", "") + " " + location[1]);
			            	writer.append(',');
							writer.append(location[0].replace("\"", ""));								
							writer.append(',');
							writer.append(location[1].replace("\"", ""));
			            }
			            else if (location.length > 0 ) {
			            	writer.append(',');
							writer.append(location[0].replace("\"", ""));
							writer.append(',');
							writer.append("");
			            }
			            else{
			            	writer.append(',');
							writer.append("");
							writer.append(',');
							writer.append("");
			            }
					}else{
		            	writer.append(',');
						writer.append("");
						writer.append(',');
						writer.append("");
		            }            
				}
				else{		
					for(int y=1;y<=15;y++){
						writer.append(',');
						writer.append("");
					}           
				}
				writer.append(',');
				writer.append(String.valueOf(elco));
				writer.append(',');					
				writer.append(s[17]);
				writer.append(',');					
				writer.append(s[18]);
				writer.append(',');					
				writer.append(s[19]);
				writer.append(',');
				writer.append(s[20]);
				writer.append('\n');
			}
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{				
			System.out.println("Loop time end:"+ System.currentTimeMillis());
		}	
	
	
}
}
