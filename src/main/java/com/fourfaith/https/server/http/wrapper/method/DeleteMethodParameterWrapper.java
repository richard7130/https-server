/** 
 * Project Name:restful-server 
 * File Name:DeleteMethodParameterWrapper.java 
 * Package Name:com.fourfaith.restful.server.http.wrapper.method 
 * Date:2016年3月26日下午1:30:04 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.http.wrapper.method;

import io.netty.handler.codec.http.FullHttpRequest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fourfaith.https.server.http.wrapper.IHttpParameterWrapper;

/**
 * ClassName: DeleteMethodParameterWrapper <br/>
 * Function: Http delete方法参数包装器. <br/>
 * date: 2016年3月26日 下午1:30:04 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class DeleteMethodParameterWrapper implements IHttpParameterWrapper {

	private static final long serialVersionUID = -7109845588658622483L;
	private static final Logger logger = Logger.getLogger(DeleteMethodParameterWrapper.class);
	
	@Override
	public Map<String, String> wrap(FullHttpRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		logger.debug("invoke http delete method wrap");
		//TODO
		return parameters;
	}

}
