/** 
 * Project Name:restful-server 
 * File Name:RestfulServiceController.java 
 * Package Name:com.fourfaith.restful.server.anno.exec 
 * Date:2016年3月26日上午8:37:07 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.anno.exec;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.fourfaith.https.server.exception.RestfulAnnoException;

/**
 * ClassName: RestfulServiceController <br/>
 * Function: restful 服务控制器，是控制调用真正的restful方法. <br/>
 * date: 2016年3月26日 上午8:37:07 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class RestfulServiceController implements Serializable {

	private static final long serialVersionUID = 9180914360086387503L;
	private static final Logger logger = Logger.getLogger(RestfulServiceController.class);
	private static RestfulServiceController instance = new RestfulServiceController();

	private RestfulServiceController() {
	}

	public static RestfulServiceController getInstance() {
		return instance;
	}

	class RestBean {

		private Object bean;
		private Method method;

		public RestBean(Object bean, Method method) {
			this.bean = bean;
			this.method = method;
		}

		public Object getBean() {
			return bean;
		}

		public Method getMethod() {
			return method;
		}
	}

	private Map<String, RestBean> map = new ConcurrentHashMap<String, RestBean>();

	/**
	 * addRestBean:加入Restful处理Bean. <br/>
	 * @author chenyuepeng
	 * @param key
	 * @param restBean
	 * @since elec-server 1.0
	 */
	public void addRestBean(String key, Object bean, Method method) {
		map.put(key, new RestBean(bean, method));
	}

	/**
	 * invoke:根据请求url，调用相关restful服务. <br/>
	 * @author chenyuepeng
	 * @param requestURL
	 * @param parameter
	 * @return JSON
	 * @throws RestfulAnnoException
	 * @since elec-server 1.0
	 */
	public String invoke(String requestURL, Map<String, String> parameter) throws RestfulAnnoException {
		try {
			RestBean restBean = map.get(requestURL);
			logger.debug("request url is " + requestURL);
			if (restBean == null) {
				throw new NullPointerException("Restful bean cannot find by request url [" + requestURL + "]");
			}
			return (String) restBean.getMethod().invoke(restBean.getBean(), parameter);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new RestfulAnnoException(ex.getMessage(), ex);
		}
	}


}
