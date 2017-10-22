package org.opensrp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import org.opensrp.common.util.IntegerUtil;

import static org.junit.Assert.assertEquals;

public class DateUtilTest {
    @Test
    public void printSomething() throws Exception {
    	Calendar now = GregorianCalendar.getInstance();   	
    	
    	List<String> dates = new ArrayList<String>(); //null;
    	for(int monthIndex = 0; monthIndex < 4; monthIndex++){
    		now = GregorianCalendar.getInstance();
    		now.add(GregorianCalendar.MONTH, -monthIndex);
        	//now.set(GregorianCalendar.MONTH, 1);
        	now.set(GregorianCalendar.DAY_OF_MONTH, 1);        	
        	int numOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);        	
        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        	//dates = new ArrayList<String>();
        	int numberOfWeeks = (int)Math.ceil( (double)numOfDaysInMonth/7);
        	System.out.println("current date- " + now.getTime() + " in a month with days- " + numOfDaysInMonth + " number of week- " + numberOfWeeks);
        	for(int i = 0; i< numberOfWeeks; i++){
        		int firstDay = i * 7 + 1;
        		int lastDay;
        		if(firstDay + 6 <= numOfDaysInMonth){
        			lastDay = firstDay + 6;
        		}
        		else{
        			lastDay = firstDay + numOfDaysInMonth % 7 -1;
        		}
        		now.set(GregorianCalendar.DAY_OF_MONTH, firstDay);
        		dates.add(dateFormatter.format(now.getTime()));
        		now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
        		dates.add(dateFormatter.format(now.getTime()));    	
        		//System.out.println(firstDay + " -- " + lastDay);
        	}
        	if(numberOfWeeks == 4){
        		dates.add("");
        		dates.add("");
        	}
    	}
    	System.out.println("number of weeks boundaries(double of numbe of weeks)- " + dates.size());
    	for(int i =0; i<dates.size()/2; i++){
    		//System.out.println(dates.get(i*2) + " -- " + dates.get(i*2 + 1));
    		//System.out.println((i*2) + " -- " + (i*2 + 1));
    	}
    }
    
    @Test
    public void execPrintSomething() throws Exception {
    	/*System.out.println(Math.ceil( (double)31/7));
    	System.out.println(Math.ceil( (double)30/7));
    	System.out.println(Math.ceil( (double)29/7));
    	System.out.println(Math.ceil( (double)28/7));*/
    	//printSomething();
    	/*Calendar cal = GregorianCalendar.getInstance();
    	cal.set(GregorianCalendar.DAY_OF_MONTH, 30);
    	for(int i =0; i<4; i++){
    		cal.add(GregorianCalendar.MONTH, -i);
    		System.out.println(cal.getTime() + " -for i = " +i);
    	}*/
    	/*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	System.out.println(dateFormat.format("2016-05-01"));*/
    	
    }
    
    /*@Test
    public void starAndEndOfTheDay(){
    	Calendar cal = GregorianCalendar.getInstance();
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	cal.set(Calendar.MILLISECOND, 0);
    	System.out.println(cal.getTime());
    	System.out.println(cal.getTime().getTime() + " -start of the day");
    	cal.set(Calendar.HOUR_OF_DAY, 23);
    	cal.set(Calendar.MINUTE, 59);
    	cal.set(Calendar.SECOND, 59);
    	cal.set(Calendar.MILLISECOND, 999);
    	System.out.println(cal.getTime().getTime() + " -end of the day");
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");   	
    	Date day= null;
		try {
			day = dateFormat.parse("2016-05-19");			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(day.getTime());
		Date anotherDay = new Date();
		System.out.println(anotherDay.getTime());
    }*/
}
