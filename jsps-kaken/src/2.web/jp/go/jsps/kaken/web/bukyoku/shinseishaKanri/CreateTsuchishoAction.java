/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ID�E�p�X���[�h�ʒm���̍쐬
 */
public class CreateTsuchishoAction extends BaseAction {
	
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
		String[] kenkyuNo = container.getKenkyuNo();
		
		ArrayList array = new ArrayList();
		 
		//��f�[�^�ȊO�ŃZ�b�g
		for(int i = 0; i < kenkyuNo.length; i++){
			if(kenkyuNo[i] != null && !kenkyuNo[i].equals("")){
				array.add(kenkyuNo[i]);
			}
		}
		String[] kenkyuNoData = new String[array.size()];
		for(int j = 0; j < array.size(); j++){
			kenkyuNoData[j] = (String)array.get(j);
		}
			
		//-----�r�W�l�X���W�b�N���s-----
		FileResource fileRes =
				getSystemServise(
					IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).createTsuchisho(
					container.getUserInfo(),
					kenkyuNoData);
		
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
