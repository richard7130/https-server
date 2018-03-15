/** 
 * Project Name:restful-server 
 * File Name:RestfulConstant.java 
 * Package Name:com.fourfaith.restful.server.comm 
 * Date:2016年3月26日上午8:55:25 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.comm;

import org.apache.commons.lang3.StringUtils;

import com.fourfaith.https.server.utils.PropertiesUtil;

/**
 * ClassName: RestfulConstant <br/>
 * Function: Restful公共常量. <br/>
 * date: 2016年3月16日 上午8:55:25 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public interface RestfulConstant {

	/**
	 * 不执行uri 1
	 */
	String SKIP_URI_1 = "/favicon.ico";

	/**
	 * 不执行uri 2
	 */
	String SKIP_URI_2 = "/";

	/**
	 * Web api 前缀
	 */
	String RestfulApiPrefix = "restful.api.prefix";

	/**
	 * Restful 服务扫描包
	 */
	String RestfulScanPkg = "restful.scan.pkg";

	/**
	 * 成功字符串
	 */
	String SUCCESS = "success";

	/**
	 * 消息字符串
	 */
	String MESSAGE = "message";
	
	/**
	 * API前缀
	 */
	String API_PRE_FIX = PropertiesUtil.getProperty(RestfulConstant.RestfulApiPrefix);
	
	/**
	 * 执行文档输出uri 1
	 */
	String DOC_URI_1 = StringUtils.isBlank(API_PRE_FIX) ? "/doc" : "/"  + API_PRE_FIX + "/doc";

	/**
	 * 执行文档输出uri 2
	 */
	String DOC_URI_2 = StringUtils.isBlank(API_PRE_FIX) ? "/doc/" : "/" + API_PRE_FIX + "/doc/";
	
	/**
	 * 文档API JSON串
	 */
	String DOC_JSON = StringUtils.isBlank(API_PRE_FIX) ? "/doc/json" : "/" + API_PRE_FIX + "/doc/json";
	
	/**
	 * suye 服务内置配置文件名称
	 */
	String RESTFUL_IN_CONF = "restful-server.properties";
	
	/**
	 * suye 服务外置配置文件名称
	 */
	String RESTFUL_EXT_CONF = "restful-server-ext.properties";
}
