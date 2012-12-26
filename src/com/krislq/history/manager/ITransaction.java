package com.krislq.history.manager;


/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public interface ITransaction{
	/**
	 * 
	 * @param result  json result
	 */
	public void transactionOver(String result);
	/**
	 * 
	 * @param erroCode error code,like 300,400,404,5000
	 * @param result response entity
	 * @param e	exception instance
	 */
	public void transactionException(int erroCode,String result,Exception e);
}
