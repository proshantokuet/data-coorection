package org.opensrp.connector.openmrs.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.motechproject.scheduletracking.api.domain.Enrollment;
import org.opensrp.connector.HttpUtil;
import org.opensrp.scheduler.Action;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public abstract class OpenmrsService {
	@Value("#{opensrp['openmrs.url']}")
	protected String OPENMRS_BASE_URL;
	
	@Value("#{opensrp['openmrs.username']}")
	protected String OPENMRS_USER;
	
	@Value("#{opensrp['openmrs.password']}")
	protected String OPENMRS_PWD;
	
	public static final SimpleDateFormat OPENMRS_DATE = new SimpleDateFormat("yyyy-MM-dd");
	
	//public static final SimpleDateFormat OPENMRS_DATETime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public OpenmrsService() {	}
	
	public OpenmrsService(String openmrsUrl, String user, String password) {
    	OPENMRS_BASE_URL = openmrsUrl;
    	OPENMRS_USER = user;
    	OPENMRS_PWD = password;
	}

	/**
	 * returns url after trimming ending slash
	 * @return
	 */
	public String getURL() {
		return HttpUtil.removeEndingSlash(OPENMRS_BASE_URL);
	}
	
public static void main(String[] args) {
	System.out.println(OPENMRS_DATE.format(new Date()));
}


	
}