/**
 * @author Asifur
 */
package org.opensrp.rest.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.opensrp.rest.repository.LuceneFormRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ldriscoll.ektorplucene.LuceneResult;

@Service
public class LuceneFormService {
	private LuceneFormRepository luceneFormRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneFormService(LuceneFormRepository luceneFormRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneFormRepository = luceneFormRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}
	/**
	 * This method return Form count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getFormCount(String start,String end,String anmId,String formName){
	    
		/*if(start.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date today = Calendar.getInstance().getTime();    	
			String makeQueryString ="type:FormSubmission" + " AND " + "anmId:" + anmId + " AND " + "formName:" + formName + " AND " + "serverVersion:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
	    	LuceneResult result = luceneFormRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="type:FormSubmission" + " AND " + "anmId:" + anmId + " AND " + "formName:" + formName + " AND " + "serverVersion:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneFormRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}*/	
		
		if(start.equalsIgnoreCase("")){  	
			String makeQueryString ="type:FormSubmission" + " AND " + "anmId:" + anmId + " AND " + "formName:" + formName;
	    	LuceneResult result = luceneFormRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="type:FormSubmission" + " AND " + "anmId:" + anmId + " AND " + "formName:" + formName + " AND " + "serverVersion:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneFormRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}	
	}
}
