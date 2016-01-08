/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 30, 20099:34:43 AM
 * 包名：base.zyf.hibernate.usertype
 * 文件名：UserEntityUserType.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;
import org.springframework.util.Assert;


/**
 * 
 * @author zhaoyifei
 * @version 1.0
 */
public class UserEntityUserType implements UserType {

	private int[] TYPES = new int[] {Hibernate.STRING.sqlType()};
	
	private static UserEntityCallbackHandler callbackHandler = new UserEntityCallbackImpl();
	public static interface UserEntityCallbackHandler {		
		
		/**
		 * 在 {@link UserWrapperType} load 到 UserWrapper 后的处理方法
		 * @param userWrapperLoaded userWrapper loaded
		 * @throws Throwable 如果发生任何异常
		 */
		void afterNullSafeGet(UserEntity ue) throws Throwable;
	}
	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException {
        if (value == null) {
            return value;
        }
        UserEntity ue = (UserEntity) value;
        return ue.cloneI();
    }

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		return (Serializable) value;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object value1, Object value2) throws HibernateException {
		if(value1 == null || value2 == null)
			return value1 == value2;
		UserEntity ue = (UserEntity) value1;
		return ue.equals(value2);
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		if(arg0 == null)
		{
			return 0;
		}
		return arg0.hashCode();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) 
	throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		String id = (String) Hibernate.STRING.nullSafeGet(rs, names);
		if (id == null) {
			return null;
		}
		UserEntity ue = new UserEntity();
		ue.setUserId(id);
		try {
				callbackHandler.afterNullSafeGet(ue);
			} catch (Throwable t) {
				
			}
		
		return ue;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		// TODO Auto-generated method stub
		String name = null;
		if (value != null) {
			Assert.isInstanceOf(returnedClass(), value, " 类型声明错误 ");
			name = ((UserEntity) value).getUserId();
		}
		Hibernate.STRING.nullSafeSet(st, name, index);	
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }


	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class returnedClass() {
		// TODO Auto-generated method stub
		return UserEntity.class;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		// TODO Auto-generated method stub
		return TYPES;
	}

}
