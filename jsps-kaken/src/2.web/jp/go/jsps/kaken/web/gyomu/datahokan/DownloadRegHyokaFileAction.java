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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsa.shinsaJigyo.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * �R�����ʓ��͏��p�̃t�@�C���_�E�����[�h�A�N�V�����N���X�B
 * �o�^�ς݂̕]���t�@�C�����_�E�����[�h����B
 * 
 * ID RCSfile="$RCSfile: DownloadRegHyokaFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class DownloadRegHyokaFileAction extends BaseAction {

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

			//------�����������擾
			ShinsaKekkaForm downloadForm = (ShinsaKekkaForm)form;			

			ShinsaKekkaPk downloadPk = new ShinsaKekkaPk();			
			downloadPk.setSystemNo(downloadForm.getSystemNo());							//�V�X�e����t�ԍ�		
			downloadPk.setShinsainNo(downloadForm.getShinsainNo());						//�R�����ԍ�
			downloadPk.setJigyoKubun(downloadForm.getJigyoKubun());						//���Ƌ敪
			
			//9/15�R�����g�A�E�g�@�Ɩ��S���҂����p���邽�߁A�t�H�[������擾����悤�ɏC��
//			downloadPk.setShinsainNo(container.getUserInfo().getShinsainInfo().getShinsainNo());		//�R�����ԍ�
//			downloadPk.setJigyoKubun(container.getUserInfo().getShinsainInfo().getJigyoKubun());		//���Ƌ敪	
						
			//�t�@�C�����\�[�X���擾
			FileResource fileRes  =
				getSystemServise(
					IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).getHyokaFileRes(
					container.getUserInfo(),
					downloadPk);
		
			//�t�@�C���̃_�E�����[�h
			DownloadFileUtil.downloadFile(response, fileRes);						

			//-----��ʑJ�ځi��^�����j-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
	}
}
