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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �\���҃p�X���[�h�Đݒ�O�A�N�V�����N���X�B
 * �Đݒ�\���ҏ����擾�B�Z�b�V�����ɓo�^����B 
 * �p�X���[�h�Đݒ��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ReconfigurePasswordAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:33 $"
 */
public class ReconfigurePasswordAction extends BaseAction {

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

		//## �p�X���[�h�Đݒ���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shinseishaInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�p�X���[�h�Đݒ�t�H�[�����̎擾
		ShinseishaForm reconfigurePasswordForm = (ShinseishaForm) form;
		
		//------�p�X���[�h�Đݒ�Ώې\���ҏ��̎擾
		ShinseishaPk pkInfo = new ShinseishaPk();
		//------�L�[���
		String shinseishaId = reconfigurePasswordForm.getShinseishaId();
		pkInfo.setShinseishaId(shinseishaId);

		//------�L�[�������Ƀp�X���[�h�Đݒ�f�[�^�擾	
		ShinseishaInfo reconfigurePasswordInfo = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

//		//�I�����ꂽ��ʖ�
//		if(reconfigurePasswordInfo.getBukyokuShubetuCd() != null && !reconfigurePasswordInfo.getBukyokuShubetuCd().equals("9")){
//			reconfigurePasswordInfo.setBukyokuShubetuName(LabelValueManager.getBukyokuShubetuCdValue(reconfigurePasswordInfo.getBukyokuShubetuCd()));
//		}
		
		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShinseishaInfo(reconfigurePasswordInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
