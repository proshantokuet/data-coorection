package org.opensrp.domain;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
import org.opensrp.scheduler.Rule;

public class ScheduleRuleDTO {

	@JsonProperty
    private String name;
	@JsonProperty
    private String createdBy;
	@JsonProperty
	private List<Rule>  rule;
	@JsonProperty
    private DateTime createdDate;
	@JsonProperty
	private String ruleID;
	public ScheduleRuleDTO() {

	}
	public ScheduleRuleDTO(String name, String createdBy,
			DateTime createdDate, List<Rule> rule,String ruleID) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.rule = rule;
		this.createdDate = new DateTime();
		this.ruleID = ruleID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<Rule> getRule() {
		return rule;
	}
	public void setRule(List<Rule> rule) {
		this.rule = rule;
	}
	public DateTime getCreatedDate() {
		return createdDate;
	}
	
	public String getRuleID() {
		return ruleID;
	}
	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
	@Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "id");
    }
	@Override
	public String toString() {
		return "ScheduleRuleDTO [name=" + name + ", createdBy=" + createdBy
				+ ", rule=" + rule + ", createdDate=" + createdDate
				+ ", ruleID=" + ruleID + "]";
	}
    
    
}
