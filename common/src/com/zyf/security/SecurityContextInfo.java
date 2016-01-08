/**
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�qware
 * ����ʱ�䣺2007-11-20����09:12:37
 * ������com.zyf.security.model
 * �ļ�����SecurityContextInfo.java
 * �����ߣ�yushn
 * @version 1.0
 */
package com.zyf.security;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.acegisecurity.context.SecurityContextHolder;

import com.zyf.security.model.CurrentUser;
import com.zyf.security.userdetails.ExtensionalUserDetails;

/**
 * ��ȫ������
 * @author yushn
 * @version 1.0
 */
public class SecurityContextInfo {
	private static ThreadLocal currentUser = new ThreadLocal();
	private static ThreadLocal currentPageUrl = new ThreadLocal();
	private static ThreadLocal mainTableClassName = new ThreadLocal();
    private static ThreadLocal singleSignOnUrl = new ThreadLocal();

    //��ŵ�ǰ�����session
    private static ThreadLocal session  = new ThreadLocal();
	/**
	 * ��յ�ǰ��ȫ������
	 * ��������
	 * 2007-11-26 ����07:47:49
	 * @version 1.0
	 * @author yushn
	 */ 
    public static void clear() {
        currentUser.set(null);
    }
	/**
	 * ��ȡ��ǰ��¼�û�
	 * ��������
	 * @return
	 * 2007-11-20 ����09:13:38
	 * @version 1.0
	 * @author yushn
	 */
	public static CurrentUser getCurrentUser()
	{
//		if (currentUser.get() == null) {
//			//TODO:��acige��ȫ�������л�ȡ��ǰ�û�,��ǰ�û���Ϣ�ڵ�¼ʱ����	
//        }
//        return (CurrentUser)currentUser.get();
//		return new MockCurrentUser();
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
		ExtensionalUserDetails user = (ExtensionalUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CurrentUser cu = null;
        if (user != null) {
            cu = user.getCurrentUser();
        }
        return cu;
    }
	/**
	 * ��ȡ��ǰ����ҳ���url
	 * ��������
	 * @return
	 * 2007-11-28 ����07:17:32
	 * @version 1.0
	 * @author yushn
	 */
	public static String getCurrentPageUrl() {
		return currentPageUrl.get().toString();
	}
	public static void setCurrentPageUrl(String currentPageUrl) {
		SecurityContextInfo.currentPageUrl.set(currentPageUrl);
	}
	public static String getMainTableClassName() {
		return mainTableClassName.get().toString();
	}
	public static void setMainTableClassName(String mainTableClassName) {
		SecurityContextInfo.mainTableClassName.set(mainTableClassName);
	}
    public static String getSingleSignOnUrl() {
        return (singleSignOnUrl.get() != null) ? singleSignOnUrl.get().toString() : "";
    }
    public static void setSingleSignOnUrl(String singleSignOnUrl) {
        SecurityContextInfo.singleSignOnUrl.set(singleSignOnUrl);
    }

	/**
	 * ��SESSION��ȡ��ǰ�����URL��Ӧ�����ݼ�¼�����ݷּ���Ϣ
	 * ��������
	 * @return
	 * 2007-12-25 ����04:55:32
	 * @version 1.0
	 * @author wenjb
	 */
	public static Map getRwCtrlTypeMap() {
		
		return  (Map) getSession().getAttribute("rwCtrlTypeMap");
	}
	/**
	 * ��SESSION�����õ�ǰ�����URL��Ӧ�����ݼ�¼�����ݷּ���Ϣ
	 * ��������
	 * @return
	 * 2007-12-25 ����04:55:32
	 * @version 1.0
	 * @author wenjb
	 */
	public static void setRwCtrlTypeMap(Map rwCtrlType) {
		getSession().removeAttribute("rwCtrlTypeMap");
		getSession().setAttribute("rwCtrlTypeMap", rwCtrlType);
	}
	/**
	 * ��ȡ��ǰ��session
	 * ��������
	 * @return
	 * 2008-1-3 ����04:55:32
	 * @version 1.0
	 * @author wenjb
	 */
	public static HttpSession getSession() {
		return  (HttpSession) SecurityContextInfo.session.get();
	}
	public static void setSession(HttpSession session) {
		SecurityContextInfo.session.set(session);
	}
}