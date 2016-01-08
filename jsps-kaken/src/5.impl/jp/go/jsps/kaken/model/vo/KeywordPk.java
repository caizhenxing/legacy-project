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
 * キーワード情報（主キー）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KeywordPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class KeywordPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -1292915334466966125L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 分科細目コード */
	private String bunkaSaimokuCd;
	
	/** 分割番号 */
	private String bunkatsuNo;
	
	/** 記号 */
	private String keywordCd;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KeywordPk() {
		super();
	}
	
	/**
	 * コンストラクタ。
	 * @param bunkaSaimokuCd
	 */
	public KeywordPk(String bunkaSaimokuCd, String bunkatsuNo, String keyword){

		this.bunkaSaimokuCd = bunkaSaimokuCd;
		
		this.bunkatsuNo = bunkatsuNo;
	
		this.keywordCd = keyword;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return 細目コードの取得
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @param string 細目コードの設定
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

	/**
	 * @return 分割番号の取得
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}
	/**
	 * @param  分割番号の設定。
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}
	
	/**
	 * @return　記号の取得
	 */
	public String getKeywordCd(){
		return keywordCd;
	}
	
	/**
	 * @param keyword 記号の設定
	 */
	public void setKeywordCd(String keyword){
		this.keywordCd = keyword;
	}


}
