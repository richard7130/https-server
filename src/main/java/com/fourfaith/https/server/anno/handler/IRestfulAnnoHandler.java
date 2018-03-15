/** 
 * Project Name:restful-server 
 * File Name:IRestfulAnnoHandler.java 
 * Package Name:com.fourfaith.restful.server.anno.handler 
 * Date:2016年3月26日上午8:43:15 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */ 

package com.fourfaith.https.server.anno.handler;

import java.io.Serializable;

import com.fourfaith.https.server.exception.RestfulAnnoException;

/**
 * ClassName: IRestfulAnnoHandler <br/>
 * Function: Restful注解处理器接口. <br/>
 * date: 2016年3月26日 上午8:43:15 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public interface IRestfulAnnoHandler extends Serializable {

	/**
	 * handle:根据qName对相应的restful注解进行处理. <br/>
	 * @author chenyuepeng
	 * @param qName
	 * @throws RestfulAnnoException
	 * @since restful-server 2.0.0
	 */
	public String handle(String qName) throws RestfulAnnoException;
	
}
