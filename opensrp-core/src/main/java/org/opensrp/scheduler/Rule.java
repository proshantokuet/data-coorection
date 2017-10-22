package org.opensrp.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;


public class Rule {
	
	@JsonProperty
	private String startFormName;
	@JsonProperty
	private String endFormName; 
	@JsonProperty	
	private List<Defination>  defination;
	
	public Rule(){
	}
	public Rule(List<Defination> defination,String startFormName,String endFormName){
		this.defination = defination;
		this.startFormName = startFormName;
		this.endFormName = endFormName;
	}
	public String getStartFormName() {
		return startFormName;
	}
	public void setStartFormName(String startFormName) {
		this.startFormName = startFormName;
	}
	public String getEndFormName() {
		return endFormName;
	}
	public void setEndFormName(String endFormName) {
		this.endFormName = endFormName;
	}
	
	
	public void setDefination(List<Defination> defination) {
		this.defination = defination;
	}
	@Override
	public String toString() {
		return "Rule [startFormName=" + startFormName + ", endFormName="
				+ endFormName + ", definations=" + defination + "]";
	}
	public List<Defination> getDefination() {
		return defination;
	}
	
}
