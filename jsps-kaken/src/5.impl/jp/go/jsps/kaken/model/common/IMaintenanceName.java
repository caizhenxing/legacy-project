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
 * メンテナンスモードを定義する。
 * 
 * ID RCSfile="$RCSfile: IMaintenanceName.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public interface IMaintenanceName {
	
	/** 登録モード */
	public static final String ADD_MODE = "add_mode";

	/** 修正モード */
	public static final String EDIT_MODE = "edit_mode";
	
}
