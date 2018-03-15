/** 
 * Project Name:restful-server 
 * File Name:ResourceDuplicateException.java 
 * Package Name:com.fourfaith.restful.server.exception 
 * Date:2016年3月26日下午5:55:53 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.exception;

/**
 * ClassName: ResourceDuplicateException <br/>
 * Function: duplicate resource exception. <br/>
 * date: 2016年3月26日 下午5:55:53 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class ResourceDuplicateException extends RestfulAnnoException {
	
	private static final long serialVersionUID = -4149362028545005282L;

	public ResourceDuplicateException() {
		super();
	}

	public ResourceDuplicateException(String message) {
		super(message);
	}

	public ResourceDuplicateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceDuplicateException(Throwable cause) {
		super(cause);
	}

}
