/*
 * Created on 2005/04/07
 * 
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ǒS���ҍ폜/�p�X���[�h�ύX�m�F�p�A�N�V�����N���X�B
 * �폜�m�F��ʁA�y�уp�X���[�h�ύX�m�F��ʕ\�����ɌĂяo�����B
 *
 */
public class ChangeAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------�C���o�^�t�H�[�����̎擾
		BukyokuForm deleteForm = (BukyokuForm)form;

		//------�L�[�������ɍ폜�f�[�^�擾	
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(deleteForm.getBukyokutantoId());	
		
		//�폜�Ώۏ����擾����
		info =
			getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
			container.getUserInfo(),
			info);
		
		//------�폜�Ώۏ����Z�b�V�����ɓo�^�B
		container.setBukyokutantoInfo(info);
			
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
