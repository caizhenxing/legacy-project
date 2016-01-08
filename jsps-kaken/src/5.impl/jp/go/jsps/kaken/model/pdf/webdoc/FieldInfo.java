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

package jp.go.jsps.kaken.model.pdf.webdoc;

import jp.go.jsps.kaken.model.vo.ValueObject;

/**
 * iod�t�@�C���̍��ڂƏo�̓v���p�e�B������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: FieldInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:57 $"
 */
public class FieldInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 351806220948002289L;

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** iod�ɐݒ肵�Ă���l */
	private String name;
	
	/** �ݒ肷��I�u�W�F�N�g�̃v���p�e�B�� */
	private String value;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public FieldInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setValue(String string) {
		value = string;
	}

}
