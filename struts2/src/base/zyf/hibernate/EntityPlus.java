/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 29, 200911:47:35 AM
 * 包名：base.zyf.hibernate
 * 文件名：EntityPlus.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate;

import java.util.Date;

import base.zyf.hibernate.usertype.UserEntity;


/**
 * 本类是给hibernate持久类继承之用一般业务持久类继承此类，
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public abstract class EntityPlus extends Entity {

	 /** 创建这个实例的用户 */
	protected UserEntity creator ;
    
    /** 最后修改这个实例的用户 */
	protected UserEntity modifier ;
    
    /** 创建时间 */
	protected Date createdTime = new Date();
    
    /** 最后修改时间 */
	protected Date modifiedTime;
    /**
	 * 用户基本信息表中，记录当前操作用户信息的记录
	 */
	protected UserEntity deleter;
	/**
	 * 记录逻辑删除时的机器时间，
	 * 使用java.util.Date获取当前机器时间
	 */
	protected Date deletedTime;
    
    
    ///////////////////////////////////////////////////////
	 public static final String DEL_FLG_TRUE="1";
	 public static final String DEL_FLG_FALSE="0";
	/**
	 * 1：删除
	 * 0或其他：正常
	 */
	 protected String delFlg = "0";

	 protected int privilege = 0;

	public UserEntity getCreator() {
		return creator;
	}

	public void setCreator(UserEntity creator) {
		this.creator = creator;
	}

	public UserEntity getModifier() {
		return modifier;
	}

	public void setModifier(UserEntity modifier) {
		this.modifier = modifier;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public UserEntity getDeleter() {
		return deleter;
	}

	public void setDeleter(UserEntity deleter) {
		this.deleter = deleter;
	}

	public Date getDeletedTime() {
		return deletedTime;
	}

	public void setDeletedTime(Date deletedTime) {
		this.deletedTime = deletedTime;
	}



	public String getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(String delFlg) {
		this.delFlg = delFlg;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}
}
