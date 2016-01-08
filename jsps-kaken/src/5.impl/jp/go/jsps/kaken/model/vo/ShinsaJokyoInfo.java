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
 * �R���󋵏���ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:11 $"
 */
public class ShinsaJokyoInfo extends ValueObject{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �R�����ԍ� */
	private String shinsainNo;

	/** �R�������i����-���j */
	private String nameKanjiSei;

	/** �R�������i����-���j */
	private String nameKanjiMei;

	/** ���Ɩ� */
	private String jigyoName;

	/** �N�x */
	private String nendo;

	/** �� */
	private String kaisu;

	/** ���Ƌ敪�i�ĐR���p�j */
	private String jigyoKubun;

	/** ����ID�i�ĐR���p�j */
	private String jigyoId;
	
	/** ���ƃR�[�h */
	private String jigyoCd;
	
//�ŏI���O�C������ǉ�
  /** �ŏI���O�C���� */
	private String loginDate;
	
//�����ԍ��i�w�n�p�j��ǉ�	2005/11/2
	/** �����ԍ��i�w�n�p�j */
	  private String seiriNo;
		
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaJokyoInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------


	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @return
	 */
	public String getJigyoKubun() {
		return jigyoKubun;
	}

	/**
	 * @return
	 */
	public String getJigyoName() {
		return jigyoName;
	}

	/**
	 * @return
	 */
	public String getKaisu() {
		return kaisu;
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

	/**
	 * @return
	 */
	public String getNendo() {
		return nendo;
	}

	/**
	 * @return
	 */
	public String getShinsainNo() {
		return shinsainNo;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoKubun(String string) {
		jigyoKubun = string;
	}

	/**
	 * @param string
	 */
	public void setJigyoName(String string) {
		jigyoName = string;
	}

	/**
	 * @param string
	 */
	public void setKaisu(String string) {
		kaisu = string;
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

	/**
	 * @param string
	 */
	public void setNendo(String string) {
		nendo = string;
	}

	/**
	 * @param string
	 */
	public void setShinsainNo(String string) {
		shinsainNo = string;
	}

//�ŏI���O�C������ǉ�
	/**
	  * @return
	  */
	public String getLoginDate() {
		return loginDate;
	}
	/**
	 * @param string
	 */
	public void setLoginDate(String string) {
		loginDate = string;
	}
	
//�����ԍ��i�w�n�p�j��ǉ�	2005/11/2	
	/**
	 * @return
	 */
	public String getSeiriNo() {
		return seiriNo;
	}

	/**
	 * @param string
	 */
	public void setSeiriNo(String string) {
		seiriNo = string;
	}

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
