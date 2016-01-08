/*======================================================================
 *    SYSTEM      : �d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchTokuteiAction
 *    Description : �`�F�b�N���X�g������ʕ\���A�N�V����
 *
 *    Author      : Horikoshi
 *    Date        : 2005/06/06
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;

/**
 * �`�F�b�N���X�g������ʕ\���A�N�V����
 */
public class SearchTokuteiAction extends BaseAction {
//delete start dyh �����F�g�p���Ȃ�
//// 20050606 Start
//	private static final String JIGYOU_TOKUTEI = "5";
//// Horikoshi End
//delete end dyh

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//��������������΃N���A����B
		removeFormBean(mapping,request);
		
		//�����������t�H�[�����Z�b�g����B
		CheckListSearchForm searchForm = new CheckListSearchForm();
		
		//------�v���_�E���f�[�^�Z�b�g
// 20050606 Start
//		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(),"4"));
		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container
                .getUserInfo(), IJigyoKubun.JIGYO_KUBUN_TOKUTEI));
// Horikoshi End
// 2006/06/16 dyh add start �����F���j���[�d�l�ύX
        searchForm.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
// 2006/06/16 dyh add end

		//2005/04/21 �ǉ���������--------------------------
		//���R �󗝏󋵂̃��X�g�ǉ�
		searchForm.setJuriList(LabelValueManager.getJuriJokyoList());
		//�ǉ� �����܂�------------------------------------
		
		updateFormBean(mapping,request,searchForm);

		return forwardSuccess(mapping);
	}
}