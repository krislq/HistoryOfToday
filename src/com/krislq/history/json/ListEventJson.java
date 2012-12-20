package com.krislq.history.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ListEventJson {
	@JsonProperty("bigEvent")
	private CategoryJson bigEvent;

	@JsonProperty("birth")
	private CategoryJson birth;

	@JsonProperty("died")
	private CategoryJson died;

	@JsonProperty("selfEvent")
	private List<ContentJson> selfEvent;

	public CategoryJson getBigEvent() {
		return bigEvent;
	}

	public void setBigEvent(CategoryJson bigEvent) {
		this.bigEvent = bigEvent;
	}

	public CategoryJson getBirth() {
		return birth;
	}

	public void setBirth(CategoryJson birth) {
		this.birth = birth;
	}

	public CategoryJson getDied() {
		return died;
	}

	public void setDied(CategoryJson died) {
		this.died = died;
	}

	public List<ContentJson> getSelfEvent() {
		return selfEvent;
	}

	public void setSelfEvent(List<ContentJson> selfEvent) {
		this.selfEvent = selfEvent;
	}

	
}
