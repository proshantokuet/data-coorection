package org.opensrp.rest.register.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
public class ElcoDTO {
	@JsonProperty("TODAY")
	private String TODAY;
	@JsonProperty("FWWOMDIVISION")
	private String FWWOMDIVISION;
	@JsonProperty("FWWOMDISTRICT")
	private String FWWOMDISTRICT;	
	@JsonProperty("FWWOMUPAZILLA")
	private String FWWOMUPAZILLA;
	@JsonProperty("FWWOMUNION")
	private String FWWOMUNION;
	@JsonProperty("WomanREGDATE")
	private String WomanREGDATE;
	
	
	@JsonProperty("FWWOMFNAME")
	private String FWWOMFNAME;
	@JsonProperty("FWWOMNID")
	private String FWWOMNID;
	@JsonProperty("FWHUSNAME")
	private String FWHUSNAME;
	@JsonProperty("FWWOMBID")
	private String FWWOMBID;
	@JsonProperty("GOBHHID")
	private String GOBHHID;
	@JsonProperty("PROVIDERID")
	private String PROVIDERID;
	
	@JsonProperty("JiVitAHHID")
	private String JiVitAHHID;
	
	@JsonProperty("SUBMISSIONDATE")
	private String SUBMISSIONDATE;
	@JsonProperty("type")
	private String type;	
	@JsonProperty
	private String id;
	
	public ElcoDTO() {
		
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