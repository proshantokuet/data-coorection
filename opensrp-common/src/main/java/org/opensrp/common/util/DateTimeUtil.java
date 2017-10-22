package org.opensrp.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDateTime;

public class DateTimeUtil {
    private static DateTimeUtility dateUtility = new RealDateTime();

    public static void fakeIt(LocalDateTime fakeDateTime) {
        dateUtility = new MockDateTime(fakeDateTime);
    }

    public static LocalDateTime now() {
        return dateUtility.now();
    }
    
    public static Long getTimestampOfADate(String day){		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");        
        Date date = null;        
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
       
        try {
            date = dateFormat.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
	}
}

interface DateTimeUtility {
    LocalDateTime now();
}

class RealDateTime implements DateTimeUtility {
    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}

class MockDateTime implements DateTimeUtility {
    private LocalDateTime fakeDateTime;

    MockDateTime(LocalDateTime fakeDateTime) {
        this.fakeDateTime = fakeDateTime;
    }

    @Override
    public LocalDateTime now() {
        return fakeDateTime;
    }
}