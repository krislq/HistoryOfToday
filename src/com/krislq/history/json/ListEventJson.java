package com.krislq.history.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
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
