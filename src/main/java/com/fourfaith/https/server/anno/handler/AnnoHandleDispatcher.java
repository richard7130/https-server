/** 
 * Project Name:restful-server 
 * File Name:AnnoHandleDispatcher.java 
 * Package Name:com.fourfaith.restful.server.anno.handler 
 * Date:2016年3月26日上午8:40:58 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.anno.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.fourfaith.restful.anno.Path;

/**
 * ClassName: AnnoHandleDispatcher <br/>
 * Function: 注解处理分发器，根据不同的restful 注解，使用不同的注解处理器进行处理. <br/>
 * date: 2016年3月26日 上午8:40:58 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
@SuppressWarnings("rawtypes")
public class AnnoHandleDispatcher {
	
	private static final Logger logger = Logger.getLogger(AnnoHandleDispatcher.class);
	private static final Map<Class, IRestfulAnnoHandler> d = new ConcurrentHashMap<Class, IRestfulAnnoHandler>();

	static {
		d.put(Path.class, new PathRestfulAnnoHandler());
	}

	/**
	 * IRestfulAnnoHandler:获取注解处理器. <br/>
	 * 
	 * @author chenyuepeng
	 * @param claz
	 * @return IRestfulAnnoHandler
	 * @since restful-server 2.0.0
	 */
	public static IRestfulAnnoHandler getAnnoHandler(Class claz) {
		return d.get(claz);
	}

	/**
	 * dispatchHandle:根据不同的restful注解分发注解处理. <br/>
	 * @author chenyuepeng
	 * @param qName
	 * @since restful-server 2.0.0
	 */
	public static void dispatchHandle(String qName) {
		try {
			if (Class.forName(qName).getAnnotation(Path.class) != null) {
				logger.debug("qName: " + qName);
				getAnnoHandler(Path.class).handle(qName);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
