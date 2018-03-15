/** 
 * Project Name:restful-server 
 * File Name:RestfulServiceHandler.java 
 * Package Name:com.fourfaith.restful.server.http 
 * Date:2016年3月26日上午9:26:40 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.http;

import io.netty.handler.codec.http.FullHttpRequest;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fourfaith.https.server.anno.exec.RestfulServiceController;
import com.fourfaith.https.server.comm.RestfulConstant;
import com.fourfaith.https.server.http.wrapper.HttpParameterWrapperFactory;
import com.google.gson.JsonObject;

/**
 * ClassName: RestfulServiceHandler <br/>
 * Function: restful 服务处理器是在http服务请求与真正调用restful web
 * api方法之间的中间层，该层负责参数的封装，请求的转发及结果的返回. <br/>
 * date: 2016年3月26日 上午9:26:40 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class RestfulServiceHandler implements Serializable {

	private static final long serialVersionUID = -58148774863860668L;
	private static final Logger logger = Logger.getLogger(RestfulServiceHandler.class);
	private static RestfulServiceHandler handler = new RestfulServiceHandler();
	private static final String questionMark = "?";

	private RestfulServiceHandler() {

	}

	public static RestfulServiceHandler getInstance() {
		return handler;
	}

	/**
	 * invoke:调用相关服务方法. <br/>
	 * 
	 * @author chenyuepeng
	 * @param request
	 * @return JSON
	 * @since restful-server 2.0.0
	 */
	public static String invoke(FullHttpRequest request) {
		Map<String, String> parameter = HttpParameterWrapperFactory.buildWrapper(request.method()).wrap(request);
		int endIndex = request.uri().indexOf(questionMark);
		endIndex = endIndex < 0 ? request.uri().length() : endIndex;
		String restPath = getPath(request.uri().substring(0, endIndex));

		if (StringUtils.isNotBlank(restPath)) {
			try {
				logger.debug("restful path[" + restPath + "]");
				return RestfulServiceController.getInstance().invoke(restPath, parameter);
			} catch (Exception ex) {
				JsonObject error = new JsonObject();
				error.addProperty(RestfulConstant.SUCCESS, "false");
				error.addProperty(RestfulConstant.MESSAGE, ex.getMessage());
				logger.error(ex.getMessage(), ex);
				return error.toString();
			}
		}

		return StringUtils.EMPTY;
	}

	private static String getPath(String _uri) {
		return _uri.substring(_uri.indexOf(RestfulConstant.API_PRE_FIX) + RestfulConstant.API_PRE_FIX.length());
	}

}
