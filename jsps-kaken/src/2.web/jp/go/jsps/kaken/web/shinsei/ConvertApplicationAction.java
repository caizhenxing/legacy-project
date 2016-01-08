/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\�����t�@�C���ϊ��A�N�V�����N���X�B
 * �w��V�X�e����t�ԍ��̐\�����ɑ΂��āAXML�ϊ��APDF�ϊ��v���𓊂���B
 * ID RCSfile="$RCSfile: ConvertApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ConvertApplicationAction extends BaseAction {

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

		//-----�\�������̓t�H�[���̎擾
		ShinseiForm shinseiForm = (ShinseiForm)form;
		
		//�T�[�o�T�[�r�X�̌Ăяo���i�t�@�C���ϊ��j
		ShinseiDataPk shinseiPk = new ShinseiDataPk(shinseiForm.getShinseiDataInfo().getSystemNo());
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		
		try{
			servise.convertApplication(container.getUserInfo(),shinseiPk);
		}catch(ValidationException e){
			List errorList = e.getErrors();
			for(int i=0; i<errorList.size(); i++){
				ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
				errors.add(errInfo.getProperty(),
						   new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
			}
			//���؃G���[�������̓g�[�N�����ăZ�b�g���i���́j��ʂ֑J�ڂ�����
			if (!errors.isEmpty()) {
				//�g�[�N�����Z�b�V�����ɕۑ�����B
				saveToken(request);
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
		}catch(ApplicationException e){
			//�G���[�������̓g�[�N�����ăZ�b�g���i���́j��ʂ֑J�ڂ�����
			//�g�[�N�����Z�b�V�����ɕۑ�����B
			saveToken(request);
			saveErrors(request, errors);
			throw e;
		}
		
		
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);
		
		//�ȈՐ\���t�H�[������
		SimpleShinseiForm simpleForm = new SimpleShinseiForm();
		simpleForm.setSystemNo(shinseiPk.getSystemNo());	//�V�X�e����t�ԍ��Z�b�g
		
		//���N�G�X�g�ɊȈՐ\���t�H�[�����Z�b�g����
		request.setAttribute(IConstants.RESULT_INFO, simpleForm);
		
		return forwardSuccess(mapping);
		
	}
	

	
}
