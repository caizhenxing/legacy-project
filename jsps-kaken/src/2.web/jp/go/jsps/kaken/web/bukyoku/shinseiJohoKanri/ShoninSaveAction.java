/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �\����񏳔F�A�N�V�����N���X�B
 * ���F�Ώې\�������X�V����B 
 * 
 */
public class ShoninSaveAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------���F�Ώې\�����V�X�e���ԍ��̎擾
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;
		
		//------���F�Ώې\���V�X�e���ԍ��̎擾
		ShinseiDataPk pkInfo = new ShinseiDataPk();
		//------�L�[���
		String systemNo = shoninForm.getSystemNo();
		pkInfo.setSystemNo(systemNo);

		//------�L�[�������ɐ\�������X�V	
		getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE).recognizeApplication(container.getUserInfo(),pkInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}
