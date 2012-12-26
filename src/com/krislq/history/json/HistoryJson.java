package com.krislq.history.json;

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
public class HistoryJson {
	@JsonProperty("month")
	private String month;
	@JsonProperty("day")
	private String day;
	@JsonProperty("dateLink")
	private String dateLink;

	@JsonProperty("listEvent")
	private ListEventJson	listEvent;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getDateLink() {
		return dateLink;
	}

	public void setDateLink(String dateLink) {
		this.dateLink = dateLink;
	}

	public ListEventJson getListEvent() {
		return listEvent;
	}

	public void setListEvent(ListEventJson listEvent) {
		this.listEvent = listEvent;
	}
	
	
}
