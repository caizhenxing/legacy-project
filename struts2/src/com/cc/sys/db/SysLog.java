package com.cc.sys.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import base.zyf.hibernate.Entity;
import base.zyf.hibernate.usertype.CodeWrapper;
import base.zyf.hibernate.usertype.UserEntity;

/**
 * SysLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysLog extends Entity {

	// Fields
	private UserEntity operator;
	private CodeWrapper modu;
	private String ip;
	private Date operTime;
	private CodeWrapper oper;
	private String remark;
	private Set sysLogDetails = new HashSet(0);

	// Constructors

	/** default constructor */
	public SysLog() {
	}

	/** minimal constructor */
	public SysLog(String id) {
		super.id = id;
	}


	
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getOperTime() {
		return this.operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}


	/**
	 * @return the modu
	 */
	public CodeWrapper getModu() {
		if(this.modu == null)
			this.modu = new CodeWrapper();
		return modu;
	}

	/**
	 * @param modu the modu to set
	 */
	public void setModu(CodeWrapper modu) {
		this.modu = modu;
	}

	/**
	 * @return the oper
	 */
	public CodeWrapper getOper() {
		if(this.oper == null)
			this.oper = new CodeWrapper();
		return oper;
	}

	/**
	 * @param oper the oper to set
	 */
	public void setOper(CodeWrapper oper) {
		this.oper = oper;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set getSysLogDetails() {
		return this.sysLogDetails;
	}

	public void setSysLogDetails(Set sysLogDetails) {
		this.sysLogDetails = sysLogDetails;
	}

	/**
	 * @return the operator
	 */
	public UserEntity getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(UserEntity operator) {
		this.operator = operator;
	}

}