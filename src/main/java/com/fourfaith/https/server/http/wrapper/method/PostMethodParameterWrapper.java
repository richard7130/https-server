/** 
 * Project Name:restful-server 
 * File Name:PostMethodParameterWrapper.java 
 * Package Name:com.fourfaith.restful.server.http.wrapper.method 
 * Date:2016年3月26日下午2:27:20 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.http.wrapper.method;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.InterfaceHttpData.HttpDataType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fourfaith.https.server.http.wrapper.IHttpParameterWrapper;

/**
 * ClassName: PostMethodParameterWrapper <br/>
 * Function: Http Post方法参数构造器. <br/>
 * date: 2016年3月26日 下午2:27:20 <br/>
 * 
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class PostMethodParameterWrapper implements IHttpParameterWrapper {

	private static final long serialVersionUID = -3164377842213230220L;
	private static final Logger logger = Logger.getLogger(PostMethodParameterWrapper.class);

	/**
	 * @see com.fourfaith.restful.server.http.wrapper.IHttpParameterWrapper#wrap(io.netty.handler.codec.http.FullHttpRequest)
	 */
	@Override
	public Map<String, String> wrap(FullHttpRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		if(request.content().readableBytes() > 0) {
			HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), request);
			List<InterfaceHttpData> datas = decoder.getBodyHttpDatas();
			try {
				if (datas != null && datas.size() > 0) {
					for (InterfaceHttpData postData : datas) {
						if (postData.getHttpDataType() == HttpDataType.Attribute) {
							Attribute attribute = (Attribute) postData;
							parameters.put(attribute.getName(), attribute.getValue());
						}
					}
				}
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		return parameters;
	}

}