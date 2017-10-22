package org.opensrp.rest.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.opensrp.rest.register.dto.CommonDTO;
import org.opensrp.rest.register.dto.HouseholdDTO;
import org.opensrp.rest.repository.LuceneHouseHoldRepository;
import org.opensrp.rest.util.ConvertDateStringToTimestampMills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.github.ldriscoll.ektorplucene.LuceneResult;
import com.github.ldriscoll.ektorplucene.LuceneResult.Row;

@Service
public class LuceneHouseHoldService {

	private LuceneHouseHoldRepository luceneHouseHoldRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	private DynamicQueryString dynamicQueryString;
	@Autowired
	public LuceneHouseHoldService(
			LuceneHouseHoldRepository luceneHouseHoldRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills) {
		this.luceneHouseHoldRepository = luceneHouseHoldRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}

	
	
	/**
	 * This method return Household count as today, this month or this week
	 * @param start this may be start date of a month or week
	 * @param end   this may be end date of a month or week
	 * */
	public int getHouseholdCount(String start,String end){
		if(start.equalsIgnoreCase("")){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = Calendar.getInstance().getTime();    	
		String makeQueryString ="type:Household" + " AND "+ "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
    	LuceneResult result = luceneHouseHoldRepository.findDocsByProvider(makeQueryString);
		return result.getRows().size();
		}else{
			String makeQueryString ="type:Household" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneHouseHoldRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}
		
		
	}
	
	/**
	 * @param queryParameters is a list of parameters.
	 * @param p page number.
	 * @param limit number of records to display in a page.
	 * @return 	Household data list.
	 * */
	
	public CommonDTO<HouseholdDTO> getData(MultiValueMap<String, String> queryParameters,int p,int limit) throws JsonParseException, JsonMappingException,
	IOException {
		ObjectMapper mapper = new ObjectMapper();		
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));		
		LuceneResult luceneResult = luceneHouseHoldRepository
				.getData(dynamicQueryString.makeDynamicQueryAsString(queryParameters), p,limit);
		List<Row> rows = luceneResult.getRows();
		 
		List<HouseholdDTO> dataList = new ArrayList<HouseholdDTO>();
		
		for (Row row : rows) {
			LinkedHashMap<String, Object> fields = row.getFields();			
			String jsonString = new JSONObject(fields).toString();
			dataList.add(mapper.readValue(jsonString.getBytes(),
					HouseholdDTO.class));
		}
		return new CommonDTO<HouseholdDTO>(dataList);
	}
	
	/**
	 * @param queryParameters is a list of parameters.	 
	 * @return 	Household data count.
	 * */
	public int getDataCount(MultiValueMap<String, String> queryParameters) throws JsonParseException, JsonMappingException,
	IOException {
		ObjectMapper mapper = new ObjectMapper();		
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));		
		return luceneHouseHoldRepository
				.getDataCount(dynamicQueryString.makeDynamicQueryAsString(queryParameters));
	
	}
	
	
}
