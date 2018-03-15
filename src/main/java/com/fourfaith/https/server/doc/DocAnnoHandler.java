/** 
 * Project Name:restful-server 
 * File Name:DocAnnoHandler.java 
 * Package Name:com.fourfaith.restful.server.doc 
 * Date:2016年3月29日下午3:37:42 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.doc;

import io.netty.handler.codec.http.HttpMethod;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fourfaith.https.server.utils.RestfulReflectionUtil;
import com.fourfaith.restful.anno.Path;
import com.fourfaith.restful.anno.doc.BasicParam;
import com.fourfaith.restful.anno.doc.ComParam;
import com.fourfaith.restful.anno.doc.Tag;

/**
 * ClassName: DocAnnoHandler <br/>
 * Function: 文档注解处理器，处理方法的Path注解、ParamDoc注解、Tag注解. <br/>
 * date: 2016年3月29日 下午3:37:42 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class DocAnnoHandler implements Serializable {

	private static final long serialVersionUID = 6183116014871541821L;
	public static Map<String, DocItem> docItems = new HashMap<String, DocItem>();

	/**
	 * handle:文档注解处理. <br/>
	 * 
	 * @author chenyuepeng
	 * @param resourcePath
	 * @param mPath
	 * @param httpMethod
	 * @param method
	 * @since restful-server 2.0.0
	 */
	public static void handle(String resourcePath, Path mPath, HttpMethod httpMethod, Method method, Object bean) {
		Tag tag = method.getAnnotation(Tag.class);
		if (tag != null) {
			DocItem docItem = docItems.get(tag.name());
			if (docItem == null) {
				docItem = new DocItem();
				docItem.setTagDesc(tag.desc());
				docItem.setTagName(tag.name());
				docItems.put(tag.name(), docItem);
			}

			if (StringUtils.isNotBlank(tag.desc())) {
				docItem.setTagDesc(tag.desc());
			}

			PathBean pathBean = new PathBean();
			pathBean.setMethod(httpMethod.toString().toLowerCase());
			pathBean.setPath(resourcePath);
			pathBean.setPathDesc(mPath.desc());
			String[] parameterNames = RestfulReflectionUtil.getParameterNames(bean.getClass(), method);
			Class<?>[] types = method.getParameterTypes();
			BasicParam basicParam = method.getAnnotation(BasicParam.class);

			for (int i = 0; i < types.length; i++) {
				if (RestfulReflectionUtil.isCustomClass(types[i])) {
					Parameter[] params = parameters(types[i]);
					if (params != null && params.length > 0) {
						for (Parameter param : params) {
							pathBean.getParameters().add(param);
						}
					}
				} else {
					int last = types[i].toString().lastIndexOf(".") + 1;
					Parameter param = new Parameter();
					param.setParamName(parameterNames[i]);
					param.setParamType(types[i].toString().substring(last));
					if (basicParam != null) {
						if (basicParam.descs() != null && basicParam.descs().length > i) {
							param.setParamDesc(basicParam.descs()[i]);
						}
						if (basicParam.requireds() != null && basicParam.requireds().length > i) {
							param.setRequired(basicParam.requireds()[i]);
						}
					}

					pathBean.getParameters().add(param);
				}
			}

			docItem.getBeans().add(pathBean);
		}
	}
	
	/**
	 * parameters:根据class的字段信息生成Parameter[]. <br/>
	 * @author chenyuepeng
	 * @param clazz
	 * @return Parameter[]
	 * @since restful-server 2.0.0
	 */
	private static Parameter[] parameters(Class<?> clazz) {
		Field[] fields = clazz.getDeclaredFields();
		Parameter[] params = new Parameter[fields.length];
		if (fields != null && fields.length > 0) {
			for (int i = 0; i < fields.length; i++) {
				Field f = fields[i];
				int last = f.getType().toString().lastIndexOf(".") + 1;

				Parameter param = new Parameter();
				param.setParamName(f.getName());
				param.setParamType(f.getType().toString().substring(last));
				ComParam paramDoc = f.getAnnotation(ComParam.class);
				if (paramDoc != null) {
					param.setParamDesc(paramDoc.desc());
					param.setRequired(paramDoc.required());
				}
				params[i] = param;
			}
		}
		return params;
	}

}
