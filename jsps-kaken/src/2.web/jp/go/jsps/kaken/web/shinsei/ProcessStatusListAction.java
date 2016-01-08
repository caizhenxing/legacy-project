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
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �\���������󋵈ꗗ�A�N�V�����N���X�B
 * �����󋵈ꗗ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ProcessStatusListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class ProcessStatusListAction extends BaseAction {

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

		//�\���ҏ��
		UserInfo userInfo = container.getUserInfo();
		ShinseishaInfo shinseishaInfo = userInfo.getShinseishaInfo();
		if(shinseishaInfo == null){
			throw new ApplicationException(
				"�\���ҏ����擾�ł��܂���ł����B",
				new ErrorInfo("errors.application"));
		}
		
		//�\����ID�����������ɃZ�b�g����
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		searchInfo.setShinseishaId(shinseishaInfo.getShinseishaId());
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);	//�V�X�e����t�ԍ��̏���
		
		//�y�[�W����͍s��Ȃ�
		searchInfo.setPageSize(0);
		searchInfo.setMaxSize(0);
		searchInfo.setStartPosition(0);
				
		Page page = null;
		try{
			//�T�[�o�T�[�r�X�̌Ăяo���i�����󋵈ꗗ�y�[�W���擾�j
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			page = servise.searchApplication(userInfo, searchInfo);
		}catch(NoDataFoundException e){
			//0���̃y�[�W�I�u�W�F�N�g�𐶐�
			page = Page.EMPTY_PAGE;
		}

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
