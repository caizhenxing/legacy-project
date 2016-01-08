/*
 * �쐬��: 2005/04/21
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ǒS���Ґ������p�A�N�V�����N���X�B
 * �C���Ŋm�F��ʂ�OK�{�^���������ADB�o�^�������s���ۂɌĂяo�����B
 *
 */
public class EditBukyokuSaveAction extends BaseAction {

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
		
		//------�Z�b�V�������V�K�o�^���̎擾
		BukyokutantoInfo bukyokuInfo = container.getBukyokutantoInfo();
		
		
		//�����@�֒S���ҘA����擾
		ShozokuSearchInfo searchInfo = new ShozokuSearchInfo();
		searchInfo.setShozokuCd(bukyokuInfo.getShozokuCd());
		try {
			Page resultTnto =
				getSystemServise(
					IServiceName.SHOZOKU_MAINTENANCE_SERVICE).search(
					container.getUserInfo(),
					searchInfo);
			//�����@�֒S���ҏ���\�����邽�߃��N�G�X�g���ɃZ�b�g
			request.setAttribute(IConstants.RESULT_TANTO,resultTnto);
		} catch(ApplicationException e) {
			//�Y���S���҂Ȃ����ʏ킠�肦�Ȃ��̂ŋ�\��
		}
		
		//���ǒS���ҏ��擾
		BukyokutantoInfo result = getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).setBukyokuData(
				container.getUserInfo(),
				bukyokuInfo);
		
		//	�o�^���ʂ����N�G�X�g�����ɃZ�b�g���o�^�����p�X���[�h��񓙂�\�����邽�߁B
		request.setAttribute(IConstants.RESULT_INFO,result);

		//------�g�[�N���̍폜	
		resetToken(request);
		//------�Z�b�V�������V�K�o�^���̍폜
		container.setShinseishaInfo(null);
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
