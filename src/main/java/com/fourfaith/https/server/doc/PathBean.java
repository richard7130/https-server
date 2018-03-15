/** 
 * Project Name:restful-server 
 * File Name:PathBean.java 
 * Package Name:com.fourfaith.restful.server.doc 
 * Date:2016年3月28日下午4:54:28 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.doc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: PathBean <br/>
 * Function: restful web 服务资源信息. <br/>
 * date: 2016年3月28日 下午4:54:28 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class PathBean implements Serializable {

	private static final long serialVersionUID = -3207649031071252626L;
	private String method;// http方法
	private String path;// 资源路径
	private String pathDesc;// 资源描述
	private List<Parameter> parameters;// 资源参数列表

	public PathBean() {
		parameters = new ArrayList<Parameter>();
	}

	public PathBean(String method, String path, String pathDesc) {
		this.method = method;
		this.path = path;
		this.pathDesc = pathDesc;
		parameters = new ArrayList<Parameter>();
	}

	public PathBean(String method, String path, String pathDesc, List<Parameter> parameters) {
		this(method, path, pathDesc);
		this.parameters = parameters;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPathDesc() {
		return pathDesc;
	}

	public void setPathDesc(String pathDesc) {
		this.pathDesc = pathDesc;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

}
