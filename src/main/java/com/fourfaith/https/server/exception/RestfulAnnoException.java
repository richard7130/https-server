/** 
 * Project Name:restful-server 
 * File Name:RestfulAnnoException.java 
 * Package Name:com.fourfaith.restful.server.exception 
 * Date:2016年3月26日下午5:13:25 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.exception;

/**
 * ClassName: RestfulAnnotationException <br/>
 * Function: Restful注解异常. <br/>
 * date: 2016年3月26日 下午5:13:25 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class RestfulAnnoException extends RuntimeException {

	private static final long serialVersionUID = -8406750848254524838L;

	public RestfulAnnoException() {
		super();
	}
	
	public RestfulAnnoException(String message) {
		super(message);
	}

	public RestfulAnnoException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestfulAnnoException(Throwable cause) {
		super(cause);
	}

}
