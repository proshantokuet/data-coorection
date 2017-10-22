package org.opensrp.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class FieldValue {
	@JsonProperty("id")
	private String id;
	@JsonProperty("field")
	private String field;
	@JsonProperty("value")
	private String value;
	@JsonProperty("type")
	private String type;

	public FieldValue(){
	}
	public FieldValue(String id, String field, String value, String type) {
		super();
		this.id = id;
		this.field = field;
		this.value = value;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FieldValue [id=" + id + ", field=" + field + ", value=" + value
				+ ", type=" + type + "]";
	}
	
}
