/**
 * @author Asifur
 */
package org.opensrp.rest.services;

import org.opensrp.rest.repository.LuceneScheduleRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ldriscoll.ektorplucene.LuceneResult;

@Service
public class LuceneScheduleService {

	private LuceneScheduleRepository luceneScheduleRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneScheduleService(LuceneScheduleRepository luceneScheduleRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneScheduleRepository = luceneScheduleRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}
	/**
	 * This method return Schedule count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getScheduleCount(String start,String end,String anmId,String scheduleName){		
		if(start.equalsIgnoreCase("")){  	
			String makeQueryString ="type:ScheduleLog" + " AND " + "anmIdentifier:" + anmId + " AND " + "scheduleName:" + scheduleName;
	    	LuceneResult result = luceneScheduleRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="type:ScheduleLog" + " AND " + "anmIdentifier:" + anmId + " AND " + "scheduleName:" + scheduleName + " AND " + "timeStamp:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneScheduleRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}	
	}
	
	public int getSchedulCount(String start,String end,String anmId,String scheduleName){	
		if(scheduleName.equalsIgnoreCase("ELCO PSRF") && start!= null && !start.isEmpty() && !start.equalsIgnoreCase("") && end!= null && !end.isEmpty() && !end.equalsIgnoreCase("")){
			LuceneResult result = luceneScheduleRepository.getByCrite(convertDateStringToTimestampMills.convertDateToTimestampMills(start),convertDateStringToTimestampMills.convertDateToTimestampMills(end),anmId,scheduleName);
			return result.getRows().size();	
		}
		else if (scheduleName.equalsIgnoreCase("ELCO PSRF")){
			LuceneResult result = luceneScheduleRepository.getByCrite(0,0,anmId,scheduleName);
			return result.getRows().size();	
		}
		else if(start!= null && !start.isEmpty() && !start.equalsIgnoreCase("") && end!= null && !end.isEmpty() && !end.equalsIgnoreCase("")){
			int count = luceneScheduleRepository.getByCriteria(convertDateStringToTimestampMills.convertDateToTimestampMills(start),convertDateStringToTimestampMills.convertDateToTimestampMills(end),anmId,scheduleName);
			return count;	
		}
		else {
			int count = luceneScheduleRepository.getByCriteria(0,0,anmId,scheduleName);
			return count;	
		}
	}

}
