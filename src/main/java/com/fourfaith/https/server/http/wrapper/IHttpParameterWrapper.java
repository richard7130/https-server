/** 
 * Project Name:restful-server 
 * File Name:IHttpParameterWrapper.java 
 * Package Name:com.fourfaith.restful.server.http.wrapper 
 * Date:2016年3月26日下午2:20:18 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */ 

package com.fourfaith.https.server.http.wrapper;

import io.netty.handler.codec.http.FullHttpRequest;

import java.io.Serializable;
import java.util.Map;

/**
 * ClassName: IHttpParameterWrapper <br/>
 * Function: Http参数包装器接口. <br/>
 * date: 2016年3月26日 下午2:20:18 <br/>
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public interface IHttpParameterWrapper extends Serializable {
	
	/**
	 * wrap:包装请求参数. <br/>
	 * @author chenyuepeng
	 * @return 2.0.0
	 * @since restful-server 2.0.0
	 */
	Map<String,String> wrap(FullHttpRequest request);
	
}
