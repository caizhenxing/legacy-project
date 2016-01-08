/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 29, 200911:36:26 AM
 * 包名：base.zyf.hibernate
 * 文件名：Entity.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate;

import java.io.Serializable;


/**
 * 本类是给hibernate持久类继承之用，主要有id，version，是一个抽象类，一般系统用持久类继承此类，
 * 业务持久类继承EntityPlus
 * @see EntityPlus.java
 * @author zhaoyifei
 * @version 1.0
 */
public abstract class Entity implements Serializable {

	/** 这个类的实例的标识符 */
    protected String id;
    
    /** 用于<code>optimistic lock</code>机制 */
    protected Integer version;
    /** 当持久化层对这个业务实体做了与数据库的同步操作时设置这个标志 */
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
