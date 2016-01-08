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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �����@�֏�񌟍��t�H�[��
 * 
 * ID RCSfile="$RCSfile: ShozokuSearchForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ShozokuSearchForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** ���ǎ�ʑI�����X�g */
	private List shubetuCdList = new ArrayList();

	/** �@�֎�ʃR�[�h */
	private String shubetuCd;
	
	/** �S����ID */
	private String shozokuTantoId;

	/** �S���Җ��i���j */
	private String tantoNameSei;
	
	/** �S���Җ��i���j */
	private String tantoNameMei;

	/** �����@�փR�[�h */
	private String shozokuCd;

	/** �����@�֖� */
	private String shozokuName;
	
//	 2005/04/20 �ǉ� ��������----------------------------------
//	 ���R �u���ǒS����ID�v�u���ǒS���Ҍ��������v���ڒǉ�
		/**	���ǒS����ID */
		private String bukyokuTantoId;
		
		/**	���ǒS���Ҍ������� */
		private String bukyokuSearchFlg;
		
		/** ���ǒS���Ҍ��������t���O�I�����X�g */
		private List bukyokuSearchFlgList = new ArrayList();
//	 �ǉ� �����܂�---------------------------------------------
		
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShozokuSearchForm() {
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
		shubetuCd = "";
		shozokuTantoId = "";
		tantoNameSei = "";
		tantoNameMei = "";
		shozokuCd = "";
		shozokuName = "";
		bukyokuTantoId = "";
		bukyokuSearchFlg = "0";
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
	public String getShubetuCd() {
		return shubetuCd;
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
	public void setShubetuCd(String string) {
		shubetuCd = string;
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
	public List getShubetuCdList() {
		return shubetuCdList;
	}

	/**
	 * @param list
	 */
	public void setShubetuCdList(List list) {
		shubetuCdList = list;
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
	/**
	 * @return bukyokuSearchFlgList ��߂��܂��B
	 */
	public List getBukyokuSearchFlgList() {
		return bukyokuSearchFlgList;
	}
	/**
	 * @param bukyokuSearchFlgList bukyokuSearchFlgList ��ݒ�B
	 */
	public void setBukyokuSearchFlgList(List bukyokuSearchFlgList) {
		this.bukyokuSearchFlgList = bukyokuSearchFlgList;
	}
}
