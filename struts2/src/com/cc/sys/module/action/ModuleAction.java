/**
 * 
 * 项目名称：struts2
 * 制作时间：May 21, 20092:16:04 PM
 * 包名：com.cc.sys.module.action
 * 文件名：ModuleAction.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package com.cc.sys.module.action;

import java.util.Map;

import base.zyf.common.tree.TreeNodeI;
import base.zyf.common.tree.module.ModuleTreeRight;
import base.zyf.web.condition.ContextInfo;
import base.zyf.web.crud.action.CommonAction;

import com.cc.sys.db.SysModule;
import com.cc.sys.module.service.SysModuleService;

/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class ModuleAction  extends CommonAction  {

	private SysModuleService sms;
	private TreeNodeI tree;
	private String grantS;
	/**
	 * 点击的node
	 */
	private String module;

	/**
	 * @return the module
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(String module) {
		this.module = module;
	}
	
	public String expandNode()
	{
		String result = "module";
		Map<String, ModuleTreeRight> moduleRights = ContextInfo.getContextUser().getModuleRights();
		moduleRights.get(module).setExpand(!moduleRights.get(module).isExpand());
		return result;
	}
	

	public String loadGrant()
	{
		tree = (TreeNodeI)this.cs.load(SysModule.class, TreeNodeI.TREE_ROOT);
		return "grantInfo";
	}
	
	public String saveGrant()
	{
		sms.grantGroup(oid, grantS);
		tree = (TreeNodeI)this.cs.load(SysModule.class, TreeNodeI.TREE_ROOT);
		return "grantInfo";
	}

	/**
	 * @return the tree
	 */
	public TreeNodeI getTree() {
		return tree;
	}

	/**
	 * @param tree the tree to set
	 */
	public void setTree(TreeNodeI tree) {
		this.tree = tree;
	}

	/**
	 * @return the grantS
	 */
	public String getGrantS() {
		return grantS;
	}

	/**
	 * @param grantS the grantS to set
	 */
	public void setGrantS(String grantS) {
		this.grantS = grantS;
	}

	/**
	 * @return the sms
	 */
	public SysModuleService getSms() {
		return sms;
	}

	/**
	 * @param sms the sms to set
	 */
	public void setSms(SysModuleService sms) {
		this.sms = sms;
	}
}
