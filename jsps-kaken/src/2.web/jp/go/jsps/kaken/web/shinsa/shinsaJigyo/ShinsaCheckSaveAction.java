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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R���������o�^�A�N�V�����N���X�B
 * 
 * ID RCSfile="$RCSfile: ShinsaCheckSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ShinsaCheckSaveAction extends BaseAction {

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

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}

		//------�L�[���
		String jigyoId = ((ShinsaKekkaSearchForm)form).getJigyoId();						//����ID
//2006/11/16 �c�@�폜��������        
//		String shinsainNo = container.getUserInfo().getShinsainInfo().getShinsainNo();		//�R�����ԍ�
//		String jigyoKubun = container.getUserInfo().getShinsainInfo().getJigyoKubun();		//���Ƌ敪
//				
//		SearchInfo searchInfo = new SearchInfo();
//2006/11/16�@�c�@�폜�����܂�        
		
		//------�L�[�������ɍX�V�f�[�^�擾	
		boolean result = getSystemServise(IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).updateJigyoShinsaComplete(
																				container.getUserInfo(),
																				jigyoId);
		//------�g�[�N���̍폜	
		resetToken(request);
		
		//�����]����NULL�̃f�[�^�����݂���ꍇ�̓G���[��ʂ֑J��																	
		if(!result){
			return forwardFailure(mapping);			
		}
// �Ո��ǉ��@�@��������@�@2006/11/16
        ShinsaKekkaSearchForm shinsaKekkaSearchForm = (ShinsaKekkaSearchForm)form;
        shinsaKekkaSearchForm.setKekkaTen("0");
        updateFormBean(mapping,request,shinsaKekkaSearchForm);
// �Ո��ǉ��@�@�����܂Ł@�@2006/11/16
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}