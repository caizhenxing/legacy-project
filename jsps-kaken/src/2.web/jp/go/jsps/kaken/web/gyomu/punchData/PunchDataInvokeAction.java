/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���iJSPS)
 *    Source name : PunchDataInvokeAction.java
 *    Description : �p���`�f�[�^��������ʂ�\������A�N�V����
 *
 *    Author      : Admin
 *    Date        : 2005/04/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/28    V1.0                       �V�K�쐬
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �p���`�f�[�^��������ʂ�\������A�N�V����
 *
 */
public class PunchDataInvokeAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
		//�錾
		PunchDataForm punchDataForm = (PunchDataForm)form;

		//if( punchDataForm.getPunchShubetu() != null && punchDataForm.getPunchShubetu().equals("7")){
		//	return forwardCancel(mapping);
		//}

		return forwardSuccess(mapping);
	}

}
