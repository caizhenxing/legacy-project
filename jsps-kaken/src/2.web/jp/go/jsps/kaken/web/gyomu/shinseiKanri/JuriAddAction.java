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

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �󗝓o�^�A�N�V�����N���X�B
 * �󗝓o�^��ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: JuriAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class JuriAddAction extends BaseAction {

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

		//���������̎擾
		JuriAddForm addForm = (JuriAddForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiDataPk searchPk = new ShinseiDataPk(addForm.getSystemNo());		
					
		//�������s
		SimpleShinseiDataInfo  selectInfo =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).selectSimpleShinseiDataInfo(
				container.getUserInfo(),
				searchPk);
	
		
		//-----�t�H�[���Ɏ󗝓o�^�����Z�b�g����B
        addForm.setJigyoCd(selectInfo.getJigyoId().substring(2,7));
		addForm.setJuriBiko(selectInfo.getJuriBiko());		//���l
		addForm.setJuriKekka(selectInfo.getJuriKekka());	//�󗝌���
		addForm.setJuriSeiriNo(selectInfo.getSeiriNo());	//�󗝐����ԍ�

		//------�v���_�E���f�[�^�Z�b�g
		//�󗝏��
		addForm.setJuriKekkaList(LabelValueManager.getJuriKekkaList());
	
		//-----�Z�b�V�����ɐ\���f�[�^���i�󗝓o�^���j���Z�b�g����B
		container.setSimpleShinseiDataInfo(selectInfo);		
		
		//------�g�[�N���̕ۑ�
		saveToken(request);
		
		//------�V�K�o�^�t�H�[���ɃZ�b�g����B
		updateFormBean(mapping, request, addForm);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}