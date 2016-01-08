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
 * 割り振り結果情報を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: WarifuriPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class WarifuriPk extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** システム番号 */
	private String systemNo;

	/** 審査員番号 */
	private String shinsainNo;

	/** 事業区分 */
	private String jigyoKubun;
		
	/** 審査員番号（修正前） */
	private String oldShinsainNo;

	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public WarifuriPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setSystemNo(String string) {
		systemNo = string;
	}

	/**
	 * @return
	 */
	public String getOldShinsainNo() {
		return oldShinsainNo;
	}

	/**
	 * @param string
	 */
	public void setOldShinsainNo(String string) {
		oldShinsainNo = string;
	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

}
