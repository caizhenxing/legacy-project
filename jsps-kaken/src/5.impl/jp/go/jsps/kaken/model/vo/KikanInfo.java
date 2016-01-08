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
 * 所属機関情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: KikanInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class KikanInfo extends KikanPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -82985179005499597L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 機関種別コード */
	private String shubetuCd;
	
	/** 機関区分 */
	private String kikanKubun;

	/** 機関名称(日本語)*/
	private String shozokuNameKanji;

	/** 機関略称*/
	private String shozokuRyakusho;

	/** 機関名称(英語)*/
	private String shozokuNameEigo;

	/** 郵便番号 */
	private String shozokuZip;

	/** 住所１ */
	private String shozokuAddress1;

	/** 住所２ */
	private String shozokuAddress2;

	/** 電話番号 */
	private String shozokuTel;

	/** FAX */
	private String shozokuFax;

	/** 代表者名 */
	private String shozokuDaihyoName;
	
	/** 備考*/
	private String biko;

	//...など

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public KikanInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getBiko() {
		return biko;
	}

	/**
	 * @return
	 */
	public String getShozokuAddress1() {
		return shozokuAddress1;
	}

	/**
	 * @return
	 */
	public String getShozokuAddress2() {
		return shozokuAddress2;
	}

	/**
	 * @return
	 */
	public String getShozokuFax() {
		return shozokuFax;
	}

	/**
	 * @return
	 */
	public String getShozokuNameEigo() {
		return shozokuNameEigo;
	}

	/**
	 * @return
	 */
	public String getShozokuNameKanji() {
		return shozokuNameKanji;
	}

	/**
	 * @return
	 */
	public String getShozokuRyakusho() {
		return shozokuRyakusho;
	}

	/**
	 * @return
	 */
	public String getShozokuTel() {
		return shozokuTel;
	}

	/**
	 * @return
	 */
	public String getShozokuZip() {
		return shozokuZip;
	}

	/**
	 * @param string
	 */
	public void setBiko(String string) {
		biko = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuAddress1(String string) {
		shozokuAddress1 = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuAddress2(String string) {
		shozokuAddress2 = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuFax(String string) {
		shozokuFax = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameEigo(String string) {
		shozokuNameEigo = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuNameKanji(String string) {
		shozokuNameKanji = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuRyakusho(String string) {
		shozokuRyakusho = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuTel(String string) {
		shozokuTel = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuZip(String string) {
		shozokuZip = string;
	}

	/**
	 * @return
	 */
	public String getShubetuCd() {
		return shubetuCd;
	}

	/**
	 * @param string
	 */
	public void setShubetuCd(String string) {
		shubetuCd = string;
	}

	/**
	 * @return
	 */
	public String getKikanKubun() {
		return kikanKubun;
	}

	/**
	 * @return
	 */
	public String getShozokuDaihyoName() {
		return shozokuDaihyoName;
	}

	/**
	 * @param string
	 */
	public void setKikanKubun(String string) {
		kikanKubun = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuDaihyoName(String string) {
		shozokuDaihyoName = string;
	}

}
