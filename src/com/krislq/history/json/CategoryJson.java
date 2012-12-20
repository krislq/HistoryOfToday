package com.krislq.history.json;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryJson {
	@JsonProperty("content")
	private List<ContentJson>   content;

	public List<ContentJson> getContent() {
		return content;
	}

	public void setContent(List<ContentJson> content) {
		this.content = content;
	}
	
}
