/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/07/21
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 *�̈�}�X�^���i��L�[�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: RyouikiInfoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class RyouikiInfoPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -1292915334466966125L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �̈�ԍ� */
	private String ryoikiNo;
	
	/** �������ڔԍ� */
	private String komokuNo;
	
	//...�Ȃ�

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public RyouikiInfoPk() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param bunkaSaimokuCd
	 */
	public RyouikiInfoPk(String cd1, String cd2 ){

		this.ryoikiNo = cd1;
		
		this.komokuNo = cd2;
	
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return �̈�ԍ��̎擾
	 */
	public String getRyoikiNo() {
		return ryoikiNo;
	}

	/**
	 * @param string �̈�ԍ��̐ݒ�
	 */
	public void setRyoikiNo(String string) {
		ryoikiNo = string;
	}

	/**
	 * @return �������ڔԍ��̎擾
	 */
	public String getKomokuNo() {
		return komokuNo;
	}
	/**
	 * @param  �������ڔԍ��̐ݒ�B
	 */
	public void setKomokuNo(String bunkatsuNo) {
		this.komokuNo = bunkatsuNo;
	}
	

}
