/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/4/8
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\���ҏ�񌟍��A�N�V�����N���X�B
 * �\���ҏ��ꗗ��ʂ�\������B
 * 
 */
public class SearchListAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//���������̎擾
		ShinseishaSearchForm searchForm = (ShinseishaSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		searchInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
		
		//�������s
		Page result =
			getSystemServise(
				IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).search(
				container.getUserInfo(),
				searchInfo);

		//�������ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		return forwardSuccess(mapping);
	}

}
