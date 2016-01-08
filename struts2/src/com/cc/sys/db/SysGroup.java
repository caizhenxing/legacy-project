package com.cc.sys.db;

import java.util.HashSet;
import java.util.Set;

import base.zyf.hibernate.Entity;

/**
 * SysGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysGroup extends Entity  {

	// Fields

	private String name;
	private String delMark;
	private String remark;
	private String isSys;
	private Set sysRightGroups = new HashSet(0);
	private Set sysUsers = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysGroup() {
	}

	/** minimal constructor */
	public SysGroup(String id) {
		super.id = id;
	}

	/** full constructor */
	public SysGroup(String id, String name, String delMark, String remark,
			String isSys, Set sysRightGroups, Set sysUsers) {
		super.id = id;
		this.name = name;
		this.delMark = delMark;
		this.remark = remark;
		this.isSys = isSys;
		this.sysRightGroups = sysRightGroups;
		this.sysUsers = sysUsers;
	}

	// Property accessors


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDelMark() {
		return this.delMark;
	}

	public void setDelMark(String delMark) {
		this.delMark = delMark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsSys() {
		return this.isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public Set getSysRightGroups() {
		return this.sysRightGroups;
	}

	public void setSysRightGroups(Set sysRightGroups) {
		this.sysRightGroups = sysRightGroups;
	}

	public Set getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set sysUsers) {
		this.sysUsers = sysUsers;
	}

}