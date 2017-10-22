package org.opensrp.dto;

import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;

public class ReportActionDTO {
	@JsonProperty
    private String anmIdentifier;
    @JsonProperty
    private String caseID;
    @JsonProperty
    private Map<String, String> data;
    @JsonProperty
    private String actionTarget;
    @JsonProperty
    private String actionType;
    @JsonProperty
    private Boolean isActionActive;
    @JsonProperty
    private DateTime scheduleGenerateDate;
    @JsonProperty
    private long timeStamp;
    @JsonProperty
    private Map<String, String> details;
    
	public ReportActionDTO(String anmIdentifier, String caseID,
			Map<String, String> data, String actionTarget, String actionType,
			Boolean isActionActive, DateTime scheduleGenerateDate,
			long timeStamp, Map<String, String> details) {
		this.anmIdentifier = anmIdentifier;
		this.caseID = caseID;
		this.data = data;
		this.actionTarget = actionTarget;
		this.actionType = actionType;
		this.isActionActive = isActionActive;
		this.scheduleGenerateDate = scheduleGenerateDate;
		this.timeStamp = timeStamp;
		this.details = details;
	}

	public ReportActionDTO() {
	}

	public String getAnmIdentifier() {
		return anmIdentifier;
	}

	public String getCaseID() {
		return caseID;
	}

	public Map<String, String> getData() {
		return data;
	}
	
	public String get(String key) {
        return data.get(key);
    }

	public String getActionTarget() {
		return actionTarget;
	}

	public String getActionType() {
		return actionType;
	}

	public Boolean getIsActionActive() {
		return isActionActive;
	}

	public DateTime getScheduleGenerateDate() {
		return scheduleGenerateDate;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public Map<String, String> getDetails() {
		return details;
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
