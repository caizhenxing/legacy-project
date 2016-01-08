/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 *  �R���󋵃t�H�[��
 * 
 * ID RCSfile="$RCSfile: ShinsaJokyoForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class ShinsaJokyoForm extends BaseSearchForm {

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

	/** ���Ƌ敪 */
	private String jigyoKubun;

	/** ����ID */
	private String jigyoId;
	
//�ŏI���O�C������ǉ�
	/** �ŏI���O�C���� */
	private String loginDate;
	
//�����ԍ��i�w�n�p�j��ǉ�
  /** �����ԍ��i�w�n�p�j */
	  private String seiriNo;

//	�\��������ǉ�
	/** �\������ */
	private String hyojiHoshikiShinsaJokyo;

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShinsaJokyoForm() {
		super();
		init();
	}

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* 
	 * �����������B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		//�E�E�E
	}

	/**
	 * ����������
	 */
	public void init() {
		shinsainNo = "";
		nameKanjiSei = "";
		nameKanjiMei = "";
		jigyoName = "";
		nendo = "";
		kaisu = "";
		jigyoKubun = "";
		jigyoId = "";
		loginDate = "";
		seiriNo = "";
		hyojiHoshikiShinsaJokyo = "";
	}

	/* 
	 * ���̓`�F�b�N�B
	 * (�� Javadoc)
	 * @see org.apache.struts.action.ActionForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		//��^����----- 
		ActionErrors errors = super.validate(mapping, request);
		//---------------------------------------------
		// ��{�I�ȃ`�F�b�N(�K�{�A�`�����j��Validator���g�p����B
		//---------------------------------------------

		//��{���̓`�F�b�N�����܂�
		if (!errors.isEmpty()) {
			return errors;
		}

		//��^����----- 

		//�ǉ�����----- 

		//---------------------------------------------
		//�g�ݍ��킹�`�F�b�N	
		//---------------------------------------------
		return errors;
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
//�����ԍ���ǉ�
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
	public String getHyojiHoshikiShinsaJokyo() {
		return hyojiHoshikiShinsaJokyo;
	}
	
	/**
	 * @param string
	 */
	public void setHyojiHoshikiShinsaJokyo(String string) {
		hyojiHoshikiShinsaJokyo = string;
	}	
}
