/*
 * Created on 2005/04/05
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
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �����S���ғo�^�p�A�N�V�����N���X�B
 * 
 */
public class AddAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	

		//------�V�K�o�^�t�H�[�����̍쐬
		BukyokuForm bukyokuForm = new BukyokuForm();
		BukyokutantoInfo bukyokuInfo = new BukyokutantoInfo();
		
		//------�X�V���[�h�F�V�K�o�^
		bukyokuForm.setAction(BaseForm.ADD_ACTION);
	
		//���ǒS��ID���Z�b�g����B
		bukyokuForm.setBukyokutantoId(((BukyokuForm)form).getBukyokutantoId());
		bukyokuInfo.setBukyokutantoId(((BukyokuForm)form).getBukyokutantoId());
		
		//�����S���ҏ����擾����
		bukyokuInfo = getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectShozokuData(
			container.getUserInfo(),
			bukyokuInfo);
		
		//�����S���ҏ����Z�b�g	
		bukyokuForm.setShozokuCd(bukyokuInfo.getShozokuCd());
		bukyokuForm.setShozokuName(bukyokuInfo.getShozokuName());
		bukyokuForm.setShozokuNameEigo(bukyokuInfo.getShozokuNameEigo());
					
		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,bukyokuForm);
		
		return forwardSuccess(mapping);
	}
}
