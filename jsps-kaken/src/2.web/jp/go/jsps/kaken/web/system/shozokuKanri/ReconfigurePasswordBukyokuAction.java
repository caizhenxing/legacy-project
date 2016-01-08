/*
 * �쐬��: 2005/04/21
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �p�X���[�h�ύX�m�F�p�A�N�V�����N���X�B
 * �p�X���[�h�ύX�m�F��ʕ\�����ɌĂяo�����B
 *
 */
public class ReconfigurePasswordBukyokuAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------�t�H�[�����̎擾
		BukyokuForm bukyokuForm = (BukyokuForm)form;

		//------�L�[�������ɊY���f�[�^�擾	
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(bukyokuForm.getBukyokutantoId());	
		
		//�Ώۏ����擾����
		info =
			getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
			container.getUserInfo(),
			info);
		
		//------�Ώۏ����Z�b�V�����ɓo�^�B
		container.setBukyokutantoInfo(info);
			
		//�g�[�N�����Z�b�V�����ɕۑ�����B
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
