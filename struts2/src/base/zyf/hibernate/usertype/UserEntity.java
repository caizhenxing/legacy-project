/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 29, 20093:15:48 PM
 * ������base.zyf.hibernate
 * �ļ�����UserEntity.java
 * �����ߣ���һ��
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;


/**
 * ���־���ʹ�õ��û��Զ������͡���Ҫ�洢�û���Ϣ��
 * @author ��һ��
 * @version 1.0
 */
public class UserEntity implements Serializable {

	 /** �û���ϵͳ���˺�, Ҳ���ǵ�¼��, 
	 */
    private String userId;

    /** �û�ʵ������, ͨ����Ϊ��ʾ */
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
