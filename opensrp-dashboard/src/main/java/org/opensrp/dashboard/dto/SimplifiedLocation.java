package org.opensrp.dashboard.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class SimplifiedLocation {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("id")
	private String id;
	
	public SimplifiedLocation(String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	public SimplifiedLocation() {
		
	}
	
	@JsonProperty("name")
	public SimplifiedLocation withName(String name) {
		this.name = name;
		return this;
	}
	
	@JsonProperty("id")
	public SimplifiedLocation withId(String id) {
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
