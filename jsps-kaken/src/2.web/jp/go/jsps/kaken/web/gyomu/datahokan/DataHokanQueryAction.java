/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �f�[�^�ۊǎ��Ɩ₢���킹�A�N�V�����N���X�B
 * �w�莖�Ə����擾����B
 * ID RCSfile="$RCSfile: DataHokanQueryAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:24 $"
 */
public class DataHokanQueryAction extends BaseAction {

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
	
		//�t�H�[���̎擾
		DataHokanForm dataHokanForm = (DataHokanForm)form;
		
		//�T�[�o�T�[�r�X�̌Ăяo���i���Ə��̎擾�j
		ISystemServise servise = getSystemServise(
						IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
		JigyoKanriInfo info = servise.getJigyoKanriInfo(
										container.getUserInfo(),
										dataHokanForm.getJigyoCd(),
										dataHokanForm.getNendo(),
										dataHokanForm.getKaisu());				
		
		//����ID�A���Ɩ��̃Z�b�g
		dataHokanForm.setJigyoId(info.getJigyoId());
		dataHokanForm.setJigyoName(info.getJigyoName());		
		
		//-----�t�H�[�������N�G�X�g�ɃZ�b�g
		updateFormBean(mapping, request, dataHokanForm);	
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
