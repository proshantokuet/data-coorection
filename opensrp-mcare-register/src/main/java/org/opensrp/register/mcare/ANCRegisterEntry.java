package org.opensrp.register.mcare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ANCRegisterEntry {

	private String caseId;

	private String INSTANCEID;

	private String PROVIDERID;

	private String LOCATIONID;

	private String anc1_current_form_status;

	private String anc2_current_form_status;

	private String anc3_current_form_status;

	private String anc4_current_form_status;

	private String pnc1_current_form_status;

	private String pnc2_current_form_status;

	private String pnc3_current_form_status;

	private String bnf_current_form_status;

	private String mother_gobhhid;

	private String mother_jivhhid;

	private String mother_first_name;

	private String mother_husname;

	private String mother_wom_nid;

	private String mother_wom_bid;

	private String mother_wom_age;

	private String mother_mauza;

	private String mother_valid;

	private String FWWOMUNION;

	private String FWWOMWARD;

	private String FWWOMSUBUNIT;

	private String FWVG;

	private String FWHRP;

	private String FWHR_PSR;

	private String FWFLAGVALUE;

	private String FWSORTVALUE;

	private String mother_wom_lmp;

	private String TODAY;

	private String START;

	private String END;

	private String relationalid;

	private String isClosed;

	private Map<String, String> details;

	private Map<String, String> ancVisitOne;

	private Map<String, String> ancVisitTwo;

	private Map<String, String> ancVisitThree;

	private Map<String, String> ancVisitFour;

	private List<Map<String, String>> bnfVisitDetails;

	private Map<String, String> pncVisitOne;

	private Map<String, String> pncVisitTwo;

	private Map<String, String> pncVisitThree;

	private long SUBMISSIONDATE;

	public ANCRegisterEntry() {
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

	public ANCRegisterEntry withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public ANCRegisterEntry withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public ANCRegisterEntry withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public ANCRegisterEntry withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public ANCRegisterEntry withanc1_current_form_status(String anc1_current_form_status) {
		this.anc1_current_form_status = anc1_current_form_status;
		return this;
	}

	public ANCRegisterEntry withanc2_current_form_status(String anc2_current_form_status) {
		this.anc2_current_form_status = anc2_current_form_status;
		return this;
	}

	public ANCRegisterEntry withanc3_current_form_status(String anc3_current_form_status) {
		this.anc3_current_form_status = anc3_current_form_status;
		return this;
	}

	public ANCRegisterEntry withanc4_current_form_status(String anc4_current_form_status) {
		this.anc4_current_form_status = anc4_current_form_status;
		return this;
	}

	public ANCRegisterEntry setPnc1_current_form_status(String pnc1_current_form_status) {
		this.pnc1_current_form_status = pnc1_current_form_status;
		return this;
	}

	public ANCRegisterEntry setPnc2_current_form_status(String pnc2_current_form_status) {
		this.pnc2_current_form_status = pnc2_current_form_status;
		return this;
	}

	public ANCRegisterEntry setPnc3_current_form_status(String pnc3_current_form_status) {
		this.pnc3_current_form_status = pnc3_current_form_status;
		return this;
	}

	public ANCRegisterEntry setBnf_current_form_status(String bnf_current_form_status) {
		this.bnf_current_form_status = bnf_current_form_status;
		return this;
	}

	public ANCRegisterEntry withmother_husname(String mother_husname) {
		this.mother_husname = mother_husname;
		return this;
	}

	public ANCRegisterEntry withmother_gobhhid(String mother_gobhhid) {
		this.mother_gobhhid = mother_gobhhid;
		return this;
	}

	public ANCRegisterEntry withmother_jivhhid(String mother_jivhhid) {
		this.mother_jivhhid = mother_jivhhid;
		return this;
	}

	public ANCRegisterEntry withJmother_first_name(String mother_first_name) {
		this.mother_first_name = mother_first_name;
		return this;
	}

	public ANCRegisterEntry withmother_wom_nid(String mother_wom_nid) {
		this.mother_wom_nid = mother_wom_nid;
		return this;
	}

	public ANCRegisterEntry withmother_wom_bid(String mother_wom_bid) {
		this.mother_wom_bid = mother_wom_bid;
		return this;
	}

	public ANCRegisterEntry withmother_wom_age(String mother_wom_age) {
		this.mother_wom_age = mother_wom_age;
		return this;
	}

	public ANCRegisterEntry setmother_mauza(String mother_mauza) {
		this.mother_mauza = mother_mauza;
		return this;
	}

	public ANCRegisterEntry setmother_valid(String mother_valid) {
		this.mother_valid = mother_valid;
		return this;
	}

	public ANCRegisterEntry setFWWOMUNION(String fWWOMUNION) {
		this.FWWOMUNION = fWWOMUNION;
		return this;
	}

	public ANCRegisterEntry setFWWOMWARD(String fWWOMWARD) {
		this.FWWOMWARD = fWWOMWARD;
		return this;
	}

	public ANCRegisterEntry setFWWOMSUBUNIT(String fWWOMSUBUNIT) {
		this.FWWOMSUBUNIT = fWWOMSUBUNIT;
		return this;
	}

	public ANCRegisterEntry setFWVG(String fWVG) {
		FWVG = fWVG;
		return this;
	}

	public ANCRegisterEntry setFWHRP(String fWHRP) {
		FWHRP = fWHRP;
		return this;
	}

	public ANCRegisterEntry setFWHR_PSR(String fWHR_PSR) {
		FWHR_PSR = fWHR_PSR;
		return this;
	}

	public ANCRegisterEntry setFWFLAGVALUE(String fWFLAGVALUE) {
		FWFLAGVALUE = fWFLAGVALUE;
		return this;
	}

	public ANCRegisterEntry setFWSORTVALUE(String fWSORTVALUE) {
		FWSORTVALUE = fWSORTVALUE;
		return this;
	}

	public ANCRegisterEntry withIsClosed(String isClosed) {
		this.isClosed = isClosed;
		return this;
	}

	public ANCRegisterEntry withTODAY(String TODAY) {
		this.TODAY = TODAY;
		return this;
	}

	public ANCRegisterEntry setIsClosed(boolean isClosed) {
		this.isClosed = Boolean.toString(isClosed);
		return this;
	}

	public ANCRegisterEntry withmother_wom_lmp(String mother_wom_lmp) {
		this.mother_wom_lmp = mother_wom_lmp;
		return this;
	}

	public ANCRegisterEntry withSTART(String START) {
		this.START = START;
		return this;
	}

	public ANCRegisterEntry withEND(String END) {
		this.END = END;
		return this;
	}

	public ANCRegisterEntry withRelationalid(String relationalid) {
		this.relationalid = relationalid;
		return this;
	}

	public ANCRegisterEntry withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}

	public ANCRegisterEntry withANCVisitOne(Map<String, String> ancVisitOne) {
		this.ancVisitOne = new HashMap<>(ancVisitOne);
		return this;
	}

	public ANCRegisterEntry withANCVisitTwo(Map<String, String> ancVisitTwo) {
		this.ancVisitTwo = new HashMap<>(ancVisitTwo);
		return this;
	}

	public ANCRegisterEntry withANCVisitThree(Map<String, String> ancVisitThree) {
		this.ancVisitThree = new HashMap<>(ancVisitThree);
		return this;
	}

	public ANCRegisterEntry withANCVisitFour(Map<String, String> ancVisitFour) {
		this.ancVisitFour = new HashMap<>(ancVisitFour);
		return this;
	}

	public ANCRegisterEntry withBNFVisitDetails(List<Map<String, String>> bnfVisitDetails) {
		this.bnfVisitDetails = bnfVisitDetails;
		return this;
	}

	public ANCRegisterEntry withPNCVisitOne(Map<String, String> pncVisitOne) {
		this.pncVisitOne = new HashMap<>(pncVisitOne);
		return this;
	}

	public ANCRegisterEntry withPNCVisitTwo(Map<String, String> pncVisitTwo) {
		this.pncVisitTwo = new HashMap<>(pncVisitTwo);
		return this;
	}

	public ANCRegisterEntry withPNCVisitThree(Map<String, String> pncVisitThree) {
		this.pncVisitThree = new HashMap<>(pncVisitThree);
		return this;
	}

	public ANCRegisterEntry withSUBMISSIONDATE(long SUBMISSIONDATE) {
		this.SUBMISSIONDATE = SUBMISSIONDATE;
		return this;
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

	public String getFWWOMUNION() {
		return FWWOMUNION;
	}

	public String getFWWOMWARD() {
		return FWWOMWARD;
	}

	public String getFWWOMSUBUNIT() {
		return FWWOMSUBUNIT;
	}

	public String getmother_mauza() {
		return mother_mauza;
	}

	public String getmother_valid() {
		return mother_valid;
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
