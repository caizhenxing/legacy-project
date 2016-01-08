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
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ރt�@�C���̃_�E�����[�h�A�N�V�����N���X�B
 * ���ރt�@�C�����_�E�����[�h����B
 * 
 * ID RCSfile="$RCSfile: DownloadShoruiFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class DownloadShoruiFileAction extends BaseAction {

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

			//------�V�K�o�^�t�H�[�����̍쐬
			ShoruiKanriForm addForm = (ShoruiKanriForm)form;
			
			//�L�[�����擾
			String systemNo = addForm.getSystemNo();//�V�X�e���ԍ�
  			ShoruiKanriPk searchInfo = new ShoruiKanriInfo();
			searchInfo.setSystemNo(systemNo);//�V�X�e���ԍ�

			//�t�@�C�����\�[�X���擾
			FileResource fileRes  =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).getShoruiKanriFileRes(
					container.getUserInfo(),
					searchInfo);
		
			//�t�@�C���̃_�E�����[�h
			//DownloadFileUtil.downloadFile(response, fileRes, DownloadFileUtil.CONTENT_TYPE_PDF);
			DownloadFileUtil.downloadFile(response, fileRes);
			
			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}

			return forwardSuccess(mapping);
	}
}
