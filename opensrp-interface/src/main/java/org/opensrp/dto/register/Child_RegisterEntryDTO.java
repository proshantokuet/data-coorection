package org.opensrp.dto.register;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class Child_RegisterEntryDTO {

	@JsonProperty
	private String caseId;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String TODAY;
	@JsonProperty
	private String START;
	@JsonProperty
	private String END;
	@JsonProperty
	private String encc1_current_form_status;
	@JsonProperty
	private String encc2_current_form_status;
	@JsonProperty
	private String encc3_current_form_status;
	@JsonProperty
	private String isClosed;
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private Map<String, String> enccVisitOne;
	@JsonProperty
	private Map<String, String> enccVisitTwo;
	@JsonProperty
	private Map<String, String> enccVisitThree;
	@JsonProperty
	private long SUBMISSIONDATE;

	public Child_RegisterEntryDTO() {
		details = new HashMap<String, String>();
		enccVisitOne = new HashMap<String, String>();
		enccVisitTwo = new HashMap<String, String>();
		enccVisitThree = new HashMap<String, String>();
	}

	public Child_RegisterEntryDTO withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public Child_RegisterEntryDTO withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Child_RegisterEntryDTO withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Child_RegisterEntryDTO withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public Child_RegisterEntryDTO withTODAY(String lmp) {
		this.TODAY = lmp;
		return this;
	}

	public Child_RegisterEntryDTO withSTART(String START) {
		this.START = START;
		return this;
	}

	public Child_RegisterEntryDTO withEND(String END) {
		this.END = END;
		return this;
	}

	public Child_RegisterEntryDTO withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}

	public Child_RegisterEntryDTO withENCCVisitOne(Map<String, String> enccVisitOne) {
		this.enccVisitOne = new HashMap<>(enccVisitOne);
		return this;
	}

	public Child_RegisterEntryDTO withENCCVisitTwo(Map<String, String> enccVisitTwo) {
		this.enccVisitTwo = new HashMap<>(enccVisitTwo);
		return this;
	}

	public Child_RegisterEntryDTO withENCCVisitThree(Map<String, String> enccVisitThree) {
		this.enccVisitThree = new HashMap<>(enccVisitThree);
		return this;
	}

	public Child_RegisterEntryDTO setEncc1_current_form_status(String encc1_current_form_status) {
		this.encc1_current_form_status = encc1_current_form_status;
		return this;
	}

	public Child_RegisterEntryDTO setEncc2_current_form_status(String encc2_current_form_status) {
		this.encc2_current_form_status = encc2_current_form_status;
		return this;
	}

	public Child_RegisterEntryDTO setEncc3_current_form_status(String encc3_current_form_status) {
		this.encc3_current_form_status = encc3_current_form_status;
		return this;
	}

	public Child_RegisterEntryDTO withSUBMISSIONDATE(long SUBMISSIONDATE) {
		this.SUBMISSIONDATE = SUBMISSIONDATE;
		return this;
	}

	public Child_RegisterEntryDTO setIsClosed(boolean isClosed) {
		this.isClosed = Boolean.toString(isClosed);
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
