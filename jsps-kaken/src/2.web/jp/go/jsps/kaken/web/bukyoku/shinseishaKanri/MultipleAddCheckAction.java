/*
 * Created on 2005/04/20
 *
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shozoku.shinseishaKanri.ShinseishaListForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��������ʕ\���p�A�N�V����
 * 
 *  @author masuo_t
 *
 */
public class MultipleAddCheckAction extends BaseAction {

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
				  
		//------�L�[���
		String kenkyuNo = ((ShinseishaListForm)form).getSelectRadioButton();
		if(kenkyuNo == null || kenkyuNo.equals("false")){
			//�G���[���b�Z�[�W
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.requiredSelect","�o�^���鉞��҂̏��"));
	
			ShinseishaSearchInfo searchInfo = container.getShinseishaSearchInfo();
			//�������s
			Page result =
				getSystemServise(
					IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).searchUnregist(
					container.getUserInfo(),
					searchInfo);

			//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
			request.setAttribute(IConstants.RESULT_INFO,result);
		
			//�g�[�N�����Z�b�V�����ɕۑ�����B
			saveToken(request);
			saveErrors(request, errors);
			return forwardFailure(mapping);	
		}
		
		return forwardSuccess(mapping);
	}

}
