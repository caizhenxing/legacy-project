/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 29, 20093:15:48 PM
 * 包名：base.zyf.hibernate
 * 文件名：UserEntity.java
 * 制作者：赵一非
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


/**
 * 供持久类使用的用户自定义类型。主要存储用户信息。
 * @author 赵一非
 * @version 1.0
 */
public class UserEntity implements Serializable {

	 /** 用户在系统的账号, 也就是登录名, 
	 */
    private String userId;

    /** 用户实际姓名, 通常作为显示 */
    private String name;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		UserEntity ue = new UserEntity();
        ue.setName(getName());
        ue.setUserId(getUserId());
        return ue;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if(UserEntity.class.isAssignableFrom(obj.getClass()))
		{
			if(StringUtils.isBlank(((UserEntity)obj).getUserId()))
				return StringUtils.isBlank(this.getUserId());
			return ((UserEntity)obj).getUserId().equals(this.getUserId());
				
		}
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.getUserId().hashCode();
	}
	public Object cloneI()
	{
		// TODO Auto-generated method stub
		UserEntity ue = new UserEntity();
        ue.setName(getName());
        ue.setUserId(getUserId());
        return ue;
	}
}
