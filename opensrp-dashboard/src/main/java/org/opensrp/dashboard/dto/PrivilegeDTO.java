package org.opensrp.dashboard.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class PrivilegeDTO {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String id;
	
	@JsonProperty
	private String status;
	
	public PrivilegeDTO(String privilegeName) {
		this.name = privilegeName;
	}
	
	public PrivilegeDTO() {
		
	}
	
	public PrivilegeDTO withPrivilegeName(String privilegeName) {
		this.name = privilegeName;
		return this;
	}
	
	public PrivilegeDTO withPrivilegeId(String privilegeId) {
		this.id = privilegeId;
		return this;
	}
	
	public PrivilegeDTO withPrivilegeStatus(String status) {
		this.status = status;
		return this;
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	public String getName() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
	
}
