/** 
 * Project Name:restful-server 
 * File Name:Parameter.java 
 * Package Name:com.fourfaith.restful.server.doc 
 * Date:2016年3月28日下午4:54:49 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.doc;

import java.io.Serializable;

/**
 * ClassName: Parameter <br/>
 * Function:参数信息. <br/>
 * date: 2016年3月28日 下午4:54:49 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class Parameter implements Serializable {

	private static final long serialVersionUID = -5467495222728036630L;
	private String paramName;// 参数名称
	private String paramType;// 参数类型
	private boolean required;// 是否必须
	private String paramDesc;// 参数描述

	public Parameter() {
		this.required = false;
		this.paramDesc = "";
	}

	public Parameter(String paramName, String paramType, boolean required, String paramDesc) {
		this.paramName = paramName;
		this.paramType = paramType;
		this.required = required;
		this.paramDesc = paramDesc;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getParamDesc() {
		return paramDesc;
	}

	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}

}
