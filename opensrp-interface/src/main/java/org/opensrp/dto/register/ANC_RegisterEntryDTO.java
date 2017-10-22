package org.opensrp.dto.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonProperty;

public class ANC_RegisterEntryDTO {
	@JsonProperty
	private String caseId;
	@JsonProperty
	private String INSTANCEID;
	@JsonProperty
	private String PROVIDERID;
	@JsonProperty
	private String LOCATIONID;
	@JsonProperty
	private String anc1_current_form_status;
	@JsonProperty
	private String anc2_current_form_status;
	@JsonProperty
	private String anc3_current_form_status;
	@JsonProperty
	private String anc4_current_form_status;
	@JsonProperty
	private String pnc1_current_form_status;
	@JsonProperty
	private String pnc2_current_form_status;
	@JsonProperty
	private String pnc3_current_form_status;
	@JsonProperty
	private String bnf_current_form_status;
	@JsonProperty
	private String mother_gobhhid;
	@JsonProperty
	private String mother_jivhhid;
	@JsonProperty
	private String mother_first_name;
	@JsonProperty
	private String mother_husname;
	@JsonProperty
	private String mother_wom_nid;
	@JsonProperty
	private String mother_wom_bid;
	@JsonProperty
	private String mother_wom_age;
	@JsonProperty
	private String mother_mauza;
	@JsonProperty
	private String mother_valid;
	@JsonProperty
	private String FWWOMUNION;
	@JsonProperty
	private String FWWOMWARD;
	@JsonProperty
	private String FWWOMSUBUNIT;
	@JsonProperty
	private String FWVG;
	@JsonProperty
	private String FWHRP;
	@JsonProperty
	private String FWHR_PSR;
	@JsonProperty
	private String FWFLAGVALUE;
	@JsonProperty
	private String FWSORTVALUE;
	@JsonProperty
	private String mother_wom_lmp;
	@JsonProperty
	private String TODAY;
	@JsonProperty
	private String START;
	@JsonProperty
	private String END;
	@JsonProperty
	private String relationalid;
	@JsonProperty
	private String isClosed;
	@JsonProperty
	private Map<String, String> details;
	@JsonProperty
	private Map<String, String> ancVisitOne;
	@JsonProperty
	private Map<String, String> ancVisitTwo;
	@JsonProperty
	private Map<String, String> ancVisitThree;
	@JsonProperty
	private Map<String, String> ancVisitFour;
	@JsonProperty
	private List<Map<String, String>> bnfVisitDetails;
	@JsonProperty
	private Map<String, String> pncVisitOne;
	@JsonProperty
	private Map<String, String> pncVisitTwo;
	@JsonProperty
	private Map<String, String> pncVisitThree;
	@JsonProperty
	private long SUBMISSIONDATE;

	public ANC_RegisterEntryDTO() {
		this.ancVisitOne = new HashMap<>();
		this.ancVisitTwo = new HashMap<>();
		this.ancVisitThree = new HashMap<>();
		this.ancVisitFour = new HashMap<>();
		this.pncVisitOne = new HashMap<>();
		this.pncVisitTwo = new HashMap<>();
		this.pncVisitThree = new HashMap<>();
		this.bnfVisitDetails = new ArrayList<>();
		this.setIsClosed(false);
	}

	public ANC_RegisterEntryDTO withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public ANC_RegisterEntryDTO withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public ANC_RegisterEntryDTO withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public ANC_RegisterEntryDTO withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public ANC_RegisterEntryDTO withanc1_current_form_status(String anc1_current_form_status) {
		this.anc1_current_form_status = anc1_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO withanc2_current_form_status(String anc2_current_form_status) {
		this.anc2_current_form_status = anc2_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO withanc3_current_form_status(String anc3_current_form_status) {
		this.anc3_current_form_status = anc3_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO withanc4_current_form_status(String anc4_current_form_status) {
		this.anc4_current_form_status = anc4_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO setPnc1_current_form_status(String pnc1_current_form_status) {
		this.pnc1_current_form_status = pnc1_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO setPnc2_current_form_status(String pnc2_current_form_status) {
		this.pnc2_current_form_status = pnc2_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO setPnc3_current_form_status(String pnc3_current_form_status) {
		this.pnc3_current_form_status = pnc3_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO setBnf_current_form_status(String bnf_current_form_status) {
		this.bnf_current_form_status = bnf_current_form_status;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_husname(String mother_husname) {
		this.mother_husname = mother_husname;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_gobhhid(String mother_gobhhid) {
		this.mother_gobhhid = mother_gobhhid;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_jivhhid(String mother_jivhhid) {
		this.mother_jivhhid = mother_jivhhid;
		return this;
	}

	public ANC_RegisterEntryDTO withJmother_first_name(String mother_first_name) {
		this.mother_first_name = mother_first_name;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_wom_nid(String mother_wom_nid) {
		this.mother_wom_nid = mother_wom_nid;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_wom_bid(String mother_wom_bid) {
		this.mother_wom_bid = mother_wom_bid;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_wom_age(String mother_wom_age) {
		this.mother_wom_age = mother_wom_age;
		return this;
	}

	public ANC_RegisterEntryDTO setFWWOMUNION(String fWWOMUNION) {
		this.FWWOMUNION = fWWOMUNION;
		return this;
	}

	public ANC_RegisterEntryDTO setFWWOMWARD(String fWWOMWARD) {
		this.FWWOMWARD = fWWOMWARD;
		return this;
	}

	public ANC_RegisterEntryDTO setFWWOMSUBUNIT(String fWWOMSUBUNIT) {
		this.FWWOMSUBUNIT = fWWOMSUBUNIT;
		return this;
	}

	public ANC_RegisterEntryDTO setmother_mauza(String mother_mauza) {
		this.mother_mauza = mother_mauza;
		return this;
	}

	public ANC_RegisterEntryDTO setmother_valid(String mother_valid) {
		this.mother_valid = mother_valid;
		return this;
	}

	public ANC_RegisterEntryDTO setFWVG(String fWVG) {
		FWVG = fWVG;
		return this;
	}

	public ANC_RegisterEntryDTO setFWHRP(String fWHRP) {
		FWHRP = fWHRP;
		return this;
	}

	public ANC_RegisterEntryDTO setFWHR_PSR(String fWHR_PSR) {
		FWHR_PSR = fWHR_PSR;
		return this;
	}

	public ANC_RegisterEntryDTO setFWFLAGVALUE(String fWFLAGVALUE) {
		FWFLAGVALUE = fWFLAGVALUE;
		return this;
	}

	public ANC_RegisterEntryDTO setFWSORTVALUE(String fWSORTVALUE) {
		FWSORTVALUE = fWSORTVALUE;
		return this;
	}

	public ANC_RegisterEntryDTO setIsClosed(boolean isClosed) {
		this.isClosed = Boolean.toString(isClosed);
		return this;
	}

	public ANC_RegisterEntryDTO withIsClosed(String isClosed) {
		this.isClosed = isClosed;
		return this;
	}

	public ANC_RegisterEntryDTO withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public ANC_RegisterEntryDTO withmother_wom_lmp(String mother_wom_lmp) {
		this.mother_wom_lmp = mother_wom_lmp;
		return this;
	}

	public ANC_RegisterEntryDTO withSTART(String START) {
		this.START = START;
		return this;
	}

	public ANC_RegisterEntryDTO withEND(String END) {
		this.END = END;
		return this;
	}

	public ANC_RegisterEntryDTO withRelationalid(String relationalid) {
		this.relationalid = relationalid;
		return this;
	}

	public ANC_RegisterEntryDTO withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}

	public ANC_RegisterEntryDTO withANCVisitOne(Map<String, String> ancVisitOne) {
		this.ancVisitOne = new HashMap<>(ancVisitOne);
		return this;
	}

	public ANC_RegisterEntryDTO withANCVisitTwo(Map<String, String> ancVisitTwo) {
		this.ancVisitTwo = new HashMap<>(ancVisitTwo);
		return this;
	}

	public ANC_RegisterEntryDTO withANCVisitThree(Map<String, String> ancVisitThree) {
		this.ancVisitThree = new HashMap<>(ancVisitThree);
		return this;
	}

	public ANC_RegisterEntryDTO withANCVisitFour(Map<String, String> ancVisitFour) {
		this.ancVisitFour = new HashMap<>(ancVisitFour);
		return this;
	}

	public ANC_RegisterEntryDTO withBNFVisitDetails(List<Map<String, String>> bnfVisitDetails) {
		this.bnfVisitDetails = bnfVisitDetails;
		return this;
	}

	public ANC_RegisterEntryDTO withPNCVisitOne(Map<String, String> pncVisitOne) {
		this.pncVisitOne = new HashMap<>(pncVisitOne);
		return this;
	}

	public ANC_RegisterEntryDTO withPNCVisitTwo(Map<String, String> pncVisitTwo) {
		this.pncVisitTwo = new HashMap<>(pncVisitTwo);
		return this;
	}

	public ANC_RegisterEntryDTO withPNCVisitThree(Map<String, String> pncVisitThree) {
		this.pncVisitThree = new HashMap<>(pncVisitThree);
		return this;
	}

	public ANC_RegisterEntryDTO withSUBMISSIONDATE(long SUBMISSIONDATE) {
		this.SUBMISSIONDATE = SUBMISSIONDATE;
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
