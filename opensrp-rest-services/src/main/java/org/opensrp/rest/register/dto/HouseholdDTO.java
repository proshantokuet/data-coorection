package org.opensrp.rest.register.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
public class HouseholdDTO {
	@JsonProperty("FWDIVISION")
	private String FWDIVISION;
	@JsonProperty("FWHOHGENDER")
	private String FWHOHGENDER;
	@JsonProperty("user_type")
	private String user_type;	
	@JsonProperty("FWNHREGDATE")
	private String FWNHREGDATE;
	@JsonProperty("FWHOHFNAME")
	private String FWHOHFNAME;
	@JsonProperty("FWGOBHHID")
	private String FWGOBHHID;
	
	
	@JsonProperty("FWCOUNTRY")
	private String FWCOUNTRY;
	@JsonProperty("FWJIVHHID")
	private String FWJIVHHID;
	@JsonProperty("TODAY")
	private String TODAY;
	@JsonProperty("FWDISTRICT")
	private String FWDISTRICT;
	@JsonProperty("FWUNION")
	private String FWUNION;
	@JsonProperty("PROVIDERID")
	private String PROVIDERID;
	
	@JsonProperty("FWUPAZILLA")
	private String FWUPAZILLA;
	
	@JsonProperty("SUBMISSIONDATE")
	private String SUBMISSIONDATE;
	@JsonProperty("type")
	private String type;
	
	@JsonProperty
	private String id;
	
	public HouseholdDTO() {
		
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