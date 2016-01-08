/** 
 * 沈阳卓越科技有限公司版权所有
 * 项目名称：qware
 * 制作时间：2007-11-20下午09:12:37
 * 包名：com.zyf.security
 * 文件名：SecurityUtils.java
 * 制作者：yushn
 * @version 1.0
 */
package com.zyf.security;

import org.apache.commons.codec.digest.DigestUtils;

public class SecurityUtils {
	/**
	 * 加密密码
	 * @param 密码明文,可以为null,表示要获取默认口令的密文
	 * @return 密码密文
	 * 2007-11-23 下午04:04:38
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
