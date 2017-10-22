package org.opensrp.dto.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.joda.time.DateTime;
public class HHRegisterEntryDTO {
	@JsonProperty
	private String CASEID;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String TODAY;
	@JsonProperty
	private String START;
	@JsonProperty
	private String END;
	@JsonProperty
	private String FWNHREGDATE;
	@JsonProperty
	private String FWGOBHHID; 
	@JsonProperty
	private String FWJIVHHID;
	@JsonProperty
	private String FWCOUNTRY;
	@JsonProperty
	private String FWDIVISION;
	@JsonProperty
	private String FWDISTRICT;
	@JsonProperty
	private String FWUPAZILLA;
	@JsonProperty
	private String FWUNION;
	@JsonProperty
	private String FWWARD;
	@JsonProperty
	private String FWSUBUNIT;
	@JsonProperty
	private String FWMAUZA_PARA;
	@JsonProperty
	private String FWNHHHGPS;
	@JsonProperty
	private String form_name;
	@JsonProperty
	private String FWHOHFNAME;
	@JsonProperty
	private String FWHOHLNAME;
	@JsonProperty
	private String FWHOHBIRTHDATE; 
	@JsonProperty
	private String FWHOHGENDER;
	@JsonProperty
	private String FWNHHMBRNUM;
	@JsonProperty
	private String FWNHHMWRA;
	@JsonProperty
	private String ELCO;
	@JsonProperty
	private String user_type;
	@JsonProperty
	private String external_user_ID;
	@JsonProperty
	private String current_formStatus;
	@JsonProperty
	private List<Map<String, String>> ELCODETAILS;
	@JsonProperty
	private List<Map<String, String>> multimediaAttachments;
	@JsonProperty
	private Map<String, String> details;
	private DateTime received_time;

	public HHRegisterEntryDTO() {
		this.ELCODETAILS = new ArrayList<>();
		this.multimediaAttachments = new ArrayList<>();
	}
	
	public HHRegisterEntryDTO withCASEID(String CASEID) {
		this.CASEID = CASEID;
		return this;
	}
	
	public HHRegisterEntryDTO withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public HHRegisterEntryDTO withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public HHRegisterEntryDTO withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public HHRegisterEntryDTO withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public HHRegisterEntryDTO withSTART(String START) {
		this.START = START;
		return this;
	}

	public HHRegisterEntryDTO withEND(String END) {
		this.END = END;
		return this;
	}

	public HHRegisterEntryDTO withFWNHREGDATE(String FWNHREGDATE) {
		this.FWNHREGDATE = FWNHREGDATE;
		return this;
	}

	public HHRegisterEntryDTO withFWGOBHHID(String FWGOBHHID) {
		this.FWGOBHHID = FWGOBHHID;
		return this;
	}

	public HHRegisterEntryDTO withFWJIVHHID(String FWJIVHHID) {
		this.FWJIVHHID = FWJIVHHID;
		return this;
	}

	public HHRegisterEntryDTO withFWNHHHGPS(String FWNHHHGPS) {
		this.FWNHHHGPS = FWNHHHGPS;
		return this;
	}
	
	public HHRegisterEntryDTO withform_name(String form_name) {
		this.form_name = form_name;
		return this;
	}
    
	public HHRegisterEntryDTO withFWCOUNTRY(String FWCOUNTRY) {
		this.FWCOUNTRY = FWCOUNTRY;
		return this;
	}
	
	public HHRegisterEntryDTO withFWDIVISION(String FWDIVISION) {
		this.FWDIVISION = FWDIVISION;
		return this;
	}
	
	public HHRegisterEntryDTO withFWDISTRICT(String FWDISTRICT) {
		this.FWDISTRICT = FWDISTRICT;
		return this;
	}
	
	public HHRegisterEntryDTO withFWUPAZILLA(String FWUPAZILLA) {
		this.FWUPAZILLA = FWUPAZILLA;
		return this;
	}
	
	public HHRegisterEntryDTO withFWUNION(String FWUNION) {
		this.FWUNION = FWUNION;
		return this;
	}
	
	public HHRegisterEntryDTO withFWWARD(String FWWARD) {
		this.FWWARD = FWWARD;
		return this;
	}
	
	public HHRegisterEntryDTO withFWSUBUNIT(String FWSUBUNIT) {
		this.FWSUBUNIT = FWSUBUNIT;
		return this;
	}
	
	public HHRegisterEntryDTO withFWMAUZA_PARA(String FWMAUZA_PARA) {
		this.FWMAUZA_PARA = FWMAUZA_PARA;
		return this;
	}

	public HHRegisterEntryDTO withFWHOHFNAME(String FWHOHFNAME) {
		this.FWHOHFNAME = FWHOHFNAME;
		return this;
	}

	
	public HHRegisterEntryDTO withFWHOHLNAME(String FWHOHLNAME) {
		this.FWHOHLNAME = FWHOHLNAME;
		return this;
	}

	public HHRegisterEntryDTO withFWHOHBIRTHDATE(String FWHOHBIRTHDATE) {
		this.FWHOHBIRTHDATE = FWHOHBIRTHDATE;
		return this;
	}

	public HHRegisterEntryDTO withFWNHHMBRNUM(String FWNHHMBRNUM) {
		this.FWNHHMBRNUM = FWNHHMBRNUM;
		return this;
	}

	public HHRegisterEntryDTO withFWHOHGENDER(String FWHOHGENDER) {
		this.FWHOHGENDER = FWHOHGENDER;
		return this;
	}
	
	public HHRegisterEntryDTO withFWNHHMWRA(String FWNHHMWRA) {
		this.FWNHHMWRA = FWNHHMWRA;
		return this;
	}

	public HHRegisterEntryDTO withELCO(String ELCO) {
		this.ELCO = ELCO;
		return this;
	}
	
	public HHRegisterEntryDTO withuser_type(String user_type) {
		this.user_type = user_type;
		return this;
	}

	public HHRegisterEntryDTO withexternal_user_ID(String external_user_ID) {
		this.external_user_ID = external_user_ID;
		return this;
	}
	
	public HHRegisterEntryDTO withcurrent_formStatus(String current_formStatus) {
		this.current_formStatus = current_formStatus;
		return this;
	}

	public HHRegisterEntryDTO withELCODETAILS(List<Map<String, String>> ELCODETAILS) {
		this.ELCODETAILS = ELCODETAILS;
		return this;
	}
	
	public HHRegisterEntryDTO withmultimediaAttachments(List<Map<String, String>> multimediaAttachments) {
		this.multimediaAttachments = multimediaAttachments;
		return this;
	}
	
	public HHRegisterEntryDTO withDetails(Map<String, String> details) {
        this.details = new HashMap<>(details);
        return this;
    }

	public HHRegisterEntryDTO withReceivedTime(DateTime received_time){
		this.received_time = received_time;
		return this;
	}
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o, "id", "revision");
	}
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, "id", "revision");
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}