/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 20053/03/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * ���ǒS���ҏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: BukyokutantoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class BukyokutantoPk extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���ǒS����ID */
	private String bukyokutantoId;
	
	/** �S�����ǃR�[�h */
	private String bukyokuCd;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public BukyokutantoPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return bukyokutantoId ��߂��܂��B
	 */
	public String getBukyokutantoId() {
		return bukyokutantoId;
	}
	/**
	 * @param bukyokutantoId bukyokutantoId ��ݒ�B
	 */
	public void setBukyokutantoId(String bukyokutantoId) {
		this.bukyokutantoId = bukyokutantoId;
	}
	/**
	 * @return bukyokuCd ��߂��܂��B
	 */
	public String getBukyokuCd() {
		return bukyokuCd;
	}
	/**
	 * @param bukyokuCd bukyokuCd ��ݒ�B
	 */
	public void setBukyokuCd(String bukyokuCd) {
		this.bukyokuCd = bukyokuCd;
	}
}
