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
 * ������\�ҋy�ѕ��S�ҁi�����g�D�\�j�̎�L�[���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KenkyuSoshikiKenkyushaPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:30 $"
 */
public class KenkyuSoshikiKenkyushaPk extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** �V�[�P���X�ԍ� */
	private String seqNo;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KenkyuSoshikiKenkyushaPk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getSeqNo() {
		return seqNo;
	}

	/**
	 * @param string
	 */
	public void setSeqNo(String string) {
		seqNo = string;
	}

}
