package org.opensrp.dashboard.dto;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class RoleDTO {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String id;
	
	@JsonProperty
	private String status;
	
	@JsonProperty
	private List<PrivilegeDTO> privileges;
	
	public RoleDTO(String roleName, String roleId, String status) {
		this.name = roleName;
		this.id = roleId;
		this.status = status;
	}
	
	public RoleDTO() {
		
	}
	
	public RoleDTO withName(String roleName) {
		this.name = roleName;
		return this;
	}
	
	public RoleDTO withRoleId(String roleId) {
		this.id = roleId;
		return this;
	}
	
	public RoleDTO withStatus(String status) {
		this.status = status;
		return this;
	}
	
	public RoleDTO withPrivileges(List<PrivilegeDTO> privileges) {
		this.privileges = privileges;
		return this;
	}
	
	public String getName() {
		return name;
	}
	
	public String getRoleId() {
		return id;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getUserName() {
		return "";
	}
	
	public List<PrivilegeDTO> getPrivileges() {
		return privileges;
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
	
}
