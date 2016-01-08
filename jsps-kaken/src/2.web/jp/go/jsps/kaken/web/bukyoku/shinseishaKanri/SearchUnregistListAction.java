/*
 * �쐬��: 2005/03/25
 *
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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���o�^�\���ҏ�񌟍��A�N�V�����N���X�B
 * ���o�^�\���ҏ��ꗗ��ʂ�\������B
 *
 * @author yoshikawa_h
 *
 */
public class SearchUnregistListAction extends BaseAction {
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
				IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).searchUnregist(
				container.getUserInfo(),
				searchInfo);

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);
		
		//2005/04/20 �ǉ� ��������------------------------------------
		//���������ێ��̂���
		container.setShinseishaSearchInfo(searchInfo);
		//�ǉ� �����܂�-----------------------------------------------
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}
