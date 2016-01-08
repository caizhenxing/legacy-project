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
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\���ҏ��o�^�O�A�N�V�����N���X�B
 * �t�H�[���ɐ\���ҏ��o�^��ʂɕK�v�ȃf�[�^���Z�b�g����B
 * �\���ҏ��V�K�o�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: AddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:33 $"
 */
public class AddAction extends BaseAction {

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

		//------�V�K�o�^�t�H�[�����̍쐬
		ShinseishaForm addForm = new ShinseishaForm();

		//------�X�V���[�h
		addForm.setAction(BaseForm.ADD_ACTION);

		//------�v���_�E���f�[�^�Z�b�g
//		addForm.setShubetuCdList(LabelValueManager.getBukyokuShubetuCdList());
		addForm.setShokushuCdList(LabelValueManager.getShokushuCdList());

		//------���O�C�������@�֒S���ҏ��ݒ���s���B
		addForm.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
		addForm.setShozokuName(container.getUserInfo().getShozokuInfo().getShozokuName());
		addForm.setShozokuNameEigo(container.getUserInfo().getShozokuInfo().getShozokuNameEigo());

		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, addForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
