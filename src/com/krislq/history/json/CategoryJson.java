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
