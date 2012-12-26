package com.krislq.history.manager;

import org.apache.http.Header;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class HttpResponseException extends Exception {
	private static final long serialVersionUID = 2425069222735716912L;
	/**
	 * http status code.
	 */
	private int state;
	/**
	 * http response entity
	 */
	private String result;
	/**
	 * response header
	 */
	private Header headers[] = null;

	public HttpResponseException(int state) {
		this(state, "");
	}
	public HttpResponseException(int state,String result) {
		this(state, result, null);
	}
	public HttpResponseException(int state,String result,Header[] headers) {
		super("Wrong HTTP requested that the error status is " + state);
		this.state = state;
		this.result = result;
		this.headers = headers;
	}

	public HttpResponseException(int state, Throwable throwable) {
		super("Wrong HTTP requested that the error status is " + state,
				throwable);
		this.state = state;
	}

	public int getState() {
		return state;
	}

	public String getResult() {
		return result;
	}
	public Header[] getHeaders() {
		return headers;
	}
	
}
