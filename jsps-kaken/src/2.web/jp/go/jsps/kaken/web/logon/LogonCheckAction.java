/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.logon;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * ���O�I���̃`�F�b�N���s���A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: LogonCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:34 $"
 */
public class LogonCheckAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ���O�N���X�B */
	private static final Log log = LogFactory.getLog(LogonCheckAction.class);

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (�� Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.web.common.UserContainer)
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

		//-----�t�H�[���̎擾
		LogonForm logonForm = (LogonForm) form;

		//------�L�����Z�����̏���
		if (isCancelled(request)) {
			form.reset(mapping, request);
			return forwardCancel(mapping);
		}

		//-----���O�I�����[�U���
		UserInfo userInfo = null;
		try {
			//-----�T�[�o�T�[�r�X���擾����B
			ISystemServise servise = getSystemServise(mapping.getParameter());

			//-----���O�I���F�؂��s���B
			userInfo = servise.authenticate(logonForm.getUserid(),logonForm.getPassword());

			//�Z�b�V�����N���A�B
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}

			//-----�Z�b�V�����Ƀ��[�U����o�^����B
			getUserContainer(request).setUserInfo(userInfo);
			
			//-----���[�h�o�����T�[����p��Cookie���Z�b�g����B
			addCookie4LB(request, response);

		} catch (InvalidLogonException e) {
			//���O�I���G���[�̕\��
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError(e.getErrorCode(),e.getErrorArgs()));
			saveErrors(request, errors);
			return forwardInput(mapping);
		}

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		//���[�U�[���u�R�����v�Ń��[���t���O�����͂Ń��[���A�h���X�����o�^�̏ꍇ�́A���[���A�h���X�o�^��ʂɑJ�ڂ���
		if(userInfo.getRole().equals(UserRole.SHINSAIN) 
		&& StringUtil.isBlank(userInfo.getShinsainInfo().getMailFlg()) 
		&& StringUtil.isBlank(userInfo.getShinsainInfo().getSofuZipemail())){
			return mapping.findForward("mail");
		}else{
			return forwardSuccess(mapping);
		}
	}
	
	
	
	/**
	 * Cookie�̐ݒ�
	 */
	private void addCookie4LB(HttpServletRequest request, HttpServletResponse response){
		
		boolean flgCookie   = false;
		String   cookieName  = SystemUtil.LB_COOKIE_NAME;
		String   cookieValue = SystemUtil.LB_COOKIE_VALUE;
		
		//�V�X�e���v���p�e�B���Z�b�g����Ă��Ȃ��ꍇ�͏������Ȃ�
		if(cookieName == null || cookieValue == null){
			if(log.isDebugEnabled()){
				log.debug("Java System Properties for LB is not set. -> Cookie was not set.");
			}
			return;
		}
		
		//�u���E�U�����M����Cookie���擾
		Cookie[] ck = request.getCookies();
		if (ck != null) {
			for (int i = 0; i < ck.length; i++) {
				if (ck[i].getName().equals(cookieName)) {
					if(log.isDebugEnabled()){
						log.debug("Cookie for LB is already set. " + ck[i].getName() + "=" + ck[i].getValue());
					}
					flgCookie = true;
				}
			}
		}
		
		//Cookie�����ɃZ�b�g����Ă���ꍇ�͏������Ȃ�
		if (!flgCookie) {
			Cookie cookie = new Cookie(cookieName, cookieValue);
			cookie.setPath("/");
			response.addCookie(cookie);
			if(log.isDebugEnabled()){
				log.debug("Cookie for LB was set. " + cookieName + "=" + cookieValue);
			}
		}
		       	
	}


}
