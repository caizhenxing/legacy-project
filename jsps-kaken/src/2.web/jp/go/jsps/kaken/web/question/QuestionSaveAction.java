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
package jp.go.jsps.kaken.web.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �A���P�[�g���͏��l�I�u�W�F�N�g���X�V����B
 * �t�H�[�����A�X�V�����N���A����B
 * 
 * ID RCSfile="$RCSfile: QuestionSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:20 $"
 */
public class QuestionSaveAction extends BaseAction {

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
		
		//------�Z�b�V�������X�V���̎擾
		QuestionInfo addInfo = container.getQuestionInfo();
		
		//2005.11.02 iso IP����ǉ�
		addInfo.setIp(request.getRemoteAddr());

		//DB�o�^
		ISystemServise service = getSystemServise(
						IServiceName.QUESTION_MAINTENANCE_SERVICE);
			service.insert(container.getUserInfo(), addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("�A���P�[�g���͏��@�o�^��� '"+ addInfo);
		}
		
		//2005.11.04 iso �Z�b�V�������̊��S�N���A�ɕύX 
//		//-----�Z�b�V�����̃A���P�[�g���͏������Z�b�g
//		container.setQuestionInfo(null);
		//�Z�b�V�����N���A�B
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		//-----�t�H�[�����폜
		removeFormBean(mapping, request);

		//------�g�[�N���̍폜	
		resetToken(request);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
