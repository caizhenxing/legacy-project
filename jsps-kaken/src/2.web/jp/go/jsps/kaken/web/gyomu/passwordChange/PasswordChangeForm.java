/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.passwordChange;

import javax.servlet.http.HttpServletRequest;

import jp.go.jsps.kaken.web.struts.BaseValidatorForm;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

/**
 * �p�X���[�h�ύX�����̓t�H�[���N���X�B
 * ID RCSfile="$RCSfile: PasswordChangeForm.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:37 $"
 */
public class PasswordChangeForm extends BaseValidatorForm {

	//---------------------------------------------------------------------
	// Instance data
	//---------------------------------------------------------------------

	/**
	 * ���݂̃p�X���[�h�B
	 */
	private String password;

	/**
	 * �V�����p�X���[�h�B
	 */
	private String newPassword1;
	
	/**
	 * �V�����p�X���[�h�i�m�F�p�j�B
	 */
	private String newPassword2;

	//---------------------------------------------------------------------
	// Constructors
	//---------------------------------------------------------------------

	/**
	 * �R���X�g���N�^�B
	 */
	public PasswordChangeForm() {
		super();
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
		this.password = "";
		this.newPassword1 ="";
		this.newPassword2 ="";
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
		//�V�����p�X���[�h�Ɗm�F�p�̐V�����p�X���[�h����v���Ă��邱�Ƃ��m�F
		if(!getNewPassword1().equals(getNewPassword2())){
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2004"));
				
			if(log.isDebugEnabled()){
				log.debug("�V�����p�X���[�h�Ɗm�F�p�̐V�����p�X���[�h����v���Ă��܂���B");			
			}
			
			//�t�H�[�������Z�b�g
			setNewPassword1("");
			setNewPassword2("");			
		}
		return errors;
	}

	//---------------------------------------------------------------------
	// Properties
	//---------------------------------------------------------------------
	/**
	 * @return
	 */
	public String getNewPassword1() {
		return newPassword1;
	}

	/**
	 * @return
	 */
	public String getNewPassword2() {
		return newPassword2;
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
	public void setNewPassword1(String string) {
		newPassword1 = string;
	}

	/**
	 * @param string
	 */
	public void setNewPassword2(String string) {
		newPassword2 = string;
	}

	/**
	 * @param string
	 */
	public void setPassword(String string) {
		password = string;
	}

}
