/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �]����񌟍��O�A�N�V�����N���X�B
 * �]����񌟍���ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: SearchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
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

		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//���[�U���̎擾
        UserInfo userInfo = container.getUserInfo();
        
		//�錾
		HyokaSearchForm searchForm = new HyokaSearchForm();

		//-------�� VO�Ƀf�[�^���Z�b�g����B
//		SearchInfo searchInfo = new SearchInfo();

		//���Ɩ�
//update start ly 2006/04/11
//		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(), ((HyokaSearchForm)form).getJigyoKubun()));
		String jigyoKbn = ((HyokaSearchForm)form).getJigyoKubun();
		
		if (IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO.equals(jigyoKbn)) {
			//�w�p�n���i�����j�̏ꍇ
			searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(), jigyoKbn));
		}
		else if (IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKbn)){
			//��Ռ����A��茤���X�^�[�g�A�b�v�̏ꍇ
			List jigyoList = new ArrayList();
			List jigyoList2 = new ArrayList();
            List jigyoList3 = new ArrayList();
//2006/05/19 �ǉ���������
//			//��Ղ̎��Ɩ�
//			jigyoList = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_KIBAN);
//			//���X�^�[�g�̎��Ɩ�
//			jigyoList2 = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
//            //���ʌ������i��̎��Ɩ�
//            jigyoList3 = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
//			//���X�^�[�g�̎��Ɩ���ǉ�
//			jigyoList.addAll(jigyoList2);
//            //���ʌ������i��̎��Ɩ���ǉ�
//            jigyoList.addAll(jigyoList3);
            
            if (userInfo.getRole().equals(UserRole.GYOMUTANTO)) {
                Set iset = userInfo.getGyomutantoInfo().getTantoJigyoKubun();
                // ��Ղ̎��Ɩ�
                if (iset.contains(IJigyoKubun.JIGYO_KUBUN_KIBAN)) {
                    jigyoList = LabelValueManager.getJigyoNameList(container.getUserInfo(), IJigyoKubun.JIGYO_KUBUN_KIBAN);
                }
                if (iset.contains(IJigyoKubun.JIGYO_KUBUN_WAKATESTART)) {
                    jigyoList2 = LabelValueManager.getJigyoNameList(container.getUserInfo(),IJigyoKubun.JIGYO_KUBUN_WAKATESTART);
                }
                if (iset.contains(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI)) {
                    jigyoList3 = LabelValueManager.getJigyoNameList(container.getUserInfo(),IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
                }
            }
            jigyoList.addAll(jigyoList2);
            jigyoList.addAll(jigyoList3);
			searchForm.setJigyoList(jigyoList);
//�c�@�ǉ������܂�            
		}
//update end ly 2006/04/11
		//�\�������w�n�p(�_���� or A�̑�����)
		searchForm.setHyojiHoshikiList(LabelValueManager.getHyokaHyojiList());
		//�\��������՗p(�]�����ꗗ or �R�����g�ꗗ)
		searchForm.setHyojiHoshikiListKiban(LabelValueManager.getHyokaHyojiListKiban());

		//�����������t�H�[�����Z�b�g����B
		updateFormBean(mapping,request,searchForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
