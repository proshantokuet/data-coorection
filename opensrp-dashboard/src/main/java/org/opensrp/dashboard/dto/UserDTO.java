package org.opensrp.dashboard.dto;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class UserDTO {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String id;
	
	@JsonProperty
	private String status;
	
	@JsonProperty
	private String given_name;
	
	@JsonProperty
	private String middle_name;
	
	@JsonProperty
	private String family_name;
	
	@JsonProperty
	private String contact_number;
	
	@JsonProperty
	private String personal_address;
	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private String user_name;
	
	@JsonProperty
	private String gender;
	
	@JsonProperty
	private UserDTO parent;
	
	@JsonProperty
	private List<UserDTO> children;
	
	@JsonProperty
	private List<RoleDTO> roles;
	
	@JsonProperty
	private List<LocationDTO> location;
	
	@JsonProperty
	private Map<String, String> meta;
	
	@JsonProperty
	private String created_at;
	
	@JsonProperty
	private String updated_at;
	
	@JsonProperty
	private String created_by;
	
	@JsonProperty
	private String updated_by;
	
	public UserDTO(String name, String id, String status) {
		this.name = name;
		this.id = id;
		this.status = status;
	}
	
	public UserDTO() {
		
	}
	
	public UserDTO withName(String roleName) {
		this.name = roleName;
		return this;
	}
	
	public UserDTO withRoleId(String roleId) {
		this.id = roleId;
		return this;
	}
	
	public UserDTO withStatus(String status) {
		this.status = status;
		return this;
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
	
	public String getGivenName() {
		return given_name;
	}
	
	public String getMiddleName() {
		return middle_name;
	}
	
	public String getFamilyName() {
		return family_name;
	}
	
	public String getContactNumber() {
		return contact_number;
	}
	
	public String getPersonalAddress() {
		return personal_address;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getGender() {
		return gender;
	}
	
	public UserDTO getParent() {
		return parent;
	}
	
	public List<UserDTO> getChildren() {
		return children;
	}
	
	public List<RoleDTO> getRoles() {
		return roles;
	}
	
	public String getUserName() {
		return user_name;
	}
	
	public List<LocationDTO> getLocation() {
		return location;
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
