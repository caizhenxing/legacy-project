package com.cc.sys.db;

import java.util.ArrayList;
import java.util.List;

import base.zyf.common.tree.TreeViewI;
import base.zyf.hibernate.EntityPlus;

/**
 * SysTree entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysTree extends EntityPlus implements TreeViewI  {

	// Fields

	private String parentId;
	private String type;
	private String procAlias;
	private String label;
	private String layerOrder;
	private String handleNode;
	private String tagShow;
	private String tagSys;
	private String tagDel;
	private String isSys;
	private String staffSign;
	private String isFreeze;
	private String remark;

	/** 如果这个值不是<code>null</code>, 这个属性就是当前节点的上级节点 */
	private SysTree parent;

	/** 当前节点的直接子节点, 元素类型也是{@link THrDept} */
	private List<SysTree> children = new ArrayList();
	// Constructors

	/** default constructor */
	public SysTree() {
	}

	/** minimal constructor */
	public SysTree(String id) {
		super.id = id;
	}

	

	// Property accessors

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProcAlias() {
		return this.procAlias;
	}

	public void setProcAlias(String procAlias) {
		this.procAlias = procAlias;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLayerOrder() {
		return this.layerOrder;
	}

	public void setLayerOrder(String layerOrder) {
		this.layerOrder = layerOrder;
	}

	public String getHandleNode() {
		return this.handleNode;
	}

	public void setHandleNode(String handleNode) {
		this.handleNode = handleNode;
	}

	public String getTagShow() {
		return this.tagShow;
	}

	public void setTagShow(String tagShow) {
		this.tagShow = tagShow;
	}

	public String getTagSys() {
		return this.tagSys;
	}

	public void setTagSys(String tagSys) {
		this.tagSys = tagSys;
	}

	public String getTagDel() {
		return this.tagDel;
	}

	public void setTagDel(String tagDel) {
		this.tagDel = tagDel;
	}

	public String getIsSys() {
		return this.isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public String getStaffSign() {
		return this.staffSign;
	}

	public void setStaffSign(String staffSign) {
		this.staffSign = staffSign;
	}

	public String getIsFreeze() {
		return this.isFreeze;
	}

	public void setIsFreeze(String isFreeze) {
		this.isFreeze = isFreeze;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the parent
	 */
	public SysTree getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SysTree parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<SysTree> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<SysTree> children) {
		this.children = children;
	}

	public String getAction() {
		return null;
	}

	public String getIcon() {
		return null;
	}

	public int getWidth() {
		int width = this.layer;
		if(this.isExpanded())
		{
			List<SysTree> l = this.getChildren();
			for(SysTree st : l){
				width = width>st.getWidth() ? width : st.getWidth();
			}
		}
		return width;
	}

	public boolean isExpanded() {
		return this.expanded;
	}

	public boolean isLast() {
		return this.getChildren().size() == 0;
	}

	public void setAction(String string) {
		
	}
	private boolean expanded = false;
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	
	public void setIcon(String string) {
		
	}
	private boolean selected = false;
	public void setSelected(boolean selected) {
		this.selected = selected;
		
	}
	private Object extender = null;
	public Object getExtender() {
		
		return extender;
	}
	private int layer = -1;
	public int getLayer() {
		if(layer == -1)
		{
			if(this.parent == null)
			{
				layer = 1;
			}else
			{
				layer = this.parent.getLayer() + 1;
			}
		}
		return layer;
	}

	public String getName() {
		
		return this.getLabel();
	}

	public String nickName() {
		
		return this.getProcAlias();
	}

}