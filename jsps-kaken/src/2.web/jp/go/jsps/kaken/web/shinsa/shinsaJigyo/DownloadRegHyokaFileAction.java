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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R�����ʓ��͏��p�̃t�@�C���_�E�����[�h�A�N�V�����N���X�B
 * �o�^�ς݂̕]���t�@�C�����_�E�����[�h����B
 * 
 * ID RCSfile="$RCSfile: DownloadRegHyokaFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
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
					IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).getHyokaFileRes(
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
