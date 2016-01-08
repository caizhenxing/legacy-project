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

package jp.go.jsps.kaken.model.vo.shinsei;

import jp.go.jsps.kaken.model.vo.ShinseiDataPk;


/**
 * �����o�����ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KenkyuKeihiInfo.java,v $"
 * Revision="$Revision: 1.3 $"
 * Date="$Date: 2007/07/20 01:27:00 $"
 */
public class KenkyuKeihiInfo extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
// ADD START 2007-07-10 BIS ���u��
	/** ����z */
	private String naiyaku;
// ADD END 2007-07-10 BIS ���u��
	
	/** �����o�� */
	private String keihi;
	
	/** �ݔ����i�� */
	private String bihinhi;
	
	/** ���Օi�� */
	private String shomohinhi;
	
	/** �������� */
	private String kokunairyohi;
	
	/** �O������ */
	private String gaikokuryohi;
	
	/** ���� */
	private String ryohi;
	
	/** �Ӌ��� */
	private String shakin;
	
	/** ���̑� */
	private String sonota;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KenkyuKeihiInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getBihinhi() {
		return bihinhi;
	}

	/**
	 * @return
	 */
	public String getGaikokuryohi() {
		return gaikokuryohi;
	}

	/**
	 * @return
	 */
	public String getKeihi() {
		return keihi;
	}

	/**
	 * @return
	 */
	public String getKokunairyohi() {
		return kokunairyohi;
	}

	/**
	 * @return
	 */
	public String getShakin() {
		return shakin;
	}

	/**
	 * @return
	 */
	public String getShomohinhi() {
		return shomohinhi;
	}

	/**
	 * @return
	 */
	public String getSonota() {
		return sonota;
	}

	/**
	 * @param string
	 */
	public void setBihinhi(String string) {
		bihinhi = string;
	}

	/**
	 * @param string
	 */
	public void setGaikokuryohi(String string) {
		gaikokuryohi = string;
	}

	/**
	 * @param string
	 */
	public void setKeihi(String string) {
		keihi = string;
	}

	/**
	 * @param string
	 */
	public void setKokunairyohi(String string) {
		kokunairyohi = string;
	}

	/**
	 * @param string
	 */
	public void setShakin(String string) {
		shakin = string;
	}

	/**
	 * @param string
	 */
	public void setShomohinhi(String string) {
		shomohinhi = string;
	}

	/**
	 * @param string
	 */
	public void setSonota(String string) {
		sonota = string;
	}

	/**
	 * @return
	 */
	public String getRyohi() {
		return ryohi;
	}

	/**
	 * @param string
	 */
	public void setRyohi(String string) {
		ryohi = string;
	}
//ADD START 2007-07-10 BIS ���u��
	public String getNaiyaku() {
		return naiyaku;
	}

	public void setNaiyaku(String naiyaku) {
		this.naiyaku = naiyaku;
	}
//ADD END 2007-07-10 BIS ���u��
}
