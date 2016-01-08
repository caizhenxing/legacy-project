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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �X�V���ꂽ�����@�֏��̓��̓`�F�b�N���s���B
 * �����@�֏C�����l�I�u�W�F�N�g���쐬����B
 * �C���m�F��ʂ�\������B 
 * 
 * ID RCSfile="$RCSfile: EditCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
 */
public class EditCheckAction extends BaseAction {

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

		//------�C���o�^�t�H�[�����̎擾
		ShozokuForm editForm = (ShozokuForm) form;
		
		ShozokuPk pkInfo = new ShozokuPk();
		
		pkInfo.setShozokuTantoId(container.getShozokuInfo().getShozokuTantoId());
		
		//------�X�V�Ώۏ��̎擾
		ISystemServise servise = getSystemServise(
						IServiceName.SHOZOKU_MAINTENANCE_SERVICE);
		ShozokuInfo editInfo = servise.select(container.getUserInfo(),pkInfo);
		
		// 2005/04/21 �ǉ� ��������-----------------------------------------------------------
		// ���R ���ǒS���Ґl�������f�[�^��菭�Ȃ��Ƃ��G���[�\��
		
		int addBukyokuNum = 0;
		//���f�[�^�̂̕��ǒS���Ґl������łȂ�
		if(editInfo.getBukyokuNum() != null && !editInfo.getBukyokuNum().equals("")){
			//���ǒS���҂̓��͒l����łȂ�
			if(editForm.getBukyokuNum() != null && !editForm.getBukyokuNum().equals("")){
				
				addBukyokuNum = Integer.parseInt(editForm.getBukyokuNum()) - Integer.parseInt(editInfo.getBukyokuNum());
				//���ǒS���Ґl���̓��͒l�����f�[�^��菭�Ȃ��l�̂Ƃ��G���[
				if(addBukyokuNum < 0){
					errors.add("errors.5034", new ActionError("errors.5034", editInfo.getBukyokuNum()));
					//�G���[��ۑ��B
					saveErrors(request, errors);
					
					//---���͓��e�ɕs��������̂ōē���
					return forwardInput(mapping);
				}
			}else{
				errors.add("errors.5034", new ActionError("errors.5034", editInfo.getBukyokuNum()));
				//�G���[��ۑ��B
				saveErrors(request, errors);
				
				//---���͓��e�ɕs��������̂ōē���
				return forwardInput(mapping);
			}
				
		}
		
		// �ǉ� �����܂�----------------------------------------------------------------------------
		
		// 2005/04/21 �ǉ�
		// ���ǒS���Ґl���̒ǉ����Z�b�g
		editInfo.setAddBukyokuNum(addBukyokuNum);
		
		try {
			PropertyUtils.copyProperties(editInfo, editForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}	
		
		//�L�������iString��Date)
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(editForm.getYukoDateYear(),editForm.getYukoDateMonth(),editForm.getYukoDateDay());
		editInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------��

		//-----�Z�b�V�����ɏ����@�֏���o�^����B
		container.setShozokuInfo(editInfo);

		//------�C���m�F�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);
		
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
