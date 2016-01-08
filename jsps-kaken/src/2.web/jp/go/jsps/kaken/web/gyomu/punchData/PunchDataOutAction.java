/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���iJSPS)
 *    Source name : PunchDataOutAction.java
 *    Description : �p���`�f�[�^�_�E�����[�h�A�N�V����
 *
 *    Author      : 
 *    Date        : 2004/10/18
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PunchDataOutAction extends BaseAction{
	
	/* (�� Javadoc)
		 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
		 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		 throws ApplicationException
		{
		
		//-----ActionErrors�̐錾�i��^�����j-----
		ActionErrors errors = new ActionErrors();

		//���������̎擾
		PunchDataForm punchdataform = (PunchDataForm)form;
			  
		//���[�U���̊l��
		UserInfo userInfo = container.getUserInfo();

		//�T�[�r�X�I�u�W�F�N�g���擾����
		ISystemServise service =  getSystemServise(
							IServiceName.PUNCHDATA_MAINTENANCE_SERVICE);
	
		FileResource fileResource = null; 
	
		//PunchDataMaintenace����p���`�f�[�^�t�@�C�����󂯎��
		try {
			 fileResource = service.getPunchDataResource(userInfo, punchdataform.getPunchShubetu());
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		}
	
		DownloadFileUtil.downloadFile(response, fileResource);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
