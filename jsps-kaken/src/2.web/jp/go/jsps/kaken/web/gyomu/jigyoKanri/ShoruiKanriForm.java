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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 * ID RCSfile="$RCSfile: ShoruiKanriForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiKanriForm extends BaseValidatorForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	
	/** ����ID */
	private String    jigyoId;
	
	/** �Ώ� */
	private String    taishoId;
	
	/** ���ރt�@�C�� */
	private String    shoruiFile;
	
	/** ���ޖ� */
	private String    shoruiName;
	
	/** �V�X�e���ԍ� */
	private String    systemNo;
	
	/** �ΏۑI�����X�g */
	private List taishoIdList = new ArrayList();
	
	/** ���ރt�@�C��(�A�b�v���[�h�t�@�C��) */
	private FormFile        shoruiUploadFile;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public ShoruiKanriForm() {
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
		init();
	}

	/**
	 * ����������
	 */
	public void init() {
		taishoId = "2";//�����\���u�����@�֒S���Ҍ����v
		shoruiFile = "";
		shoruiName = "";
		systemNo = "";
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
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2009"));
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
	public String getShoruiFile() {
		return shoruiFile;
	}

	/**
	 * @return
	 */
	public String getShoruiName() {
		return shoruiName;
	}

	/**
	 * @return
	 */
	public FormFile getShoruiUploadFile() {
		return shoruiUploadFile;
	}

	/**
	 * @return
	 */
	public String getTaishoId() {
		return taishoId;
	}

	/**
	 * @param string
	 */
	public void setShoruiFile(String string) {
		shoruiFile = string;
	}

	/**
	 * @param string
	 */
	public void setShoruiName(String string) {
		shoruiName = string;
	}

	/**
	 * @param file
	 */
	public void setShoruiUploadFile(FormFile file) {
		shoruiUploadFile = file;
	}

	/**
	 * @param string
	 */
	public void setTaishoId(String string) {
		taishoId = string;
	}

	/**
	 * @return
	 */
	public List getTaishoIdList() {
		return taishoIdList;
	}

	/**
	 * @param list
	 */
	public void setTaishoIdList(List list) {
		taishoIdList = list;
	}

	/**
	 * @return
	 */
	public String getJigyoId() {
		return jigyoId;
	}

	/**
	 * @param string
	 */
	public void setJigyoId(String string) {
		jigyoId = string;
	}

	/**
	 * @return
	 */
	public String getSystemNo() {
		return systemNo;
	}

	/**
	 * @param string
	 */
	public void setSystemNo(String string) {
		systemNo = string;
	}

}