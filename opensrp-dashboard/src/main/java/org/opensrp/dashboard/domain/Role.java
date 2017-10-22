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
import org.opensrp.dashboard.dto.SimplifiedPrivilege;

@TypeDiscriminator("doc.type === 'Role'")
public class Role extends MotechBaseDataObject {
	
	/**
     * 
     */
    private static final long serialVersionUID = 1L;
	@JsonProperty("name")
	private String name;
	@JsonProperty("status")
	private String status;	
	@JsonProperty("privileges")
	private List<SimplifiedPrivilege> privileges;
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
	
	public Role(){
		this.created_at = new Date().toString();
		this.updated_at = new Date().toString();
		this.created_by = "Admin";
		this.updated_by = null;
	}
	@JsonProperty("name")
	public Role withName(String roleName) {
		this.name = roleName;
		return this;
	}
	@JsonProperty("status")
	public Role withStatus(String status) {
		this.status = status;
		return this;
	}
	@JsonProperty("privileges")
	public Role withPrivileges(List<SimplifiedPrivilege> privileges){
		this.privileges = privileges;
		return this;
	}
	@JsonProperty("name")
	public String getName() {
		return name;
	}
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}
	@JsonProperty("privileges")
	public List<SimplifiedPrivilege> getPrivileges(){
		return privileges;
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
