/***
 * @author proshanto
 * */
package org.opensrp.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.ektorp.ViewResult;
import org.opensrp.common.util.DateUtil;
import org.opensrp.common.util.WeekBoundariesAndTimestamps;
import org.opensrp.dto.CountServiceDTO;
import org.opensrp.dto.FormCountDTO;
import org.opensrp.dto.CountServiceDTOForChart;
import org.opensrp.form.repository.AllFormSubmissions;
import org.opensrp.register.mcare.domain.HouseHold;
import org.opensrp.register.mcare.repository.AllElcos;
import org.opensrp.register.mcare.repository.AllHouseHolds;
import org.opensrp.register.mcare.repository.AllMothers;
import org.opensrp.rest.services.LuceneElcoService;
import org.opensrp.rest.services.LuceneFormService;
import org.opensrp.rest.services.LuceneHouseHoldService;
import org.opensrp.rest.services.LuceneMotherService;
import org.opensrp.rest.services.LuceneScheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataCountService {
	private static Logger logger = LoggerFactory.getLogger(DataCountService.class);
	private final AllHouseHolds allHouseHolds;
	private final AllElcos allElcos;	
	private LuceneFormService luceneFormService;	
	private LuceneMotherService luceneMotherService;
	private LuceneScheduleService luceneScheduleService;	
	@Autowired
	public DataCountService(AllHouseHolds allHouseHolds,LuceneFormService luceneFormService,AllElcos allElcos,
			LuceneMotherService luceneMotherService,LuceneScheduleService luceneScheduleService){
		this.allHouseHolds = allHouseHolds;
		
		this.allElcos = allElcos;		
		this.luceneMotherService = luceneMotherService;
		this.luceneFormService = luceneFormService;
		this.luceneScheduleService = luceneScheduleService;
	}
	/**
	 * This method return count data of registers.
	 * @param provider    who sent data
	 * @param startMonth  means start date of a month
	 * @param endMonth    means end day of a month
	 * @param startWeek   means start day of a week
	 * @param endWeek     means end day of a week
	 * */
	public List<CountServiceDTO> getHHCountInformation(String provider,String startMonth,String endMonth,String startWeek,String endWeek,String type){
		List<CountServiceDTO> commonServiceDTOs = new ArrayList<CountServiceDTO>();
		
		CountServiceDTO commonServiceDTO = new CountServiceDTO();
		if(type.equalsIgnoreCase("all")){
			this.getHouseholdCountForHomePage(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			this.getElcoCountForHomePage(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
			this.getMotherCountForHomePage(provider, startMonth, endMonth, startWeek, endWeek, commonServiceDTO);
					
		}else{
			
		}
		commonServiceDTOs.add(commonServiceDTO);
		return commonServiceDTOs;
		
	}
	
	public List<CountServiceDTOForChart> getHHCountInformationForLastFourMonthAsWeekWise(){
		ViewResult hhViewResult;
		Long startTime =DateUtil.getStartTimeStampOfAMonth(3);
		hhViewResult = allHouseHolds.HouseholdBetweenTwoDatesAsViewResult(startTime);
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(hhViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getHHCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult hhViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			hhViewResult = allHouseHolds.allHHsCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(hhViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getElcoCountInformationForLastFourMonthAsWeekWise(){
		ViewResult elcoViewResult;	
		Long startTime =DateUtil.getStartTimeStampOfAMonth(3);		
		elcoViewResult = allElcos.elcoBetweenTwoDatesAsViewResult(startTime);		
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getElcoCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult elcoViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			elcoViewResult = allElcos.allElcosCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			elcoViewResult = allElcos.allElcosCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getPregnantWomenCountInformationForLastFourMonthAsWeekWise(){
		ViewResult pwViewResult;		
		Long startTime =DateUtil.getStartTimeStampOfAMonth(3);
		pwViewResult = allElcos.pregnantElcoBetweenTwoDatesAsViewResult(startTime);
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(pwViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
	
	public List<CountServiceDTOForChart> getMotherCountInformationForChart(String provider, String district, String upazilla, String union){
		ViewResult elcoViewResult;		
		String key = this.createRawStartKey(provider, district, upazilla, union);
		if(!provider.equalsIgnoreCase("")){
			elcoViewResult = allElcos.allMothersCreatedLastFourMonthsByProviderAndLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		else{
			elcoViewResult = allElcos.allMothersCreatedLastFourMonthsByLocationViewResult(key,key.substring(0, key.length()-1) + ",{}]");
		}
		List<CountServiceDTOForChart> DTOs= new ArrayList<CountServiceDTOForChart>();
		CountServiceDTOForChart newDTO = new CountServiceDTOForChart();
		newDTO.setCounts(this.convertViewResultToWeekWiseCount(elcoViewResult));
		DTOs.add(newDTO);
		return DTOs;
	}
		
	private List<Integer> convertViewResultToWeekWiseCount( ViewResult vr){		
		List<Integer> seperateWeeklyCountDataForRegisterFromViewResult = new ArrayList<>();
		for (int index = 0; index < 23; index++) {
			seperateWeeklyCountDataForRegisterFromViewResult.add(index,0);
		}
		WeekBoundariesAndTimestamps boundaries = DateUtil.getWeekBoundariesForDashboard();
    	List<Long> startAndEndOfWeeksAsTimestamp = boundaries.weekBoundariesAsTimeStamp;     	
    	int todaysCount=0;
    	long todayTimeStamp = DateUtil.getTimestampToday() ; 
    	long oldTimeStamp=0;
    	int oldPosition=0;
    	int position = 0;
    	for (ViewResult.Row row : vr.getRows()) {
    		String stringValue = row.getValue(); 
    		long value = Long.parseLong(stringValue);			
			if(todayTimeStamp==value){
				todaysCount++;
			}			
    		try{ 
    			if(Long.parseLong(stringValue) == oldTimeStamp){
    				Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(oldPosition);
    				seperateWeeklyCountDataForRegisterFromViewResult.set(oldPosition, existingCount+1);
    			}else{
	    			position = DateUtil.binarySearch(Long.parseLong(stringValue), startAndEndOfWeeksAsTimestamp);
	    			Integer existingCount = seperateWeeklyCountDataForRegisterFromViewResult.get(position);
	    			seperateWeeklyCountDataForRegisterFromViewResult.set(position, existingCount+1);
    			}    			
    			oldTimeStamp = Long.parseLong(stringValue);
    			oldPosition = position;
    			   			
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}    	
    	
    	for(int monthIndex = 3; monthIndex >= 0; monthIndex--){
			int month = DateUtil.getMontNumber(monthIndex);    		
    		if(monthIndex ==3 && month==1 ){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(4, 0);    			
    		}else if(monthIndex ==2 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(9, 0);    			
    		}else if(monthIndex ==1 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(14, 0);    			
    		}else if(monthIndex ==0 && month==1){
    			seperateWeeklyCountDataForRegisterFromViewResult.add(19, 0);    			
    		}else{
    			
    		}
    	}
    	seperateWeeklyCountDataForRegisterFromViewResult.set(20, todaysCount);
		return seperateWeeklyCountDataForRegisterFromViewResult;
	}
	private String createRawStartKey(String provider, String district, String upazilla, String union){
		String key = "";
		if(provider.equalsIgnoreCase("")){
			if(union.equalsIgnoreCase("")){
				if(upazilla.equalsIgnoreCase("")){
					key = "[\"" + district + "\"]";
				}
				else{
					key = "[\"" + district + "\",\"" + upazilla + "\"]";
				}
			}
			else{
				key = "[\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
			}
		}
		else{
			if(union.equalsIgnoreCase("")){
				if(upazilla.equalsIgnoreCase("")){
					if(district.equalsIgnoreCase("")){
						key = "[\"" + provider + "\"]";
					}
					else{
						key = "[\"" + provider + "\",\"" + district + "\"]";
					}
					
				}
				else{
					key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\"]";
				}
			}
			else{
				key = "[\"" + provider + "\",\"" + district + "\",\"" + upazilla + "\",\"" + union + "\"]";
			}
		
		}
		
		return key;
	}
		
	private CountServiceDTO getHouseholdCountForHomePage(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		Long startTime =DateUtil.getStartTimeStampOfAMonth(0);
		ViewResult vr = allHouseHolds.HouseholdBetweenTwoDatesAsViewResult(startTime);
		commonServiceDTO.setHouseholdTotalCount(allHouseHolds.totalHousehold()) ;
		List<Integer> countData = this.convertViewResultToWeekWiseCount(vr);		
		commonServiceDTO.setHouseholdTodayCount(countData.get(20));	
		
		commonServiceDTO.setHouseholdThisMonthCount(countData.get(19)+countData.get(18)+countData.get(17)+countData.get(16)+countData.get(15));
		commonServiceDTO.setHouseholdThisWeekCount(countData.get(15+DateUtil.getCurrentMonthCurrentweek()));
		return commonServiceDTO;
	}
	private CountServiceDTO getElcoCountForHomePage(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		Long startTime =DateUtil.getStartTimeStampOfAMonth(0);
		ViewResult vr = allElcos.elcoBetweenTwoDatesAsViewResult(startTime);
		List<Integer> countData = this.convertViewResultToWeekWiseCount(vr);
		commonServiceDTO.setElcoTotalCount(allElcos.totalElco());				
		commonServiceDTO.setElcoThisMonthCount(countData.get(19)+countData.get(18)+countData.get(17)+countData.get(16)+countData.get(15));
		commonServiceDTO.setElcoThisWeekCount(countData.get(15+DateUtil.getCurrentMonthCurrentweek()));
		commonServiceDTO.setElcoTodayCount(countData.get(20));
		return commonServiceDTO;
	}
	private CountServiceDTO getMotherCountForHomePage(String provider,String startMonth,String endMonth,String startWeek,String endWeek,CountServiceDTO commonServiceDTO){
		Long startTime =DateUtil.getStartTimeStampOfAMonth(0);
		ViewResult vr = allElcos.pregnantElcoBetweenTwoDatesAsViewResult(startTime);
		List<Integer> countData = this.convertViewResultToWeekWiseCount(vr);
		commonServiceDTO.setPwTotalCount(vr.getTotalRows());
		commonServiceDTO.setPwThisMonthCount(countData.get(15) + countData.get(16) + countData.get(17) + countData.get(18) + countData.get(19));//(luceneMotherService.getMotherCount(startMonth, endMonth));
		commonServiceDTO.setPwThisWeekCount(countData.get(15+DateUtil.getCurrentMonthCurrentweek()));
		commonServiceDTO.setPwTodayCount(countData.get(20));
		return commonServiceDTO;
	}
	
	public FormCountDTO getFormCount(String provider){
		FormCountDTO formCountDTO = new FormCountDTO();
		
		Calendar c = Calendar.getInstance();   // this takes current date
	    c.set(Calendar.DAY_OF_MONTH,
                c.getActualMinimum(Calendar.DAY_OF_MONTH));
	     
	    String week1Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week1Start: " + week1Start);
	    
	    c.add(Calendar.DATE, 6);
	    String week1End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week1End: " + week1End);
	    
	    c.add(Calendar.DATE, 1);
	    String week2Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week2Start: " + week2Start);
	    
	    c.add(Calendar.DATE, 6);
	    String week2End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week2End: " + week2End);
	    
	    c.add(Calendar.DATE, 1);
	    String week3Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week3Start: " + week3Start);
	    
	    c.add(Calendar.DATE, 6);
	    String week3End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week3End: " + week3End);
	    
	    c.add(Calendar.DATE, 1);
	    String week4Start= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week4Start: " + week4Start);
		
	    c.set(Calendar.DAY_OF_MONTH,
                c.getActualMaximum(Calendar.DAY_OF_MONTH));
	    
	    String week4End= new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	    System.out.println("week4End: " + week4End);
		
	    /*System.out.println("Total "+luceneFormService.getFormCount("","",provider,formName));
		System.out.println("ThisMonth "+luceneFormService.getFormCount(week1Start, week4End, provider, formName));
		System.out.println("Week1 "+luceneFormService.getFormCount(week1Start, week1End, provider, formName));	
		System.out.println("Week2 "+luceneFormService.getFormCount(week2Start, week2End, provider, formName));	
		System.out.println("Week3 "+luceneFormService.getFormCount(week3Start, week3End, provider, formName));	
		System.out.println("Week4 "+luceneFormService.getFormCount(week4Start, week4End, provider, formName));*/	
		
		formCountDTO.setHouseholdTotalCount(luceneFormService.getFormCount("","",provider,"new_household_registration")); 
		formCountDTO.setHouseholdThisMonthCount(luceneFormService.getFormCount(week1Start, week4End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek1Count(luceneFormService.getFormCount(week1Start, week1End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek2Count(luceneFormService.getFormCount(week2Start, week2End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek3Count(luceneFormService.getFormCount(week3Start, week3End, provider, "new_household_registration"));
		formCountDTO.setHouseholdWeek4Count(luceneFormService.getFormCount(week4Start, week4End, provider, "new_household_registration"));
		
		formCountDTO.setPsrfTotalCount(luceneFormService.getFormCount("","",provider, "psrf_form")); 
		formCountDTO.setPsrfThisMonthCount(luceneFormService.getFormCount(week1Start, week4End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek1Count(luceneFormService.getFormCount(week1Start, week1End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek2Count(luceneFormService.getFormCount(week2Start, week2End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek3Count(luceneFormService.getFormCount(week3Start, week3End, provider, "psrf_form"));
		formCountDTO.setPsrfWeek4Count(luceneFormService.getFormCount(week4Start, week4End, provider, "psrf_form"));
		
		formCountDTO.setCensusTotalCount(luceneFormService.getFormCount("","",provider, "census_enrollment_form")); 
		formCountDTO.setCensusThisMonthCount(luceneFormService.getFormCount(week1Start, week4End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek1Count(luceneFormService.getFormCount(week1Start, week1End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek2Count(luceneFormService.getFormCount(week2Start, week2End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek3Count(luceneFormService.getFormCount(week3Start, week3End, provider, "census_enrollment_form"));
		formCountDTO.setCensusWeek4Count(luceneFormService.getFormCount(week4Start, week4End, provider, "census_enrollment_form"));
		
		
		int anc1TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_1"); 
		int anc1ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_1");
		int anc1Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_1");
		int anc1Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_1");
		int anc1Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_1");
		int anc1Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_1");
		
		int anc2TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_2"); 
		int anc2ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_2");
		int anc2Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_2");
		int anc2Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_2");
		int anc2Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_2");
		int anc2Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_2");
		
		int anc3TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_3"); 
		int anc3ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_3");
		int anc3Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_3");
		int anc3Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_3");
		int anc3Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_3");
		int anc3Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_3");
		
		int anc4TotalCount = luceneFormService.getFormCount("","",provider, "anc_reminder_visit_4"); 
		int anc4ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "anc_reminder_visit_4");
		int anc4Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "anc_reminder_visit_4");
		int anc4Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "anc_reminder_visit_4");
		int anc4Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "anc_reminder_visit_4");
		int anc4Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "anc_reminder_visit_4");
		
		int ancTotalCount = anc1TotalCount + anc2TotalCount + anc3TotalCount + anc4TotalCount;
		int ancThisMonthCount = anc1ThisMonthCount + anc2ThisMonthCount + anc3ThisMonthCount + anc4ThisMonthCount;
		int ancWeek1Count = anc1Week1Count + anc2Week1Count + anc3Week1Count + anc4Week1Count;
		int ancWeek2Count = anc1Week2Count + anc2Week2Count + anc3Week2Count + anc4Week2Count;
		int ancWeek3Count = anc1Week3Count + anc2Week3Count + anc3Week3Count + anc4Week3Count;
		int ancWeek4Count = anc1Week4Count + anc2Week4Count + anc3Week4Count + anc4Week4Count;
		
		formCountDTO.setAncTotalCount(ancTotalCount); 
		formCountDTO.setAncThisMonthCount(ancThisMonthCount);
		formCountDTO.setAncWeek1Count(ancWeek1Count);
		formCountDTO.setAncWeek2Count(ancWeek2Count);
		formCountDTO.setAncWeek3Count(ancWeek3Count);
		formCountDTO.setAncWeek4Count(ancWeek4Count);
		
		
		int pnc1TotalCount = luceneFormService.getFormCount("","",provider, "pnc_reminder_visit_1"); 
		int pnc1ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "pnc_reminder_visit_1");
		int pnc1Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "pnc_reminder_visit_1");
		int pnc1Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "pnc_reminder_visit_1");
		int pnc1Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "pnc_reminder_visit_1");
		int pnc1Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "pnc_reminder_visit_1");
		
		int pnc2TotalCount = luceneFormService.getFormCount("","",provider, "pnc_reminder_visit_2"); 
		int pnc2ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "pnc_reminder_visit_2");
		int pnc2Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "pnc_reminder_visit_2");
		int pnc2Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "pnc_reminder_visit_2");
		int pnc2Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "pnc_reminder_visit_2");
		int pnc2Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "pnc_reminder_visit_2");
		
		int pnc3TotalCount = luceneFormService.getFormCount("","",provider, "pnc_reminder_visit_3"); 
		int pnc3ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "pnc_reminder_visit_3");
		int pnc3Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "pnc_reminder_visit_3");
		int pnc3Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "pnc_reminder_visit_3");
		int pnc3Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "pnc_reminder_visit_3");
		int pnc3Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "pnc_reminder_visit_3");
		
		int pncTotalCount = pnc1TotalCount + pnc2TotalCount + pnc3TotalCount ;
		int pncThisMonthCount = pnc1ThisMonthCount + pnc2ThisMonthCount + pnc3ThisMonthCount ;
		int pncWeek1Count = pnc1Week1Count + pnc2Week1Count + pnc3Week1Count ;
		int pncWeek2Count = pnc1Week2Count + pnc2Week2Count + pnc3Week2Count ;
		int pncWeek3Count = pnc1Week3Count + pnc2Week3Count + pnc3Week3Count ;
		int pncWeek4Count = pnc1Week4Count + pnc2Week4Count + pnc3Week4Count ;
		
		formCountDTO.setPncTotalCount(pncTotalCount); 
		formCountDTO.setPncThisMonthCount(pncThisMonthCount);
		formCountDTO.setPncWeek1Count(pncWeek1Count);
		formCountDTO.setPncWeek2Count(pncWeek2Count);
		formCountDTO.setPncWeek3Count(pncWeek3Count);
		formCountDTO.setPncWeek4Count(pncWeek4Count);
		
		
		int encc1TotalCount = luceneFormService.getFormCount("","",provider, "encc_visit_1"); 
		int encc1ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "encc_visit_1");
		int encc1Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "encc_visit_1");
		int encc1Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "encc_visit_1");
		int encc1Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "encc_visit_1");
		int encc1Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "encc_visit_1");
		
		int encc2TotalCount = luceneFormService.getFormCount("","",provider, "encc_visit_2"); 
		int encc2ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "encc_visit_2");
		int encc2Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "encc_visit_2");
		int encc2Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "encc_visit_2");
		int encc2Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "encc_visit_2");
		int encc2Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "encc_visit_2");
		
		int encc3TotalCount = luceneFormService.getFormCount("","",provider, "encc_visit_3"); 
		int encc3ThisMonthCount = luceneFormService.getFormCount(week1Start, week4End, provider, "encc_visit_3");
		int encc3Week1Count = luceneFormService.getFormCount(week1Start, week1End, provider, "encc_visit_3");
		int encc3Week2Count = luceneFormService.getFormCount(week2Start, week2End, provider, "encc_visit_3");
		int encc3Week3Count = luceneFormService.getFormCount(week3Start, week3End, provider, "encc_visit_3");
		int encc3Week4Count = luceneFormService.getFormCount(week4Start, week4End, provider, "encc_visit_3");
		
		int enccTotalCount = encc1TotalCount + encc2TotalCount + encc3TotalCount ;
		int enccThisMonthCount = encc1ThisMonthCount + encc2ThisMonthCount + encc3ThisMonthCount ;
		int enccWeek1Count = encc1Week1Count + encc2Week1Count + encc3Week1Count ;
		int enccWeek2Count = encc1Week2Count + encc2Week2Count + encc3Week2Count ;
		int enccWeek3Count = encc1Week3Count + encc2Week3Count + encc3Week3Count ;
		int enccWeek4Count = encc1Week4Count + encc2Week4Count + encc3Week4Count ;
		
		formCountDTO.setEnccTotalCount(enccTotalCount); 
		formCountDTO.setEnccThisMonthCount(enccThisMonthCount);
		formCountDTO.setEnccWeek1Count(enccWeek1Count);
		formCountDTO.setEnccWeek2Count(enccWeek2Count);
		formCountDTO.setEnccWeek3Count(enccWeek3Count);
		formCountDTO.setEnccWeek4Count(enccWeek4Count);
		
		formCountDTO.setPregnancyTotalCount(luceneMotherService.getMotherCount(provider));
		formCountDTO.setPregnancyThisMonthCount(luceneMotherService.getMotherCount(week1Start, week4End, provider));
		formCountDTO.setPregnancyWeek1Count(luceneMotherService.getMotherCount(week1Start, week1End, provider));
		formCountDTO.setPregnancyWeek2Count(luceneMotherService.getMotherCount(week2Start, week2End, provider));
		formCountDTO.setPregnancyWeek3Count(luceneMotherService.getMotherCount(week3Start, week3End, provider));
		formCountDTO.setPregnancyWeek4Count(luceneMotherService.getMotherCount(week4Start, week4End, provider));	
		
		formCountDTO.setFamilyPlanning(luceneFormService.getFormCount("","",provider, "mis_elco")); 
		formCountDTO.setNewborn(luceneMotherService.getBirth(provider).get(0));
		formCountDTO.setLiveBirth(luceneMotherService.getBirth(provider).get(1));
		formCountDTO.setStillBirth(luceneMotherService.getBirth(provider).get(2));
		formCountDTO.setmiscarriage(luceneMotherService.getBirth(provider).get(0));
		formCountDTO.setMortalityAtDelivery(0);
		formCountDTO.setMortalityAtBirth(0);

		int schedulePSRFTotalCount = luceneScheduleService.getSchedulCount("","",provider, "ELCO PSRF"); 
		int schedulePSRFThisMonthCount = luceneScheduleService.getSchedulCount(week1Start, week4End, provider, "ELCO PSRF");
		int schedulePSRFWeek1Count = luceneScheduleService.getSchedulCount(week1Start, week1End, provider, "ELCO PSRF");
		int schedulePSRFWeek2Count = luceneScheduleService.getSchedulCount(week2Start, week2End, provider, "ELCO PSRF");
		int schedulePSRFWeek3Count = luceneScheduleService.getSchedulCount(week3Start, week3End, provider, "ELCO PSRF");
		int schedulePSRFWeek4Count = luceneScheduleService.getSchedulCount(week4Start, week4End, provider, "ELCO PSRF");
				
		int scheduleANCTotalCount = luceneScheduleService.getSchedulCount("","",provider, "Ante Natal Care Reminder Visit"); 
		int scheduleANCThisMonthCount = luceneScheduleService.getSchedulCount(week1Start, week4End, provider, "Ante Natal Care Reminder Visit");
		int scheduleANCWeek1Count = luceneScheduleService.getSchedulCount(week1Start, week1End, provider, "Ante Natal Care Reminder Visit");
		int scheduleANCWeek2Count = luceneScheduleService.getSchedulCount(week2Start, week2End, provider, "Ante Natal Care Reminder Visit");
		int scheduleANCWeek3Count = luceneScheduleService.getSchedulCount(week3Start, week3End, provider, "Ante Natal Care Reminder Visit");
		int scheduleANCWeek4Count = luceneScheduleService.getSchedulCount(week4Start, week4End, provider, "Ante Natal Care Reminder Visit");
		
		int schedulePNCTotalCount = luceneScheduleService.getSchedulCount("","",provider, "Post Natal Care Reminder Visit"); 
		int schedulePNCThisMonthCount = luceneScheduleService.getSchedulCount(week1Start, week4End, provider, "Post Natal Care Reminder Visit");
		int schedulePNCWeek1Count = luceneScheduleService.getSchedulCount(week1Start, week1End, provider, "Post Natal Care Reminder Visit");
		int schedulePNCWeek2Count = luceneScheduleService.getSchedulCount(week2Start, week2End, provider, "Post Natal Care Reminder Visit");
		int schedulePNCWeek3Count = luceneScheduleService.getSchedulCount(week3Start, week3End, provider, "Post Natal Care Reminder Visit");
		int schedulePNCWeek4Count = luceneScheduleService.getSchedulCount(week4Start, week4End, provider, "Post Natal Care Reminder Visit");
		
		int scheduleENCCTotalCount = luceneScheduleService.getSchedulCount("","",provider, "Essential Newborn Care Checklist"); 
		int scheduleENCCThisMonthCount = luceneScheduleService.getSchedulCount(week1Start, week4End, provider, "Essential Newborn Care Checklist");
		int scheduleENCCWeek1Count = luceneScheduleService.getSchedulCount(week1Start, week1End, provider, "Essential Newborn Care Checklist");
		int scheduleENCCWeek2Count = luceneScheduleService.getSchedulCount(week2Start, week2End, provider, "Essential Newborn Care Checklist");
		int scheduleENCCWeek3Count = luceneScheduleService.getSchedulCount(week3Start, week3End, provider, "Essential Newborn Care Checklist");
		int scheduleENCCWeek4Count = luceneScheduleService.getSchedulCount(week4Start, week4End, provider, "Essential Newborn Care Checklist");
		
		System.out.println("Total PSRF "+schedulePSRFTotalCount);
		System.out.println("ThisMonth "+schedulePSRFThisMonthCount);
		System.out.println("Week1 "+schedulePSRFWeek1Count);	
		System.out.println("Week2 "+schedulePSRFWeek2Count);	
		System.out.println("Week3 "+schedulePSRFWeek3Count);	
		System.out.println("Week4 "+schedulePSRFWeek4Count);
		
		System.out.println("Total ANC "+scheduleANCTotalCount);
		System.out.println("ThisMonth "+scheduleANCThisMonthCount);
		System.out.println("Week1 "+scheduleANCWeek1Count);	
		System.out.println("Week2 "+scheduleANCWeek2Count);	
		System.out.println("Week3 "+scheduleANCWeek3Count);	
		System.out.println("Week4 "+scheduleANCWeek4Count);
		
		System.out.println("Total PNC "+schedulePNCTotalCount);
		System.out.println("ThisMonth "+schedulePNCThisMonthCount);
		System.out.println("Week1 "+schedulePNCWeek1Count);	
		System.out.println("Week2 "+schedulePNCWeek2Count);	
		System.out.println("Week3 "+schedulePNCWeek3Count);	
		System.out.println("Week4 "+schedulePNCWeek4Count);

		System.out.println("Total ENCC "+scheduleENCCTotalCount);
		System.out.println("ThisMonth "+scheduleENCCThisMonthCount);
		System.out.println("Week1 "+scheduleENCCWeek1Count);	
		System.out.println("Week2 "+scheduleENCCWeek2Count);	
		System.out.println("Week3 "+scheduleENCCWeek3Count);	
		System.out.println("Week4 "+scheduleENCCWeek4Count);
		
	    
	    /*int hhViewResult = allHouseHolds.countHouseHolds();
		System.out.println("ThisMonth "+ hhViewResult);
		System.out.println("time end: "+System.currentTimeMillis());
		
	    int elcoViewResult = allElcos.countElcos();
		System.out.println("ThisMonth "+ elcoViewResult);
		System.out.println("time end: "+System.currentTimeMillis());
		
	    int motViewResult = allMothers.countMothers();
		System.out.println("ThisMonth "+ motViewResult);
		System.out.println("time end: "+System.currentTimeMillis());
		
		System.out.println("Before: "+System.currentTimeMillis());
		System.out.println("ThisMonth "+luceneHouseHoldService.getHouseholdCount("2016-01-01", "2017-12-31"));
		System.out.println("After: "+System.currentTimeMillis());*/
	    
		return formCountDTO;
	}

}
