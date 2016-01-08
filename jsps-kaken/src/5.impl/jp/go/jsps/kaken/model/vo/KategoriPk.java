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

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * �J�e�S�����i��L�[�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KategoriPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class KategoriPk extends ValueObject {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���ȃJ�e�S�� */
	private String bukaKategori;

	//...�Ȃ�

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KategoriPk() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param bunkaSaimokuCd
	 */
	public KategoriPk(String bukaKategori){
		this.bukaKategori = bukaKategori;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getBukaKategori() {
		return bukaKategori;
	}

	/**
	 * @param string
	 */
	public void setBukaKategori(String string) {
		bukaKategori = string;
	}

}
