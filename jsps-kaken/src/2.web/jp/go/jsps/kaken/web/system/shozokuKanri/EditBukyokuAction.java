/*
 * �쐬��: 2005/04/21
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ǒS���ҏC���o�^�p�̃A�N�V�����N���X�B
 *
 */
public class EditBukyokuAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		BukyokuForm bukyokuForm = (BukyokuForm)form;
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(bukyokuForm.getBukyokutantoId());	
		
		//���ǒS���ҏ����擾����
		info =
			getSystemServise(
				IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
				container.getUserInfo(),
				info);
		
		//------�X�V�o�^�t�H�[�����̍쐬		
		BukyokuForm editForm = new BukyokuForm();
		//------�X�V���[�h�F�C��
		editForm.setAction(BaseForm.EDIT_ACTION);
		//------�Ώۏ����Z�b�V�����ɓo�^�B
		container.setBukyokutantoInfo(info);
		
		try {
			PropertyUtils.copyProperties(editForm, info);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}

		//�f�[�^���t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);
		
		return forwardSuccess(mapping);
	}

}