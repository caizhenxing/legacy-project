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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ƊǗ����̃t�@�C���_�E�����[�h�A�N�V�����N���X�B
 * �\�����e�t�@�C���iWin�j�A�\�����e�t�@�C���iMac�j�A�]���t�@�C�����_�E�����[�h����B
 * 
 * ID RCSfile="$RCSfile: RegDownloadFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class RegDownloadFileAction extends BaseAction {

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
			String fileFlg = downloadForm.getDownloadFileFlg();//�_�E�����[�h�t�@�C���t���O
			String jigyoId = downloadForm.getJigyoId();//����ID	

			JigyoKanriInfo downloadInfo = new JigyoKanriInfo();			
			downloadInfo.setJigyoId(jigyoId);			

			//�t�@�C�����\�[�X���擾
			FileResource fileRes  =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).getJigyoKanriFileRes(
					container.getUserInfo(),
					downloadInfo, 
					fileFlg);
		
			//�t�@�C���̃_�E�����[�h
			//DownloadFileUtil.downloadFile(response, fileRes, DownloadFileUtil.CONTENT_TYPE_MS_WORD);
			DownloadFileUtil.downloadFile(response, fileRes);						

			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
	}
}
