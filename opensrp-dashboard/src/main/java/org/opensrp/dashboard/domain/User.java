package org.opensrp.dashboard.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;
import org.opensrp.dashboard.dto.SimplifiedLocation;
import org.opensrp.dashboard.dto.SimplifiedRole;
import org.opensrp.dashboard.dto.SimplifiedUser;

@TypeDiscriminator("doc.type === 'User'")
public class User extends MotechBaseDataObject {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	@JsonProperty("status")
	private String status;
	@JsonProperty("given_name")
	private String given_name;
	@JsonProperty("middle_name")
	private String middle_name;
	@JsonProperty("family_name")
	private String family_name;
	@JsonProperty("contact_number")
	private String contact_number;
	@JsonProperty("personal_address")
	private String personal_address;
	@JsonProperty("email")
	private String email;
	@JsonProperty("password")
	private String password;
	@JsonProperty("user_name")
	private String user_name;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("parent")
	private SimplifiedUser parent;
	@JsonProperty("children")
	private List<SimplifiedUser> children;	
	@JsonProperty("roles")
	private List<SimplifiedRole> roles;
	@JsonProperty("location")
	private List<SimplifiedLocation> location;	
	@JsonProperty("meta")
	private Map<String, String> meta;
	@JsonProperty("created_at")
	private String created_at;
	@JsonProperty("updated_at")
	private String updated_at;
	@JsonProperty("created_by")
	private String created_by;
	@JsonProperty("updated_by")
	private String updated_by;
	
	public User(){
		this.created_at = new Date().toString();
		this.updated_at = new Date().toString();
		this.created_by = "Admin";
		this.updated_by = null;
	}
	@JsonProperty("status")
	public User withStatus(String status) {
		this.status = status;
		return this;
	}
	@JsonProperty("given_name")
	public User withGivenName(String givenName) {
		this.given_name = givenName;
		return this;
	}
	@JsonProperty("middle_name")
	public User withMiddleName(String middleName) {
		this.middle_name = middleName;
		return this;
	}
	@JsonProperty("family_name")
	public User withFamilyName(String familyName) {
		this.family_name = familyName;
		return this;
	}
	@JsonProperty("contact_number")
	public User withContactNumber(String contactNumber) {
		this.contact_number = contactNumber;
		return this;
	}
	@JsonProperty("personal_address")
	public User withPersonalAddress(String personalAddress) {
		this.personal_address = personalAddress;
		return this;
	}
	@JsonProperty("password")
	public User withPassword(String password) {
		this.password = password;
		return this;
	}
	@JsonProperty("email")
	public User withEmail(String email) {
		this.email = email;
		return this;
	}
	@JsonProperty("gender")
	public User withGender(String gender) {
		this.gender = gender;
		return this;
	}
	@JsonProperty("parent")
	public User withParent(SimplifiedUser parent) {
		this.parent = parent;
		return this;
	}
	@JsonProperty("children")
	public User withChildren(List<SimplifiedUser> children) {
		this.children = children;
		return this;
	}
	@JsonProperty("roles")
	public User withRoles(List<SimplifiedRole> roles) {
		this.roles = roles;
		return this;
	}
	@JsonProperty("user_name")
	public User withUserName(String userName) {
		this.user_name = userName;
		return this;
	}
	@JsonProperty("location")
	public User withLocation(List<SimplifiedLocation> locations) {
		this.location = locations;
		return this;
	}
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	@JsonProperty("given_name")
	public String getGivenName() {
		return given_name;
	}
	@JsonProperty("middle_name")
	public String getMiddleName() {
		return middle_name;
	}
	@JsonProperty("family_name")
	public String getFamilyName() {
		return family_name;
	}
	@JsonProperty("contact_number")
	public String getContactNumber() {
		return contact_number;
	}
	@JsonProperty("personal_address")
	public String getPersonalAddress() {
		return personal_address;
	}
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}
	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}
	@JsonProperty("parent")
	public SimplifiedUser getParent() {
		return parent;
	}
	@JsonProperty("children")
	public List<SimplifiedUser> getChildren() {
		return children;
	}
	@JsonProperty("roles")
	public List<SimplifiedRole> getRoles() {
		return roles;
	}
	@JsonProperty("user_name")
	public String withUserName() {
		return user_name;
	}
	@JsonProperty("location")
	public List<SimplifiedLocation> getLocation(){
		return location;
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
