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
 * ���ȍזڏ���ێ�����N���X�B
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

	/** �זږ� */
	private String saimokuName;

	/** ���ȃR�[�h */
	private String bunkaCd;
	
	/** ���Ȗ� */
	private String bunkaName;
	
	/** ����R�[�h */
	private String bunyaCd;
	
	/** ���얼 */
	private String bunyaName;
	
	/** �n */
	private String kei;
	
	/** �L�[���[�h */
	private String keyword;
	

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
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
	 * @return �L�[���[�h ��߂��܂��B
	 */
	public String getKeyword() {
		return keyword;
	}
	/**
	 * @param  �L�[���[�h ��ݒ�B
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
