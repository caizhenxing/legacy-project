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
 * �L�[���[�h���i��L�[�j��ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: KeywordPk.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class KeywordPk extends ValueObject {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = -1292915334466966125L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���ȍזڃR�[�h */
	private String bunkaSaimokuCd;
	
	/** �����ԍ� */
	private String bunkatsuNo;
	
	/** �L�� */
	private String keywordCd;

	//...�Ȃ�

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KeywordPk() {
		super();
	}
	
	/**
	 * �R���X�g���N�^�B
	 * @param bunkaSaimokuCd
	 */
	public KeywordPk(String bunkaSaimokuCd, String bunkatsuNo, String keyword){

		this.bunkaSaimokuCd = bunkaSaimokuCd;
		
		this.bunkatsuNo = bunkatsuNo;
	
		this.keywordCd = keyword;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------



	/**
	 * @return �זڃR�[�h�̎擾
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

	/**
	 * @param string �זڃR�[�h�̐ݒ�
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

	/**
	 * @return �����ԍ��̎擾
	 */
	public String getBunkatsuNo() {
		return bunkatsuNo;
	}
	/**
	 * @param  �����ԍ��̐ݒ�B
	 */
	public void setBunkatsuNo(String bunkatsuNo) {
		this.bunkatsuNo = bunkatsuNo;
	}
	
	/**
	 * @return�@�L���̎擾
	 */
	public String getKeywordCd(){
		return keywordCd;
	}
	
	/**
	 * @param keyword �L���̐ݒ�
	 */
	public void setKeywordCd(String keyword){
		this.keywordCd = keyword;
	}


}
