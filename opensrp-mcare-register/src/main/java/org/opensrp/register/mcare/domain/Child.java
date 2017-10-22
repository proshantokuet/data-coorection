/**
 * @author Asifur
 */
package org.opensrp.register.mcare.domain;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@TypeDiscriminator("doc.type === 'Child'")
public class Child extends MotechBaseDataObject {
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
	private String district;
	@JsonProperty
	private String division;	
	@JsonProperty
	private String upazilla;
	@JsonProperty
	private String union;
	@JsonProperty
	private String unit;
	@JsonProperty
	private String mouzaPara;
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
	@JsonProperty
	private long clientVersion;
	@JsonProperty
	private String user_type;
	@JsonProperty("timeStamp")
	private Long timeStamp;
	public Child() {
		details = new HashMap<String, String>();
		enccVisitOne = new HashMap<String, String>();
		enccVisitTwo = new HashMap<String, String>();
		enccVisitThree = new HashMap<String, String>();
	}

	public Child withCaseId(String caseId) {
		this.caseId = caseId;
		return this;
	}
	
	
	public Long getTimeStamp() {
		return timeStamp;
	}

	public Child setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
		return this;
	}

	public Child withUpazilla(String upazilla) {
		this.upazilla = upazilla;
		return this;
	}
	
	public Child withUnion(String union) {
		this.union = union;
		return this;
	}
	
	public Child withUnit(String unit) {
		this.unit = unit;
		return this;
	}
	
	public Child withMouzaPara(String mouzaPara) {
		this.mouzaPara = mouzaPara;
		return this;
	}
	
	public Child withDistrict(String district) {
		this.district = district;
		return this;
	}
	
	public Child withDivision(String division) {
		this.division = division;
		return this;
	}

	public Child withINSTANCEID(String INSTANCEID) {
		this.INSTANCEID = INSTANCEID;
		return this;
	}

	public Child withPROVIDERID(String PROVIDERID) {
		this.PROVIDERID = PROVIDERID;
		return this;
	}

	public Child withLOCATIONID(String LOCATIONID) {
		this.LOCATIONID = LOCATIONID;
		return this;
	}

	public Child withTODAY(String lmp) {
		this.TODAY = lmp;
		return this;
	}

	public Child withSTART(String START) {
		this.START = START;
		return this;
	}

	public Child withEND(String END) {
		this.END = END;
		return this;
	}

	public Child withDetails(Map<String, String> details) {
		this.details = new HashMap<>(details);
		return this;
	}

	public Child withENCCVisitOne(Map<String, String> enccVisitOne) {
		this.enccVisitOne = new HashMap<>(enccVisitOne);
		return this;
	}

	public Child withENCCVisitTwo(Map<String, String> enccVisitTwo) {
		this.enccVisitTwo = new HashMap<>(enccVisitTwo);
		return this;
	}

	public Child withENCCVisitThree(Map<String, String> enccVisitThree) {
		this.enccVisitThree = new HashMap<>(enccVisitThree);
		return this;
	}

	public Child setEncc1_current_form_status(String encc1_current_form_status) {
		this.encc1_current_form_status = encc1_current_form_status;
		return this;
	}

	public Child setEncc2_current_form_status(String encc2_current_form_status) {
		this.encc2_current_form_status = encc2_current_form_status;
		return this;
	}

	public Child setEncc3_current_form_status(String encc3_current_form_status) {
		this.encc3_current_form_status = encc3_current_form_status;
		return this;
	}

	public Child withSUBMISSIONDATE(long SUBMISSIONDATE) {
		this.SUBMISSIONDATE = SUBMISSIONDATE;
		return this;
	}
	public Child withClientVersion(long clientVersion) {
		this.clientVersion = clientVersion;
		return this;
	}

	public Child setIsClosed(boolean isClosed) {
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
	
	public long clientVersion() {
		return clientVersion;
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

	public String getUser_type() {
		return user_type;
	}

	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}

	public String getUpazilla() {
		return upazilla;
	}

	public String getUnion() {
		return union;
	}

	public String getUnit() {
		return unit;
	}

	public String getMouzaPara() {
		return mouzaPara;
	}
	

	public String getDistrict() {
		return district;
	}

	public String getDivision() {
		return division;
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