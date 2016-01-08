/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : 
 *    Date        : 
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.common;

import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * アプリケーション全体で使用する情報を格納するためのコンテナクラス。
 * 
 * ID RCSfile="$RCSfile: ApplicationContainer.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $"
 */
public class ApplicationContainer {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * クラス名。 
	 */
	private static final String CLASS_NAME = ApplicationContainer.class.getName();

	/**
	 * ログクラス。 
	 */
	private static final Log log = LogFactory.getLog(CLASS_NAME);


	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * システムのLocale情報
	 */
	private Locale systemLocale = null;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ApplicationContainer() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * システムのLocale情報を取得する。
	 * @return	Locale情報
	 */
	public Locale getSystemLocale() {
		return systemLocale;
	}

	/**
	 * システムのLocale情報を設定する。
	 * @param aLocale　Locale情報
	 */
	public void setSystemLocale(Locale aLocale) {
		systemLocale = aLocale;
	}


}
