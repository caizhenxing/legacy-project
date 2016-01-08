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
 * 申請者情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinseishaPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShinseishaPk extends ValueObject{
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 申請者ID */
	private String shinseishaId;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinseishaPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getShinseishaId() {
		return shinseishaId;
	}

	/**
	 * @param string
	 */
	public void setShinseishaId(String string) {
		shinseishaId = string;
	}

}
