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

/**
 * 分科細目情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KeywordInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class KeywordInfo extends KeywordPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 細目名 */
	private String saimokuName;

	/** 分科コード */
	private String bunkaCd;
	
	/** 分科名 */
	private String bunkaName;
	
	/** 分野コード */
	private String bunyaCd;
	
	/** 分野名 */
	private String bunyaName;
	
	/** 系 */
	private String kei;
	
	/** キーワード */
	private String keyword;
	

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KeywordInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getBunkaCd() {
		return bunkaCd;
	}

	/**
	 * @return
	 */
	public String getBunkaName() {
		return bunkaName;
	}

	/**
	 * @return
	 */
	public String getBunyaCd() {
		return bunyaCd;
	}

	/**
	 * @return
	 */
	public String getBunyaName() {
		return bunyaName;
	}

	/**
	 * @return
	 */
	public String getKei() {
		return kei;
	}

	/**
	 * @return
	 */
	public String getSaimokuName() {
		return saimokuName;
	}

	/**
	 * @param string
	 */
	public void setBunkaCd(String string) {
		bunkaCd = string;
	}

	/**
	 * @param string
	 */
	public void setBunkaName(String string) {
		bunkaName = string;
	}

	/**
	 * @param string
	 */
	public void setBunyaCd(String string) {
		bunyaCd = string;
	}

	/**
	 * @param string
	 */
	public void setBunyaName(String string) {
		bunyaName = string;
	}

	/**
	 * @param string
	 */
	public void setKei(String string) {
		kei = string;
	}

	/**
	 * @param string
	 */
	public void setSaimokuName(String string) {
		saimokuName = string;
	}

	/**
	 * @return キーワード を戻します。
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param  キーワード を設定。
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
