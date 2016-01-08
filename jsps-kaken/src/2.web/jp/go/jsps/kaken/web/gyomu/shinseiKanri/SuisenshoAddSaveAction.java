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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * ���E�����l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A���E�������N���A����B
 * 
 * ID RCSfile="$RCSfile: SuisenshoAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class SuisenshoAddSaveAction extends BaseAction {

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

		//------�V�K�o�^�t�H�[�����̎擾
		SuisenshoAddForm addForm = (SuisenshoAddForm) form;

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiDataPk addPk = new ShinseiDataPk(addForm.getSystemNo());
		
		//---���E���o�^�t�@�C��
		FormFile suisenshoFile = addForm.getSuisenshoUploadFile();	
		FileResource hyokaFileRes = createFileResorce(suisenshoFile);	
		//-------��
		
		//DB�o�^
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		
		servise.registSuisenFile(container.getUserInfo(), addPk, hyokaFileRes);
				
		//------�g�[�N���̍폜	
		resetToken(request);
		
		//------�t�H�[�����̍폜
		removeFormBean(mapping, request);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

	/**
	 * @param file �A�b�v���[�h�t�@�C��
	 * @return �t�@�C�����\�[�X
	 */
	private FileResource createFileResorce(FormFile file)
								 throws ApplicationException {
		FileResource fileRes = null;
		try{	
			if(file != null){
				fileRes = new FileResource();
				fileRes.setPath(file.getFileName());	//�t�@�C����
				fileRes.setBinary(file.getFileData());	//�t�@�C���T�C�Y
			}
			return fileRes;
		}catch(IOException e){
			throw new ApplicationException(
				"�Y�t�t�@�C���̎擾�Ɏ��s���܂����B",
				new ErrorInfo("errors.7000"),
				e);
		}		
	}

}
