/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺Jun 4, 20094:28:33 PM
 * ������base.zyf.struts.interceptor
 * �ļ�����LogInterceptor.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.struts.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * ��������ҪĿ�����Զ���¼�û���ϵͳ�ڲ����ĺۼ�����¼�����ݿ��У���log4j�����ò�һ����
 * @author zhaoyifei
 * @version 1.0
 */
public class LogInterceptor extends AbstractInterceptor {
	private Map<String, Object> params;
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation actionInv) throws Exception {
		params = actionInv.getInvocationContext().getParameters();
		if(params != null)
		{
			
		}
		return actionInv.invoke();
	}

}
