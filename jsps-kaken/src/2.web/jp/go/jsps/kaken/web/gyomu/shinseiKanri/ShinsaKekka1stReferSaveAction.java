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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 1���R�����ʎQ�Ɠo�^���l�I�u�W�F�N�g��o�^����B
 * �t�H�[�����A�o�^�����N���A����B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka1stReferSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class ShinsaKekka1stReferSaveAction extends BaseAction {

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

		//------�V�K�o�^�t�H�[�����̎擾
		ShinsaKekka1stForm addForm = (ShinsaKekka1stForm) form;

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinsaKekkaReferenceInfo addInfo = new ShinsaKekkaReferenceInfo();
		addInfo.setSystemNo(addForm.getSystemNo());				//�V�X�e���ԍ�
		addInfo.setShinsa1Biko(addForm.getShinsa1Biko());		//�Ɩ��S���Ҕ��l
		//-------��
		
		//DB�o�^
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		servise.regist1stShinsaKekkaBiko(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("1���R�����ʎQ�Ə��@�o�^��� '"+ addInfo);
		}
		
		//------�g�[�N���̍폜	
		resetToken(request);
		
		//------�t�H�[�����̍폜
		removeFormBean(mapping,request);

		//-----�Z�b�V�������̍폜
		container.setShinsaKekkaReferenceInfo(null);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
