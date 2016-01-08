/**
 * 
 * 项目名称：struts2
 * 制作时间：Jun 4, 20094:28:33 PM
 * 包名：base.zyf.struts.interceptor
 * 文件名：LogInterceptor.java
 * 制作者：zhaoyifei
 * @version 1.0
 */
package base.zyf.struts.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 拦截其主要目的是自动记录用户在系统内操作的痕迹，记录到数据库中，和log4j的作用不一样。
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
