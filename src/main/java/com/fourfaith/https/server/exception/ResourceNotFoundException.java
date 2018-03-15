/** 
 * Project Name:restful-server 
 * File Name:ResourceNotFoundException.java 
 * Package Name:com.fourfaith.restful.server.exception 
 * Date:2016年3月26日上午11:19:18 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.exception;

/**
 * ClassName: ResourceNotFoundException <br/>
 * Function: restful resource not found exception. <br/>
 * date: 2016年3月26日 上午11:19:18 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class ResourceNotFoundException extends RestfulAnnoException {

	private static final long serialVersionUID = -6205798145593858601L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}

}
