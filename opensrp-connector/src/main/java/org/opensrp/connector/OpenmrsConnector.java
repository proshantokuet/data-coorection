package org.opensrp.connector;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.math.NumberUtils;
import org.opensrp.api.domain.Address;
import org.opensrp.api.domain.BaseEntity;
import org.opensrp.api.domain.Client;
import org.opensrp.api.domain.Event;
import org.opensrp.api.domain.Obs;
import org.opensrp.common.util.DateUtil;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.Encounter;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.OpenmrsEntity;
import org.opensrp.connector.openmrs.constants.OpenmrsConstants.Person;
import org.opensrp.connector.openmrs.service.OpenmrsService;
import org.opensrp.form.domain.FormField;
import org.opensrp.form.domain.FormSubmission;
import org.opensrp.form.domain.SubFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StringUtils;

@Service
public class OpenmrsConnector {

	private FormAttributeMapper formAttributeMapper;
		
	@Autowired
	public OpenmrsConnector(FormAttributeMapper formAttributeMapper) {
		this.formAttributeMapper = formAttributeMapper;
	}
	
	/**
	 * Whether form submission is an openmrs form. The xlsform made openmrs form by mapping to an encounter_type in settings in xlsform.
	 * @param fs
	 * @return
	 */
	public boolean isOpenmrsForm(FormSubmission fs) {
		List<String> a = new ArrayList<>();
		a .add("encounter_type");
		String eventType = formAttributeMapper.getUniqueAttributeValue(a , fs).get("encounter_type");
		return !StringUtils.isEmptyOrWhitespaceOnly(eventType);
	}
	
	/** 
	 * Extract Event from given form submission
	 * @param fs
	 * @return
	 * @throws ParseException
	 */
	public Event getEventFromFormSubmission(FormSubmission fs) throws ParseException {
		String encounterDateField = getFieldName(Encounter.encounter_date, fs);
		String encounterLocation = getFieldName(Encounter.location_id, fs);
		
		//TODO
		String encounterStart = getFieldName(Encounter.encounter_start, fs);
		String encounterEnd = getFieldName(Encounter.encounter_end, fs);
		
		List<String> a = new ArrayList<>();
		a .add("encounter_type");
		String eventType = formAttributeMapper.getUniqueAttributeValue(a , fs).get("encounter_type");
		Event e = new Event()
			.withBaseEntityId(fs.getField("relationalid")==null?fs.entityId():fs.getField("relationalid"))
			.withEventDate(OpenmrsService.OPENMRS_DATE.parse(fs.getField(encounterDateField)))
			.withEventType(eventType)
			.withLocationId(fs.getField(encounterLocation))
			.withProviderId(fs.anmId())
			.withFormSubmissionId(fs.getInstanceId());
			;
		
		for (FormField fl : fs.instance().form().fields()) {
			Map<String, String> att = formAttributeMapper.getAttributesForField(fl.name(), fs);
			if(!StringUtils.isEmptyOrWhitespaceOnly(fl.value()) && att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("concept")){
            	if (isMultiSelect(fl.value())){
            		String[] fieldValues = fl.value().split(" ");
            		String val = "";
            		for (String fieldVal : fieldValues){
            			val = formAttributeMapper.getInstanceAttributesForFormFieldAndValue(fl.name(), fieldVal, null, fs);                       
                        e.addObs(new Obs("concept", att.get("openmrs_entity_id"), 
                                att.get("openmrs_entity_parent"), StringUtils.isEmptyOrWhitespaceOnly(val)?fieldVal:val, null, fl.name()));
            		}          		         		
            	}
            	else{
    				String val = formAttributeMapper.getInstanceAttributesForFormFieldAndValue(fl.name(), fl.value(), null, fs);				
    				e.addObs(new Obs("concept", att.get("openmrs_entity_id"), 
    						att.get("openmrs_entity_parent"), StringUtils.isEmptyOrWhitespaceOnly(val)?fl.value():val, null, fl.name()));  				   				
            	}				
			}
		}
		return e;
	}
	
	/**
	 * Extract Event for given subform with given data mapped to specified Encounter Type.
	 * @param fs
	 * @param subform
	 * @param eventType
	 * @param subformInstance
	 * @return
	 * @throws ParseException
	 */
	private Event getEventForSubform(FormSubmission fs, String subform, String eventType, Map<String, String> subformInstance) throws ParseException {
		String encounterDateField = getFieldName(Encounter.encounter_date, fs);
		String encounterLocation = getFieldName(Encounter.location_id, fs);
		
		//TODO
		String encounterStart = getFieldName(Encounter.encounter_start, fs);
		String encounterEnd = getFieldName(Encounter.encounter_end, fs);
		
		List<String> a = new ArrayList<>();
		a .add("encounter_type");
		Event e = new Event()
			.withBaseEntityId(subformInstance.get("id"))
			.withEventDate(OpenmrsService.OPENMRS_DATE.parse(fs.getField(encounterDateField)))
			.withEventType(eventType)
			.withLocationId(fs.getField(encounterLocation))
			.withProviderId(fs.anmId())
			;
		
		  for (Entry<String, String> fl : subformInstance.entrySet()) {
	            Map<String, String> att = formAttributeMapper.getAttributesForSubform(subform, fl.getKey(), fs);
	            if(!StringUtils.isEmptyOrWhitespaceOnly(fl.getValue()) && att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("concept")){
	            	if (isMultiSelect(fl.getValue())){
	            		String[] fieldValues = fl.getValue().split(" ");
	            		String val = "";
	            		for (String fieldVal : fieldValues){
	                        val = formAttributeMapper.getInstanceAttributesForFormFieldAndValue(fl.getKey(), fieldVal, subform, fs) + " ";
	                        //System.out.println("getEventForSubformFields from  MultiSelect >> fileld name: " + fl.getKey()+ " ,field value: " + fieldVal + " ,concept: " + att.get("openmrs_entity_id")+ " ,openmrs value: " + val);               
	                        e.addObs(new Obs("concept", att.get("openmrs_entity_id"), 
	                                att.get("openmrs_entity_parent"), StringUtils.isEmptyOrWhitespaceOnly(val)?fieldVal:val, null, fl.getKey()));
	            		} 
	            		
	            		
	            	}else{
		                String val = formAttributeMapper.getInstanceAttributesForFormFieldAndValue(fl.getKey(), fl.getValue(), subform, fs);
		                //System.out.println("getEventForSubformFields >> fileld name: " + fl.getKey()+ " ,field value: " + fl.getValue() + " ,concept: " + att.get("openmrs_entity_id")+ " ,openmrs value: " + val);
		                e.addObs(new Obs("concept", att.get("openmrs_entity_id"), 
		                        att.get("openmrs_entity_parent"), StringUtils.isEmptyOrWhitespaceOnly(val)?fl.getValue():val, null, fl.getKey()));
	            	}
	            }
        }
		return e;
	}
	private boolean isMultiSelect (String fieldValue){
		boolean multiSelect = true;
		String[] fieldValues = fieldValue.split(" ");
		if (fieldValues.length<=1){
			multiSelect = false;
		}
		else  {
			for (String fieldVal : fieldValues) {
				try {
					Integer.parseInt(fieldVal);
				} catch (NumberFormatException e) {
					multiSelect = false;
					break;
				}
			}
		}		
		return multiSelect;
	}
	
	/**
	 * Get field name for specified openmrs entity in given form submission
	 * @param en
	 * @param fs
	 * @return
	 */
	String getFieldName(OpenmrsEntity en, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openmrs_entity" , en.entity());
		m.put("openmrs_entity_id" , en.entityId());
		return formAttributeMapper.getFieldName(m , fs);
	}
	
	/**
	 * Get field name for specified openmrs entity in given form submission for given subform
	 * @param en
	 * @param subform
	 * @param fs
	 * @return
	 */
	String getFieldName(OpenmrsEntity en, String subform, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openmrs_entity" , en.entity());
		m.put("openmrs_entity_id" , en.entityId());
		return formAttributeMapper.getFieldName(m , subform, fs);
	}
	
	/**
	 * Get field name for specified openmrs attribute mappings in given form submission
	 * @param entity
	 * @param entityId
	 * @param entityParentId
	 * @param fs
	 * @return
	 */
	String getFieldName(String entity, String entityId, String entityParentId, FormSubmission fs) {
		Map<String, String> m = new HashMap<String, String>();
		m.put("openmrs_entity" , entity);
		m.put("openmrs_entity_id" , entityId);
		m.put("openmrs_entity_parent" , entityParentId);
		return formAttributeMapper.getFieldName(m , fs);
	}
	
	    Map<String, Address> extractAddresses(FormSubmission fs) throws ParseException {
        Map<String, Address> paddr = new HashMap<>();
        for (FormField fl : fs.instance().form().fields()) {
            fillAddressFields(fl, null, fs, paddr);
        }
        return paddr;
    }
    
    Map<String, Address> extractAddressesForSubform(Map<String, String> instance, String subform, FormSubmission fs) throws ParseException {
        Map<String, Address> paddr = new HashMap<>();
        for (Entry<String, String> fl : instance.entrySet()) {
            fillAddressFields(new FormField(fl.getKey(), fl.getValue(), null), null, fs, paddr);
        }
        return paddr;
    }
    
    void fillAddressFields(FormField fl, String subform, FormSubmission fs, Map<String, Address> addresses) throws ParseException {
        Map<String, String> att = new HashMap<>();
        if(StringUtils.isEmptyOrWhitespaceOnly(subform)){
            att = formAttributeMapper.getAttributesForField(fl.name(), fs);
        }
        else {
            att = formAttributeMapper.getAttributesForSubform(subform, fl.name(), fs);
        }
        if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_address")){
            String addressType = att.get("openmrs_entity_parent");
            String addressField = att.get("openmrs_entity_id");
            Address ad = addresses.get(addressType);
            if(ad == null){
                ad = new Address(addressType, null, null, null, null, null, null, null, null);
            }
 
            if(addressField.equalsIgnoreCase("startDate")||addressField.equalsIgnoreCase("start_date")){
                ad.setStartDate(DateUtil.parseDate(fl.value()));
            }
            else if(addressField.equalsIgnoreCase("endDate")||addressField.equalsIgnoreCase("end_date")){
                ad.setEndDate(DateUtil.parseDate(fl.value()));
            }
            else if(addressField.equalsIgnoreCase("latitude")){
                ad.setLatitude(fl.value());
            }
            else if(addressField.equalsIgnoreCase("longitute")){
                ad.setLongitute(fl.value());
            }
            else if(addressField.equalsIgnoreCase("geopoint")){
                // example geopoint 34.044494 -84.695704 4 76 = lat lon alt prec
                String geopoint = fl.value();
                if(!StringUtils.isEmptyOrWhitespaceOnly(geopoint)){
                    String[] g = geopoint.split(" ");
                    ad.setLatitude(g[0]);
                    ad.setLongitute(g[1]);
                    ad.addAddressField(addressField, fl.value());
                }
            }
            else if(addressField.equalsIgnoreCase("postalCode")||addressField.equalsIgnoreCase("postal_code")){
                ad.setPostalCode(fl.value());
            }
            else if(addressField.equalsIgnoreCase("state")||addressField.equalsIgnoreCase("state_province")||addressField.equalsIgnoreCase("stateProvince")){
                ad.setState(fl.value());
            }
            else if(addressField.equalsIgnoreCase("country")){
                ad.setCountry(fl.value());
            }
            else {
                ad.addAddressField(addressField, fl.value());
            }
 
            addresses.put(addressType, ad);
        }
    }
	
	Map<String, String> extractIdentifiers(FormSubmission fs) {
		Map<String, String> pids = new HashMap<>();
		for (FormField fl : fs.instance().form().fields()) {
			if(!StringUtils.isEmptyOrWhitespaceOnly(fl.value())){
				Map<String, String> att = new HashMap<>();
				att = formAttributeMapper.getAttributesForField(fl.name(), fs);
				
				if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_identifier")){
					pids.put(att.get("openmrs_entity_id"), fl.value());
				}
			}
		}
		return pids;
	}
	
	Map<String, String> extractIdentifiers(Map<String, String> subformInstanceData, String subform, FormSubmission fs) {
		Map<String, String> pids = new HashMap<>();
		for (Entry<String, String> fl : subformInstanceData.entrySet()) {
			if(!StringUtils.isEmptyOrWhitespaceOnly(fl.getValue())){
				Map<String, String> att = new HashMap<>();
				att = formAttributeMapper.getAttributesForSubform(subform, fl.getKey(), fs);
				if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_identifier")){
					pids.put(att.get("openmrs_entity_id"), fl.getValue());
				}
			}
		}
		return pids;
	}
	
	Map<String, Object> extractAttributes(FormSubmission fs) {
		Map<String, Object> pattributes = new HashMap<>();
		for (FormField fl : fs.instance().form().fields()) {
			if(!StringUtils.isEmptyOrWhitespaceOnly(fl.value())){
				Map<String, String> att = new HashMap<>();
				att = formAttributeMapper.getAttributesForField(fl.name(), fs);
				if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_attribute")){
					pattributes.put(att.get("openmrs_entity_id"), fl.value());
				}
			}
		}
		return pattributes;
	}
	
	Map<String, Object> extractAttributes(Map<String, String> subformInstanceData, String subform, FormSubmission fs) {
		Map<String, Object> pattributes = new HashMap<>();
		for (Entry<String, String> fl : subformInstanceData.entrySet()) {
			if(!StringUtils.isEmptyOrWhitespaceOnly(fl.getValue())){
				Map<String, String> att = new HashMap<>();
				att = formAttributeMapper.getAttributesForSubform(subform, fl.getKey(), fs);
				if(att.size()>0 && att.get("openmrs_entity").equalsIgnoreCase("person_attribute")){
					pattributes.put(att.get("openmrs_entity_id"), fl.getValue());
				}
			}
		}
		return pattributes;
	}
	
	/**
	 * Extract Client from given form submission
	 * @param fs
	 * @return
	 * @throws ParseException
	 */
	public Client getClientFromFormSubmission(FormSubmission fs) throws ParseException {
		String firstName = fs.getField(getFieldName(Person.first_name, fs));
		String middleName = fs.getField(getFieldName(Person.middle_name, fs));
		String lastName = fs.getField(getFieldName(Person.last_name, fs));
		String bd = fs.getField(getFieldName(Person.birthdate, fs));
		Date birthdate = (bd==null || bd.isEmpty())? OpenmrsService.OPENMRS_DATE.parse("1971-01-01"):OpenmrsService.OPENMRS_DATE.parse(bd);
		String dd = fs.getField(getFieldName(Person.deathdate, fs));
		Date deathdate = (dd==null || dd.isEmpty())?null:OpenmrsService.OPENMRS_DATE.parse(dd);
		String aproxbd = fs.getField(getFieldName(Person.birthdate_estimated, fs));
		Boolean birthdateApprox = false;
		if(!StringUtils.isEmptyOrWhitespaceOnly(aproxbd) && NumberUtils.isNumber(aproxbd)){
			int bde = 0;
			try {
				bde = Integer.parseInt(aproxbd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			birthdateApprox = bde > 0 ? true:false;
		}
		String aproxdd = fs.getField(getFieldName(Person.deathdate_estimated, fs));
		Boolean deathdateApprox = false;
		if(!StringUtils.isEmptyOrWhitespaceOnly(aproxdd) && NumberUtils.isNumber(aproxdd)){
			int dde = 0;
			try {
				dde = Integer.parseInt(aproxdd);
			} catch (Exception e) {
				e.printStackTrace();
			}
			deathdateApprox = dde > 0 ? true:false;
		}
		String gender = fs.getField(getFieldName(Person.gender, fs));
		
		List<Address> addresses = new ArrayList<>(extractAddresses(fs).values());
		
		Client c = new Client()
			.withBaseEntity(new BaseEntity(fs.entityId(), firstName, middleName, lastName, birthdate, deathdate, 
					birthdateApprox, deathdateApprox, gender, addresses, extractAttributes(fs)))
			.withIdentifiers(extractIdentifiers(fs));
		return c;
	}
	
	/**
	 * Extract Client and Event from given form submission for entities dependent on main beneficiary (excluding main beneficiary). 
	 * The dependent entities are specified via subforms (repeat groups) in xls forms.
	 * @param fs
	 * @return The clients and events Map with id of dependent entity as key. Each entry in Map contains an 
	 * internal map that holds Client and Event info as "client" and "event" respectively for that 
	 * dependent entity (whose id is the key of main Map).
	 * Ex: 
	 * {222222-55d555-ffffff-232323-ffffff: {client: ClientObjForGivenID, event: EventObjForGivenIDAndForm}},
	 * {339393-545445-ffdddd-333333-ffffff: {client: ClientObjForGivenID, event: EventObjForGivenIDAndForm}},
	 * {278383-765766-dddddd-767666-ffffff: {client: ClientObjForGivenID, event: EventObjForGivenIDAndForm}}
	 * @throws ParseException
	 */
	public Map<String, Map<String, Object>> getDependentClientsFromFormSubmission(FormSubmission fs) throws ParseException {
		Map<String, Map<String, Object>> map = new HashMap<>();
		if (fs.subForms() == null || fs.subForms().isEmpty()){
			//System.out.println("subform does not exists inside this form: " + fs.formName());
			return map;
		}
		for (SubFormData sbf : fs.subForms()) {
			Map<String, String> att = formAttributeMapper.getAttributesForSubform(sbf.name(), fs);
			if(att.size() > 0 && att.get("openmrs_entity").equalsIgnoreCase("person")){
				for (Map<String, String> sfdata : sbf.instances()) {
					String firstName = sfdata.get(getFieldName(Person.first_name, sbf.name(), fs));
					Map<String, String> idents = extractIdentifiers(sfdata, sbf.name(), fs);
					if(StringUtils.isEmptyOrWhitespaceOnly(firstName) && idents.size() < 2 && !sbf.name().equals("child_registration"))
					{	//we need to ignore uuid of entity
						// if empty repeat group leave this entry and move to next
						continue;
					}
					Map<String, Object> cne = new HashMap<>();
					
					String middleName = sfdata.get(getFieldName(Person.middle_name, sbf.name(), fs));
					String lastName = sfdata.get(getFieldName(Person.last_name, sbf.name(), fs));
					String bd = sfdata.get(getFieldName(Person.birthdate, sbf.name(), fs));
					Date birthdate = (bd==null || bd.isEmpty())? OpenmrsService.OPENMRS_DATE.parse("1971-01-01"):OpenmrsService.OPENMRS_DATE.parse(bd);
					String dd = sfdata.get(getFieldName(Person.deathdate, sbf.name(), fs));
					Date deathdate = (dd==null || dd.isEmpty())?null:OpenmrsService.OPENMRS_DATE.parse(dd);
					String aproxbd = sfdata.get(getFieldName(Person.birthdate_estimated, sbf.name(), fs));
					Boolean birthdateApprox = false;
					if(!StringUtils.isEmptyOrWhitespaceOnly(aproxbd) && NumberUtils.isNumber(aproxbd)){
						int bde = 0;
						try {
							bde = Integer.parseInt(aproxbd);
						} catch (Exception e) {
							e.printStackTrace();
						}
						birthdateApprox = bde > 0 ? true:false;
					}
					String aproxdd = sfdata.get(getFieldName(Person.deathdate_estimated, sbf.name(), fs));
					Boolean deathdateApprox = false;
					if(!StringUtils.isEmptyOrWhitespaceOnly(aproxdd) && NumberUtils.isNumber(aproxdd)){
						int dde = 0;
						try {
							dde = Integer.parseInt(aproxdd);
						} catch (Exception e) {
							e.printStackTrace();
						}
						deathdateApprox = dde > 0 ? true:false;
					}
					String gender = sfdata.get(getFieldName(Person.gender, sbf.name(), fs));
					
					List<Address> addresses = new ArrayList<>(extractAddressesForSubform(sfdata, sbf.name(), fs).values());
					
					if(sbf.name().equals("child_registration"))
					{
						 firstName = sfdata.get("FWBNFCHILDNAME");
						 middleName = null;
						 lastName = null;		 
						 birthdate = OpenmrsService.OPENMRS_DATE.parse(sfdata.get("FWBNFDOB"));
						 deathdate = null;
						 birthdateApprox = false;
						 deathdateApprox = false;
						 gender = sfdata.get("FWBNFGEN");
						 addresses.add(new Address());
					}
					
					Client c = new Client()
					.withBaseEntity(new BaseEntity(sfdata.get("id"), firstName, middleName, lastName, birthdate, deathdate, 
							birthdateApprox, deathdateApprox, gender, addresses, extractAttributes(sfdata, sbf.name(), fs)))
					.withIdentifiers(idents);
					
					cne.put("client", c);
					cne.put("event", getEventForSubform(fs, sbf.name(), att.get("openmrs_entity_id"), sfdata));
					
					map.put(c.getBaseEntityId(), cne);
				}
			}
		}
		return map;
	}
}