/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchAction.java
 *    Description : �`�F�b�N���X�g������ʕ\���A�N�V�����B
 *
 *    Author      : Admin
 *    Date        : 2005/04/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/12    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �`�F�b�N���X�g������ʕ\���A�N�V����
 * 
 * @author masuo_t
 */
public class SearchAction extends BaseAction {


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
		String jigyoKbn = (String)request.getParameter("jigyoKbn");
//2006/06/02 �ǉ���������
//        searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(),jigyoKbn));
        if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKbn)){
            searchForm.setJigyoList(LabelValueManager.getJigyoNameListByJigyoCdsWithoutKikanSAB(container.getUserInfo(),jigyoKbn));
        } else {
            searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(),jigyoKbn));
        }
//�c�@�ǉ������܂�        
	    searchForm.setJigyoKbn(jigyoKbn);
		//2005/04/21 �ǉ���������--------------------------
		//���R �󗝏󋵂̃��X�g�ǉ�
		searchForm.setJuriList(LabelValueManager.getJuriJokyoList());
		//�ǉ� �����܂�------------------------------------
		
		updateFormBean(mapping,request,searchForm);

		return forwardSuccess(mapping);
	}
}