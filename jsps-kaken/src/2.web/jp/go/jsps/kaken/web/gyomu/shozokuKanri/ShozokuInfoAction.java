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
package jp.go.jsps.kaken.web.gyomu.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * �����@�֏��̕\���N���X�B
 * �����@�֏��m�F��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShozokuInfoAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:25 $"
 */
public class ShozokuInfoAction extends BaseAction {

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

		//------�\���Ώۏ����@�֏��̎擾
		ShozokuPk pkInfo = new ShozokuPk();
		//------�L�[���
		String shozokuTantoId = ((ShozokuForm)form).getShozokuTantoId();
		pkInfo.setShozokuTantoId(shozokuTantoId);
		
		//------�L�[�������ɕ\���f�[�^�擾	
		ShozokuInfo editInfo = 
			getSystemServise(IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
		
		//------�\���Ώۏ����Z�b�V�����ɓo�^�B
		container.setShozokuInfo(editInfo);
		
		//�������ʂ����N�G�X�g�����ɃZ�b�g
//		request.setAttribute(IConstants.RESULT_INFO,editInfo);
		//------�Z�b�V�������V�K�o�^���̍폜
//		container.setShozokuInfo(null);
		//------�t�H�[�����̍폜
//		removeFormBean(mapping,request);
				
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
