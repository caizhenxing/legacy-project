/**
 * 
 * 项目名称：struts2
 * 制作时间：May 31, 20099:26:49 AM
 * 包名：com.cc.sys.tree.action
 * 文件名：TreeAction.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package com.cc.sys.tree.action;

import java.io.IOException;

import base.zyf.common.tree.FlatTreeUtils;
import base.zyf.common.tree.TreeNodeI;
import base.zyf.web.crud.action.CommonAction;


/**
 * 用于tree显示用的action
 * @author zhaoyifei
 * @version 1.0
 */
public class TreeAction extends CommonAction {


	private String parentCode = null;
	public String tree()
	{
		if(parentCode == null)
			parentCode = TreeNodeI.TREE_ROOT;
		TreeNodeI node = (TreeNodeI)cs.load(this.getEntityClass(), parentCode);
		
		response.setContentType("text/plain; charset=GBK");
		
		try {
			response.getWriter().print(FlatTreeUtils.serialize(node.getChildren(), false));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * @return the parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
}
