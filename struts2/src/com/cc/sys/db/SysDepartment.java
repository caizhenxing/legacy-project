package com.cc.sys.db;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import base.zyf.common.tree.TreeViewI;
import base.zyf.hibernate.Entity;

/**
 * SysDepartment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysDepartment extends Entity implements TreeViewI {

	// Fields

	
	private String remarks;
	private String parentId;
	private String tagShow;
	private String name;
	private String admin;
	private String isSys;
	private Set sysUsers = new HashSet(0);
	/** 如果这个值不是<code>null</code>, 这个属性就是当前节点的上级节点 */
	private SysDepartment parent;

	/** 当前节点的直接子节点, 元素类型也是{@link THrDept} */
	private List children = new ArrayList();

	// Constructors

	/** default constructor */
	public SysDepartment() {
	}

	/** minimal constructor */
	public SysDepartment(String id) {
		super.id = id;
	}


	// Property accessors

	

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

	public String getAdmin() {
		return this.admin;
	}

	public void setAdmin(String admin) {
		this.admin = admin;
	}

	public String getIsSys() {
		return this.isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public Set getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set sysUsers) {
		this.sysUsers = sysUsers;
	}

	/**
	 * @return the parent
	 */
	public SysDepartment getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(SysDepartment parent) {
		this.parent = parent;
	}

	/**
	 * @return the children
	 */
	public List getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(List children) {
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

	public void setAction(String string) {
		
	}

	public void setExpanded(boolean expanded) {
		
	}

	public void setIcon(String string) {
		
	}

	private boolean selected = false;
	public boolean isSelected() {
		return this.selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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

	public String nickName() {
		return this.id;
	}

}