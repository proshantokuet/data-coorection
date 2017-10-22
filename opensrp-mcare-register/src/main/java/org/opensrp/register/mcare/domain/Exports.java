package org.opensrp.register.mcare.domain;

import org.codehaus.jackson.annotate.JsonProperty;
import org.ektorp.support.TypeDiscriminator;
import org.motechproject.model.MotechBaseDataObject;

@TypeDiscriminator("doc.type === 'Exports'")
public class Exports extends MotechBaseDataObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("formName")
	private String formName;
	@JsonProperty("reportName")
	private String reportName;
	@JsonProperty("user")
	private String user;
	@JsonProperty("created_date")
	private String created_date;
	@JsonProperty("timeStamp")
	private long timeStamp;
	public Exports() {
		// TODO Auto-generated constructor stub
	}
	public Exports(String formName, String reportName, String user,
			String created_date,long timeStamp) {
		super();
		this.formName = formName;
		this.reportName = reportName;
		this.user = user;
		this.created_date = created_date;
		this.timeStamp = timeStamp;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((created_date == null) ? 0 : created_date.hashCode());
		result = prime * result
				+ ((formName == null) ? 0 : formName.hashCode());
		result = prime * result
				+ ((reportName == null) ? 0 : reportName.hashCode());
		result = prime * result + (int) (timeStamp ^ (timeStamp >>> 32));
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exports other = (Exports) obj;
		if (created_date == null) {
			if (other.created_date != null)
				return false;
		} else if (!created_date.equals(other.created_date))
			return false;
		if (formName == null) {
			if (other.formName != null)
				return false;
		} else if (!formName.equals(other.formName))
			return false;
		if (reportName == null) {
			if (other.reportName != null)
				return false;
		} else if (!reportName.equals(other.reportName))
			return false;
		if (timeStamp != other.timeStamp)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Exports [formName=" + formName + ", reportName=" + reportName
				+ ", user=" + user + ", created_date=" + created_date
				+ ", timeStamp=" + timeStamp + "]";
	}
	
	
	

	
}
