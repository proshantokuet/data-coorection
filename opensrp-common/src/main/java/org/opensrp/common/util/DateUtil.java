package org.opensrp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Period;

import static org.motechproject.util.DateUtil.inRange;

public class DateUtil {
    private static DateUtility dateUtility = new RealDate();
    static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public static DateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static DateFormat yyyyMMddTHHmmssSSSZ = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static void fakeIt(LocalDate fakeDayAsToday) {
        dateUtility = new MockDate(fakeDayAsToday);
    }

    public static LocalDate today() {
        return dateUtility.today();
    }

    public static long millis() {
        return dateUtility.millis();
    }

    public static boolean isDateWithinGivenPeriodBeforeToday(LocalDate referenceDateForSchedule, Period period) {        
    	return inRange(toTime(referenceDateForSchedule), toTime(today().minus(period)), toTime(today()));
    }
    
    public static boolean isDateWithinGivenRange(LocalDate referenceDateForSchedule,LocalDate startDate,LocalDate endDate) {
        return inRange(toTime(referenceDateForSchedule), toTime(startDate), toTime(endDate));
    }

    private static DateTime toTime(LocalDate referenceDateForSchedule) {
        return referenceDateForSchedule.toDateTime(new LocalTime(0, 0));
    }
    /*
     * @param monthIndex 0 for January then increases accordingly 
     */
    public static List<Date> startAndEndDatesOfAllWeeksOfAMonth(int monthIndex){
    	List<Date> dates = new ArrayList<Date>();
    	Calendar now = GregorianCalendar.getInstance();
    	now.set(GregorianCalendar.MONTH, monthIndex);
    	int numberOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
    	for(int i = 0; i < 5; i++){
    		
    	}
    	return null;
    }

    /**
     * Parses dates of following formats 
     * - yyyy-MM-dd
     * - yyyy-MM-dd HH:mm:ss
     * - yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * @return
     * @throws ParseException 
     */
    public static Date parseDate(String date) throws ParseException{
    	try{
    		return yyyyMMdd.parse(date);
    	}
    	catch(ParseException e){}
    	try {
			return yyyyMMddHHmmss.parse(date);
		} catch (ParseException e) {}
		
    	return yyyyMMddTHHmmssSSSZ.parse(date);
    }
    
    public static LocalDate tryParse(String value, LocalDate defaultValue) {
        try {
            return LocalDate.parse(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }
    public static Date getDateFromString(String dateString)
    {
    	Date parsed = null;
	    try {
	    	if(dateString!=null && !dateString.equals("null") && dateString.length()>0)
	    	{
	    		parsed = sdf.parse(dateString.trim());	    	
	    	}
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return parsed;
    }
    
    @SuppressWarnings("static-access")
	public static int getMontNumber(int monthIndex){
    	Calendar cal = Calendar.getInstance();    		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.MONTH, -monthIndex);
		cal.set(Calendar.DAY_OF_MONTH, 1);		
		return cal.get(cal.MONTH);
		 
    }
    
    public static int getCurrentMonthCurrentweek(){
    	Calendar c = Calendar.getInstance();
		int dayOfTheMonth = c.get(Calendar.DAY_OF_MONTH);		
		return (int) Math.floor(dayOfTheMonth/7)-1 ;
    }
   
	public static Long getStartTimeStampOfAMonth(int monthIndex){
    	Calendar cal = Calendar.getInstance();  
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.MONTH, -monthIndex);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Long startTime = 0l;
		try{
			startTime = dateFormatter.parse(dateFormatter.format(cal.getTime())).getTime();	
		}catch(Exception e){
			e.printStackTrace();
		}
		 return startTime;
    }
    public static WeekBoundariesAndTimestamps getWeekBoundariesForDashboard(){   	
    	Calendar now = GregorianCalendar.getInstance();   	
    	
    	List<String> dates = new ArrayList<String>();
    	List<Long> dateTimestamps = new ArrayList<Long>();
    	List<String> tempDates;
    	List<Long> tempDateTimestamps;
    	String tempDateStr;
    	for(int monthIndex = 0; monthIndex < 4; monthIndex++){
    		now = GregorianCalendar.getInstance();
    		now.add(GregorianCalendar.MONTH, -monthIndex);
        	now.set(GregorianCalendar.DAY_OF_MONTH, 1);
        	now.set(Calendar.HOUR_OF_DAY, 0);
        	now.set(Calendar.MINUTE, 0);
        	now.set(Calendar.SECOND, 0);
        	int numOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);        	
        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        	int numberOfWeeks = (int)Math.ceil( (double)numOfDaysInMonth/7);
        	System.out.println("current date- " + now.getTime() + " in a month with days- " + numOfDaysInMonth + " number of week- " + numberOfWeeks);
        	tempDates = new ArrayList<String>();
        	tempDateTimestamps = new ArrayList<Long>();
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
        		tempDates.add(dateFormatter.format(now.getTime()));
        		now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
        		tempDates.add(dateFormatter.format(now.getTime()));   
        		try {
        			now.set(GregorianCalendar.DAY_OF_MONTH, firstDay);
            		tempDateStr = dateFormatter.format(now.getTime());	
            		tempDates.add(tempDateStr);
            		tempDateTimestamps.add(dateFormatter.parse(tempDateStr).getTime());
					now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
					tempDateStr = dateFormatter.format(now.getTime());	
            		tempDates.add(tempDateStr);
            		tempDateTimestamps.add(dateFormatter.parse(tempDateStr).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	/*if(numberOfWeeks == 4){
        		tempDates.add("");
        		tempDates.add("");
        		tempDateTimestamps.add(0l);
        		tempDateTimestamps.add(0l);
        	}*/
        	Collections.reverse(tempDates);        	
        	dates.addAll(tempDates);
        	Collections.reverse(tempDateTimestamps);
        	dateTimestamps.addAll(tempDateTimestamps);
    	}
    	Collections.reverse(dates);
    	Collections.reverse(dateTimestamps);
    	WeekBoundariesAndTimestamps boundaries = new WeekBoundariesAndTimestamps(dates, dateTimestamps);
    	
    	return boundaries;
    }
    
    public static List<Long> getCurrentWeekBoundaries(){
		List<Long> weekBoundaries = new ArrayList<Long>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = GregorianCalendar.getInstance();
		int numOfDaysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		int todayInMonth = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int index = todayInMonth/7;
		int mod = todayInMonth % 7;
		int firstDayOfWeek, lastDayOfWeek;
		if(mod == 0){
			lastDayOfWeek = todayInMonth;
			firstDayOfWeek = lastDayOfWeek - 6;
		}
		else{
			firstDayOfWeek = index * 7 + 1;
			if(firstDayOfWeek > 28){
				lastDayOfWeek = numOfDaysInMonth;
			}
			else{
				lastDayOfWeek = firstDayOfWeek + 6;
			}					
		}
		//System.out.println(firstDayOfWeek + " -- " + lastDayOfWeek + " -- " + numOfDaysInMonth);
		cal.set(GregorianCalendar.DAY_OF_MONTH, firstDayOfWeek);
		String newDateTemp = dateFormatter.format(cal.getTime());
		Date tempDate = null;
		
		try {
			tempDate = dateFormatter.parse(newDateTemp);
			weekBoundaries.add(tempDate.getTime());
			cal = GregorianCalendar.getInstance();
			cal.set(GregorianCalendar.DAY_OF_MONTH, lastDayOfWeek);
			newDateTemp = dateFormatter.format(cal.getTime());
			tempDate = dateFormatter.parse(newDateTemp);
			weekBoundaries.add(tempDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return weekBoundaries;
	}
	
	public static Long getTimestampToday(){		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        
        Date day = null;        
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        String today = dateFormat.format(now.getTime());
        try {
            day = dateFormat.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day.getTime();
	}
	
	public static Long getTimestamp(String day){		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        Date date = null;        
        //String today = dateFormat.format(day);
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
	}
	
	public static List<Long> getMonthBoundaries(){
		List<Long> monthBoundaries = new ArrayList<Long>();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		int numOfDaysInMonth = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		String newDateTemp = dateFormatter.format(cal.getTime());
		Date tempDate = null;
		
		try {
			tempDate = dateFormatter.parse(newDateTemp);
			monthBoundaries.add(tempDate.getTime());
			cal.set(GregorianCalendar.DAY_OF_MONTH, numOfDaysInMonth);
			newDateTemp = dateFormatter.format(cal.getTime());
			tempDate = dateFormatter.parse(newDateTemp);
			monthBoundaries.add(tempDate.getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return monthBoundaries;
	}
	
	public static String getTodayAsString(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date today = Calendar.getInstance().getTime();
		return format.format(today).toString();
	}
	
    public static List<Long> getWeekBoundariesForDashboardAsTimestamp(){   	
    	Calendar now = GregorianCalendar.getInstance();   	
    	
    	List<Long> dates = new ArrayList<Long>(); 
    	List<Long> tempDates;
    	Date tempDate;
    	String tempDateStr;
    	for(int monthIndex = 0; monthIndex < 4; monthIndex++){
    		now = GregorianCalendar.getInstance();
    		now.add(GregorianCalendar.MONTH, -monthIndex);
        	now.set(GregorianCalendar.DAY_OF_MONTH, 1);        	
        	int numOfDaysInMonth = now.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);        	
        	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        	int numberOfWeeks = (int)Math.ceil( (double)numOfDaysInMonth/7);
        	System.out.println("current date- " + now.getTime() + " in a month with days- " + numOfDaysInMonth + " number of week- " + numberOfWeeks);
        	tempDates = new ArrayList<Long>();
        	for(int i = 0; i< numberOfWeeks; i++){
        		int firstDay = i * 7 + 1;
        		int lastDay;
        		if(firstDay + 6 <= numOfDaysInMonth){
        			lastDay = firstDay + 6;
        		}
        		else{
        			lastDay = firstDay + numOfDaysInMonth % 7 -1;
        		}
        		
        		try {
        			now.set(GregorianCalendar.DAY_OF_MONTH, firstDay);
            		tempDateStr = dateFormatter.format(now.getTime());				
            		tempDates.add(dateFormatter.parse(tempDateStr).getTime());
					now.set(GregorianCalendar.DAY_OF_MONTH, lastDay);
					tempDateStr = dateFormatter.format(now.getTime());				
					tempDates.add(dateFormatter.parse(tempDateStr).getTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(numberOfWeeks == 4){
        		tempDates.add(0l);
        		tempDates.add(0l);
        	}
        	Collections.reverse(tempDates);
        	dates.addAll(tempDates);
    	}
    	
    	return dates;
    }
    public synchronized static boolean ifDateInsideAWeek(long timestamp, long lowerLimit, long upperLimit){
    	if(timestamp >= lowerLimit && timestamp <= upperLimit){
    		return true;
    	}
    	return false;
    }
    
    // weekBoundaries should have a size of even number
    public synchronized static int binarySearch(long timestamp, List<Long> weekBoundaries){
    	int max = weekBoundaries.size()/2 - 1, min = 0, mid = (max + min)/2;
    	while(max >= min){
    		
    		if(ifDateInsideAWeek(timestamp, weekBoundaries.get(2*mid), weekBoundaries.get(2*mid + 1))){
        		return mid;
        	}
    		else if(timestamp > weekBoundaries.get(2*mid + 1)){
    			min = mid + 1;
    			//max = mid - 1;
    		}
    		else if(timestamp < weekBoundaries.get(2*mid )){
    			max = mid -1;
    			//min = mid + 1;
    		}
    		mid = (max + min) / 2;
    	}
    	
    	/*for(int i = 0; i < weekBoundaries.size(); i+=2){
    		if(timestamp >= weekBoundaries.get(i) && timestamp <= weekBoundaries.get(i+1)){
    			return 1;
    		}
    	}*/
    	System.out.println("could not find index for - " + timestamp);
    	return -1;
    }
    
    

}

interface DateUtility {
    LocalDate today();

    long millis();
}

class RealDate implements DateUtility {
    @Override
    public LocalDate today() {
        return LocalDate.now();
    }

    @Override
    public long millis() {
        return DateTime.now().getMillis();
    }
}

class MockDate implements DateUtility {
    private DateTime fakeDay;

    MockDate(LocalDate fakeDay) {
        this.fakeDay = fakeDay.toDateTimeAtStartOfDay();
    }

    @Override
    public LocalDate today() {
        return fakeDay.toLocalDate();
    }

    @Override
    public long millis() {
        return fakeDay.getMillis();
    }
}

