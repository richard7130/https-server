/** 
 * Project Name:restful-server 
 * File Name:DocItem.java 
 * Package Name:com.fourfaith.restful.server.doc 
 * Date:2016年3月28日下午4:50:12 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.doc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: DocItem <br/>
 * Function: 文档项. <br/>
 * date: 2016年3月28日 下午4:50:12 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class DocItem implements Serializable {

	private static final long serialVersionUID = 5566913128369515629L;
	private String tagName;// 标签名称
	private String tagDesc;// 标签描述
	private List<PathBean> beans;// 资源列表

	public DocItem() {
		beans = new ArrayList<PathBean>();
	}

	public DocItem(String tagName, String tagDesc) {
		this.tagName = tagName;
		this.tagDesc = tagDesc;
		beans = new ArrayList<PathBean>();
	}

	public DocItem(String tagName, String tagDesc, List<PathBean> beans) {
		this(tagName, tagDesc);
		this.beans = beans;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getTagDesc() {
		return tagDesc;
	}

	public void setTagDesc(String tagDesc) {
		this.tagDesc = tagDesc;
	}

	public List<PathBean> getBeans() {
		return beans;
	}

	public void setBeans(List<PathBean> beans) {
		this.beans = beans;
	}

}
