package org.opensrp.connector.openmrs.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpVersion;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opensrp.api.domain.Address;
import org.opensrp.api.domain.BaseEntity;
import org.opensrp.api.domain.Client;
import org.opensrp.connector.HttpUtil;
import org.opensrp.connector.MultipartUtility;
import org.opensrp.domain.Multimedia;
import org.opensrp.repository.MultimediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;

@Service
public class PatientService extends OpenmrsService{
	
	private OpenmrsLocationService locationService;
	private MultimediaRepository multimediaRepository;
   //TODO include everything for patient registration. i.e. person, person name, patient identifier
	// include get for patient on different params like name, identifier, location, uuid, attribute,etc
	//person methods should be separate
	private static final String PERSON_URL = "ws/rest/v1/person";
	private static final String PATIENT_URL = "ws/rest/v1/patient";
	private static final String PATIENT_IMAGE_URL = "ws/rest/v1/patientimage/uploadimage";
	private static final String PATIENT_IDENTIFIER_URL = "identifier";
	private static final String PERSON_ATTRIBUTE_URL = "attribute";
	private static final String PERSON_ATTRIBUTE_TYPE_URL = "ws/rest/v1/personattributetype";
	private static final String PATIENT_IDENTIFIER_TYPE_URL = "ws/rest/v1/patientidentifiertype";
	
	private static final String OPENSRP_IDENTIFIER_TYPE = "OpenSRP Thrive UID";
	
	@Autowired
    public PatientService(OpenmrsLocationService actionService) {
        this.locationService = locationService;
    }
	public PatientService() { }

    public PatientService(String openmrsUrl, String user, String password) {
    	super(openmrsUrl, user, password);
    }
	
    public JSONObject getPatientByIdentifier(String identifier) throws JSONException
    {
    	JSONArray p = new JSONObject(HttpUtil.get(getURL()
    			+"/"+PATIENT_URL, "v=full&identifier="+identifier, OPENMRS_USER, OPENMRS_PWD).body())
    			.getJSONArray("results");

    	return p.length()>0?p.getJSONObject(0):null;
    }
    
    public JSONObject getIdentifierType(String identifierType) throws JSONException
    {
    	// we have to use this ugly approach because identifier not found throws exception and 
    	// its hard to find whether it was network error or object not found or server error
    	JSONArray res = new JSONObject(HttpUtil.get(getURL()+"/"+PATIENT_IDENTIFIER_TYPE_URL, "v=full", 
    			OPENMRS_USER, OPENMRS_PWD).body()).getJSONArray("results");
    	for (int i = 0; i < res.length(); i++) {
			if(res.getJSONObject(i).getString("display").equalsIgnoreCase(identifierType)){
				return res.getJSONObject(i);
			}
		}
    	return null;
    }
	
    public JSONObject createIdentifierType(String name, String description) throws JSONException{
		JSONObject o = convertIdentifierToOpenmrsJson(name, description);
		return new JSONObject(HttpUtil.post(getURL()+"/"+PATIENT_IDENTIFIER_TYPE_URL, "", o.toString(), OPENMRS_USER, OPENMRS_PWD).body());
	}
    
	public JSONObject convertIdentifierToOpenmrsJson(String name, String description) throws JSONException {
		JSONObject a = new JSONObject();
		a.put("name", name);
		a.put("description", description);
		return a;
	}
	
    public JSONObject getPersonAttributeType(String attributeName) throws JSONException
    {
    	JSONArray p = new JSONObject(HttpUtil.get(getURL()+"/"+PERSON_ATTRIBUTE_TYPE_URL, 
    			"v=full&q="+attributeName, OPENMRS_USER, OPENMRS_PWD).body()).getJSONArray("results");
    	return p.length()>0?p.getJSONObject(0):null;
    }
	
	public JSONObject createPerson(BaseEntity be) throws JSONException{
		JSONObject per = convertBaseEntityToOpenmrsJson(be);
		System.out.println("Going to create person: " + per.toString());
		return new JSONObject(HttpUtil.post(getURL()+"/"+PERSON_URL, "", per.toString(), OPENMRS_USER, OPENMRS_PWD).body());
	}
	
	public JSONObject convertBaseEntityToOpenmrsJson(BaseEntity be) throws JSONException {
		JSONObject per = new JSONObject();
		per.put("gender", be.getGender());
		if(be.getBirthdate() != null){
			per.put("birthdate", OPENMRS_DATE.format(be.getBirthdate()));
		}
		else{
			per.put("birthdate", OPENMRS_DATE.format("1900-01-01"));
		}
		per.put("birthdateEstimated", be.getBirthdateApprox());
		if(be.getDeathdate() != null){
			per.put("deathDate", OPENMRS_DATE.format(be.getDeathdate()));
		}
		
		String fn = be.getFirstName();
		String mn = be.getMiddleName()==null?"":be.getMiddleName();
		String ln = be.getLastName()==null?".":be.getLastName();
		per.put("names", new JSONArray("[{\"givenName\":\""+fn+"\",\"middleName\":\""+mn+"\", \"familyName\":\""+ln+"\"}]"));
		per.put("attributes", convertAttributesToOpenmrsJson(be.getAttributes()));
		per.put("addresses", convertAddressesToOpenmrsJson(be.getAddresses()));
		return per;
	}
	
	public JSONArray convertAttributesToOpenmrsJson(Map<String, Object> attributes) throws JSONException {
		if(CollectionUtils.isEmpty(attributes)){
			return null;
		}
		JSONArray attrs = new JSONArray();
		for (Entry<String, Object> at : attributes.entrySet()) {
			JSONObject a = new JSONObject();			
				a.put("attributeType", getPersonAttributeType(at.getKey()).getString("uuid"));
				a.put("value", at.getValue());
				attrs.put(a);
		}
		
		return attrs;
	}
	
	public JSONArray convertAddressesToOpenmrsJson(List<Address> adl) throws JSONException{
		if(CollectionUtils.isEmpty(adl)){
			return null;
		}

		//System.out.println("Addresses : " + org.apache.commons.lang.StringUtils.join(adl, ","));
		
		JSONArray jaar = new JSONArray();
		for (Address ad : adl) {
			JSONObject jao = new JSONObject();
			if(ad.getAddressFields() != null){
				jao.put("address1", ad.getAddressFieldMatchingRegex("(?i)(ADDRESS1|HOUSE_NUMBER|HOUSE|HOUSE_NO|UNIT|UNIT_NUMBER|UNIT_NO)"));
				jao.put("address2", ad.getAddressFieldMatchingRegex("(?i)(ADDRESS2|STREET|STREET_NUMBER|STREET_NO|LANE)"));
				jao.put("address3", ad.getAddressFieldMatchingRegex("(?i)(ADDRESS3|SECTOR|AREA)"));
				jao.put("address4", ad.getAddressFieldMatchingRegex("(?i)(ADDRESS4|SUB_DISTRICT|MUNICIPALITY|TOWN|LOCALITY|REGION)"));
				jao.put("address5", ad.getAddressFieldMatchingRegex("(?i)(ADDRESS5)"));
				jao.put("countyDistrict", ad.getAddressFieldMatchingRegex("(?i)(county_district|countyDistrict|COUNTY|DISTRICT)"));
				jao.put("cityVillage", ad.getAddressFieldMatchingRegex("(?i)(cityVillage|city_village|CITY|VILLAGE)"));

				/*String ad5V = "";
				for (Entry<String, String> af : ad.getAddressFields().entrySet()) {
					if(!af.getKey().matches("(?i)(ADDRESS1|HOUSE_NUMBER|HOUSE|HOUSE_NO|UNIT|UNIT_NUMBER|UNIT_NO|"
							+ "ADDRESS2|STREET|STREET_NUMBER|STREET_NO|LANE|"
							+ "ADDRESS3|SECTOR|AREA|"
							+ "ADDRESS4|SUB_DISTRICT|MUNICIPALITY|TOWN|LOCALITY|REGION|"
							+ "countyDistrict|county_district|COUNTY|DISTRICT|"
							+ "cityVillage|city_village|CITY|VILLAGE)")){
						ad5V += af.getKey()+":"+af.getValue()+";";
					}
				}
				if(!StringUtils.isEmptyOrWhitespaceOnly(ad5V)){
					jao.put("address5", ad5V);
				}*/
				
			}
			jao.put("address6", ad.getAddressType());
			jao.put("stateProvince", ad.getState());
			jao.put("country", ad.getCountry());
			jao.put("postalCode", ad.getPostalCode());
			jao.put("latitude", ad.getLatitude());
			jao.put("longitude", ad.getLongitute());
			if(ad.getStartDate() != null){
				jao.put("startDate", OPENMRS_DATE.format(ad.getStartDate()));
			}
			if(ad.getEndDate() != null){
				jao.put("endDate", OPENMRS_DATE.format(ad.getEndDate()));
			}
			
			jaar.put(jao);
		}
		
		return jaar;
	}
	
	public JSONObject createPatient(Client c) throws JSONException
	{
		JSONObject patientExist = null;
		patientExist = getPatientByIdentifier(c.getBaseEntity().getId());
		if (patientExist != null){
			System.out.println("Person or Patient already existis inside openmrs id:" + c.getBaseEntity().getId());
			return patientExist;
		}
		
		JSONObject p = new JSONObject();
		p.put("person", createPerson(c.getBaseEntity()).getString("uuid"));
		JSONArray ids = new JSONArray();
		if (c.getIdentifiers() != null) {
			for (Entry<String, String> id : c.getIdentifiers().entrySet()) {
				patientExist = getPatientByIdentifier(id.getValue());
				if (patientExist != null){
					System.out.println("Person or Patient already existis inside openmrs with identifier:" + id.getValue());
					return patientExist;
				}
				JSONObject jio = new JSONObject();
				JSONObject idobj = getIdentifierType(id.getKey());
				if (idobj == null) {
					idobj = createIdentifierType(id.getKey(), id.getKey()
							+ " - FOR THRIVE OPENSRP");
				}
				jio.put("identifierType", idobj.getString("uuid"));				
				jio.put("identifier", id.getValue());
				Object cloc = c.getBaseEntity().getAttribute("Location");
				jio.put("location", cloc == null ? "Unknown Location" : cloc);
				// jio.put("preferred", true);
				ids.put(jio);
			}
		}
		
		JSONObject jio = new JSONObject();
		JSONObject ido = getIdentifierType(OPENSRP_IDENTIFIER_TYPE);
		if(ido == null){
			ido = createIdentifierType(OPENSRP_IDENTIFIER_TYPE, OPENSRP_IDENTIFIER_TYPE+" - FOR THRIVE OPENSRP");
		}
		jio.put("identifierType", ido.getString("uuid"));
		jio.put("identifier", c.getBaseEntityId());
		Object cloc = c.getBaseEntity().getAttribute("Location");
		jio.put("location", cloc == null?"Unknown Location":cloc);
		jio.put("preferred", true);
		
		ids.put(jio);
		
		p.put("identifiers", ids);
		System.out.println("Going to create patient: " + p.toString());
		return new JSONObject(HttpUtil.post(getURL()+"/"+PATIENT_URL, "", p.toString(), OPENMRS_USER, OPENMRS_PWD).body());
	}
	
	public void patientImageUpload(Multimedia multimedia) throws IOException
	{
		
	     //String requestURL =  "http://46.101.51.199:8080/openmrs/ws/rest/v1/patientimage/uploadimage";
		
		 try {
			    File convFile = new File("/opt"+multimedia.getFilePath());
	            MultipartUtility multipart = new MultipartUtility(getURL()+"/"+PATIENT_IMAGE_URL, OPENMRS_USER, OPENMRS_PWD);
	             
	           // multipart.addHeaderField("User-Agent", "CodeJava");
	           // multipart.addHeaderField("Test-Header", "Header-Value");
	             
	            multipart.addFormField("patientidentifier", multimedia.getCaseId());
	            multipart.addFormField("category", multimedia.getFileCategory());
	             
	            multipart.addFilePart("file", convFile);
	 
	            
	            List<String> response = multipart.finish();
	             
	            System.out.println("SERVER REPLIED:");
	             
	            for (String line : response) {
	                System.out.println(line);
	            }
	        } catch (IOException ex) {
	            System.err.println(ex);
	        }
	}
}
