/*
 * �쐬��: 2005/04/20
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

/**
 * @author yoshikawa_h
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
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
 * ���ǒS���ҏ��폜�A�N�V�����N���X�B
 * 
 */
public class DeleteBukyokuSaveAction extends BaseAction {

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
		
		//------�Z�b�V�������폜���̎擾
		BukyokutantoInfo deleteInfo = container.getBukyokutantoInfo();

		//------�폜
		ISystemServise servise = getSystemServise(
							IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE);
		servise.delete(container.getUserInfo(),deleteInfo);

		if (log.isDebugEnabled()) {
			log.debug("���ǒS���ҏ�� �폜   '" + deleteInfo + "'");
		}

		//------�g�[�N���̍폜	
		resetToken(request);
		
		//------�Z�b�V������菈���Ώۏ����@�֏��̍폜
		container.setBukyokutantoInfo(null);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}

