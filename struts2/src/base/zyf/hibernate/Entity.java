/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 29, 200911:36:26 AM
 * ������base.zyf.hibernate
 * �ļ�����Entity.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate;

import java.io.Serializable;


/**
 * �����Ǹ�hibernate�־���̳�֮�ã���Ҫ��id��version����һ�������࣬һ��ϵͳ�ó־���̳д��࣬
 * ҵ��־���̳�EntityPlus
 * @see EntityPlus.java
 * @author zhaoyifei
 * @version 1.0
 */
public abstract class Entity implements Serializable {

	/** ������ʵ���ı�ʶ�� */
    protected String id;
    
    /** ����<code>optimistic lock</code>���� */
    protected Integer version;
    /** ���־û�������ҵ��ʵ�����������ݿ��ͬ������ʱ���������־ */
    protected boolean flushFlag;
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        String oid = ((Entity) obj).getId();
        if (oid == null || oid.trim().length() == 0) {
            return false;
        }
        return oid.equals(getId());
    }

    public int hashCode() {
        if (getId() == null || getId().trim().length() == 0) {
            return super.hashCode();
        } else {
            return getClass().hashCode() * 29 + getId().hashCode();
        }
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean isFlushFlag() {
		return flushFlag;
	}

	public void setFlushFlag(boolean flushFlag) {
		this.flushFlag = flushFlag;
	}
}
