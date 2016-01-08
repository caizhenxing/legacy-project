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
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\���������󋵈ꗗ�A�N�V�����N���X�B
 * �����󋵈ꗗ��ʂ�\������B
 * 
 */
public class ShoninKyakaListAction extends BaseAction {


	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//�����@�֏��
		UserInfo userInfo = container.getUserInfo();
		ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
		if(shozokuInfo == null){
			throw new ApplicationException(
				"�����@�֏����擾�ł��܂���ł����B",
				new ErrorInfo("errors.application"));
		}
		
		//�����������Z�b�g����
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());					//�����@�փR�[�h
		searchInfo.setJokyoId(new String[]{StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU});	//�\����ID
		//TODO ���ݕۗ�
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);				//�V�X�e����t�ԍ��̏���

		//�T�[�o�T�[�r�X�̌Ăяo���i�����󋵈ꗗ�y�[�W���擾�j
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);

		Page page = null;
		try{
			page = servise.searchApplication(userInfo, searchInfo);
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			page = Page.EMPTY_PAGE;
		}
//		Page page = servise.searchApplication(userInfo, searchInfo);

		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO, page);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}

}
