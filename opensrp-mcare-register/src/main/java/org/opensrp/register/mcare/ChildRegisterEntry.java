package org.opensrp.register.mcare;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChildRegisterEntry {

	private String caseId;

	private String INSTANCEID;

	private String LOCATIONID;

	private String PROVIDERID;

	private String TODAY;

	private String START;

	private String END;

	private String encc1_current_form_status;

	private String encc2_current_form_status;

	private String encc3_current_form_status;

	private String isClosed;

	private Map<String, String> details;

	private Map<String, String> enccVisitOne;

	private Map<String, String> enccVisitTwo;

	private Map<String, String> enccVisitThree;

	private long SUBMISSIONDATE;

	public ChildRegisterEntry() {
		details = new HashMap<String, String>();
		enccVisitOne = new HashMap<String, String>();
		enccVisitTwo = new HashMap<String, String>();
		enccVisitThree = new HashMap<String, String>();
	}

	public ChildRegisterEntry withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}

	public ChildRegisterEntry withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public ChildRegisterEntry withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public ChildRegisterEntry withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public ChildRegisterEntry withTODAY(String lmp) {
		this.TODAY = lmp;
		return this;
	}

	public ChildRegisterEntry withSTART(String START) {
		this.START = START;
		return this;
	}

	public ChildRegisterEntry withEND(String END) {
		this.END = END;
		return this;
	}

	public ChildRegisterEntry withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}

	public ChildRegisterEntry withENCCVisitOne(Map<String, String> enccVisitOne) {
		this.enccVisitOne = new HashMap<>(enccVisitOne);
		return this;
	}

	public ChildRegisterEntry withENCCVisitTwo(Map<String, String> enccVisitTwo) {
		this.enccVisitTwo = new HashMap<>(enccVisitTwo);
		return this;
	}

	public ChildRegisterEntry withENCCVisitThree(Map<String, String> enccVisitThree) {
		this.enccVisitThree = new HashMap<>(enccVisitThree);
		return this;
	}

	public ChildRegisterEntry setEncc1_current_form_status(String encc1_current_form_status) {
		this.encc1_current_form_status = encc1_current_form_status;
		return this;
	}

	public ChildRegisterEntry setEncc2_current_form_status(String encc2_current_form_status) {
		this.encc2_current_form_status = encc2_current_form_status;
		return this;
	}

	public ChildRegisterEntry setEncc3_current_form_status(String encc3_current_form_status) {
		this.encc3_current_form_status = encc3_current_form_status;
		return this;
	}

	public ChildRegisterEntry withSUBMISSIONDATE(long SUBMISSIONDATE) {
		this.SUBMISSIONDATE = SUBMISSIONDATE;
		return this;
	}

	public ChildRegisterEntry setIsClosed(boolean isClosed) {
		this.isClosed = Boolean.toString(isClosed);
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

	public String TODAY() {
		return TODAY;
	}

	public String START() {
		return START;
	}

	public String END() {
		return END;
	}

	private String getCaseId() {
		return caseId;
	}

	public Map<String, String> details() {
		return details;
	}

	public String getDetail(String name) {
		return details.get(name);
	}

	public Map<String, String> enccVisitOne() {
		return enccVisitOne;
	}

	public Map<String, String> enccVisitTwo() {
		return enccVisitTwo;
	}

	public Map<String, String> enccVisitThree() {
		return enccVisitThree;
	}

	public long SUBMISSIONDATE() {
		return SUBMISSIONDATE;
	}

	public String isClosed() {
		return isClosed;
	}

	public String getEncc1_current_form_status() {
		return encc1_current_form_status;
	}

	public String getEncc2_current_form_status() {
		return encc2_current_form_status;
	}

	public String getEncc3_current_form_status() {
		return encc3_current_form_status;
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
