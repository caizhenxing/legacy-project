/**
 * 
 * ��Ŀ���ƣ�struts2
 * ����ʱ�䣺May 4, 20091:21:21 PM
 * ������base.zyf.struts.interceptor
 * �ļ�����AutoSearchConditionInterceptor.java
 * �����ߣ�zhaoyifei
 * @version 1.0
 */
package base.zyf.struts.interceptor;

import java.util.Arrays;

import base.zyf.struts.action.BaseAction;
import base.zyf.web.condition.Condition;
import base.zyf.web.condition.ConditionInfo;
import base.zyf.web.condition.ContextInfo;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * �������������Զ����ز�ѯ�������߳��У�ʵ���Զ���ѯ��
 * �������������������������Ѿ�����
 * @author zhaoyifei
 * @version 1.0
 */
public class AutoSearchConditionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 * TODO
	 * @version 1.0
	 */
	public AutoSearchConditionInterceptor() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation actionInv) throws Exception {
		// TODO Auto-generated method stub
		BaseAction ba = (BaseAction)actionInv.getAction();
		ConditionInfo ci = new ConditionInfo();
		Condition[] ctemp = null;
		if(ba.getConditions().values().size() != 0)
		{
			
			ctemp = new Condition[ba.getConditions().values().size()];
			int i = 0;
			for(Condition c:ba.getConditions().values())
			{
				ctemp[i] = c;
				i++;
			}
		}
		ci.setAppendConditions(ctemp);
		ci.setPageInfo(ba.getPageInfo());
		ContextInfo.setContextCondition(ci);
		return actionInv.invoke();
	}

}
