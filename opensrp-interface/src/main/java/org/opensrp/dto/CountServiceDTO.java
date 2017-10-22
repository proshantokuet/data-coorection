package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CountServiceDTO {
	@JsonProperty
	private int householdTotalCount;
	@JsonProperty
	private int householdTodayCount;
	@JsonProperty
	private int householdThisWeekCount;
	@JsonProperty
	private int householdThisMonthCount;
	@JsonProperty
	private int elcoTotalCount;
	@JsonProperty
	private int elcoTodayCount;
	@JsonProperty
	private int elcoThisWeekCount;
	@JsonProperty
	private int elcoThisMonthCount;
	@JsonProperty
	private int pwTotalCount;
	@JsonProperty
	private int pwTodayCount;
	@JsonProperty
	private int pwThisWeekCount;
	@JsonProperty
	private int pwThisMonthCount;	

	public CountServiceDTO(){
		
	}
	
	public CountServiceDTO(int householdTotalCount, int householdTodayCount,
			int householdThisWeekCount, int householdThisMonthCount,
			int elcoTotalCount, int elcoTodayCount, int elcoThisWeekCount,
			int elcoThisMonthCount, int pwTotalCount, int pwTodayCount,
			int pwThisWeekCount, int pwThisMonthCount) {
		super();
		this.householdTotalCount = householdTotalCount;
		this.householdTodayCount = householdTodayCount;
		this.householdThisWeekCount = householdThisWeekCount;
		this.householdThisMonthCount = householdThisMonthCount;
		this.elcoTotalCount = elcoTotalCount;
		this.elcoTodayCount = elcoTodayCount;
		this.elcoThisWeekCount = elcoThisWeekCount;
		this.elcoThisMonthCount = elcoThisMonthCount;
		this.pwTotalCount = pwTotalCount;
		this.pwTodayCount = pwTodayCount;
		this.pwThisWeekCount = pwThisWeekCount;
		this.pwThisMonthCount = pwThisMonthCount;
		
	}
	
	public int getHouseholdTotalCount() {
		return householdTotalCount;
	}

	public void setHouseholdTotalCount(int householdTotalCount) {
		this.householdTotalCount = householdTotalCount;
	}

	public int getHouseholdTodayCount() {
		return householdTodayCount;
	}

	public void setHouseholdTodayCount(int householdTodayCount) {
		this.householdTodayCount = householdTodayCount;
	}

	public int getHouseholdThisWeekCount() {
		return householdThisWeekCount;
	}

	public void setHouseholdThisWeekCount(int householdThisWeekCount) {
		this.householdThisWeekCount = householdThisWeekCount;
	}

	public int getHouseholdThisMonthCount() {
		return householdThisMonthCount;
	}

	public void setHouseholdThisMonthCount(int householdThisMonthCount) {
		this.householdThisMonthCount = householdThisMonthCount;
	}

	public int getElcoTotalCount() {
		return elcoTotalCount;
	}

	public void setElcoTotalCount(int elcoTotalCount) {
		this.elcoTotalCount = elcoTotalCount;
	}

	public int getElcoTodayCount() {
		return elcoTodayCount;
	}

	public void setElcoTodayCount(int elcoTodayCount) {
		this.elcoTodayCount = elcoTodayCount;
	}

	public int getElcoThisWeekCount() {
		return elcoThisWeekCount;
	}

	public void setElcoThisWeekCount(int elcoThisWeekCount) {
		this.elcoThisWeekCount = elcoThisWeekCount;
	}

	public int getElcoThisMonthCount() {
		return elcoThisMonthCount;
	}

	public void setElcoThisMonthCount(int elcoThisMonthCount) {
		this.elcoThisMonthCount = elcoThisMonthCount;
	}

	public int getPwTotalCount() {
		return pwTotalCount;
	}

	public void setPwTotalCount(int pwTotalCount) {
		this.pwTotalCount = pwTotalCount;
	}

	public int getPwTodayCount() {
		return pwTodayCount;
	}

	public void setPwTodayCount(int pwTodayCount) {
		this.pwTodayCount = pwTodayCount;
	}

	public int getPwThisWeekCount() {
		return pwThisWeekCount;
	}

	public void setPwThisWeekCount(int pwThisWeekCount) {
		this.pwThisWeekCount = pwThisWeekCount;
	}

	public int getPwThisMonthCount() {
		return pwThisMonthCount;
	}

	public void setPwThisMonthCount(int pwThisMonthCount) {
		this.pwThisMonthCount = pwThisMonthCount;
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
