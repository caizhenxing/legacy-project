/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 19, 20094:04:32 PM
 * ������com.cc.sys.module.service.impl
 * �ļ�����SysModuleService.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package com.cc.sys.module.service;

import base.zyf.common.tree.TreeNodeI;

/**
 * ���ڼ���ϵͳģ��ķ��񣬿����޸ģ���ӣ�ɾ��ģ�顣
 * @author zhaoyifei
 * @version 1.0
 */
public interface SysModuleService {

	public TreeNodeI loadAll();
	
	public TreeNodeI loadByUser(String id);
	
	public void grantGroup(String id, String grantS);
}
