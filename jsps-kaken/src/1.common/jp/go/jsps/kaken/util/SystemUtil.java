/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/10/15
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.util;

/**
 * システムプロパティユーティリティクラス<!--。-->
 * jakarta-commons の SystemUtilsクラスを拡張したもの。
 * ID RCSfile="$RCSfile: SystemUtil.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:44 $"
 */
public class SystemUtil extends org.apache.commons.lang.SystemUtils {
	
	/** ロードバランサースイッチング制御用Cookie名 */
	public static final String LB_COOKIE_NAME = System.getProperty("lb.cookie.name");
	
	/** ロードバランサースイッチング制御用Cookie値 */
	public static final String LB_COOKIE_VALUE = System.getProperty("lb.cookie.value");
	
	
}
