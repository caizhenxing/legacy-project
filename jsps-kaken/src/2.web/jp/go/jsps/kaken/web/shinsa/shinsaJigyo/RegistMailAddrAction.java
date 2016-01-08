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

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���[���A�h���X�o�^�A�N�V�����N���X�B
 * ���[���A�h���X�o�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: RegistMailAddrAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RegistMailAddrAction extends BaseAction {

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

		//------ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
		
		RegistMailAddrForm mailForm = new RegistMailAddrForm();
		
		//------�R�������擾
		ShinsainInfo info = getSystemServise(
								IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(
												container.getUserInfo(),
												container.getUserInfo().getShinsainInfo());
		
		//------�t�H�[���f�[�^�Z�b�g
		mailForm.setMailFlg(info.getMailFlg());							//���[���t���O
		mailForm.setMailAddress(info.getSofuZipemail());				//���[���A�h���X
		
		//------�v���_�E���f�[�^�Z�b�g
		mailForm.setMailFlgList(LabelValueManager.getMailFlgList());	//���[���t���O���X�g
		
		//------�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		//------�����t�H�[�������Z�b�g
		updateFormBean(mapping, request, mailForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
