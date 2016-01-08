/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/04/11
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\����񌟍��O�A�N�V�����N���X�B
 * �\����񌟍���ʂ�\������B
 * 
 */
public class SearchAction extends BaseAction {


	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
				
		//�錾
		ShinseiSearchForm searchForm = new ShinseiSearchForm();

		//�R���{�f�[�^�̎擾
		//���Ɩ�
		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo()));
		//�\����
		searchForm.setJokyoList(LabelValueManager.getJokyoList());
		//�\������
		searchForm.setHyojiHoshikiList(LabelValueManager.getShinseishoHyojiList());

		//�����������t�H�[�����Z�b�g����B
		updateFormBean(mapping,request,searchForm);

		return forwardSuccess(mapping);
	}

}
