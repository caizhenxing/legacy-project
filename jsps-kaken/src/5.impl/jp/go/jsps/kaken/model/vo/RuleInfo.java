/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/12/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import java.util.Date;

/**
 * 発行ルール情報クラス。
 * 
 * ID RCSfile="$RCSfile: RuleInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class RuleInfo extends RulePk{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -7513899876625044564L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** 対象者ID */
	private String taishoId;

	/** 文字数 */
	private String mojisuChk;
	 
	/** 大文字・小文字の混在 */
	private String charChk1;

	/** アルファベットと数字の混在 */
	private String charChk2;

	/** 予備1 */
	private String charChk3;

	/** 予備2 */
	private String charChk4;
	
	/** 予備3 */
	private String charChk5;
	
	/** 有効期限 */
	private Date yukoDate; 
	
	/** 備考*/
	private String biko;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * コンストラクタ。
	 */
	public RuleInfo() {
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
	public String getCharChk1() {
		return charChk1;
	}

	/**
	 * @return
	 */
	public String getCharChk2() {
		return charChk2;
	}

	/**
	 * @return
	 */
	public String getCharChk3() {
		return charChk3;
	}

	/**
	 * @return
	 */
	public String getCharChk4() {
		return charChk4;
	}

	/**
	 * @return
	 */
	public String getCharChk5() {
		return charChk5;
	}

	/**
	 * @return
	 */
	public String getMojisuChk() {
		return mojisuChk;
	}

	/**
	 * @return
	 */
	public String getTaishoId() {
		return taishoId;
	}

	/**
	 * @return
	 */
	public Date getYukoDate() {
		return yukoDate;
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
	public void setCharChk1(String string) {
		charChk1 = string;
	}

	/**
	 * @param string
	 */
	public void setCharChk2(String string) {
		charChk2 = string;
	}

	/**
	 * @param string
	 */
	public void setCharChk3(String string) {
		charChk3 = string;
	}

	/**
	 * @param string
	 */
	public void setCharChk4(String string) {
		charChk4 = string;
	}

	/**
	 * @param string
	 */
	public void setCharChk5(String string) {
		charChk5 = string;
	}

	/**
	 * @param string
	 */
	public void setMojisuChk(String string) {
		mojisuChk = string;
	}

	/**
	 * @param string
	 */
	public void setTaishoId(String string) {
		taishoId = string;
	}

	/**
	 * @param date
	 */
	public void setYukoDate(Date date) {
		yukoDate = date;
	}

}
