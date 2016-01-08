/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.model.vo;

import jp.go.jsps.kaken.model.vo.SearchInfo;

/**
 * �R��������������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsainSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:12 $"
 */
public class ShinsainSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �R���������i���j */
	private String nameKanjiSei;

	/** �R���������i���j */
	private String nameKanjiMei;

//	/** �����@�֖� */
//	private String shozokuName;

	/** ���ȍזڃR�[�h */
	private String bunkaSaimokuCd;

//	/** �L�[���[�h */
//	private String keyword;

	/** �����@�֖��i�R�[�h�j*/
	private String shozokuCd;

	/** �S�����Ƌ敪 */
	private String jigyoKubun;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsainSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @return
	 */
	public String getNameKanjiMei() {
		return nameKanjiMei;
	}

	/**
	 * @return
	 */
	public String getNameKanjiSei() {
		return nameKanjiSei;
	}

//	/**
//	 * @return
//	 */
//	public String getShozokuName() {
//		return shozokuName;
//	}

	/**
	 * @return
	 */
	public String getBunkaSaimokuCd() {
		return bunkaSaimokuCd;
	}

//	/**
//	 * @return
//	 */
//	public String getKeyword() {
//		return keyword;
//	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiMei(String string) {
		nameKanjiMei = string;
	}

	/**
	 * @param string
	 */
	public void setNameKanjiSei(String string) {
		nameKanjiSei = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setShozokuName(String string) {
//		shozokuName = string;
//	}

	/**
	 * @param string
	 */
	public void setBunkaSaimokuCd(String string) {
		bunkaSaimokuCd = string;
	}

//	/**
//	 * @param string
//	 */
//	public void setKeyword(String string) {
//		keyword = string;
//	}

/**
 * @return
 */
public String getShozokuCd() {
	return shozokuCd;
}

/**
 * @param string
 */
public void setShozokuCd(String string) {
	shozokuCd = string;
}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

}
