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
 * �Y�t�t�@�C�����i�L�[�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: TenpuFilePk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class TenpuFilePk extends ShinseiDataPk{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �V�[�P���X�ԍ� */
	private String seqTenpu;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public TenpuFilePk() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getSeqTenpu() {
		return seqTenpu;
	}

	/**
	 * @param string
	 */
	public void setSeqTenpu(String string) {
		seqTenpu = string;
	}


}
