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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
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
 * ���ފǗ��o�^���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�o�^�����N���A����B
 * 
 * ID RCSfile="$RCSfile: ShoruiAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiAddSaveAction extends BaseAction {

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

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------�V�K�o�^�t�H�[�����̎擾
		ShoruiKanriForm addForm = (ShoruiKanriForm) form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShoruiKanriInfo addInfo = container.getShoruiKanriInfo();
		
		//-------�A�b�v���[�h�t�@�C�����Z�b�g
		//---���ރt�@�C��
		FormFile shoruiFile = addForm.getShoruiUploadFile();//���ރt�@�C��
		FileResource shoruiFileRes = createFileResorce(shoruiFile);	
		addInfo.setShoruiFileRes(shoruiFileRes);
		
		addInfo.setShoruiName(addForm.getShoruiName());//���ޖ�
		addInfo.setTaishoId(addForm.getTaishoId());//�Ώ�ID
		//-------��
		
		//DB�o�^
		ISystemServise servise = getSystemServise(
						IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
			List result = servise.insert(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("���ފǗ����@�o�^��� '"+ result);
		}
		
		//���ފǗ��������Z�b�g�i����ID�A���Ɩ��A�N�x�A�񐔈ȊO�j
		addInfo.reset();
		
		//�t�H�[���������Z�b�g
		addForm.reset(mapping, request);
				
		//-----�Z�b�V�����ɏ��ފǗ�����o�^����B
		container.setShoruiKanriInfo(addInfo);
		
		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, addForm);

		//------���ފǗ���񃊃X�g���Z�b�V�����ɓo�^�B
		container.setShoruiKanriList(result);
				
		//------�g�[�N���̍폜
		resetToken(request);
	
		//------�g�[�N���̓o�^
		saveToken(request);	

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
