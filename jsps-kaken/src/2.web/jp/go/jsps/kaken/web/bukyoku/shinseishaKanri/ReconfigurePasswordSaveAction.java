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
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
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
 * �p�X���[�h�Đݒ���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�p�X���[�h�Đݒ�����N���A����B
 * 
 */
public class ReconfigurePasswordSaveAction extends BaseAction {

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
		ShinseishaInfo reconfigurePasswordInfo = container.getShinseishaInfo();

		//�����@�֒S���ҘA����擾
		ShozokuSearchInfo searchInfo = new ShozokuSearchInfo();
		searchInfo.setShozokuCd(reconfigurePasswordInfo.getShozokuCd());
		try {
			Page resultTnto =
				getSystemServise(
					IServiceName.SHOZOKU_MAINTENANCE_SERVICE).search(
					container.getUserInfo(),
					searchInfo);
			request.setAttribute(IConstants.RESULT_TANTO,resultTnto);
		} catch(ApplicationException e) {
			//�Y���S���҂Ȃ����ʏ킠�肦�Ȃ��̂ŋ�\��
		}

		//DB�o�^
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEISHA_MAINTENANCE_SERVICE);
		ShinseishaInfo result = servise.reconfigurePassword(container.getUserInfo(),reconfigurePasswordInfo);
		
		if(log.isDebugEnabled()){
			log.debug("�\���ҏ��@�p�X���[�h�Đݒ��� '"+ request);
		}

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g���o�^�����p�X���[�h��񓙂�\�����邽�߁B
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
