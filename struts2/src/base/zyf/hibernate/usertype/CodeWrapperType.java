/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Apr 29, 20094:52:10 PM
 * ������base.zyf.hibernate
 * �ļ�����CodeWrapperType.java
 * �����ߣ���һ��
 * @version 1.0
 */
package base.zyf.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.type.Type;
import org.hibernate.usertype.CompositeUserType;
import org.springframework.util.Assert;


/**
 * 
 * @author ��һ��
 * @version 1.0
 */
public class CodeWrapperType implements CompositeUserType {
	public Object assemble(Serializable cached, SessionImplementor session, Object owner) throws HibernateException {
		return cached;
	}

	public Serializable disassemble(Object value, SessionImplementor session) throws HibernateException {
		return (Serializable) value;
	}

	public void setPropertyValue(Object component, int property, Object value) throws HibernateException {
		throw new UnsupportedOperationException("Immutable!");
	}

	public Object replace(Object original, Object target, SessionImplementor session, Object owner) throws HibernateException {
		return original;
	}

	

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public boolean isMutable() {
		return false;
	}
	private static CodeWrapperCallbackHanler codeWrapperCallbackHanler=new ReformCodeWrapperCallbackHandler();
	
	/**
	 * ���ڴ��� {@link CodeWrapper} �Ļص��ӿ�, һ���Ǹ��� code �õ� name, ��Ҫʹ����ʵ��
	 *
	 */
	public static interface CodeWrapperCallbackHanler {
		
		/**
		 * �� {@link CodeWrapper} load �� CodeWrapper ��Ĵ�����
		 * @param codeWrapperLoaded codeWrapper loaded
		 * @throws Throwable ��������κ��쳣
		 */
		void afterNullSafeGet(CodeWrapper codeWrapperLoaded) throws Throwable;
		
	}
	
	/**
	 * Ϊ CodeWrapperType ע��һ���ص��ӿ�
	 * @param handler the handler
	 */
	public static void registerCallbackHandler(CodeWrapperCallbackHanler handler) {
		//FIXME ��ʱֻȡ����ʵ��
//		if (CODE_WRAPPER_CALLBACK_HANDLER_VERDOR_CLASS_NAME.equals(handler.getClass().getName())) {
//			if (logger.isInfoEnabled()) {
//				logger.info(" Ϊ " + CodeWrapperType.class + " ע��ص��ӿ� " + handler);
//			}
//			codeWrapperCallbackHanler = handler;
//		}
	}
	
	/**
	 * Ϊ CodeWrapperType  ע��һ���ص��ӿ�
	 * @param handler the handler
	 */
	public static void deRegisterCallbackHandler(CodeWrapperCallbackHanler handler) {
		
		codeWrapperCallbackHanler = null;
	}
		
	public Class returnedClass() {
		return CodeWrapper.class;
	}

	public String[] getPropertyNames() {
		return new String[] {"code"};
	}

	public Type[] getPropertyTypes() {
		return new Type[] {Hibernate.STRING};
	}

	public Object getPropertyValue(Object component, int property) throws HibernateException {
		Assert.isInstanceOf(returnedClass(), component, " ������������ ");
		CodeWrapper codeWrapper = (CodeWrapper) component;
		return codeWrapper.getCode();
	}

	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner) 
		throws HibernateException, SQLException {
		
		String code = (String) Hibernate.STRING.nullSafeGet(rs, names);
		if (code == null) {
			return null;
		}
		CodeWrapper codeWrapper = new CodeWrapper();
		codeWrapper.setCode(code);

		try {
			codeWrapperCallbackHanler.afterNullSafeGet(codeWrapper);
		} catch (Throwable t) {
			
		}
	
		return codeWrapper;
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session) 
		throws HibernateException, SQLException {
		
		String name = null;
		if (value != null) {
			Assert.isInstanceOf(returnedClass(), value, " ������������ ");
			name = ((CodeWrapper) value).getCode();
			CodeWrapper cwp=(CodeWrapper)value;
			if(cwp.isModify())
			try {
				codeWrapperCallbackHanler.afterNullSafeGet(cwp);
			} catch (Throwable t) {
				
			}
		}
		Hibernate.STRING.nullSafeSet(st, name, index);
		
	}

	public Object deepCopy(Object value) throws HibernateException {
		// TODO Auto-generated method stub
		if(value==null)
			return null;
		if(CodeWrapper.class.isAssignableFrom(value.getClass()))
			try {
				return ((CodeWrapper)value).clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				throw new HibernateException(e);
			}
		return null;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		// TODO Auto-generated method stub
		if(x==null)
			return y==null;
		return x.equals(y);
	}
}
