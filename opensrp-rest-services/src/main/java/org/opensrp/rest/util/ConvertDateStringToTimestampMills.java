/**
 * @author proshanto
 * */
package org.opensrp.rest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class ConvertDateStringToTimestampMills {
	
	/***
	 * This Method return converted  value of a day as long format
	 * @param date converted date value as "yyyy-MM-dd" format
	 * */
	public long convertDateToTimestampMills(String date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   	
    	Date day= null;
		try {
			day = dateFormat.parse(date);			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return day.getTime();
		
	}

}
