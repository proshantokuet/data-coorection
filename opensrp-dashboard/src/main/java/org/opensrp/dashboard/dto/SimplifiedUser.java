package org.opensrp.dashboard.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class SimplifiedUser {
	
	@JsonProperty("user_name")
	private String user_name;
	
	@JsonProperty("id")
	private String id;
	
	public SimplifiedUser(String name, String id) {
		this.user_name = name;
		this.id = id;
	}
	
	public SimplifiedUser() {
		
	}
	
	@JsonProperty("user_name")
	public SimplifiedUser withUsername(String user_name) {
		this.user_name = user_name;
		return this;
	}
	
	@JsonProperty("id")
	public SimplifiedUser withId(String id) {
		this.id = id;
		return this;
	}
	
	@JsonProperty("user_name")
	public String getUserName() {
		return this.user_name;
	}
	
	@JsonProperty("id")
	public String getId() {
		return this.id;
	}
}
