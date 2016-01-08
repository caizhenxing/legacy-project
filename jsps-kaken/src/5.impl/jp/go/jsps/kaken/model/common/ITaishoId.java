/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.common;

/**
 * 対象者IDを定義する。
 * 
 * ID RCSfile="$RCSfile: ITaishoId.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface ITaishoId {
	
	/** 申請者 */
	public static final String SHINSEISHA = "1";

	/** 所属機関担当者 */
	public static final String SHOZOKUTANTO = "2";

	/** 業務担当者 */
	public static final String GYOMUTANTO = "3";

	/** 審査員 */
	public static final String SHINSAIN = "4";

	/** 研究センタ */
	public static final String CENTER = "5";
	
	/* 2005/03/24 追加 ここから---------------------------
	 * 理由 「部局担当者」の対象者ID追加のため */
	
	/** 部局担当者 */
	public static final String BUKYOKUTANTO = "6";
	
	/* 追加 ここまで-------------------------------------- */

}
