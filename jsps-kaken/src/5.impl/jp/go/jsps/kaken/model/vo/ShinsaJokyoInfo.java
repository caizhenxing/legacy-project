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
 * 審査状況情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShinsaJokyoInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 審査員番号 */
	private String shinsainNo;

	/** 審査員名（漢字-氏） */
	private String nameKanjiSei;

	/** 審査員名（漢字-名） */
	private String nameKanjiMei;

	/** 事業名 */
	private String jigyoName;

	/** 年度 */
	private String nendo;

	/** 回数 */
	private String kaisu;

	/** 事業区分（再審査用） */
	private String jigyoKubun;

	/** 事業ID（再審査用） */
	private String jigyoId;
	
	/** 事業コード */
	private String jigyoCd;
	
//最終ログイン日を追加
  /** 最終ログイン日 */
	private String loginDate;
	
//整理番号（学創用）を追加	2005/11/2
	/** 整理番号（学創用） */
	  private String seiriNo;
		
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaJokyoInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

//最終ログイン日を追加
	/**
	  * @return
	  */
	public String getLoginDate() {
		return loginDate;
	}
	/**
	 * @param string
	 */
	public void setLoginDate(String string) {
		loginDate = string;
	}
	
//整理番号（学創用）を追加	2005/11/2	
	/**
	 * @return
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
	 * @param string
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

}
