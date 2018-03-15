/** 
 * Project Name:restful-server 
 * File Name:PathRestfulAnnoHandler.java 
 * Package Name:com.fourfaith.restful.server.anno.handler 
 * Date:2016年3月26日上午8:44:27 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */ 

package com.fourfaith.https.server.anno.handler;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.fourfaith.https.server.anno.exec.RestfulServiceController;
import com.fourfaith.https.server.exception.RestfulAnnoException;
import com.fourfaith.restful.anno.Path;

/**
 * ClassName: PathRestfulAnnoHandler <br/>
 * Function: Restful Path注解处理器. <br/>
 * date: 2016年3月26日 上午8:44:27 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class PathRestfulAnnoHandler implements IRestfulAnnoHandler {
	
	private static final long serialVersionUID = -5104434260786340429L;
	private static final Logger logger = Logger.getLogger(PathRestfulAnnoHandler.class);

	public String handle(String qName) throws RestfulAnnoException {
		try {
			Path typePath = Class.forName(qName).getAnnotation(Path.class);
			Object bean = Class.forName(qName).newInstance();
			Method[] methods = Class.forName(qName).getDeclaredMethods();
			if (methods != null && methods.length > 0) {
				for (Method method : methods) {
					Path path = method.getAnnotation(Path.class);
					if (path != null) {
						logger.debug("Path Annotation Method: " + method.getName());
						RestfulServiceController.getInstance().addRestBean(typePath.value() + path.value(), bean, method);
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new RestfulAnnoException(ex.getMessage(), ex);
		}
		return StringUtils.EMPTY;
	}
}
