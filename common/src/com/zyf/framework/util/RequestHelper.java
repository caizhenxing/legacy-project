package com.zyf.framework.util;

import javax.servlet.ServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @since 2005-9-7
 * @author ����
 * @version $Id: RequestHelper.java,v 1.1 2007/12/08 08:17:12 lanxg Exp $
 */
public class RequestHelper {

	/**
	 * �� reqeust ��ȡ����, ���Ϊ��, ����Ĭ��ֵ
	 * @param request HttpServletRequest
	 * @param paramName ��������
	 * @param defaultValue Ĭ��ֵ
	 * @return ����ֵ
	 */
	public static String getParam(ServletRequest request, String paramName, String defaultValue) {
		String paramValue = request.getParameter(paramName);
		return StringUtils.isEmpty(paramValue) ? defaultValue : paramValue;
	}
	
}
