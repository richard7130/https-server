/** 
 * Project Name:restful-server 
 * File Name:PropertiesUtil.java 
 * Package Name:com.fourfaith.restful.server.utils 
 * Date:2016年3月26日上午9:10:16 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.fourfaith.https.server.comm.RestfulConstant;

/**
 * ClassName: PropertiesUtil <br/>
 * Function: 配置文件属性工具类. <br/>
 * date: 2016年3月26日 上午9:10:16 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class PropertiesUtil implements Serializable {

	private static final long serialVersionUID = -9156486401563801803L;
	private static Map<String, String> conf_map = new HashMap<String, String>();

	static {
		// 先读取外部配置文件suye-server-ext.properties文件，该文件位于jar包同级目录，
		// 如果为空，才读取本服务的配置文件suye-server.properties
		InputStream stream = null;

		String extFilePath = System.getProperty("user.dir").replace("\\", "/") + "/" + RestfulConstant.RESTFUL_EXT_CONF;
		File extFile = new File(extFilePath);

		Properties p = new Properties();
		try {
			if (extFile.exists()) {
				stream = new FileInputStream(extFile);
			}

			if (stream == null) {
				stream = PropertiesUtil.class.getClassLoader().getResourceAsStream(RestfulConstant.RESTFUL_IN_CONF);
			}

			p.load(stream);
			@SuppressWarnings("rawtypes")
			Enumeration en = p.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				conf_map.put(key, p.getProperty(key).trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * getProperty:获取配置文件值. <br/>
	 * 
	 * @author chenyuepeng
	 * @param key
	 * @return property
	 * @since restful-server 2.0.0
	 */
	public static String getProperty(String key) {
		return conf_map.get(key);
	}

}
