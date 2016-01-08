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
 * 分科細目情報（主キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: SaimokuPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class SaimokuPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -1292915334466966125L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 分科細目コード */
	private String bunkaSaimokuCd;
	
//	2005/04/12 追加 ここから----------
//	理由:分割番号追加のため
	
	/** 分割番号 */
	private String bunkatsuNo;
	
//	2005/04/12 追加 ここまで----------

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public SaimokuPk() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 * @param bunkaSaimokuCd
	 */
	public SaimokuPk(String bunkaSaimokuCd,String bunkatsuNo){
		this.bunkaSaimokuCd = bunkaSaimokuCd;
//2005/04/12 追加 ここから----------
//理由:分割番号追加のため
		
		this.bunkatsuNo = bunkatsuNo;
		
//2005/04/12 追加 ここまで----------
		
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

//	2005/04/12 追加 ここから----------
//	理由:分割番号追加のため
	
	/**
	 * @return bunkatsuNo を戻します。
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}
	/**
	 * @param bunkatsuNo bunkatsuNo を設定。
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}
	
//	2005/04/12 追加 ここまで----------
}
