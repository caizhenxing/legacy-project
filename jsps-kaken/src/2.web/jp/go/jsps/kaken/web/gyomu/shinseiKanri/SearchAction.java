/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : SearchAction.java
 *    Description : �\����񌟍��O�A�N�V�����N���X
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

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
 * Date="$Date: 2007/06/28 02:06:54 $"
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
		searchForm.setJigyoNameList(jigyoList);											//���Ɩ����X�g
		searchForm.setKantenList(LabelValueManager.getKantenList());					//���E�̊ϓ_���X�g
		searchForm.setJokyoList(LabelValueManager.getGyomuJokyoList());					//�\���󋵃��X�g
		searchForm.setHyojiSentakuList(LabelValueManager.getShinseiHyojiSentakuList());	//�\���I�����X�g

		//2005/9/2 ���X�g���ڒǉ�
		searchForm.setShinkiKeibetuList(LabelValueManager.getSinkiKeizokuFlgList(true));//�V�K�p�����X�g
// 2006/07/24 dyh update start ���R�FgetZennendoOboList���@�ύX��������
//        searchForm.setZennendoList(LabelValueManager.getZennendoOboList());        //�O�N�x�̉��僊�X�g
		searchForm.setZennendoList(LabelValueManager.getZennendoOboList(true));		//�O�N�x�̉��僊�X�g
// 2006/07/24 dyh update end
		searchForm.setBuntankinList(LabelValueManager.getBuntankinList());				//���S���̗L�����X�g
		searchForm.setKenkyuKubunList(LabelValueManager.getKenkyuKubunList());			//�v�挤���E���匤���E�I�������̈�敪
		searchForm.setKaijiKiboList(LabelValueManager.getKaijiKiboList());				//�J����]�̗L���ǉ��̂���
		searchForm.setChouseiList(LabelValueManager.getChouseiList());					//�����ǂ̃��X�g

		updateFormBean(mapping,request,searchForm);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}