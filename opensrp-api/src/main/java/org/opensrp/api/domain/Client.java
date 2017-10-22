package org.opensrp.api.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.opensrp.api.constants.Gender;

/**
 * Client is the main beneficiary, the system facilitates to capture data for. The entity inherits all of the generic 
 * data from {@link BaseEntity}.
 */
public class Client extends BaseDataObject{

	private Map<String, String> identifiers;
	private String baseEntityId;
	private BaseEntity baseEntity;

	public Client() {}

	public Client(String baseEntityId, String firstName, String middleName, String lastName, Date birthdate, 
			Date deathdate, Boolean birthdateApprox, Boolean deathdateApprox, String gender) {
		this.baseEntity = new BaseEntity(baseEntityId, firstName, middleName, lastName, birthdate, deathdate, birthdateApprox, deathdateApprox, gender, null, null);
		this.baseEntityId = baseEntityId;
	}
	
	public Client(String baseEntityId, String firstName, String middleName, String lastName, Date birthdate, 
			Date deathdate, Boolean birthdateApprox, Boolean deathdateApprox, String gender, 
			String identifierType, String identifier) {
		this.baseEntity = new BaseEntity(baseEntityId, firstName, middleName, lastName, birthdate, deathdate, birthdateApprox, deathdateApprox, gender, null, null);
		this.baseEntityId = baseEntityId;
		this.identifiers = new HashMap<>();
		identifiers.put(identifierType, identifier);
	}
	
	public Client(String baseEntityId, String firstName, String middleName, String lastName, Date birthdate, Date deathdate, 
			Boolean birthdateApprox, Boolean deathdateApprox, String gender, List<Address> addresses,
			Map<String, String> identifiers, Map<String, Object> attributes) {
		this.baseEntity = new BaseEntity(baseEntityId, firstName, middleName, lastName, birthdate, deathdate, birthdateApprox, deathdateApprox, gender, addresses, attributes);
		this.baseEntityId = baseEntityId;
		this.identifiers = identifiers;
	}
	/**
	 * Allows to instantiate from a list of predefined genders in {@link Gender} 
	 * @param clientId
	 * @param firstName
	 * @param lastName
	 * @param birthdate
	 * @param deathdate
	 * @param birthdateApprox
	 * @param deathdateApprox
	 * @param gender
	 * @param addresses
	 * @param attributes
	 */
	public Client(String baseEntityId, String firstName, String middleName, String lastName, Date birthdate, Date deathdate, 
			Boolean birthdateApprox, Boolean deathdateApprox, Gender gender, List<Address> addresses,
			Map<String, String> identifiers, Map<String, Object> attributes) {
		this.baseEntity = new BaseEntity(baseEntityId, firstName, middleName, lastName, birthdate, deathdate, birthdateApprox, deathdateApprox, gender, addresses, attributes);
		this.baseEntityId = baseEntityId;
		this.identifiers = identifiers;
	}

	public Map<String, String> getIdentifiers() {
		return identifiers;
	}

	/**
	 * WARNING: Overrides all existing identifiers
	 * @param identifiers
	 * @return
	 */
	public void setIdentifiers(Map<String, String> identifiers) {
		this.identifiers = identifiers;
	}
	
	public void addIdentifier(String identifierType, String identifier) {
		if(identifiers == null){
			identifiers = new HashMap<>();
		}
		
		identifiers.put(identifierType, identifier);
	}
	
	public void removeIdentifier(String identifierType) {
		identifiers.remove(identifierType);
	}
	
	public String getBaseEntityId() {
		return baseEntityId;
	}
	
	public void setBaseEntityId(String baseEntityId) {
		this.baseEntityId = baseEntityId;
	}

	public BaseEntity getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(BaseEntity baseEntity) {
		this.baseEntity = baseEntity;
		this.baseEntityId = baseEntity.getId();
	}

	public Client withBaseEntity(BaseEntity baseEntity) {
		this.baseEntity = baseEntity;
		this.baseEntityId = baseEntity.getId();
		return this;
	}
	
	public Client withBaseEntityId(String baseEntityId) {
		this.baseEntityId = baseEntityId;
		return this;
	}

	/**
	 * WARNING: Overrides all existing identifiers
	 * @param identifiers
	 * @return
	 */
	public Client withIdentifiers(Map<String, String> identifiers) {
		this.identifiers = identifiers;
		return this;
	}
	
	public Client withIdentifier(String identifierType, String identifier) {
		if(identifiers == null){
			identifiers = new HashMap<>();
		}
		identifiers.put(identifierType, identifier);
		return this;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
