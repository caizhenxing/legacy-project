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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInputInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R�����ʏ��̃t�@�C���_�E�����[�h�A�N�V�����N���X�B
 * �o�^�m�F�p�̕]���t�@�C�����_�E�����[�h����B
 * �t�@�C�����́AFileResorce����擾����B
 * 
 * ID RCSfile="$RCSfile: DownloadHyokaFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class DownloadHyokaFileAction extends BaseAction {

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
						
			//------�t�@�C���p�X���擾
			ShinsaKekkaInputInfo downloadInfo = container.getShinsaKekkaInputInfo();				
			FileResource fileRes = null;
			fileRes = downloadInfo.getHyokaFileRes();//�Y�t�t�@�C���iWin�j

			//------�t�@�C�����_�E�����[�h
			if(fileRes != null){
				//DownloadFileUtil.downloadFile(
				//		response,
				//		fileRes,
				//		FileUtil.CONTENT_TYPE_MS_WORD
				//		);
				DownloadFileUtil.downloadFile(response, fileRes);
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
