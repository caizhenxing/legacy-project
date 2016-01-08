/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 31, 20093:37:44 PM
 * ������base.zyf.struts.interceptor
 * �ļ�����ParamInterceptor.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.struts.interceptor;

import java.util.Map;

import base.zyf.struts.action.BaseAction;
import base.zyf.web.crud.action.CommonAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * ��������Ҫ��������prepare������֮ǰ����id
 * @author zhaoyifei
 * @version 1.0
 */
public class ParamIdInterceptor extends AbstractInterceptor {

	private Map<String, Object> params;
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation actionInv) throws Exception {
		BaseAction ba = (BaseAction)actionInv.getAction();
		if(CommonAction.class.isInstance(ba))
		{
			CommonAction ca = (CommonAction)ba;
		params = actionInv.getInvocationContext().getParameters();
		if(params != null)
		{
			if(params.containsKey("oid"))
				{
				ca.setOid(((String[])params.get("oid"))[0]);
				}
			if(params.containsKey("entityClass"))
			{
			ca.setEntityClass(((String[])params.get("entityClass"))[0]);
			}
		}
		}
		return actionInv.invoke();
	}
	

}
