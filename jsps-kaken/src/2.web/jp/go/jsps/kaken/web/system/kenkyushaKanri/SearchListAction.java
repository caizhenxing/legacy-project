/*
 * Created on 2005/04/15
 * 
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.KenkyushaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����ҏ�񌟍��A�N�V�����N���X�B
 * �����ҏ��ꗗ��ʂ�\������B
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
		KenkyushaSearchForm searchForm = (KenkyushaSearchForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		KenkyushaSearchInfo searchInfo = new KenkyushaSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//�������s
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		Page result = service.search(searchInfo);

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		return forwardSuccess(mapping);
	}

}
