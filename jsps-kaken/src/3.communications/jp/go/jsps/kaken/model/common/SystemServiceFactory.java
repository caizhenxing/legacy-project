/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

import java.lang.reflect.Proxy;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.client.CallServletHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * サービスを取得するためのファクトリクラス。
 * ID RCSfile="$RCSfile: SystemServiceFactory.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class SystemServiceFactory {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * クラス名。 
	 */
	private static final String CLASS_NAME = SystemServiceFactory.class.getName();

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(CLASS_NAME);

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	private SystemServiceFactory() {
		super();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/**
	 * システムサービスを取得する。
	 * @param serviceName		サービス名
	 * @return					システムサービス実装クラス
	 */
	public static ISystemServise getSystemService(String serviceName) {
		Class[] serviceInterface = new Class[] { ISystemServise.class };
		ISystemServise proxy =
			(ISystemServise) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				serviceInterface,
				new CallServletHandler(serviceName));
		return proxy;
	}
	
	/**
	 * システムサービスを取得する。
	 * @param serviceName		サービス名
	 * @param serverUrl			サーバURL
	 * @return					システムサービス実装クラス
	 */
	public static ISystemServise getSystemService(
		String serviceName,
		String serverUrl) {
		Class[] serviceInterface = new Class[] { ISystemServise.class };
		ISystemServise proxy =
			(ISystemServise) Proxy.newProxyInstance(
				Thread.currentThread().getContextClassLoader(),
				serviceInterface,
				new CallServletHandler(serviceName,serverUrl));
		return proxy;
	}
}
