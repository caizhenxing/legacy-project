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
 * �Ɩ��S���ҏ���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: GyomutantoPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class GyomutantoPk extends ValueObject{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �Ɩ��S����ID */
	private String gyomutantoId;
	
	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public GyomutantoPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getGyomutantoId() {
		return gyomutantoId;
	}

	/**
	 * @param string
	 */
	public void setGyomutantoId(String string) {
		gyomutantoId = string;
	}

}
