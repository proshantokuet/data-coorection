package org.opensrp.dashboard.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class SimplifiedRole {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("id")
	private String id;
	
	public SimplifiedRole(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public SimplifiedRole() {
		
	}
	
	@JsonProperty("name")
	public SimplifiedRole withName(String name) {
		this.name = name;
		return this;
	}
	
	@JsonProperty("id")
	public SimplifiedRole withId(String id) {
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
