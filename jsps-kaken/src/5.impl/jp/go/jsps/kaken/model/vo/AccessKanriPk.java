/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/07/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;


/**
 * �A�N�Z�X�������L�[��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: AccessKanriPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class AccessKanriPk extends GyomutantoPk {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���ƃR�[�h */
	private String jigyoCd     = null;
	
	//...

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public AccessKanriPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getJigyoCd() {
		return jigyoCd;
	}

	/**
	 * @param string
	 */
	public void setJigyoCd(String string) {
		jigyoCd = string;
	}

}
