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
 * ID RCSfile="$RCSfile: SaimokuInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:10 $"
 */
public class SaimokuInfo extends SaimokuPk {

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
	
//	2005/04/12 �ǉ� ��������----------
//	���R:�����ԍ��ǉ��̂���

	/** �����ԍ� */
	//SaimokuPk���p�����ꂽ�̂ŁA�s�v�ƂȂ�
	//private String bunkatsuNo;
	
//	2005/04/12 �ǉ� �����܂�----------

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public SaimokuInfo() {
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

//	/**
//	 * @return bunkatsuNo ��߂��܂��B
//	 */
//	public String getBunkatsuNo() {
//		return bunkatsuNo;
//	}
//	/**
//	 * @param bunkatsuNo bunkatsuNo ��ݒ�B
//	 */
//	public void setBunkatsuNo(String bunkatsuNo) {
//		this.bunkatsuNo = bunkatsuNo;
//	}
}
