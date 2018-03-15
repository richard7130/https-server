/** 
 * Project Name:restful-server 
 * File Name:RestfulBean.java 
 * Package Name:com.fourfaith.restful.server.anno 
 * Date:2016年3月26日上午8:24:38 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.anno;

import java.io.Serializable;
import java.lang.reflect.Method;

import com.fourfaith.https.server.utils.RestfulReflectionUtil;

/**
 * ClassName: RestfulBean <br/>
 * Function: RestfulBean类是根据扫描获得到的restful web
 * api的信息封装而成。一个指定的resource，需要一个方法和对象来执行. <br/>
 * date: 2016年3月26日 上午8:24:38 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class RestfulBean implements Serializable {
	
	private static final long serialVersionUID = 4899782561400241634L;
	private Object bean;
	private Method method;
	private String[] paramNames;
	private Class<?>[] paramTypes;

	RestfulBean(Object bean, Method method) {
		this.bean = bean;
		this.method = method;
		this.paramNames = RestfulReflectionUtil.getParameterNames(bean.getClass(), method);
		this.paramTypes = method.getParameterTypes();
	}

	public Object getBean() {
		return bean;
	}

	public Method getMethod() {
		return method;
	}

	public String[] getParamNames() {
		return paramNames;
	}

	public Class<?>[] getParamTypes() {
		return paramTypes;
	}
}
