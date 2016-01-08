/*======================================================================
 *    SYSTEM      : ���{�w�p�U����d�q�\���V�X�e���i�Ȋw������⏕���j
 *    Source name : ConfirmApplicationAction.java
 *    Description : �\�����m�F�����A�N�V�����N���X
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/09    v1.0        takano         �V�K�쐬
 *    2006/06/19    v1.1        DIS.gongXB     �V�K�쐬
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �\�����m�F�����A�N�V�����N���X�B
 * �w��\���f�[�^�̏����󋵃X�e�[�^�X���u�����@�֎�t���v�ɍX�V����B
 * ID RCSfile="$RCSfile: ConfirmApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class ConfirmApplicationAction extends BaseAction {

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

		//-----�ȈՐ\�������̓t�H�[���̎擾
		SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
		
		//�T�[�o�T�[�r�X�̌Ăяo���i�\�����m�F�����j
		ShinseiDataPk shinseiPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
        try{
            servise.confirmComplete(container.getUserInfo(),shinseiPk);         
        }catch(NoDataFoundException e){
            errors.add("�Y�������񂪑��݂��܂���ł����B",new ActionError("errors.5002"));
        }
        
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