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
 * ϵͳ��������Ϣ, ������ǰִ���̵߳���Ϣ, ���統ǰ�û�, ��ȫ��֤, ������������Ϣ. �����Ϣ
 * �������û���һ���������������, �����<code>web</code>ҳ��������Ӧ����
 * 
 * @author scott
 * @author java2enterprise
 * @since 2006-2-26
 * @version $Id: ContextInfo.java,v 1.3 2008/01/29 14:06:58 lanxg Exp $
 *
 */
public class ContextInfo implements Serializable {
    
    /**
	 * ������
	 * ��������serialVersionUID
	 * �������ͣ�long
	 */
	private static final long serialVersionUID = -5088740905552443587L;

	private static Log logger = LogFactory.getLog(ContextInfo.class);

    /**
     * ���浱ǰִ���������еļ���,�������ݵ�����
     */
    private static ThreadLocal contextCondition = new ThreadLocal();
    
    
    private static ThreadLocal contextUser = new ThreadLocal();
    
    /**
     * �ӵ�ǰִ�л����м�������������
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
     * ���ε���ǰ������, ������<code>AOP</code>�����ʹ��. ������Ҫ�޸������������Ҫ��ѯ
     * �����־, �Ծ����Ƿ�Ӧ����Щ����. ��ȫ�е�����Ӧ�ò��������������. �����ǰִ���߳�
     * û��������Ϣ, ʲôҲ����
     */
    public static void concealQuery() {
        ConditionInfo info = getContextCondition();
        info.setConcealQuery(true);
    }
    
    /**
     * �����ǰִ���߳�������������Ϣ, ������������ٴλָ�������Ϣ. ��ִ��<code>DAO</code>
     * ��ѯʱ��ҪӦ���������. �����ǰִ���߳�û��������Ϣ��ʲôҲ����
     */
    public static void recoverQuery() {
        ConditionInfo info = getContextCondition();
        info.setConcealQuery(false);
    }

    /**
     * ��ѯ��ǰִ���߳��Ƿ����ε���Ӧ����<code>DAO</code>����������, ���е���Բ�ѯ������
     * <code>AOP</code>�����Ӧ�÷���ǰ��Ҫ��ѯ�����־, ����ȷ�Ƿ�Ӧ������.�����ǰִ����
     * ��û��������Ϣ, ʲôҲ����
     * @return �Ƿ����ε��˲�ѯ����, �������<code>true</code>, �Ͳ�ҪӦ����Щ����
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
