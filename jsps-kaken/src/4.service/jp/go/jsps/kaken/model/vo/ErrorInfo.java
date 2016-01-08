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
package jp.go.jsps.kaken.model.vo;

import java.io.Serializable;

/**
 * エラー情報保持クラス。
 * 
 * ID RCSfile="$RCSfile: ErrorInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:38 $"
 */
public class ErrorInfo implements Serializable{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -3441380396156858703L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 
	 * エラーコード情報。
	 */
	private String errorCode;

	/** 
	 * エラー引数情報。
	 */
	private Object[] errorArgs;
	
	
	/**
	 * エラープロパティ
	 */
	private String property = "GLOBAL_ERROR";
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 * @param errorCode		エラーコード情報
	 */
	public ErrorInfo(String errorCode) {
		super();
		this.errorCode = errorCode;
	}

	/**
	 * コンストラクタ。
	 * @param errorCode		エラーコード情報
	 * @param errorArgs		エラー引数情報
	 */
	public ErrorInfo(String errorCode,Object[] errorArgs) {
		super();
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
	}

	
	/**
	 * コンストラクタ。
	 * @param errorCode       エラーコード情報
	 * @param errorArgs       エラ＾引数情報
	 * @param property 	   エラープロパティ
	 */
	public ErrorInfo(String errorCode, Object[] errorArgs, String property){
		super();
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;
		this.property  = property;
	}
	

	//---------------------------------------------------------------------
	// Proerties
	//---------------------------------------------------------------------

	/**
	 * エラー引数情報を取得する。
	 * @return	エラー引数情報
	 */
	public Object[] getErrorArgs() {
		return errorArgs;
	}

	/**
	 * エラーコード情報を取得する。
	 * @return	エラーコード情報
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * エラー引数情報をセットする。
	 * @param objects	エラー引数情報
	 */
	public void setErrorArgs(Object[] objects) {
		errorArgs = objects;
	}

	/**
	 * エラーコード情報をセットする。
	 * @param string	エラーコード情報
	 */
	public void setErrorCode(String string) {
		errorCode = string;
	}

	/**
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param string
	 */
	public void setProperty(String string) {
		property = string;
	}

}
