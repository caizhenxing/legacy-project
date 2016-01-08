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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���[���A�h���X�o�^�����A�N�V�����N���X�B
 * ���[���A�h���X�o�^������ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: RegistMailAddrOKAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RegistMailAddrOKAction extends BaseAction {

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
		
		//------�t�H�[�������擾
		RegistMailAddrForm mailForm = (RegistMailAddrForm)form;
		
		//------info�ɍX�V�����Z�b�g
		ShinsainInfo info = container.getUserInfo().getShinsainInfo();
		
		info.setMailFlg(mailForm.getMailFlg());				//���[���t���O
		info.setSofuZipemail(mailForm.getMailAddress());	//���[���A�h���X
		
		//------�X�V
		ShinsainInfo result = getSystemServise(
								IServiceName.SHINSAIN_MAINTENANCE_SERVICE).update(
												container.getUserInfo(),
												info);
		
		//UserInfo�̐R���������X�V
		container.getUserInfo().setShinsainInfo(info);
		
		//�X�V���ʂ����N�G�X�g�����ɃZ�b�g���X�V�������[���A�h���X��񓙂�\�����邽�߁B
		request.setAttribute(IConstants.RESULT_INFO,result);
		
		//------�g�[�N���̍폜	
		resetToken(request);

		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//���[���A�h���X��o�^���Ȃ���I�������ꍇ�A������ʂɑJ�ڂ����ꗗ��ʂɖ߂�
		if("1".equals(mailForm.getMailFlg())){
			return mapping.findForward("noregist");
		}else{
			return forwardSuccess(mapping);
		}

	}

}
