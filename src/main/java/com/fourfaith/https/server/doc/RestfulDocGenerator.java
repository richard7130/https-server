/** 
 * Project Name:restful-server 
 * File Name:RestfulDocGenerator.java 
 * Package Name:com.fourfaith.restful.server.doc 
 * Date:2016年3月26日下午3:46:22 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.doc;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * ClassName: RestfulDocGenerator <br/>
 * Function: Restful web服务API文档生成器. <br/>
 * date: 2016年3月26日 下午3:46:22 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public final class RestfulDocGenerator implements Serializable {

	private static final long serialVersionUID = -4812000393973318134L;
	private static final Logger logger = Logger.getLogger(RestfulDocGenerator.class);
	private static final String filePath = "RestfulDoc.html";
	private static String htmlDoc = "";

	/**
	 * generate:生成restful api文档. <br/>
	 * 
	 * @author chenyuepeng
	 * @return ByteBuf
	 * @since restful-server 2.0.0
	 */
	public static ByteBuf generate() throws Exception {
		if (StringUtils.isBlank(htmlDoc)) {
			init();
			logger.debug("generate restful api document.");
		}
		return Unpooled.copiedBuffer(htmlDoc.getBytes("utf-8"));
	}

	private static void init() throws Exception {
		StringBuffer buffer = new StringBuffer();

		InputStream in = RestfulDocGenerator.class.getClassLoader().getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			htmlDoc = buffer.toString();
			reader.close();
			in.close();
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * getDocItemJSON:获取文档对象JSON. <br/>
	 * 
	 * @author chenyuepeng
	 * @return JSON
	 * @since restful-server 2.0.0
	 */
	public static String getDocItemJSON() {
		return new Gson().toJson(DocAnnoHandler.docItems.values());
	}
}
