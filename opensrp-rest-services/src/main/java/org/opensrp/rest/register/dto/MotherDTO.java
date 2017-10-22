package org.opensrp.rest.register.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
public class MotherDTO {
	@JsonProperty("TODAY")
	private String TODAY;
	@JsonProperty("mother_first_name")
	private String mother_first_name;
	@JsonProperty("mother_husname")
	private String mother_husname;	
	@JsonProperty("mother_wom_nid")
	private String mother_wom_nid;
	@JsonProperty("mother_wom_bid")
	private String mother_wom_bid;
	@JsonProperty("mother_wom_age")
	private String mother_wom_age;
	@JsonProperty("FWWOMDISTRICT")
	private String FWWOMDISTRICT;
	@JsonProperty("FWWOMUPAZILLA")
	private String FWWOMUPAZILLA;
	@JsonProperty("FWWOMUNION")
	private String FWWOMUNION;
	@JsonProperty("PROVIDERID")
	private String PROVIDERID;	
	@JsonProperty("SUBMISSIONDATE")
	private String SUBMISSIONDATE;
	@JsonProperty("type")
	private String type;	
	@JsonProperty
	private String id;
	
	public MotherDTO() {
		
	}	
    

	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o, "id", "revision");
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "revision");
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}