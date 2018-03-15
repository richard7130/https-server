/** 
 * Project Name:restful-server 
 * File Name:GetMethodParameterWrapper.java 
 * Package Name:com.fourfaith.restful.server.http.wrapper.method 
 * Date:2016年3月26日下午2:26:52 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.http.wrapper.method;

import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fourfaith.https.server.http.wrapper.IHttpParameterWrapper;

/**
 * ClassName: GetMethodParameterWrapper <br/>
 * Function: Http Get方法参数包装器. <br/>
 * date: 2016年3月26日 下午2:26:52 <br/>
 * 
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class GetMethodParameterWrapper implements IHttpParameterWrapper {

	private static final long serialVersionUID = -1050820868329905829L;
	private static final Logger logger = Logger.getLogger(GetMethodParameterWrapper.class);

	/**
	 * @see com.fourfaith.restful.server.http.wrapper.IHttpParameterWrapper#wrap(io.netty.handler.codec.http.FullHttpRequest)
	 */
	@Override
	public Map<String, String> wrap(FullHttpRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		int startIndex = request.uri().indexOf("?");
		if (startIndex > -1) {
			String param = request.uri().substring(request.uri().indexOf("?") + 1);
			String[] arr = param.split("&");
			if (arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					parameters.put(arr[i].split("=")[0], arr[i].split("=")[1]);
				}
			}
		}

		logger.debug("parameters: " + parameters);
		return parameters;
	}
}