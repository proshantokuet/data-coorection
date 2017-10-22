/**
 * @author julkar nain 
 */
package org.opensrp.register.mcare.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'Mother'")
public class Mother extends MotechBaseDataObject {
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
	@JsonProperty
	private String FWWOMDISTRICT;
	@JsonProperty
	private String FWWOMUPAZILLA;
	@JsonProperty
	private long clientVersion;
	@JsonProperty("timeStamp")
	private Long timeStamp;
	public Mother() {
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

	public Mother withCASEID(String caseId) {
		this.caseId = caseId;
		return this;
	}
	
	public Long getTimeStamp() {
		return timeStamp;
	}

	public Mother setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	public Mother withFWWOMDISTRICT(String FWWOMDISTRICT) {
		this.FWWOMDISTRICT = FWWOMDISTRICT;
		return this;
	}
	public Mother withFWWOMUPAZILLA(String FWWOMUPAZILLA) {
		this.FWWOMUPAZILLA = FWWOMUPAZILLA;
		return this;
	}
	
	public Mother withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public Mother withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Mother withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Mother withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public Mother withanc1_current_form_status(String anc1_current_form_status) {
		this.anc1_current_form_status = anc1_current_form_status;
		return this;
	}

	public Mother withanc2_current_form_status(String anc2_current_form_status) {
		this.anc2_current_form_status = anc2_current_form_status;
		return this;
	}

	public Mother withanc3_current_form_status(String anc3_current_form_status) {
		this.anc3_current_form_status = anc3_current_form_status;
		return this;
	}

	public Mother withanc4_current_form_status(String anc4_current_form_status) {
		this.anc4_current_form_status = anc4_current_form_status;
		return this;
	}

	public Mother setPnc1_current_form_status(String pnc1_current_form_status) {
		this.pnc1_current_form_status = pnc1_current_form_status;
		return this;
	}

	public Mother setPnc2_current_form_status(String pnc2_current_form_status) {
		this.pnc2_current_form_status = pnc2_current_form_status;
		return this;
	}

	public Mother setPnc3_current_form_status(String pnc3_current_form_status) {
		this.pnc3_current_form_status = pnc3_current_form_status;
		return this;
	}

	public Mother setBnf_current_form_status(String bnf_current_form_status) {
		this.bnf_current_form_status = bnf_current_form_status;
		return this;
	}

	public Mother withmother_husname(String mother_husname) {
		this.mother_husname = mother_husname;
		return this;
	}

	public Mother withmother_gobhhid(String mother_gobhhid) {
		this.mother_gobhhid = mother_gobhhid;
		return this;
	}

	public Mother withmother_jivhhid(String mother_jivhhid) {
		this.mother_jivhhid = mother_jivhhid;
		return this;
	}

	public Mother withJmother_first_name(String mother_first_name) {
		this.mother_first_name = mother_first_name;
		return this;
	}

	public Mother withmother_wom_nid(String mother_wom_nid) {
		this.mother_wom_nid = mother_wom_nid;
		return this;
	}

	public Mother withmother_wom_bid(String mother_wom_bid) {
		this.mother_wom_bid = mother_wom_bid;
		return this;
	}

	public Mother withmother_wom_age(String mother_wom_age) {
		this.mother_wom_age = mother_wom_age;
		return this;
	}

	public Mother setMother_mauza(String mother_mauza) {
		this.mother_mauza = mother_mauza;
		return this;
	}

	public Mother setMother_valid(String mother_valid) {
		this.mother_valid = mother_valid;
		return this;
	}

	public Mother setFWWOMUNION(String fWWOMUNION) {
		this.FWWOMUNION = fWWOMUNION;
		return this;
	}

	public Mother setFWWOMWARD(String fWWOMWARD) {
		this.FWWOMWARD = fWWOMWARD;
		return this;
	}

	public Mother setFWWOMSUBUNIT(String fWWOMSUBUNIT) {
		this.FWWOMSUBUNIT = fWWOMSUBUNIT;
		return this;
	}

	public Mother setFWVG(String fWVG) {
		FWVG = fWVG;
		return this;
	}

	public Mother setFWHRP(String fWHRP) {
		FWHRP = fWHRP;
		return this;
	}

	public Mother setFWHR_PSR(String fWHR_PSR) {
		FWHR_PSR = fWHR_PSR;
		return this;
	}

	public Mother setFWFLAGVALUE(String fWFLAGVALUE) {
		FWFLAGVALUE = fWFLAGVALUE;
		return this;
	}

	public Mother setFWSORTVALUE(String fWSORTVALUE) {
		FWSORTVALUE = fWSORTVALUE;
		return this;
	}

	public Mother setIsClosed(boolean isClosed) {
		this.isClosed = Boolean.toString(isClosed);
		return this;
	}

	public Mother withIsClosed(String isClosed) {
		this.isClosed = isClosed;
		return this;
	}

	public Mother withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public Mother withmother_wom_lmp(String mother_wom_lmp) {
		this.mother_wom_lmp = mother_wom_lmp;
		return this;
	}

	public Mother withSTART(String START) {
		this.START = START;
		return this;
	}

	public Mother withEND(String END) {
		this.END = END;
		return this;
	}

	public Mother withRelationalid(String relationalid) {
		this.relationalid = relationalid;
		return this;
	}

	public Mother withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}

	public Mother withANCVisitOne(Map<String, String> ancVisitOne) {
		this.ancVisitOne = new HashMap<>(ancVisitOne);
		return this;
	}

	public Mother withANCVisitTwo(Map<String, String> ancVisitTwo) {
		this.ancVisitTwo = new HashMap<>(ancVisitTwo);
		return this;
	}

	public Mother withANCVisitThree(Map<String, String> ancVisitThree) {
		this.ancVisitThree = new HashMap<>(ancVisitThree);
		return this;
	}

	public Mother withANCVisitFour(Map<String, String> ancVisitFour) {
		this.ancVisitFour = new HashMap<>(ancVisitFour);
		return this;
	}

	public Mother withBNFVisitDetails(List<Map<String, String>> bnfVisitDetails) {
		this.bnfVisitDetails = bnfVisitDetails;
		return this;
	}

	public Mother withPNCVisitOne(Map<String, String> pncVisitOne) {
		this.pncVisitOne = new HashMap<>(pncVisitOne);
		return this;
	}

	public Mother withPNCVisitTwo(Map<String, String> pncVisitTwo) {
		this.pncVisitTwo = new HashMap<>(pncVisitTwo);
		return this;
	}

	public Mother withPNCVisitThree(Map<String, String> pncVisitThree) {
		this.pncVisitThree = new HashMap<>(pncVisitThree);
		return this;
	}

	public Mother withSUBMISSIONDATE(long SUBMISSIONDATE) {
		this.SUBMISSIONDATE = SUBMISSIONDATE;
		return this;
	}
	
	public Mother withClientVersion(long clientVersion) {
		this.clientVersion = clientVersion;
		return this;
	}

	public String FWWOMDISTRICT() {
		return FWWOMDISTRICT;
	}
	public String FWWOMUPAZILLA() {
		return FWWOMUPAZILLA;
	}
	public String caseId() {
		return caseId;
	}

	public String INSTANCEID() {
		return INSTANCEID;
	}

	public String PROVIDERID() {
		return PROVIDERID;
	}

	public String LOCATIONID() {
		return LOCATIONID;
	}

	public String anc1_current_form_status() {
		return anc1_current_form_status;
	}

	public String anc2_current_form_status() {
		return anc2_current_form_status;
	}

	public String anc3_current_form_status() {
		return anc3_current_form_status;
	}

	public String anc4_current_form_status() {
		return anc4_current_form_status;
	}

	public String getPnc1_current_form_status() {
		return pnc1_current_form_status;
	}

	public String getPnc2_current_form_status() {
		return pnc2_current_form_status;
	}

	public String getPnc3_current_form_status() {
		return pnc3_current_form_status;
	}

	public String getBnf_current_form_status() {
		return bnf_current_form_status;
	}

	public String mother_husname() {
		return mother_husname;
	}

	public String mother_gobhhid() {
		return mother_gobhhid;
	}

	public String mother_jivhhid() {
		return mother_jivhhid;
	}

	public String mother_first_name() {
		return mother_first_name;
	}

	public String mother_wom_nid() {
		return mother_wom_nid;
	}

	public String mother_wom_bid() {
		return mother_wom_bid;
	}

	public String mother_wom_age() {
		return mother_wom_age;
	}

	public String getMother_mauza() {
		return mother_mauza;
	}

	public String getMother_valid() {
		return mother_valid;
	}

	public String getFWWOMUNION() {
		return FWWOMUNION;
	}

	public String getFWWOMWARD() {
		return FWWOMWARD;
	}

	public String getFWWOMSUBUNIT() {
		return FWWOMSUBUNIT;
	}

	public String getFWVG() {
		return FWVG;
	}

	public String getFWHRP() {
		return FWHRP;
	}

	public String getFWHR_PSR() {
		return FWHR_PSR;
	}

	public String getFWFLAGVALUE() {
		return FWFLAGVALUE;
	}

	public String getFWSORTVALUE() {
		return FWSORTVALUE;
	}

	public String isClosed() {
		return isClosed;
	}

	public String TODAY() {
		return TODAY;
	}

	public String mother_wom_lmp() {
		return mother_wom_lmp;
	}

	public String START() {
		return START;
	}

	public String END() {
		return END;
	}

	public String relationalid() {
		return relationalid;
	}

	private String getCaseId() {
		return caseId;
	}

	public String getRelationalid() {
		return relationalid;
	}

	public Map<String, String> details() {
		if (details == null)
			this.details = new HashMap<>();
		return details;
	}

	public String getDetail(String name) {
		if (details == null)
			this.details = new HashMap<>();
		return details.get(name);
	}

	public Map<String, String> ancVisitOne() {
		return ancVisitOne;
	}

	public Map<String, String> ancVisitTwo() {
		return ancVisitTwo;
	}

	public Map<String, String> ancVisitThree() {
		return ancVisitThree;
	}

	public Map<String, String> ancVisitFour() {
		return ancVisitFour;
	}

	public List<Map<String, String>> bnfVisitDetails() {
		if (bnfVisitDetails == null) {
			bnfVisitDetails = new ArrayList<>();
		}
		return bnfVisitDetails;
	}

	public String getbnfVisitDetails(String name) {
		if (bnfVisitDetails == null) {
			bnfVisitDetails = new ArrayList<>();
			return "";
		}
		return bnfVisitDetails.get(0).get(name);
	}

	public Map<String, String> pncVisitOne() {
		return pncVisitOne;
	}

	public Map<String, String> pncVisitTwo() {
		return pncVisitTwo;
	}

	public Map<String, String> pncVisitThree() {
		return pncVisitThree;
	}

	public long SUBMISSIONDATE() {
		return SUBMISSIONDATE;
	}
	
	public long clientVersion() {
		return clientVersion;
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