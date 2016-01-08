/*
 * �쐬��: 2005/04/21
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �����@�֒S���҃p�X���[�h�Đݒ�O�A�N�V�����N���X�B
 * �Đݒ�\���ҏ����擾�B�Z�b�V�����ɓo�^����B 
 * �p�X���[�h�Đݒ��ʂ�\������B
 * 
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

		//## �p�X���[�h�Đݒ���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.shozokuInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�p�X���[�h�Đݒ�t�H�[�����̎擾
		ShozokuForm reconfigurePasswordForm = (ShozokuForm) form;
		
		//------�p�X���[�h�Đݒ�Ώۏ����@�֒S���ҏ��̎擾
		ShozokuPk pkInfo = new ShozokuPk();
		//------�L�[���
		String shozokuId = reconfigurePasswordForm.getShozokuTantoId();
		pkInfo.setShozokuTantoId(shozokuId);

		//------�L�[�������ɏ����@�֒S���҃f�[�^�擾	
		ShozokuInfo reconfigurePasswordInfo = getSystemServise(IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		
		//------�Ώۏ����Z�b�V�����ɓo�^�B
		container.setShozokuInfo(reconfigurePasswordInfo);

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