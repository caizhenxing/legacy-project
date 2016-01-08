/**
 * 
 * 项目名称：struts2
 * 制作时间：May 20, 200911:35:32 AM
 * 包名：base.zyf.common.tree.module
 * 文件名：ModuleTreeRight.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.common.tree.module;

import base.zyf.common.tree.TreeViewI;

/**
 * 存储用户节点权限
 * @author zhaoyifei
 * @version 1.0
 */
public class ModuleTreeRight {
	
	
	private TreeViewI node;
	
	private boolean isExpand = false;
	
	private boolean isSelected = false;

	public ModuleTreeRight()
	{
		
	}
	public ModuleTreeRight(TreeViewI node)
	{
		this.node = node;
	}
	public ModuleTreeRight(boolean isExpand, boolean isSelected)
	{
		this.isExpand = isExpand;
		this.isSelected = isSelected;
	}
	public ModuleTreeRight(boolean isExpand, boolean isSelected, TreeViewI node)
	{
		this.isExpand = isExpand;
		this.isSelected = isSelected;
		this.node = node;
	}
	/**
	 * @return the isExpand
	 */
	public boolean isExpand() {
		return isExpand;
	}

	/**
	 * @param isExpand the isExpand to set
	 */
	public void setExpand(boolean isExpand) {
		this.isExpand = isExpand;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	/**
	 * @return the node
	 */
	public TreeViewI getNode() {
		return node;
	}
	/**
	 * @param node the node to set
	 */
	public void setNode(TreeViewI node) {
		this.node = node;
	}
	
	
}
