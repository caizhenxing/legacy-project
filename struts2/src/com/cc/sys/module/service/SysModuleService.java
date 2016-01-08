/**
 * 
 * 项目名称：struts2
 * 制作时间：May 19, 20094:04:32 PM
 * 包名：com.cc.sys.module.service.impl
 * 文件名：SysModuleService.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package com.cc.sys.module.service;

import base.zyf.common.tree.TreeNodeI;

/**
 * 用于加载系统模块的服务，可以修改，添加，删除模块。
 * @author zhaoyifei
 * @version 1.0
 */
public interface SysModuleService {

	public TreeNodeI loadAll();
	
	public TreeNodeI loadByUser(String id);
	
	public void grantGroup(String id, String grantS);
}
