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
 * ���ȍזڏ��i��L�[�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: SaimokuPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:14 $"
 */
public class SaimokuPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -1292915334466966125L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���ȍזڃR�[�h */
	private String bunkaSaimokuCd;
	
//	2005/04/12 �ǉ� ��������----------
//	���R:�����ԍ��ǉ��̂���
	
	/** �����ԍ� */
	private String bunkatsuNo;
	
//	2005/04/12 �ǉ� �����܂�----------

	//...�Ȃ�

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public SaimokuPk() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param bunkaSaimokuCd
	 */
	public SaimokuPk(String bunkaSaimokuCd,String bunkatsuNo){
		this.bunkaSaimokuCd = bunkaSaimokuCd;
//2005/04/12 �ǉ� ��������----------
//���R:�����ԍ��ǉ��̂���
		
		this.bunkatsuNo = bunkatsuNo;
		
//2005/04/12 �ǉ� �����܂�----------
		
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

//	2005/04/12 �ǉ� ��������----------
//	���R:�����ԍ��ǉ��̂���
	
	/**
	 * @return bunkatsuNo ��߂��܂��B
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}
	/**
	 * @param bunkatsuNo bunkatsuNo ��ݒ�B
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}
	
//	2005/04/12 �ǉ� �����܂�----------
}
