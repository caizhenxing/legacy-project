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
 * 職種情報（主キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShokushuPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShokushuPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 311796230497307855L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 職種コード */
	private String shokushuCd;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShokushuPk() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 * @param bunkaSaimokuCd
	 */
	public ShokushuPk(String shokushuCd){
		this.shokushuCd = shokushuCd;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

}
