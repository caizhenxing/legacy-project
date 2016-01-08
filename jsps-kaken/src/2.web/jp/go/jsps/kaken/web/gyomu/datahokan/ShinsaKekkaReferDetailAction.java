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
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.gyomu.shinseiKanri.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;


/**
 * �R�����ʎQ�Ɖ�ʕ\���O�A�N�V�����N���X�B
 * �R�����ʎQ�Ɖ�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaReferDetailAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class ShinsaKekkaReferDetailAction extends BaseAction {

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
		
		//------�����ΏېR�����ʏ��̎擾
		ShinsaKekkaReferForm selectForm = (ShinsaKekkaReferForm)form;
		
		//------�L�[���
		ShinsaKekkaPk pkInfo  = new ShinsaKekkaPk();
		pkInfo.setSystemNo(selectForm.getSystemNo());		//�V�X�e����t�ԍ�
		pkInfo.setShinsainNo(selectForm.getShinsainNo());	//�R�����ԍ�
		pkInfo.setJigyoKubun(selectForm.getJigyoKubun());	//���Ƌ敪	
			
		//------�L�[�������ɍX�V�f�[�^�擾	
		ShinsaKekkaInputInfo selectInfo = 
					getSystemServise(IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).select1stShinsaKekka(
								container.getUserInfo(),
								pkInfo);

		//�\���Ώۏ������N�G�X�g�����ɃZ�b�g
		request.setAttribute(IConstants.RESULT_INFO,selectInfo);
		
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
