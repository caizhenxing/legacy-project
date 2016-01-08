/*
 * Created on 2005/04/06
 *
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ǒS���ҍ폜�����p�A�N�V�����N���X�B
 *
 */
public class DeleteSaveAction extends BaseAction {


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
		
		//------�Z�b�V�������폜���̎擾
		BukyokutantoInfo deleteInfo = container.getBukyokutantoInfo();

		//�폜����
		getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).delete(
			container.getUserInfo(),
			deleteInfo);

		//------�g�[�N���̍폜	
		resetToken(request);
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);
		//------�Z�b�V������菈���Ώې\�����̍폜
		container.setShinseishaInfo(null);
		
		return forwardSuccess(mapping);
	}

}
