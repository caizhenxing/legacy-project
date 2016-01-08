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
package jp.go.jsps.kaken.web.system.passwordChange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �p�X���[�h�̕ύX���s���A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ChangeSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class ChangeSaveAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O�N���X�B */
	private static final Log log = LogFactory.getLog(ChangeSaveAction.class);

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

			//-----ActionErrors�̐錾�i��^�����j-----
			ActionErrors errors = new ActionErrors();

			//------�L�����Z����		
			if (isCancelled(request)) {
				return forwardCancel(mapping);
			}

			//-----�擾�����g�[�N���������ł���Ƃ�
			if (!isTokenValid(request)) {
				errors.add(ActionErrors.GLOBAL_ERROR,
						   new ActionError("error.transaction.token"));
				saveErrors(request, errors);
				return forwardTokenError(mapping);
			}

			//���������̎擾
			PasswordChangeForm changeForm = (PasswordChangeForm)form;

			try {
				//�����̃p�X���[�h�̃`�F�b�N�ƃp�X���[�h�X�V
				ISystemServise servise =  getSystemServise(
						IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE);
				boolean result = servise.changePassword(
						container.getUserInfo(),container.getUserInfo().getGyomutantoInfo(),changeForm.getPassword(),changeForm.getNewPassword1());
			} catch (ValidationException e) {
				//�T�[�o�[�G���[��ۑ��B
				saveServerErrors(request, errors, e);
				//�t�H�[�������Z�b�g
				changeForm.setPassword("");
				//---���͓��e�ɕs��������̂ōē���
				return forwardInput(mapping);
			}
	
			if(log.isDebugEnabled()){
				log.debug("�p�X���[�h�ύX���'"+ request);
			}
		
			//------�g�[�N���̍폜	
			resetToken(request);

			//------�t�H�[�����̍폜
			removeFormBean(mapping,request);

			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
		}
}
