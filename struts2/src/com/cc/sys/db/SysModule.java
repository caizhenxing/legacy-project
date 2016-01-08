package com.cc.sys.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import base.zyf.common.tree.TreeViewI;
import base.zyf.hibernate.Entity;

/**
 * SysModule entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysModule extends Entity implements TreeViewI {

	// Fields

	/**
	 * 描述：
	 * 属性名：serialVersionUID
	 * 属性类型：long
	 */
	private static final long serialVersionUID = 6821518562172958710L;
	private String action;
	private String icon;
	private String remarks;
	private String parentId;
	private String tagShow;
	private String name;
	private String layerOrder;
	private String isSys;

    private Set sysRightGroups = new HashSet(0);
	/** 如果这个值不是<code>null</code>, 这个属性就是当前节点的上级节点 */
	private SysModule parent;

	/** 当前节点的直接子节点, 元素类型也是{@link THrDept} */
	private List<SysModule> children = new ArrayList();
	// Constructors

	/** default constructor */
	public SysModule() {
	}

	/** minimal constructor */
	public SysModule(String id) {
		super.id = id;
	}

	/** full constructor */
	public SysModule(String id, String action, String icon, String remarks,
			String parentId, String tagShow, String name, String layerOrder,
			String isSys) {
		super.id = id;
		this.action = action;
		this.icon = icon;
		this.remarks = remarks;
		this.parentId = parentId;
		this.tagShow = tagShow;
		this.name = name;
		this.layerOrder = layerOrder;
		this.isSys = isSys;
	}

	// Property accessors


	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getParentId() {
		return this.parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTagShow() {
		return this.tagShow;
	}

	public void setTagShow(String tagShow) {
		this.tagShow = tagShow;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLayerOrder() {
		return this.layerOrder;
	}

	public void setLayerOrder(String layerOrder) {
		this.layerOrder = layerOrder;
	}

	public String getIsSys() {
		return this.isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	/**
	 * @return the parent
	 */
	public SysModule getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SysModule parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List<SysModule> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List<SysModule> children) {
		this.children = children;
	}

	

	
	public int getWidth() {
		int width = this.layer;
		if(this.isExpanded())
		{
			List<SysModule> l = this.getChildren();
			for(SysModule sm : l){
				width = width>sm.getWidth() ? width : sm.getWidth();
			}
		}
		return width;
	}
	private boolean isExpanded;
	public boolean isExpanded() {
		
		return this.isExpanded;
	}

	public boolean isLast() {
		
		return this.getChildren().size() == 0;
	}



	public void setExpanded(boolean expanded) {
		
		this.isExpanded = expanded;
	}



	public Object getExtender() {
		
		return null;
	}

	public String getLabel() {
	
		return this.name;
	}
	private int layer = -1;
	public int getLayer() {
		
		if(layer == -1)
		{
			if(this.getParent() != null)
			{
				layer = this.getParent().getLayer() + 1;
			}else
			{
				layer = 1;
			}
		}
		return layer;
	}
	private boolean selected = false;
	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String nickName() {
		return this.id;
	}

	/**
	 * @return the sysRightGroups
	 */
	public Set getSysRightGroups() {
		return sysRightGroups;
	}

	/**
	 * @param sysRightGroups the sysRightGroups to set
	 */
	public void setSysRightGroups(Set sysRightGroups) {
		this.sysRightGroups = sysRightGroups;
	}

	
}