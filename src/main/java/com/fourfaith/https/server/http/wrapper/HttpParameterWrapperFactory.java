/** 
 * Project Name:restful-server 
 * File Name:HttpParameterWrapperFactory.java 
 * Package Name:com.fourfaith.restful.server.http.wrapper 
 * Date:2016年3月26日下午2:17:55 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.http.wrapper;

import io.netty.handler.codec.http.HttpMethod;

import com.fourfaith.https.server.http.wrapper.method.DeleteMethodParameterWrapper;
import com.fourfaith.https.server.http.wrapper.method.GetMethodParameterWrapper;
import com.fourfaith.https.server.http.wrapper.method.PostMethodParameterWrapper;
import com.fourfaith.https.server.http.wrapper.method.PutMethodParameterWrapper;

/**
 * ClassName: HttpParameterWrapperFactory <br/>
 * Function: Http参数包装器工厂，根据不同的Http请求方法封装请求参数. <br/>
 * date: 2016年3月26日 下午2:17:55 <br/>
 * 
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class HttpParameterWrapperFactory {

	private static IHttpParameterWrapper getWrapper = new GetMethodParameterWrapper();
	private static IHttpParameterWrapper postWrapper = new PostMethodParameterWrapper();
	private static IHttpParameterWrapper deleteWrapper = new DeleteMethodParameterWrapper();
	private static IHttpParameterWrapper putWrapper = new PutMethodParameterWrapper();

	/**
	 * buildWrapper:根据不同的请求方法，选用不同的Http请求参数构造器. <br/>
	 * 
	 * @author chenyuepeng
	 * @param method
	 * @return IHttpParameterWrapper
	 * @since restful-server 2.0.0
	 */
	public static IHttpParameterWrapper buildWrapper(HttpMethod method) {
		if (method.equals(HttpMethod.GET)) {
			return getWrapper;
		} else if (method.equals(HttpMethod.POST)) {
			return postWrapper;
		} else if (method.equals(HttpMethod.DELETE)) {
			return deleteWrapper;
		} else if (method.equals(HttpMethod.PUT)) {
			return putWrapper;
		}
		return getWrapper;
	}
}