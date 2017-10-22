package org.opensrp.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleData {
    public List<Map<String, String>> data;
    private String target;
    private String type;
    private Map<String, String> details;

    public static ScheduleData createAlert(BeneficiaryType beneficiaryType, String scheduleName, String visitCode,
                                         AlertStatus alertStatus, DateTime startDate, DateTime expiryDate) {
        
    	ScheduleData data =  new ScheduleData("alert", "createAlert");
    	Map<String, String > mapData = new HashMap<>();
    	mapData.put("beneficiaryType", beneficiaryType.value());
    	mapData.put("scheduleName", scheduleName);
    	mapData.put("visitCode", visitCode);
    	mapData.put("alertStatus", alertStatus.value());
    	mapData.put("startDate", startDate.toLocalDate().toString());
    	mapData.put("expiryDate", expiryDate.toLocalDate().toString());
    	data.data.add(mapData);    	
    	return data;    	
    }

    public static ScheduleData markAlertAsClosed(String visitCode, String completionDate) {
        return new ScheduleData("alert", "closeAlert")
                .with("visitCode", visitCode)
                .with("completionDate", completionDate);
    }

    public static ScheduleData reportForIndicator(String indicator, String annualTarget, String monthSummaries) {
        return new ScheduleData("report", indicator)
                .with("annualTarget", annualTarget)
                .with("monthlySummaries", monthSummaries);
    }

    public static ScheduleData closeBeneficiary(String target, String reasonForClose) {
        return new ScheduleData(target, "close")
                .with("reasonForClose", reasonForClose);
    }

    public static ScheduleData from(String actionType, String actionTarget, List<Map<String, String>> data, Map<String, String> details) {
        ScheduleData actionData = new ScheduleData(actionTarget, actionType);
        actionData.data.addAll(data);
        actionData.details.putAll(details);
        return actionData;
    }

    public ScheduleData(String target, String type) {
        this.target = target;
        this.type = type;
        data = new ArrayList<Map<String,String>>();
        details = new HashMap<String, String>();
    }

    private ScheduleData with(String key, String value) {
         //data.add(key, value);
    	Map<String,String> addthis = new HashMap<>();
    	addthis.put(key, value);
    	data.add(addthis);
        return this;
    }

    public List<Map<String, String>> data() {
        return (List<Map<String, String>>) data;
    }

    public String target() {
        return target;
    }

    public String type() {
        return type;
    }

    public Map<String, String> details() {
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
