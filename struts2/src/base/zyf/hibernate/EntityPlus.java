/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 29, 200911:47:35 AM
 * ������base.zyf.hibernate
 * �ļ�����EntityPlus.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate;

import java.util.Date;

import base.zyf.hibernate.usertype.UserEntity;


/**
 * �����Ǹ�hibernate�־���̳�֮��һ��ҵ��־���̳д��࣬
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public abstract class EntityPlus extends Entity {

	 /** �������ʵ�����û� */
	protected UserEntity creator ;
    
    /** ����޸����ʵ�����û� */
	protected UserEntity modifier ;
    
    /** ����ʱ�� */
	protected Date createdTime = new Date();
    
    /** ����޸�ʱ�� */
	protected Date modifiedTime;
    /**
	 * �û�������Ϣ���У���¼��ǰ�����û���Ϣ�ļ�¼
	 */
	protected UserEntity deleter;
	/**
	 * ��¼�߼�ɾ��ʱ�Ļ���ʱ�䣬
	 * ʹ��java.util.Date��ȡ��ǰ����ʱ��
	 */
	protected Date deletedTime;
    
    
    ///////////////////////////////////////////////////////
	 public static final String DEL_FLG_TRUE="1";
	 public static final String DEL_FLG_FALSE="0";
	/**
	 * 1��ɾ��
	 * 0������������
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
