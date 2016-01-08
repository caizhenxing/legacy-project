/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 20053/03/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * 部局担当者情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: BukyokutantoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class BukyokutantoPk extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 部局担当者ID */
	private String bukyokutantoId;
	
	/** 担当部局コード */
	private String bukyokuCd;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public BukyokutantoPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return bukyokutantoId を戻します。
	 */
	public String getBukyokutantoId() {
		return bukyokutantoId;
	}
	/**
	 * @param bukyokutantoId bukyokutantoId を設定。
	 */
	public void setBukyokutantoId(String bukyokutantoId) {
		this.bukyokutantoId = bukyokutantoId;
	}
	/**
	 * @return bukyokuCd を戻します。
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}
	/**
	 * @param bukyokuCd bukyokuCd を設定。
	 */
	public void setBukyokuCd(String bukyokuCd) {
		this.bukyokuCd = bukyokuCd;
	}
}
