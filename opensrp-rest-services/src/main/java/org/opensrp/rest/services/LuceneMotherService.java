/**
 * @author proshanto
 * */
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
import org.opensrp.rest.register.dto.MotherDTO;
import org.opensrp.rest.repository.LuceneMotherRepository;
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
public class LuceneMotherService {
	private LuceneMotherRepository luceneMotherRepository;
	@Autowired
	private DynamicQueryString dynamicQueryString;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	public LuceneMotherService(LuceneMotherRepository luceneMotherRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneMotherRepository = luceneMotherRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}

	/**
	 * @param queryParameters is a list of parameters.
	 * @param p page number.
	 * @param limit number of records to display in a page.
	 * @return 	Household data list.
	 * */
	
	public CommonDTO<MotherDTO> getData(MultiValueMap<String, String> queryParameters,int p,int limit) throws JsonParseException, JsonMappingException,
	IOException {
		ObjectMapper mapper = new ObjectMapper();		
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));		
		LuceneResult luceneResult = luceneMotherRepository
				.getData(dynamicQueryString.makeDynamicQueryAsString(queryParameters), p,limit);
		List<Row> rows = luceneResult.getRows();
		 
		List<MotherDTO> dataList = new ArrayList<MotherDTO>();
		
		for (Row row : rows) {
			LinkedHashMap<String, Object> fields = row.getFields();			
			String jsonString = new JSONObject(fields).toString();
			dataList.add(mapper.readValue(jsonString.getBytes(),
					MotherDTO.class));
		}
		return new CommonDTO<MotherDTO>(dataList);
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
		return luceneMotherRepository
				.getDataCount(dynamicQueryString.makeDynamicQueryAsString(queryParameters));
	
	}
	public int getMotherCount(String start,String end){
		if(start.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date today = Calendar.getInstance().getTime();    	
			String makeQueryString ="isClosed:false" + " AND " + "type:Mother" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
	    	LuceneResult result = luceneMotherRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="isClosed:false" + " AND " + "type:Mother" + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneMotherRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}		
	}
	
	public int getMotherCount(String start,String end,String provider){
		if(start.equalsIgnoreCase("")){
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    	Date today = Calendar.getInstance().getTime();    	
			String makeQueryString ="isClosed:false" + " AND " + "type:Mother" + " AND " + "PROVIDERID:" + provider + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(dateFormat.format(today))+"]" ;
	    	LuceneResult result = luceneMotherRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}else{
			String makeQueryString ="isClosed:false" + " AND " + "type:Mother" + " AND " + "PROVIDERID:" + provider + " AND " + "SUBMISSIONDATE:["+convertDateStringToTimestampMills.convertDateToTimestampMills(start)+" TO "+convertDateStringToTimestampMills.convertDateToTimestampMills(end)+"]" ;
	    	LuceneResult result = luceneMotherRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();
		}		
	}
	
	public List<Integer> getBirth(String anmId){
		List<Integer> result = luceneMotherRepository.getByCriteria(anmId);
		return result;		
	}
	
	public int getMotherCount(String anmId){
			String makeQueryString ="isClosed:false" + " AND " + "type:Mother" + " AND " + "PROVIDERID:" + anmId ;
	    	LuceneResult result = luceneMotherRepository.findDocsByProvider(makeQueryString);
			return result.getRows().size();		
	}

}
