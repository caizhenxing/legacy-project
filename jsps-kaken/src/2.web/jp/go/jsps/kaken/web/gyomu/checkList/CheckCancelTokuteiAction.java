/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : CheckCancelTokuteiAction.java
 *    Description : �`�F�b�N���X�g�̊m��������s���N���X�B
 *
 *    Author      : Admin
 *    Date        : 2005/04/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/13    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g�̊m��������s���N���X�B
 * �`�F�b�N���X�g�Ǘ��T�[�r�X�Ń`�F�b�N���X�g�̏�ID���X�V����B
 * 
 * @author masuo_t
 *
 */
public class CheckCancelTokuteiAction extends BaseAction {

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
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
	
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		CheckListForm checkForm = (CheckListForm)form;
	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		checkInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
		checkInfo.setChangeJokyoId(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU);

// 20050613 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

		//------�g�[�N���̍폜	
		resetToken(request);
		
		try{
			//��ID�ύX
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkListUpdate(
				container.getUserInfo(),
				checkInfo, false);
		}catch(ValidationException e){
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}