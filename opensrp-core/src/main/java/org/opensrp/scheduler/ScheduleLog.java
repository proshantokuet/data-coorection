package org.opensrp.scheduler;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.joda.time.DateTime;
import org.motechproject.model.MotechBaseDataObject;
import org.motechproject.util.DateUtil;
import org.opensrp.dto.ActionData;
import org.opensrp.dto.AlertStatus;
import org.opensrp.dto.ScheduleData;


@TypeDiscriminator("doc.type === 'ScheduleLog'")
public class ScheduleLog extends MotechBaseDataObject  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
    private String anmIdentifier;
    @JsonProperty
    private String caseID;
    @JsonProperty
    private String instanceId;
    @JsonProperty
    private String closeById;
    @JsonProperty
    private String trackId;
    @JsonProperty
    private String visitCode;
    @JsonProperty
    private List<Map<String, String>> data;
    @JsonProperty
    private String actionTarget;
    @JsonProperty
    private AlertStatus currentWindow;
    @JsonProperty
    private String scheduleName;
    @JsonProperty
    private DateTime scheduleCloseDate;
    @JsonProperty
    private DateTime currentWindowStartDate;
    @JsonProperty
    private DateTime currentWindowEndDate;
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
    
    

    public ScheduleLog() {
    }

    public ScheduleLog(String caseId, String instanceId, String anmIdentifier, ScheduleData actionData,long timeStamp,String visitCode) {
        this.anmIdentifier = anmIdentifier;
        this.caseID = caseId;
        this.instanceId = instanceId;
        this.data =  actionData.data();
        this.actionTarget = actionData.target();
        this.actionType = actionData.type();
        this.timeStamp = timeStamp;
        this.scheduleGenerateDate = DateUtil.now();
        this.details = actionData.details();
        this.isActionActive = true;
        this.visitCode = visitCode;
        
        
    }

    public ScheduleLog(String caseId, String instanceId, String anmIdentifier, ScheduleData actionData,String trackId,AlertStatus currentWindow,DateTime scheduleCloseDate,DateTime currentWindowStartDate,DateTime currentWindowEndDate,String scheduleName,long timeStamp,String visitCode) {
        this.anmIdentifier = anmIdentifier;
        this.caseID = caseId;
        this.instanceId = instanceId;
        this.data =  actionData.data();
        this.actionTarget = actionData.target();
        this.actionType = actionData.type();
        this.timeStamp = timeStamp;
        this.scheduleGenerateDate = DateUtil.now();
        this.details = actionData.details();
        //this.isActionActive = true;
        this.trackId = trackId;
        this.currentWindow = currentWindow;
        this.scheduleCloseDate = scheduleCloseDate;
        this.currentWindowStartDate = currentWindowStartDate;
        this.currentWindowEndDate = currentWindowEndDate;
        this.scheduleName = scheduleName;
        this.visitCode = visitCode;
        
    }

    public Boolean getIsActionActive() {
		return isActionActive;
	}

	public void setIsActionActive(Boolean isActionActive) {
		this.isActionActive = isActionActive;
	}
    public String anmIdentifier() {
        return anmIdentifier;
    }

    public String caseId() {
        return caseID;
    }
    
    public String instanceId() {
        return instanceId;
    }
    public String trackId() {
        return trackId;
    }
    public String scheduleName() {
        return scheduleName;
    }
    
    public List<Map<String, String>> data() {
        return data;
    }

    public String actionType() {
        return actionType;
    }
    public void closeById(String closeById) {
        this.closeById =closeById;
    }
    public void setVisitCode(String visitCode) {
        this.visitCode =visitCode;
    }
    public void timestamp(long timeStamp) {
        this.timeStamp =timeStamp;
    }
    public void currentWindow(AlertStatus currentWindow) {
        this.currentWindow = currentWindow;
    }
    public void scheduleCloseDate(DateTime scheduleCloseDate) {
        this.scheduleCloseDate = scheduleCloseDate;
    }
    public void currentWindowStartDate(DateTime currentWindowStartDate) {
        this.currentWindowStartDate = currentWindowStartDate;
    }
    public void currentWindowEndDate(DateTime currentWindowEndDate) {
        this.currentWindowEndDate = currentWindowEndDate;
    }
    public DateTime scheduleGenerateDate(DateTime scheduleGenerateDate) {
        return scheduleGenerateDate;
    }
    public String target() {
        return actionTarget;
    }

    public ScheduleLog markAsInActive() {
        this.isActionActive = false;
        return this;
    }

    public Boolean getIsActionActive(boolean isActionActive) {
        return isActionActive;
    }

    public Map<String, String> details() {
        return details;
    }
    
    public String getDetail(String name) {
		return details.get(name);
	}

    public String getCaseID() {
        return caseID;
    }
    public String getTrackId() {
        return trackId;
    }
    public String getVisitCode() {
        return visitCode;
    }
    public String getCloseById(String closeById){
    	return closeById;
    }
    public String getScheduleName() {
        return scheduleName;
    }

    public AlertStatus getCurrentWindow() {
        return currentWindow;
    }
    public DateTime getScheduleCloseDate() {
        return scheduleCloseDate;
    }
    public DateTime getCurrentWindowStartDate() {
        return currentWindowStartDate;
    }
    public DateTime getCurrentWindowEndDate() {
        return currentWindowEndDate;
    }
    public String getActionTarget() {
        return actionTarget;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o, "timeStamp", "revision");
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, "timeStamp", "revision");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
