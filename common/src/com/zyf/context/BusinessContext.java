package com.zyf.context;

import javax.servlet.http.HttpSession;

import com.zyf.security.SecurityContextInfo;

public class BusinessContext {

	/*
	 *��ȡ����ֵʱ�򣬷���-1
	 * */
	
	public static int getOperType() {
		HttpSession session =getHttpSession();
		if (null == session.getAttribute("operType")){
			return -1;
		}else{
			return ((Integer)session.getAttribute("operType")).intValue();
		}
	}

	public static void setOperType(int operType) {
		HttpSession session =getHttpSession();
		Integer operTypeInteger=new Integer(operType);
		if (null == session.getAttribute("operType")){
			session.setAttribute("operType", operTypeInteger);	
		}else{
			session.removeAttribute("operType");	
			session.setAttribute("operType", operTypeInteger);			
		}
	}
	
	/*
	 * ��ȡ��ǰ��SESSION
	 * */
	private static HttpSession getHttpSession(){
		return SecurityContextInfo.getSession();
	}
	
	public static boolean isNull(){
		HttpSession session =getHttpSession();
		if(session.getAttribute("operType")==null){
			return true;
		}
		return false;
	}

	/**
	 * �õ�ǰһ��ҳ���oid
	 * @return String
	 */
	public static String getUserSetedOid() {
		HttpSession session =getHttpSession();
		return session.getAttribute("userSetedOid")==null?null:(String)session.getAttribute("userSetedOid");
	}

	/**
	 * ����ǰһ��ҳ���oid
	 * @param userSetedOid
	 */
	public static void setUserSetedOid(String userSetedOid) {
		HttpSession session =getHttpSession();
		session.setAttribute("userSetedOid", userSetedOid);
	}
}
