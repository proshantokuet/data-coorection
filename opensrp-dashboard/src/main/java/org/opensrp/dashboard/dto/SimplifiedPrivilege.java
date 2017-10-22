package org.opensrp.dashboard.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class SimplifiedPrivilege {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("id")
	private String id;
	
	public SimplifiedPrivilege(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public SimplifiedPrivilege() {
		
	}
	
	@JsonProperty("name")
	public SimplifiedPrivilege withName(String name) {
		this.name = name;
		return this;
	}
	
	@JsonProperty("id")
	public SimplifiedPrivilege withId(String id) {
		this.id = id;
		return this;
	}
	
	@JsonProperty("name")
	public String getName() {
		return this.name;
	}
	
	@JsonProperty("id")
	public String getId() {
		return this.id;
	}
}
