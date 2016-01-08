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
 * ���s���[�����N���X�B
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

	/** �Ώێ�ID */
	private String taishoId;

	/** ������ */
	private String mojisuChk;
	 
	/** �啶���E�������̍��� */
	private String charChk1;

	/** �A���t�@�x�b�g�Ɛ����̍��� */
	private String charChk2;

	/** �\��1 */
	private String charChk3;

	/** �\��2 */
	private String charChk4;
	
	/** �\��3 */
	private String charChk5;
	
	/** �L������ */
	private Date yukoDate; 
	
	/** ���l*/
	private String biko;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
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
