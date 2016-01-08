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
package jp.go.jsps.kaken.web.system.shinseiKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\����񌟍��O�A�N�V�����N���X�B
 * �\����񌟍���ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class SearchAction extends BaseAction {

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

		//��������������΃N���A����B
		removeFormBean(mapping,request);
		
		//�����������t�H�[�����Z�b�g����B
		ShinseiSearchForm searchForm = new ShinseiSearchForm();

		//���ƃ��X�g�̎擾�i�S�����鎖�Ƌ敪�̂݁j
		UserInfo userInfo = container.getUserInfo();
		List jigyoList = LabelValueManager.getJigyoNameList(userInfo);
		
		//------�v���_�E���f�[�^�Z�b�g
		searchForm.setJigyoNameList(jigyoList);													//���Ɩ����X�g
//		searchForm.setKantenList(LabelValueManager.getKantenList());							//���E�̊ϓ_���X�g
		searchForm.setJokyoList(LabelValueManager.getSystemJokyoList());						//�\���󋵃��X�g
		searchForm.setDelFlgList(LabelValueManager.getDelFlgList());						//�폜�t���O���X�g
		searchForm.setHyojiSentakuList(LabelValueManager.getShinseishoHyojiList());				//�\���I�����X�g
						
		updateFormBean(mapping,request,searchForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
