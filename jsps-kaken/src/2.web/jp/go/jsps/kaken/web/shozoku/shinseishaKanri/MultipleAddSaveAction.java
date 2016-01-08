/*
 * �쐬��: 2005/03/28
 *
 */
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���\���ҏ��ꊇ�o�^�A�N�V�����N���X�B
 * ���\���ҏ����ꊇ�o�^����B
 * 
 * @author yoshikawa_h
 *
 */
public class MultipleAddSaveAction extends BaseAction {
	
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

		//2005/04/20 �폜 ��������-------------------------------------------------------
		//���R MultipleAddCheckAction�Ńg�[�N���̃`�F�b�N���s������
/*		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
		
		//------�L�����Z����		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
*/		//�폜 �����܂�------------------------------------------------------------------

		//------�L�[���
		String[] kenkyuNo = ((ShinseishaListForm)form).getKenkyuNo();
		
		ISystemServise servise = getSystemServise(
				IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		
		servise.registShinseishaFromKenkyusha(container.getUserInfo(), kenkyuNo);
		
// 2005/04/04 �폜 ��������-------------------------------------------------------
// ���R �ꊇ�o�^�������commit���邽��
//		//------�o�^�Ώی����ҏ��̎擾
//		KenkyushaPk pkInfo = new KenkyushaPk();
//		KenkyushaInfo addInfo = null;
//		ShinseishaInfo result = new ShinseishaInfo();
//		
//		//------���o�^�\���҂̌������J��Ԃ�
//		for(int i=0; i < kenkyuNo.length; i++){
//			
//			if(kenkyuNo[i] == null || kenkyuNo[i].equals("")){
//				continue;
//			}
//			
//			//------��L�[���̃Z�b�g
//			pkInfo.setKenkyuNo(kenkyuNo[i]);
//			pkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
//			
//			//------�L�[�������ɍX�V�f�[�^�擾	
//			addInfo = servise.select(container.getUserInfo(),pkInfo);
//			//DB�o�^
//			result = servise.registShinseishaFromKenkyusha(container.getUserInfo(),addInfo);
//			
//			if(log.isDebugEnabled()){
//				log.debug("�\���ҏ��@�o�^��� '"+ request);
//			}
//		}	
// �폜 �����܂�------------------------------------------------------------------
		//------�g�[�N���̍폜	
		resetToken(request);
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);
		
		//2005/04/30 �ǉ� ----------------------------------------------��������
		//���R �o�^����������No��UserContainer�֒��ڕێ�����悤�ɏC��
		container.setKenkyuNo(kenkyuNo);
		//2005/04/30 �ǉ� ----------------------------------------------�����܂�

		//�폜 ��������-----------------------------------------------------------
		//���R �s�v�ȉ�ʑJ�ڂ̂���
		
		//-----��ʑJ�ځi��^�����j-----
		//if (!errors.isEmpty()) {
		//	saveErrors(request, errors);
		//	return forwardFailure(mapping);
		//}
		//�폜 �����܂�-----------------------------------------------------------
		
		return forwardSuccess(mapping);
	}
	
}
