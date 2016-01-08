/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.gyomu.shinseiKanri.ShinsaKekkaReferForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �R�����ʎQ�Ɖ�ʐU�蕪���A�N�V�����N���X�B
 * �t�H�[���ɃZ�b�g����Ă��鎖�Ƌ敪�œ]������B
 * 
 * ID RCSfile="$RCSfile: DispatchJigyoKubunAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class DispatchJigyoKubunAction extends BaseAction {

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
		
		//------���Ƌ敪�̎擾
		String jigyoKubun = selectForm.getJigyoKubun();
				
		//-----��ʑJ�ځi��^�����j-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//���Ƌ敪�ɓ]�����ύX
		return mapping.findForward(jigyoKubun);
		
	}
	
	
	
	
}
