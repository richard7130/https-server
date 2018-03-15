/** 
 * Project Name:restful-server 
 * File Name:RestfulReflectionUtil.java 
 * Package Name:com.fourfaith.restful.server.utils 
 * Date:2016年3月26日下午4:35:20 
 * Copyright (c) 2016, chenyuepeng All Rights Reserved. 
 */

package com.fourfaith.https.server.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fourfaith.https.server.anno.RestfulBean;

/**
 * ClassName: RestfulReflectionUtil <br/>
 * Function: restful 反射工具类. <br/>
 * date: 2016年3月26日 下午4:35:20 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public final class RestfulReflectionUtil implements Serializable {

	private static final long serialVersionUID = -7827153377652165673L;
	private static final Logger logger = Logger.getLogger(RestfulReflectionUtil.class);

	/**
	 * getArguments:根据restfulBean、请求参数值values，生成调用指定方法的参数数组. <br/>
	 * 
	 * @author chenyuepeng
	 * @param restfulBean
	 * @param values
	 * @return Object[]
	 * @since restful-server 2.0.0
	 */
	public static Object[] getArguments(RestfulBean restfulBean, Map<String, String> values) {
		Object[] arguments = null;

		Class<?>[] types = restfulBean.getParamTypes();
		String[] paramNames = restfulBean.getParamNames();

		if (paramNames != null && paramNames.length > 0) {
			arguments = new Object[paramNames.length];
			for (int i = 0; i < paramNames.length; i++) {
				String paramName = paramNames[i];
				if (isCustomClass(types[i])) {
					arguments[i] = toConcreteT(values, types[i]);
				} else {
					arguments[i] = string2ConcreteT(values.get(paramName), types[i]);
				}
			}
		}
		return arguments;
	}

	/**
	 * toConcreteT:根据给定的values，通过反射转换成真正的clazz对象. <br/>
	 * @author chenyuepeng
	 * @param values
	 * @param clazz
	 * @return Object
	 * @since restful-server 2.0.0
	 */
	private static Object toConcreteT(Map<String, String> values, Class<?> clazz) {
		Object obj = null;
		try {
			obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			if (fields != null && fields.length > 0) {
				for (Field f : fields) {
					f.setAccessible(true);
					f.set(obj, string2ConcreteT(values.get(f.getName()),f.getType()));
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return obj;
	}

	/**
	 * string2ConcreteT:将String值转换成指定的基本类型值. <br/>
	 * 
	 * @author chenyuepeng
	 * @param val
	 * @param clazz
	 * @return Object
	 * @since restful-server 2.0.0
	 */
	private static Object string2ConcreteT(String val, Class<?> clazz) {
		try {
			if (clazz == boolean.class || clazz == Boolean.class) {
				return StringUtils.isBlank(val) ? false : Boolean.valueOf(val);
			}
			if (clazz == byte.class || clazz == Byte.class) {
				return StringUtils.isBlank(val) ? 0 : Byte.valueOf(val);
			}
			if (clazz == short.class || clazz == Short.class) {
				return StringUtils.isBlank(val) ? 0 : Short.parseShort(val);
			}
			if (clazz == int.class || clazz == Integer.class) {
				return StringUtils.isBlank(val) ? 0 : Integer.parseInt(val);
			}
			if (clazz == float.class || clazz == Float.class) {
				return StringUtils.isBlank(val) ? 0 : Float.valueOf(val);
			}
			if (clazz == long.class || clazz == Long.class) {
				return StringUtils.isBlank(val) ? 0 : Long.valueOf(val);
			}
			if (clazz == double.class || clazz == Double.class) {
				return StringUtils.isBlank(val) ? 0 : Double.valueOf(val);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			return null;
		}
		return val;
	}

	/**
	 * getParameterNames:根据给定的Class、Method实例，获取参数名称. <br/>
	 * 
	 * @author chenyuepeng
	 * @param clazz
	 * @param method
	 * @return String[]
	 * @since restful-server 2.0.0
	 */
	public static String[] getParameterNames(Class<?> clazz, Method method) {
		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass cc = pool.get(clazz.getName());
			CtMethod cm = cc.getDeclaredMethod(method.getName());

			MethodInfo methodInfo = cm.getMethodInfo();
			CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
			LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);

			if (attr != null) {
				String[] paramNames = new String[cm.getParameterTypes().length];
				int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
				for (int i = 0; i < paramNames.length; i++) {
					paramNames[i] = attr.variableName(i + pos);
				}
				return paramNames;
			}
		} catch (NotFoundException ex) {
			logger.error(ex.getMessage(), ex);
		}
		return null;
	}

	/**
	 * isCustomClass:判断该clazz是否为自定义类型. <br/>
	 * 
	 * @author chenyuepeng
	 * @param clazz
	 * @return boolean
	 * @since restful-server 2.0.0
	 */
	public static boolean isCustomClass(Class<?> clazz) {
		if (clazz.isPrimitive() || clazz == String.class || clazz == Character.class || clazz == Boolean.class) {
			return false;
		}
		if (clazz == java.util.Date.class || clazz == java.sql.Date.class || clazz == Void.class) {
			return false;
		}
		if (clazz == Integer.class || clazz == Number.class || clazz == Double.class || clazz == Float.class) {
			return false;
		}
		return true;
	}

	/**
	 * isCustomClass:判断该对象obj是否为自定义类. <br/>
	 * 
	 * @author chenyuepeng
	 * @param obj
	 * @return boolean
	 * @since restful-server 2.0.0
	 */
	public static boolean isCustomClass(Object obj) {
		return isCustomClass(obj.getClass());
	}

}
