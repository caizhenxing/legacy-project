/**
 * 
 * 项目名称：struts2
 * 制作时间：Apr 29, 20094:52:10 PM
 * 包名：base.zyf.hibernate
 * 文件名：CodeWrapperType.java
 * 制作者：赵一非
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
 * @author 赵一非
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
	 * 用于处理 {@link CodeWrapper} 的回调接口, 一般是根据 code 得到 name, 需要使用者实现
	 *
	 */
	public static interface CodeWrapperCallbackHanler {
		
		/**
		 * 在 {@link CodeWrapper} load 到 CodeWrapper 后的处理方法
		 * @param codeWrapperLoaded codeWrapper loaded
		 * @throws Throwable 如果发生任何异常
		 */
		void afterNullSafeGet(CodeWrapper codeWrapperLoaded) throws Throwable;
		
	}
	
	/**
	 * 为 CodeWrapperType 注册一个回调接口
	 * @param handler the handler
	 */
	public static void registerCallbackHandler(CodeWrapperCallbackHanler handler) {
		//FIXME 暂时只取公用实现
//		if (CODE_WRAPPER_CALLBACK_HANDLER_VERDOR_CLASS_NAME.equals(handler.getClass().getName())) {
//			if (logger.isInfoEnabled()) {
//				logger.info(" 为 " + CodeWrapperType.class + " 注册回调接口 " + handler);
//			}
//			codeWrapperCallbackHanler = handler;
//		}
	}
	
	/**
	 * 为 CodeWrapperType  注销一个回调接口
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
		Assert.isInstanceOf(returnedClass(), component, " 类型声明错误 ");
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
			Assert.isInstanceOf(returnedClass(), value, " 类型声明错误 ");
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
