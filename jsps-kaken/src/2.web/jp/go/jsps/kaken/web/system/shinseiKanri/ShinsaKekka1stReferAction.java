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
package jp.go.jsps.kaken.web.system.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 1���R�����ʎQ�ƃA�N�V�����N���X�B
 * 1���R�����ʎQ�Ɖ�ʂ�\������B
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka1stReferAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class ShinsaKekka1stReferAction extends BaseAction {

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
		SimpleShinseiForm addForm = (SimpleShinseiForm)form;
		
		//-------�� VO�Ƀf�[�^���Z�b�g����B
		ShinseiDataPk searchPk = new ShinseiDataPk(addForm.getSystemNo());		
					
		//�������s
		ShinsaKekkaReferenceInfo selectInfo = null;
		try{
			selectInfo =
				getSystemServise(
					IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).getShinsaKekkaReferenceInfo(
					container.getUserInfo(),
					searchPk);
		}catch(NoDataFoundException e){
			selectInfo = new ShinsaKekkaReferenceInfo();
			selectInfo.setShinsaKekkaInfo(new ShinsaKekkaInfo[0]);
		}
	
		//-----�Z�b�V������1���R�����ʏ����Z�b�g����B
		container.setShinsaKekkaReferenceInfo(selectInfo);

		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
