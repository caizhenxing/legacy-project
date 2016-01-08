/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : JuriCancelSaveTokuteiAction.java
 *    Description : �󗝉���(����)���s���B
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝉���(����)���s���B
 * 
 * @author masuo_t
 */
public class JuriCancelSaveTokuteiAction extends BaseAction {
	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}

		//------�g�[�N���̍폜	
		resetToken(request);
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		//���������̎擾
		CheckListForm searchForm = (CheckListForm)form;

		//���������̐ݒ�
		if(searchForm.getJigyoId() != null && !searchForm.getJigyoId().equals("")){
			searchInfo.setJigyoId(searchForm.getJigyoId());
		}
		if(searchForm.getShozokuCd() != null && !searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
// 20050617
		searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi

		//��ID��06����04�ɕύX
		searchInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
		searchInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
		

		ISystemServise servise =
			getSystemServise(IServiceName.CHECKLIST_MAINTENANCE_SERVICE);

		try {
			servise.checkListUpdate(
				container.getUserInfo(),
				searchInfo, true);

		} catch (ValidationException e) {
			return forwardSuccess(mapping);
		}

		return forwardSuccess(mapping);
	}
}