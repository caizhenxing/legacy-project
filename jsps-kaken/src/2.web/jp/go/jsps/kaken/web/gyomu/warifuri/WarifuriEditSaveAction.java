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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.WarifuriPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R��������U��X�V���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�X�V�����N���A����B
 *  
 * ID RCSfile="$RCSfile: WarifuriEditSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class WarifuriEditSaveAction extends BaseAction {

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
		
		//------�Z�b�V�������C���o�^���̎擾
//		WarifuriInfo warifuriInfo = container.getWarifuriInfo();

		//------�t�H�[�����̎擾
		WarifuriForm editForm = (WarifuriForm)form;

		WarifuriPk editPk = new WarifuriPk();

		editPk.setSystemNo(editForm.getSystemNo());				//�\���ԍ�
		editPk.setShinsainNo(editForm.getShinsainNo());			//�R�����ԍ�
		editPk.setJigyoKubun(editForm.getJigyoKubun());			//���Ƌ敪
		editPk.setOldShinsainNo(editForm.getOldShinsainNo());	//�R�����ԍ��i�C���O�j		
		
		//------�X�V
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSAIN_WARIFURI_SERVICE);
		servise.update(container.getUserInfo(), editPk);

		if (log.isDebugEnabled()) {
			log.debug("����U�茋�ʏ�� �C���o�^ '" + editPk + "'");
		}

		//------�g�[�N���̍폜	
		resetToken(request);
		
		//------�Z�b�V�������Ώۊ���U�茋�ʏ��̍폜
		container.setWarifuriInfo(null);
		
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
