package com.cc.sys.db;

import java.util.Date;

import base.zyf.hibernate.Entity;
import base.zyf.hibernate.usertype.CodeWrapper;

/**
 * SysUserInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class SysUserInfo extends Entity {

	// Fields


	private SysUser sysUser;
	private String realName;
	private CodeWrapper sexId;
	private CodeWrapper identityKind;
	private String identityCard;
	private String email;
	private Date birthday;
	private CodeWrapper countryId;
	private CodeWrapper provinceId;
	private CodeWrapper cityId;
	private String qq;
	private CodeWrapper bloodType;
	private String address;
	private String postalcode;
	private String mobile;
	private CodeWrapper finishSchool;
	private String speciality;
	private String workId;
	private String remark;

	// Constructors

	/** default constructor */
	public SysUserInfo() {
	}

	

	

	// Property accessors


	public SysUser getSysUser() {
		return this.sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	

	public String getIdentityCard() {
		return this.identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}


	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return this.postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	/**
	 * @return the sexId
	 */
	public CodeWrapper getSexId() {
		if(sexId == null)
		{
			sexId = new CodeWrapper();
		}
		return sexId;
	}





	/**
	 * @param sexId the sexId to set
	 */
	public void setSexId(CodeWrapper sexId) {
		this.sexId = sexId;
	}





	/**
	 * @return the identityKind
	 */
	public CodeWrapper getIdentityKind() {
		if(identityKind == null)
		{
			identityKind = new CodeWrapper();
		}
		return identityKind;
	}





	/**
	 * @param identityKind the identityKind to set
	 */
	public void setIdentityKind(CodeWrapper identityKind) {
		this.identityKind = identityKind;
	}





	/**
	 * @return the countryId
	 */
	public CodeWrapper getCountryId() {
		if(countryId == null)
		{
			countryId = new CodeWrapper();
		}
		return countryId;
	}





	/**
	 * @param countryId the countryId to set
	 */
	public void setCountryId(CodeWrapper countryId) {
		this.countryId = countryId;
	}





	/**
	 * @return the provinceId
	 */
	public CodeWrapper getProvinceId() {
		if(provinceId == null)
		{
			provinceId = new CodeWrapper();
		}
		return provinceId;
	}





	/**
	 * @param provinceId the provinceId to set
	 */
	public void setProvinceId(CodeWrapper provinceId) {
		this.provinceId = provinceId;
	}





	/**
	 * @return the cityId
	 */
	public CodeWrapper getCityId() {
		if(cityId == null)
		{
			cityId = new CodeWrapper();
		}
		return cityId;
	}





	/**
	 * @param cityId the cityId to set
	 */
	public void setCityId(CodeWrapper cityId) {
		this.cityId = cityId;
	}





	/**
	 * @return the bloodType
	 */
	public CodeWrapper getBloodType() {
		if(bloodType == null)
		{
			bloodType = new CodeWrapper();
		}
		return bloodType;
	}





	/**
	 * @param bloodType the bloodType to set
	 */
	public void setBloodType(CodeWrapper bloodType) {
		this.bloodType = bloodType;
	}





	/**
	 * @return the finishSchool
	 */
	public CodeWrapper getFinishSchool() {
		if(finishSchool == null)
		{
			finishSchool = new CodeWrapper();
		}
		return finishSchool;
	}





	/**
	 * @param finishSchool the finishSchool to set
	 */
	public void setFinishSchool(CodeWrapper finishSchool) {
		this.finishSchool = finishSchool;
	}





	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getWorkId() {
		return this.workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}