/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.masterTorikomi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

//import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

//import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping; 
import org.apache.struts.upload.FormFile;

/**
 * �\�����V�X�e���ԍ��t�H�[��
 * 
 * ID RCSfile="$RCSfile: MasterTorikomiForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class MasterTorikomiForm extends BaseSearchForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/** �}�X�^��� */
	private String   masterShubetu    = "";

	/** �V�K�X�V�t���O */
	//2005.10.18 iso �����l���X�V�ɕύX
//	private String   shinkiKoshinFlg  = "0";
	private String   shinkiKoshinFlg  = "1";
	
	/** �A�b�v���[�h�t�@�C�� */
	private FormFile uploadCsv        = null;

	/** �}�X�^�Ǘ��ꗗ���X�g */
	private List     masterKanriList  = new ArrayList();

	/** �}�X�^��ʃR���{ */
	private List     shubetuComboList = new ArrayList();

	/** �V�K�X�V�t���O���X�g */
	private List     shinkiKoshinList = new ArrayList();

	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public MasterTorikomiForm() {
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
	 * @param string
	 */
	public void setMasterShubetu(String string) {
		masterShubetu = string;
	}

	/**
	 * @param string
	 */
	public void setShinkiKoshinFlg(String string) {
		shinkiKoshinFlg = string;
	}

	/**
	 * @param file
	 */
	public void setUploadCsv(FormFile file) {
		uploadCsv = file;
	}

	/**
	 * @param list
	 */
	public void setMasterKanriList(List list) {
		masterKanriList = list;
	}

	/**
	 * @param list
	 */
	public void setShubetuComboList(List list) {
		shubetuComboList = list;
	}

	/**
	 * @param list
	 */
	public void setShinkiKoshinList(List list) {
		shinkiKoshinList = list;
	}


	/**
	 * @return
	 */
	public String getMasterShubetu() {
		return masterShubetu;
	}

	/**
	 * @return
	 */
	public String getShinkiKoshinFlg() {
		return shinkiKoshinFlg;
	}

	/**
	 * @return
	 */
	public FormFile getUploadCsv() {
		return uploadCsv;
	}

	/**
	 * @return
	 */
	public List getMasterKanriList() {
		return masterKanriList;
	}

	/**
	 * @return
	 */
	public List getShubetuComboList() {
		return shubetuComboList;
	}

	/**
	 * @return
	 */
	public List getShinkiKoshinList() {
		return shinkiKoshinList;
	}

}
