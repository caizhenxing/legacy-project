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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���J�m��o�^���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�o�^�����N���A����B
 * 
 * ID RCSfile="$RCSfile: KokaiKakuteiSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class KokaiKakuteiSaveAction extends BaseAction {

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
			((KokaiKakuteiForm)form).init();
			return forwardCancel(mapping);
		}

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------�V�K�o�^�t�H�[�����̎擾
		KokaiKakuteiForm updateForm = (KokaiKakuteiForm) form;
				
		List jigyoIds = updateForm.getJigyoIds();
		JigyoKanriPk[] jigyoPks = new JigyoKanriPk[jigyoIds.size()];
		for(int i = 0; i < jigyoPks.length; i++){
			String jigyoId = (String)jigyoIds.get(i);
			JigyoKanriPk jigyoPk = new JigyoKanriPk();			 
			jigyoPk.setJigyoId(jigyoId);
			jigyoPks[i] = jigyoPk;
		}				
		
		//DB�o�^
		ISystemServise servise = getSystemServise(
						IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
		servise.updateKokaiKakutei(container.getUserInfo(), jigyoPks, updateForm.getKessaiNo(), updateForm.getPassword());
		
		//-----�t�H�[���폜
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
