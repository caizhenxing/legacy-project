/*
 * �쐬��: 2005/04/24
 *
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �o�^�ς݃t�@�C���ꎞ�폜�A�N�V�����N���X�B
 * 
 * 2005/04/26 �t�@�C���𖈉�A�b�v���[�h���邽�߁A�g�p���Ȃ��B
 * 
 * @author yoshikawa_h
 *
 */
public class DeleteFileAction extends BaseAction {

	/** �_�E�����[�h�t�@�C���t���O�B�Y�t�t�@�C���iWin�j */
	public static String FILE_FLG_TENPU_WIN = "0";
	
	/** �_�E�����[�h�t�@�C���t���O�B�Y�t�t�@�C���iMac�j */
	public static String FILE_FLG_TENPU_MAC = "1";
	
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

		//## �X�V�s�̃v���p�e�B(�Z�b�V�����ɕێ�)�@$!userContainer.jigyoKanriInfo.�v���p�e�B��
		//## �X�V�Ώۃv���p�e�B 				$!jigyoKanriForm.�v���p�e�B��
		//##

		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();
		
		//------�C���o�^�t�H�[�����̎擾
		JigyoKanriForm editForm = (JigyoKanriForm) form;
		String downloadFileFlg = editForm.getDownloadFileFlg();
		
		JigyoKanriInfo editInfo = container.getJigyoKanriInfo();
		editForm.setAction(BaseForm.EDIT_ACTION);
		
		try {
			PropertyUtils.copyProperties(editInfo, editForm);
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("�v���p�e�B�̐ݒ�Ɏ��s���܂����B", e);
		}
		
		if(downloadFileFlg.equals(FILE_FLG_TENPU_WIN)){
			editInfo.setDelWinFileFlg(true);
			editInfo.setTenpuWin(null);
			editForm.setTenpuWin(null);
		}else if(downloadFileFlg.equals(FILE_FLG_TENPU_MAC)){
			editInfo.setDelMacFileFlg(true);
			editInfo.setTenpuMac(null);
			editForm.setTenpuMac(null);
		}
		
		//------�C���o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping,request,editForm);
		
		//------�X�V�Ώۏ����Z�b�V�����ɓo�^�B
		container.setJigyoKanriInfo(editInfo);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}