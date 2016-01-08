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
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

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
			
		//2005/04/08 �ǉ� ��������-------------------------------------------------------------
		//�L�����Z�����Ɍ����������ێ������悤�ɒǉ�

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		//�ǉ� �����܂�------------------------------------------------------------------------

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

		searchInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
		
		// 2005/03/25 �폜 ��������--------------------------------------------
		// ���R �s�v�ȏ����̂���	
		//	searchInfo.setShozokuName(container.getUserInfo().getShozokuInfo().getShozokuName());
		// �폜 �����܂�-------------------------------------------------------
		
		//�������s
		Page result =
			getSystemServise(
				IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).search(
				container.getUserInfo(),
				searchInfo);

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		return forwardSuccess(mapping);
	}

}
