/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.masterTorikomi;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.MasterKanriInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * �}�X�^�捞�A�N�V�����N���X�B
 * CSV�t�@�C����Ǎ��݁ADB���X�V����B
 * 
 * ID RCSfile="$RCSfile: RegistMasterAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class RegistMasterAction extends BaseAction {

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

		//------�L�����Z����		
/*		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
*/				
		//-----�}�X�^�捞���̓t�H�[���̎擾
		MasterTorikomiForm torikomiForm = (MasterTorikomiForm)form;
		
		
		MasterKanriInfo result = null;
		
		//-----�}�X�^�捞���\�b�h���Ăяo��		
		try{
			result = registApplication(container, torikomiForm);
		}catch(ValidationException e){
			List errorList = e.getErrors();
			for(int i=0; i<errorList.size(); i++){
				ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
				errors.add(errInfo.getProperty(),
						   new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
			}
		}
		

		//�o�^���ʂ����N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		//------�g�[�N���̍폜	
		resetToken(request);
		//------�t�H�[�����̍폜  ���t�@�C���ϊ��A�N�V�����ŗ��p���邽�ߍ폜���Ȃ��B
		//removeFormBean(mapping,request);

		return forwardSuccess(mapping);

	}
	
	
	/**
	 * CSV�t�@�C���捞�����s����B
	 * @param container ���O�C���\���ҏ��
	 * @param form      �}�X�^�捞�t�H�[���f�[�^
	 * @throws ValidationException  �f�[�^�`�F�b�N�G���[�����������ꍇ
	 * @throws ApplicationException �\���o�^�Ɏ��s�����ꍇ
	 */
	private MasterKanriInfo registApplication(UserContainer container, MasterTorikomiForm form)
		throws ValidationException, ApplicationException
	{
		//�Y�t�t�@�C��
		FileResource annexFileRes = null;
		try{
			FormFile file = form.getUploadCsv();
			if(file != null && file.getFileData().length != 0){
				annexFileRes = new FileResource();
				annexFileRes.setPath(file.getFileName());	//�t�@�C����
				annexFileRes.setBinary(file.getFileData());	//�t�@�C���T�C�Y
			}
		}catch(IOException e){
			throw new ApplicationException(
				"CSV�t�@�C���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.7000"),
				e);
		}

		if(annexFileRes == null){
			//annexFileRes��null�̏ꍇ
			throw new ApplicationException(
						"�w�肳�ꂽ�t�@�C�������݂��܂���B",
						new ErrorInfo("errors.7004"));
		}

		//�T�[�o�T�[�r�X�̌Ăяo��
		ISystemServise servise = getSystemServise(
						IServiceName.SYSTEM_MAINTENANCE_SERVICE);
	
		MasterKanriInfo mkInfo = servise.torikomimaster(container.getUserInfo(),
														annexFileRes,
														form.getMasterShubetu(),
														form.getShinkiKoshinFlg());
		return mkInfo;

	}
	
}
