package org.opensrp.dto;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class CountServiceDTOForChart {
	@JsonProperty
	private List<Integer>  weeklyCountsForChart;

	public List<Integer>  getCounts() {
		return weeklyCountsForChart;
	}

	public void setCounts(List<Integer> list) {
		this.weeklyCountsForChart = list;
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
