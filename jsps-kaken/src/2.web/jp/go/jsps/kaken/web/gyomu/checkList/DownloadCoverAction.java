/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : DownloadCoverAction.java
 *    Description : PDF�\���t�@�C���_�E�����[�h�B
 *
 *    Author      : Admin
 *    Date        : 2005/04/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/13    v1.0        Admin          �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * PDF�\���t�@�C���_�E�����[�h
 * 
 * @author masuo_t
 */
public class DownloadCoverAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

// 2006/07/21 dyh delete start ���R�F�g�p���Ȃ�
//		//contentType��PDF�Ɏw�� 
//		String contentType = DownloadFileUtil.CONTENT_TYPE_PDF;
// 2006/07/21 dyh delete end

		//2005/05/25 �폜 ��������----------------------------------------------------
		//�\���t�@�C���p�X��DB����擾���邽�ߍ폜	
		//ApplicationSettings����\���t�@�C���̃p�X�擾
		//String SHINSEI_COVER_FOLDER  = ApplicationSettings.getString(ISettingKeys.PDF_COVER);
							   
		//File file = new File(SHINSEI_COVER_FOLDER);
		//�폜 �����܂�--------------------------------------------------------------		
		
		//2005/05/25 �ǉ� ��������----------------------------------------------------
		//�\���t�@�C���p�X��DB����擾���邽�ߒǉ�
		
		//-----�ȈՐ\�������̓t�H�[���̎擾
		CheckListForm checkListForm = (CheckListForm)form;
		
		//�T�[�o�T�[�r�X�̌Ăяo���i�\��PDF�t�@�C���擾�j
		CheckListSearchInfo checkListInfo = new CheckListSearchInfo();
		checkListInfo.setJigyoId(checkListForm.getJigyoId());
		checkListInfo.setShozokuCd(checkListForm.getShozokuCd());
		
		ISystemServise servise = getSystemServise(
						IServiceName.CHECKLIST_MAINTENANCE_SERVICE);
//		String filePath = servise.getPdfFilePath(container.getUserInfo(), checkListInfo);
//		
//		if(filePath == null || filePath.equals("")){
//			throw new FileIOException("�\���t�@�C���p�X�̎擾�Ɏ��s���܂����B");
//		}
//		
//		File file = new File(filePath);
//		//�ǉ� �����܂�--------------------------------------------------------------
//			
//		//-----�N���C�A���g��PDF�t�@�C�����_�E�����[�h������
//		boolean isDownload = DownloadFileUtil.downloadFile(response, file, contentType);		
//		
//		if(!isDownload){	
//			//�_�E�����[�h�ł��Ȃ��ꍇ�̓G���[��ʂ֑J�ڂ���
//			throw new FileIOException("�\���̎擾�Ɏ��s���܂����B");
//		}
//		return null;

		FileResource fileRes = null;
		try {
			fileRes = servise.getPdfFile(container.getUserInfo(), checkListInfo);
		} catch (ValidationException e) {
			//�T�[�o�[�G���[��ۑ��B
			saveServerErrors(request, errors, e);
		}
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//-----�t�@�C���̃_�E�����[�h
		DownloadFileUtil.downloadFile(response, fileRes);	

		return forwardSuccess(mapping);
	}
}