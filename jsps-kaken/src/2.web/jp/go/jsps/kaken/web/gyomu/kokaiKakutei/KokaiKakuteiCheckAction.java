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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * ���J�m��m�F��ʕ\���O�A�N�V�����N���X�B
 * ���J�m��m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: KokaiKakuteiCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class KokaiKakuteiCheckAction extends BaseAction {

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
			
		//------�����Ώی��J�m����̎擾
		KokaiKakuteiSearchForm searchForm = (KokaiKakuteiSearchForm)form;
		
		//------�L�[���
		List jigyoIds = searchForm.getJigyoIds();
		JigyoKanriPk[] jigyoPks = new JigyoKanriPk[jigyoIds.size()];
		for(int i = 0; i < jigyoPks.length; i++){
		 	String jigyoId = (String)jigyoIds.get(i);
			JigyoKanriPk jigyoPk = new JigyoKanriPk();			 
			jigyoPk.setJigyoId(jigyoId);
			jigyoPks[i] = jigyoPk;
		}
		
		//------�����������Z�b�g
		JigyoKanriSearchInfo searchInfo = new JigyoKanriSearchInfo();
		searchInfo.setJigyoPks(jigyoPks);
		
		//�y�[�W����
		searchInfo.setPageSize(0);		
		searchInfo.setMaxSize(0);
				
		//------�L�[�������ɍX�V�f�[�^�擾	
		Page result = 
					getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).search(container.getUserInfo(), searchInfo);
			
		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);	
				
		//------�g�[�N�����Z�b�g����B
		saveToken(request);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
