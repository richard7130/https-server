/** 
 * Project Name:restful-server 
 * File Name:RestfulAnnoPkgScaner.java 
 * Package Name:com.fourfaith.restful.server.anno.exec 
 * Date:2016年3月26日上午8:33:22 
 * Copyright (c) 2016, chenyuepeng Rights Reserved. 
 */

package com.fourfaith.https.server.anno.exec;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import com.fourfaith.https.server.anno.handler.AnnoHandleDispatcher;

/**
 * ClassName: RestfulAnnoPkgScaner <br/>
 * Function: 这个类负责根据传入的restful web api要扫描的包路径，对相应包下的restful annotation进行处理. <br/>
 * date: 2016年3月26日 上午8:33:22 <br/>
 *
 * @author chenyuepeng
 * @version 2.0.0
 * @since restful-server 2.0.0
 */
public class RestfulAnnoPkgScaner implements Serializable {

	private static final long serialVersionUID = -7124188518714223191L;
	private static final Logger logger = Logger.getLogger(RestfulAnnoPkgScaner.class);
	private static RestfulAnnoPkgScaner scaner = new RestfulAnnoPkgScaner();

	private RestfulAnnoPkgScaner() {
	}

	public static RestfulAnnoPkgScaner getInstance() {
		return scaner;
	}

	/**
	 * scan:扫描指定包下的注解,并对注解进行相应处理. <br/>
	 * 
	 * @author chenyuepeng
	 * @param packages
	 * @since restful-server 2.0.0
	 */
	public void scan(String... packages) {
		if (packages != null) {
			for (String pkg : packages) {
				annoHandle(pkg.replace(".", "/"));
			}
		}
	}

	/**
	 * annoHandle:对注解进行处理处理器，根据指定的包名称，对文件或jar文件分别进行处理. <br/>
	 * 
	 * @author chenyuepeng
	 * @param pkgDirName
	 * @since restful-server 2.0.0
	 */
	private void annoHandle(String pkgDirName) {
		try {
			String packageName = pkgDirName.replace("/", ".");
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(pkgDirName);
			for (; urls != null && urls.hasMoreElements();) {
				URL url = urls.nextElement();
				String type = url.getProtocol();
				if ("file".equals(type)) {
					fileTypeHandle(pkgDirName, packageName, url.getFile());
				} else if ("jar".equals(type)) {
					jarTypeHandle(pkgDirName, packageName, url.getPath());
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * fileTypeHandle:文件类型注解处理. <br/>
	 * 
	 * @author chenyuepeng
	 * @param packageDirName
	 * @param packageName
	 * @param filePath
	 * @since restful-server 2.0.0
	 */
	private void fileTypeHandle(String packageDirName, String packageName, String filePath) {
		File packageDir = new File(filePath);
		File[] files = packageDir.listFiles();
		if (files != null && files.length > 0) {
			for (File f : files) {
				if (f.isDirectory()) {
					annoHandle(packageDirName + "/" + f.getName());
				} else {
					String fName = f.getName();
					logger.debug("will to handle qName:" + packageName + "." + fName.substring(0, fName.length() - 6));
					AnnoHandleDispatcher.dispatchHandle(packageName + "." + fName.substring(0, fName.length() - 6));
				}
			}
		}
	}

	/**
	 * jarTypeHandle:jar包类型注解处理. <br/>
	 * 
	 * @author chenyuepeng
	 * @param pkgDirName
	 * @param pkgName
	 * @param jarPath
	 * @since restful-server 2.0.0
	 */
	private void jarTypeHandle(String pkgDirName, String pkgName, String jarPath) {
		String[] jarInfo = jarPath.split("!");
		String jarFilePath = jarInfo[0].substring(jarInfo[0].indexOf("/"));
		try {
			JarFile jarFile = new JarFile(jarFilePath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jarEntry = entrys.nextElement();
				String entryName = jarEntry.getName();
				if (entryName.startsWith(pkgDirName) && entryName.endsWith(".class")) {
					String qName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
					logger.debug("will to handle qName:" + qName);
					AnnoHandleDispatcher.dispatchHandle(qName);
				}
			}
			jarFile.close();
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
