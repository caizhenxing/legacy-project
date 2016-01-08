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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ފǗ��o�^���l�I�u�W�F�N�g���폜����B
 * �t�H�[�����A�o�^�����N���A����B
 * 
 * ID RCSfile="$RCSfile: ShoruiDeleteSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiDeleteSaveAction extends BaseAction {

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

		//-----�擾�����g�[�N���������ł���Ƃ�
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------�V�K�o�^�t�H�[�����̎擾
		ShoruiKanriForm addForm = (ShoruiKanriForm) form;
		
		//------�Z�b�V�������폜���̎擾
		ShoruiKanriInfo deleteInfo = container.getShoruiKanriInfo();
//		ShoruiKanriInfo deleteInfo = new ShoruiKanriInfo();
//		deleteInfo.setJigyoId(addForm.getJigyoId());//�Ώ�
		deleteInfo.setSystemNo(addForm.getSystemNo());//�V�X�e���ԍ�

		//------�폜
		ISystemServise servise = getSystemServise(
							IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
		List result = servise.delete(container.getUserInfo(), deleteInfo);

		if (log.isDebugEnabled()) {
			log.debug("���ފǗ���� �폜   '" + deleteInfo + "'");
		}
		
		//�t�H�[���������Z�b�g
		addForm.reset(mapping, request);
				

		//------���ފǗ���񃊃X�g���Z�b�V�����ɓo�^�B
		container.setShoruiKanriList(result);
		
		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, addForm);
		
		//------�g�[�N���̍폜�i��ʑJ�ڂ��s��Ȃ����߁j
		resetToken(request);

		//------�g�[�N���̓o�^
		saveToken(request);			

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
