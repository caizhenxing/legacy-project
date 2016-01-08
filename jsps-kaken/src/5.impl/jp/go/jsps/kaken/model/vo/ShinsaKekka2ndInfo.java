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
 * 2次審査結果情報（2次審査結果入力用）を保持するクラス。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka2ndInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class ShinsaKekka2ndInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 2次審査結果 */
	private String    kekka2;
	
	/** 総経費 */
	private String    souKehi;
	
	/** 初年度経費 */
	private String    shonenKehi;
	
	/** 業務担当者記入欄 */
	private String    shinsa2Biko;
	
	//・・・・・・・・・・

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public ShinsaKekka2ndInfo() {
		super();
	}
	
	
	
	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getKekka2() {
		return kekka2;
	}

	/**
	 * @return
	 */
	public String getShinsa2Biko() {
		return shinsa2Biko;
	}

	/**
	 * @return
	 */
	public String getShonenKehi() {
		return shonenKehi;
	}

	/**
	 * @return
	 */
	public String getSouKehi() {
		return souKehi;
	}

	/**
	 * @param string
	 */
	public void setKekka2(String string) {
		kekka2 = string;
	}

	/**
	 * @param string
	 */
	public void setShinsa2Biko(String string) {
		shinsa2Biko = string;
	}

	/**
	 * @param string
	 */
	public void setShonenKehi(String string) {
		shonenKehi = string;
	}

	/**
	 * @param string
	 */
	public void setSouKehi(String string) {
		souKehi = string;
	}

}
