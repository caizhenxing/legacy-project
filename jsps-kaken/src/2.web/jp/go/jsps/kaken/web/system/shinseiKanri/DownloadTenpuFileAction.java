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
package jp.go.jsps.kaken.web.system.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �Y�t�t�@�C���_�E�����[�h�A�N�V�����N���X�B
 * ID RCSfile="$RCSfile: DownloadTenpuFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class DownloadTenpuFileAction extends BaseAction {

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

		//-----�ȈՐ\�������̓t�H�[���̎擾
		SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
		
		//�T�[�o�T�[�r�X�̌Ăяo���i�Y�t�t�@�C���擾�j
		ShinseiDataPk shinseiPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		FileResource tenpuFileRes = servise.getTenpuFileRes(container.getUserInfo(),shinseiPk);
		
		//-----�N���C�A���g��PDF�t�@�C�����_�E�����[�h������
//2007/03/23 �c�@�C����������                
//		String contentType = DownloadFileUtil.CONTENT_TYPE_MS_WORD;
        String contentType = "";
        String fileType = tenpuFileRes.getPath().substring(tenpuFileRes.getPath().length() - 3);
        if ("pdf".equals(fileType)) {
            contentType = DownloadFileUtil.CONTENT_TYPE_PDF;
        } else if ("doc".equals(fileType)) {
            contentType = DownloadFileUtil.CONTENT_TYPE_MS_WORD;
        }
//2007/03/23�@�c�@�C�������܂�        
		DownloadFileUtil.downloadFile(response, tenpuFileRes, contentType);
		
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
