/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/07/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * 申請データ情報（キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinseiDataPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShinseiDataPk extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** システム受付番号 */
	private String systemNo;
	
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinseiDataPk() {
		super();
	}
	
	/**
	 * コンストラクタ
	 * @param systemNo システム受付番号
	 */
	public ShinseiDataPk(String systemNo){
		super();
		this.systemNo = systemNo;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @param string
	 */
	public void setSystemNo(String string) {
		systemNo = string;
	}

}
