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
package jp.go.jsps.kaken.web.system.dataKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ���ƃf�[�^�폜�A�N�V�����B
 * 
 * ID RCSfile="$RCSfile: DeleteDataAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:02 $"
 */
public class DeleteDataAction extends BaseAction {

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

		//�f�[�^�Ǘ��t�H�[��
		DataKanriForm dataKanriForm = (DataKanriForm)form;
		
		//�T�[�r�X�Ăяo���i���ƃf�[�^�폜�j
		JigyoKanriPk jigyoPk = new JigyoKanriPk(dataKanriForm.getJigyoId());
		JigyoKanriInfo info = getSystemServise(
								IServiceName.SYSTEM_MAINTENANCE_SERVICE).deleteJigyo(
									container.getUserInfo(),
									jigyoPk);
		
		//�폜�������{�������Ə��̏����Z�b�g����
		dataKanriForm.setJigyoName(info.getJigyoName());
		dataKanriForm.setNendo(info.getNendo());
		dataKanriForm.setKaisu(info.getKaisu());
		
		//-----�t�H�[�������N�G�X�g�ɃZ�b�g
		updateFormBean(mapping, request, dataKanriForm);	
		
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}

}
