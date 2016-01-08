/*
 * �쐬��: 2005/04/20
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * @author yoshikawa_h
 *
 */

/**
 * ���ǒS���ҏ��폜�O�A�N�V�����N���X�B
 * �폜�Ώە��ǒS���ҏ����擾�B�Z�b�V�����ɓo�^����B 
 * �폜�m�F��ʂ�\������B
 * 
 */
public class DeleteBukyokuAction extends BaseAction {

	/* 
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## �폜���v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.bukyokuInfo.�v���p�e�B��

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//------�C���o�^�t�H�[�����̎擾
		BukyokuForm deleteForm = (BukyokuForm) form;
		
		//------�폜�Ώۏ����@�֏��̎擾
		BukyokutantoInfo info = new BukyokutantoInfo();
		//------�L�[���
		String bukyokuTantoId = deleteForm.getBukyokutantoId();
		info.setBukyokutantoId(bukyokuTantoId);

		//------�L�[�������ɍ폜�f�[�^�擾	
		BukyokutantoInfo deleteInfo = getSystemServise(IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(container.getUserInfo(),info);
	
		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setBukyokutantoInfo(deleteInfo);

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

