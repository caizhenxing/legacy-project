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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ƊǗ����̃t�@�C���_�E�����[�h�A�N�V�����N���X�B
 * �\�����e�t�@�C���iWin�j�A�\�����e�t�@�C���iMac�j�A�]���t�@�C�����_�E�����[�h����B
 * �t�@�C�����́AFileResorce����擾����B
 * 
 * ID RCSfile="$RCSfile: DownloadFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class DownloadFileAction extends BaseAction {


	/** �_�E�����[�h�t�@�C���t���O�B�Y�t�t�@�C���iWin�j */
	public static String FILE_FLG_TENPU_WIN = "0";
	
	/** �_�E�����[�h�t�@�C���t���O�B�Y�t�t�@�C���iMac�j */
	public static String FILE_FLG_TENPU_MAC = "1";
	
	/** �_�E�����[�h�t�@�C���t���O�B�]���p�t�@�C�� */
	public static String FILE_FLG_HYOKA = "2";

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

			//------�t�@�C���t���O���擾
			JigyoKanriForm downloadForm = (JigyoKanriForm)form;			
			String downloadFileFlg = downloadForm.getDownloadFileFlg();
						
			//------�t�@�C���p�X���擾
			JigyoKanriInfo downloadInfo = container.getJigyoKanriInfo();				
			FileResource fileRes = null;
			if(downloadFileFlg.equals(FILE_FLG_TENPU_WIN)){
				fileRes = downloadInfo.getTenpuWinFileRes();//�Y�t�t�@�C���iWin�j
				
				// 2005/04/25 �ǉ� ��������----------------------------------------------
				// ���R �o�^�ς݃t�@�C�����m�F��ʂł��\�����邽��
				if(downloadInfo.isDelWinFileFlg() == false && (downloadInfo.getTenpuWin() != null && !downloadInfo.getTenpuWin().equals(""))){
					try {
						fileRes = FileUtil.readFile(new File(downloadInfo.getTenpuWin()));
					} catch (FileNotFoundException e) {
						throw new FileIOException(
								"�t�@�C����������܂���ł����B",			
								e);
					} catch (IOException e) {
						throw new FileIOException(
								"�t�@�C���̓��o�͒��ɃG���[���������܂����B",
								e);
					}
				}
				// �ǉ� �����܂�---------------------------------------------------------
			
			}else if(downloadFileFlg.equals(FILE_FLG_TENPU_MAC)){
				fileRes = downloadInfo.getTenpuMacFileRes();//�Y�t�t�@�C���iMac�j
				
				// 2005/04/25 �ǉ� ��������----------------------------------------------
				// ���R �o�^�ς݃t�@�C�����m�F��ʂł��\�����邽��
				if(downloadInfo.isDelMacFileFlg() == false && (downloadInfo.getTenpuMac() != null && !downloadInfo.getTenpuMac().equals(""))){
					try {
						fileRes = FileUtil.readFile(new File(downloadInfo.getTenpuMac()));
					} catch (FileNotFoundException e) {
						throw new FileIOException(
								"�t�@�C����������܂���ł����B",			
								e);
					} catch (IOException e) {
						throw new FileIOException(
								"�t�@�C���̓��o�͒��ɃG���[���������܂����B",
								e);
					}
				}
				// �ǉ� �����܂�---------------------------------------------------------

			}else if(downloadFileFlg.equals(FILE_FLG_HYOKA)){
				fileRes = downloadInfo.getHyokaFileRes();//�]���p�t�@�C��
				
				// 2005/04/25 �ǉ� ��������----------------------------------------------
				// ���R �o�^�ς݃t�@�C�����m�F��ʂł��\�����邽��
				if(downloadInfo.getHyokaFileRes() == null || downloadInfo.getHyokaFileRes().getBinary()==null || downloadInfo.getHyokaFileRes().getBinary().length <= 0){
					try {
						fileRes = FileUtil.readFile(new File(downloadInfo.getHyokaFile()));
					} catch (FileNotFoundException e) {
						throw new FileIOException(
								"�t�@�C����������܂���ł����B",			
								e);
					} catch (IOException e) {
						throw new FileIOException(
								"�t�@�C���̓��o�͒��ɃG���[���������܂����B",
								e);
					}
				}
				// �ǉ� �����܂�---------------------------------------------------------
			}
		
			//------�t�@�C�����_�E�����[�h
			if(fileRes != null){
//				DownloadFileUtil.downloadFile(
//						response,
//						fileRes,
//						FileUtil.CONTENT_TYPE_MS_WORD
//						);
				DownloadFileUtil.downloadFile(
						response,
						fileRes
						);
			}else{
				throw new FileIOException(
					"�t�@�C���̓��o�͒��ɃG���[���������܂����BfileRes'" + fileRes +"'" );				
			}

			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
	}
}
