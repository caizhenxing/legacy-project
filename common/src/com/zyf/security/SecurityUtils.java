/** 
 * ����׿Խ�Ƽ����޹�˾��Ȩ����
 * ��Ŀ���ƣ�qware
 * ����ʱ�䣺2007-11-20����09:12:37
 * ������com.zyf.security
 * �ļ�����SecurityUtils.java
 * �����ߣ�yushn
 * @version 1.0
 */
package com.zyf.security;

import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtils {
	/**
	 * ��������
	 * @param ��������,����Ϊnull,��ʾҪ��ȡĬ�Ͽ��������
	 * @return ��������
	 * 2007-11-23 ����04:04:38
	 * @version 1.0
	 * @author yushn
	 */
	public static String passwordHex(String plain)
	{
		if(null == plain)
			return DigestUtils.md5Hex("1");
		else
			return DigestUtils.md5Hex(plain);
	}
}
