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
 * 2���R�����ʏ��i2���R�����ʓ��͗p�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka2ndInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class ShinsaKekka2ndInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** 2���R������ */
	private String    kekka2;
	
	/** ���o�� */
	private String    souKehi;
	
	/** ���N�x�o�� */
	private String    shonenKehi;
	
	/** �Ɩ��S���ҋL���� */
	private String    shinsa2Biko;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
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
