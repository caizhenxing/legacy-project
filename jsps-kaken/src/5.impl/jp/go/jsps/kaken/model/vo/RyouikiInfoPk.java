/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/07/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 *領域マスタ情報（主キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: RyouikiInfoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class RyouikiInfoPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -1292915334466966125L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 領域番号 */
	private String ryoikiNo;
	
	/** 研究項目番号 */
	private String komokuNo;
	
	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public RyouikiInfoPk() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 * @param bunkaSaimokuCd
	 */
	public RyouikiInfoPk(String cd1, String cd2 ){

		this.ryoikiNo = cd1;
		
		this.komokuNo = cd2;
	
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return 領域番号の取得
	 */
	public String getRyoikiNo() {
		return ryoikiNo;
	}

	/**
	 * @param string 領域番号の設定
	 */
	public void setRyoikiNo(String string) {
		ryoikiNo = string;
	}

	/**
	 * @return 研究項目番号の取得
	 */
	public String getKomokuNo() {
		return komokuNo;
	}
	/**
	 * @param  研究項目番号の設定。
	 */
	public void setKomokuNo(String bunkatsuNo) {
		this.komokuNo = bunkatsuNo;
	}
	

}
