/**
 * 
 * 项目名称：struts2
 * 制作时间：May 4, 20091:21:21 PM
 * 包名：base.zyf.struts.interceptor
 * 文件名：AutoSearchConditionInterceptor.java
 * 制作者：zhaoyifei
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
 * 拦截器作用是自动加载查询条件到线程中，实现自动查询。
 * 不用清理条件，其它拦截器已经清理。
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
