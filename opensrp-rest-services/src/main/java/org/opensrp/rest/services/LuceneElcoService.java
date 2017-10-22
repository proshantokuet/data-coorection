package org.opensrp.rest.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONObject;
import org.opensrp.rest.register.dto.CommonDTO;
import org.opensrp.rest.register.dto.ElcoDTO;
import org.opensrp.rest.register.dto.HouseholdDTO;
import org.opensrp.rest.repository.LuceneElcoRepository;
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
public class LuceneElcoService {
	
	private LuceneElcoRepository luceneElcoRepository;
	private ConvertDateStringToTimestampMills convertDateStringToTimestampMills;
	@Autowired
	private DynamicQueryString dynamicQueryString;
	@Autowired
	public LuceneElcoService(LuceneElcoRepository luceneElcoRepository,ConvertDateStringToTimestampMills convertDateStringToTimestampMills){
		this.luceneElcoRepository = luceneElcoRepository;
		this.convertDateStringToTimestampMills = convertDateStringToTimestampMills;
	}
	
	/**
	 * @param queryParameters is a list of parameters.
	 * @param p page number.
	 * @param limit number of records to display in a page.
	 * @return 	Household data list.
	 * */
	
	public CommonDTO<ElcoDTO> getData(MultiValueMap<String, String> queryParameters,int p,int limit) throws JsonParseException, JsonMappingException,
	IOException {
		ObjectMapper mapper = new ObjectMapper();		
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.setVisibilityChecker(VisibilityChecker.Std.defaultInstance()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY));		
		LuceneResult luceneResult = luceneElcoRepository
				.getData(dynamicQueryString.makeDynamicQueryAsString(queryParameters), p,limit);
		List<Row> rows = luceneResult.getRows();
		 
		List<ElcoDTO> dataList = new ArrayList<ElcoDTO>();
		
		for (Row row : rows) {
			LinkedHashMap<String, Object> fields = row.getFields();			
			String jsonString = new JSONObject(fields).toString();
			dataList.add(mapper.readValue(jsonString.getBytes(),
					ElcoDTO.class));
		}
		return new CommonDTO<ElcoDTO>(dataList);
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
		return luceneElcoRepository
				.getDataCount(dynamicQueryString.makeDynamicQueryAsString(queryParameters));
	
	}
}
