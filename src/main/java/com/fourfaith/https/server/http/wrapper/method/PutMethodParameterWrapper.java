/** 
 * Project Name:restful-server 
 * File Name:PutMethodParameterWrapper.java 
 * Package Name:com.fourfaith.restful.server.http.wrapper.method 
 * Date:2016年3月26日下午2:35:06 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.http.wrapper.method;

import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fourfaith.https.server.http.wrapper.IHttpParameterWrapper;

/**
 * ClassName: PutMethodParameterWrapper <br/>
 * Function: Http put方法参数包装器. <br/>
 * date: 2016年3月26日 下午2:35:06 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class PutMethodParameterWrapper implements IHttpParameterWrapper {

	private static final long serialVersionUID = 9040198079628512576L;
	private static final Logger logger = Logger.getLogger(PutMethodParameterWrapper.class);

	/**
	 * @see com.fourfaith.restful.server.http.wrapper.IHttpParameterWrapper#wrap(io.netty.handler.codec.http.FullHttpRequest)
	 */
	@Override
	public Map<String, String> wrap(FullHttpRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		logger.debug("invoke http put method begin to warp parameters");
		//TODO
		return parameters;
	}

}
