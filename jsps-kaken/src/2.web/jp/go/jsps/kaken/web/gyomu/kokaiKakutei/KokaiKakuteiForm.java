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
package jp.go.jsps.kaken.web.gyomu.kokaiKakutei;

import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * ID RCSfile="$RCSfile: KokaiKakuteiForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $"
 */
public class KokaiKakuteiForm extends BaseValidatorForm {
	

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------
	/** �p�X���[�h */
	private String    password;
	
	/** ���J���ٔԍ� */
	private String    kessaiNo;
	
	/** ����ID */
	private List     jigyoIds;
	
	//�E�E�E�E�E�E�E�E�E�E

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public KokaiKakuteiForm() {
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
		password = "";
		kessaiNo = "";
		jigyoIds = new Vector();
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

		if(jigyoIds == null){
			
		}
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
	 * �L�[�Ɉ�v���鎖��ID��ݒ肷��B
	 * @param key		�L�[
	 * @param value		����ID
	 */
	public void setJigyoId(int key, Object value) {
		jigyoIds.add(value);
	 }

	/**
	 * �L�[�Ɉ�v���鎖��ID���擾����B
	 * @param key		�L�[
	 * @return	����ID
	 */
	public Object getJigyoId(int key) {
		 return jigyoIds.get(key);
	 }

	/**
	 * @return
	 */
	public String getKessaiNo() {
		return kessaiNo;
	}


	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param string
	 */
	public void setKessaiNo(String string) {
		kessaiNo = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

	/**
	 * @return
	 */
	public List getJigyoIds() {
		return jigyoIds;
	}

	/**
	 * @param list
	 */
	public void setJigyoIds(List list) {
		jigyoIds = list;
	}

}