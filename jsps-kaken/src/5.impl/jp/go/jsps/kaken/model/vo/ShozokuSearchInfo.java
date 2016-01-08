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

import jp.go.jsps.kaken.model.vo.SearchInfo;

/**
 * �����@�֌���������ێ�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShozokuSearchInfo.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:13 $"
 */
public class ShozokuSearchInfo extends SearchInfo{

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	static final long serialVersionUID = 6539727479837879456L;
	
	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �@�֎�ʃR�[�h */
	private String shubetuCd;
	
	/** �����@�փR�[�h */
	private String shozokuCd;

	/** �����@�֖� */
	private String shozokuName;
	
	/** �S����ID */
	private String shozokuTantoId;

	/** �S�������i���j */
	private String tantoNameSei;

	/** �S�������i���j */
	private String tantoNameMei;
	
//	2005/04/20 �ǉ� ��������-----------------------------------
//	���R �u���ǒS����ID�v�u���ǒS���Ҍ��������t���O�v���ڒǉ�
	/** ���ǒS����ID */
	private String bukyokuTantoId;
	
	/**	���ǒS���Ҍ��������t���O */
	private String bukyokuSearchFlg;
//	�ǉ� �����܂�----------------------------------------------


	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShozokuSearchInfo() {
		super();
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	
	/**
	 * @return
	 */
	public String getShozokuTantoId() {
		return shozokuTantoId;
	}

	/**
	 * @return
	 */
	public String getTantoNameSei() {
		return tantoNameSei;
	}

	/**
	 * @return
	 */
	public String getShozokuCd() {
		return shozokuCd;
	}

	/**
	 * @return
	 */
	public String getShozokuName() {
		return shozokuName;
	}

	/**
	 * @param string
	 */
	public void setShozokuTantoId(String string) {
		shozokuTantoId = string;
	}

	/**
	 * @param string
	 */
	public void setTantoNameSei(String string) {
		tantoNameSei = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuCd(String string) {
		shozokuCd = string;
	}

	/**
	 * @param string
	 */
	public void setShozokuName(String string) {
		shozokuName = string;
	}

	/**
	 * @return
	 */
	public String getTantoNameMei() {
		return tantoNameMei;
	}

	/**
	 * @param string
	 */
	public void setTantoNameMei(String string) {
		tantoNameMei = string;
	}

	/**
	 * @return
	 */
	public String getShubetuCd() {
		return shubetuCd;
	}

	/**
	 * @param string
	 */
	public void setShubetuCd(String string) {
		shubetuCd = string;
	}

	/**
	 * @return bukyokuSearchFlg ��߂��܂��B
	 */
	public String getBukyokuSearchFlg() {
		return bukyokuSearchFlg;
	}
	/**
	 * @param bukyokuSearchFlg bukyokuSearchFlg ��ݒ�B
	 */
	public void setBukyokuSearchFlg(String bukyokuSearchFlg) {
		this.bukyokuSearchFlg = bukyokuSearchFlg;
	}
	/**
	 * @return bukyokuTantoId ��߂��܂��B
	 */
	public String getBukyokuTantoId() {
		return bukyokuTantoId;
	}
	/**
	 * @param bukyokuTantoId bukyokuTantoId ��ݒ�B
	 */
	public void setBukyokuTantoId(String bukyokuTantoId) {
		this.bukyokuTantoId = bukyokuTantoId;
	}
}
