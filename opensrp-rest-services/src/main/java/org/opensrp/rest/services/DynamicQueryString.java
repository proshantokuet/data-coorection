package org.opensrp.rest.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class DynamicQueryString {
	
	/**
	 * This methods makes query string for quering in couchdb using couchdb-lucene.
	 * @param queryParameters is a list of parameters.
	 * @return query string.
	 * */
	public String  makeDynamicQueryAsString(MultiValueMap<String, String> queryParameters){
		if(queryParameters.containsKey("p")){
			queryParameters.remove("p");
		}
		if(queryParameters.containsKey("limit")){
			queryParameters.remove("limit");
		}
		
		Map<String, String> preparedParameters = prepareParameters(queryParameters);
		String makeQueryString = "";
		int paramCounter = 1;
		String wildcard = "";
		for(Entry<String, String> entry : preparedParameters.entrySet())
		{	
			if(entry.getKey().equalsIgnoreCase("FWHOHFNAME")){
				if(preparedParameters.size()>paramCounter)					
					 wildcard =  "* AND "; 
				makeQueryString+=entry.getKey()+":"+entry.getValue()+ wildcard;
			}else if(entry.getKey().equalsIgnoreCase("FWWOMFNAME")) {
				if(preparedParameters.size()>paramCounter)					
					 wildcard =  "* AND "; 
				makeQueryString+=entry.getKey()+":"+entry.getValue()+ wildcard;
			}else if(entry.getKey().equalsIgnoreCase("mother_first_name")) {
				if(preparedParameters.size()>paramCounter)					
					 wildcard =  "* AND "; 
				makeQueryString+=entry.getKey()+":"+entry.getValue()+ wildcard;
			}
			else{
				makeQueryString+=entry.getKey()+":"+entry.getValue();
				if(preparedParameters.size()>paramCounter)
					makeQueryString+=" AND ";	
			}			
			
			paramCounter++;
		}
		System.out.println("makeQueryString:"+makeQueryString);
		return makeQueryString;
		
	}
	
	
	private Map<String, String> prepareParameters(MultiValueMap<String, String> queryParameters) {

		Map<String, String> parameters = new HashMap<String, String>();

		Iterator<String> it = queryParameters.keySet().iterator();

		while (it.hasNext()) {
			String theKey = (String) it.next();
			parameters.put(theKey, queryParameters.getFirst(theKey));
		}

		return parameters;

	}
	
}
