package base.zyf.web.condition;

import java.io.Serializable;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import base.zyf.web.user.UserBean;

import com.sun.org.apache.commons.logging.Log;
import com.sun.org.apache.commons.logging.LogFactory;


/**
 * 系统上下文信息, 包含当前执行线程的信息, 比如当前用户, 安全认证, 检索条件等信息. 这个信息
 * 将跟随用户的一次完整的请求过程, 比如从<code>web</code>页面请求到响应结束
 * 
 * @author scott
 * @author java2enterprise
 * @since 2006-2-26
 * @version $Id: ContextInfo.java,v 1.3 2008/01/29 14:06:58 lanxg Exp $
 *
 */
public class ContextInfo implements Serializable {
    
    /**
	 * 描述：
	 * 属性名：serialVersionUID
	 * 属性类型：long
	 */
	private static final long serialVersionUID = -5088740905552443587L;

	private static Log logger = LogFactory.getLog(ContextInfo.class);

    /**
     * 保存当前执行上下文中的检索,更新数据的条件
     */
    private static ThreadLocal contextCondition = new ThreadLocal();
    
    
    private static ThreadLocal contextUser = new ThreadLocal();
    
    /**
     * 从当前执行环境中检索保存条件的
     * @return
     */
    public static ConditionInfo getContextCondition() {
        ConditionInfo info = (ConditionInfo) contextCondition.get();
        if (info == null) {
            info = new ConditionInfo();
            contextCondition.set(info);
        }

        return info;
    }

    public static void setContextCondition(ConditionInfo contextCondition) {
        ContextInfo.contextCondition.set(contextCondition);
    }
    public static void setContextUser(UserBean ub) {
        ContextInfo.contextUser.set(ub);
    }
    /**
     * 屏蔽掉当前的条件, 避免在<code>AOP</code>组件中使用. 所有需要修改条件的组件都要查询
     * 这个标志, 以决定是否应用这些条件. 安全中的条件应用不适用于这个规则. 如果当前执行线程
     * 没有条件信息, 什么也不做
     */
    public static void concealQuery() {
        ConditionInfo info = getContextCondition();
        info.setConcealQuery(true);
    }
    
    /**
     * 如果当前执行线程屏蔽了条件信息, 调用这个方法再次恢复条件信息. 当执行<code>DAO</code>
     * 查询时仍要应用这个条件. 如果当前执行线程没有条件信息就什么也不做
     */
    public static void recoverQuery() {
        ConditionInfo info = getContextCondition();
        info.setConcealQuery(false);
    }

    /**
     * 查询当前执行线程是否屏蔽掉了应用于<code>DAO</code>操作的条件, 所有的针对查询操作的
     * <code>AOP</code>组件在应用方法前都要查询这个标志, 以明确是否应用条件.如果当前执行线
     * 程没有条件信息, 什么也不做
     * @return 是否屏蔽掉了查询条件, 如果返回<code>true</code>, 就不要应用这些条件
     */
    public static boolean isConcealQuery() {
        return getContextCondition().isConcealQuery();
    }
    
    public static UserBean getContextUser() {
    	if(null == ContextInfo.contextUser.get())
    	{
    	SecurityContext securityContext = SecurityContextHolder.getContext();
    	
		Authentication authentication = securityContext.getAuthentication();
		if(authentication != null){
			Object principal = authentication.getPrincipal();
			if("anonymousUser".equals(principal))
			{
				return null;
			}
			UserDetails ud = (UserDetails)principal;
			UserBean cn = new UserBean();
			cn.setAuthoritys(ud.getAuthorities());
            cn.setUserId(ud.getUsername());
            contextUser.set(cn);
			
		}
    	}
        return (UserBean) ContextInfo.contextUser.get();
    }
    
    public static void clear() {
        contextUser.set(null);
        contextCondition.set(null);
    }

}
