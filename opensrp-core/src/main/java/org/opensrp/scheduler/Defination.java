package org.opensrp.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;


public class Defination {
	@JsonProperty
	private String name;
	@JsonProperty
	private String value;
	
	public Defination(){
		
	}
	public Defination(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Defination [name=" + name + ", value=" + value + "]";
	}
	
}
