package com.cc.sys.db;

import java.util.Date;

import base.zyf.hibernate.Entity;

/**
 * SysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUser extends Entity {

	// Fields

	private String userId;
	private SysDepartment sysDepartment;
	private SysGroup sysGroup;
	private String password;
	private String userName;
	private String deleteMark;
	private String remark;
	private String staffSign;
	private String isFreeze;
	private String isSys;
	private String skill;
	private Date createTime;
	private String auditing;
	private SysUserInfo sysUserInfo;

	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(String userId) {
		this.userId = userId;
	}

	
	// Property accessors

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysDepartment getSysDepartment() {
		return this.sysDepartment;
	}

	public void setSysDepartment(SysDepartment sysDepartment) {
		this.sysDepartment = sysDepartment;
	}

	

	public SysGroup getSysGroup() {
		return this.sysGroup;
	}

	public void setSysGroup(SysGroup sysGroup) {
		this.sysGroup = sysGroup;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDeleteMark() {
		return this.deleteMark;
	}

	public void setDeleteMark(String deleteMark) {
		this.deleteMark = deleteMark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getIsSys() {
		return this.isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	public String getSkill() {
		return this.skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAuditing() {
		return this.auditing;
	}

	public void setAuditing(String auditing) {
		this.auditing = auditing;
	}

	

	
	public SysUserInfo getSysUserInfo() {
		if(sysUserInfo == null)
		{
			sysUserInfo = new SysUserInfo();
		}
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

}