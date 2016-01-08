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
 * �E����i��L�[�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShokushuPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShokushuPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 311796230497307855L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �E��R�[�h */
	private String shokushuCd;

	//...�Ȃ�

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShokushuPk() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param bunkaSaimokuCd
	 */
	public ShokushuPk(String shokushuCd){
		this.shokushuCd = shokushuCd;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getShokushuCd() {
		return shokushuCd;
	}

	/**
	 * @param string
	 */
	public void setShokushuCd(String string) {
		shokushuCd = string;
	}

}
