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
package jp.go.jsps.kaken.web.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.web.common.UserContainer;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ��ʑJ�ڂ��s���̏ꍇ�̃A�N�V�����B
 * 
 * ID RCSfile="$RCSfile: TokenErrorAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class TokenErrorAction extends BaseAction {

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
			
//		//-----ActionErrors�̐錾�i��^�����j-----
//		ActionErrors errors = new ActionErrors();

		throw new ApplicationException("�s���ȉ�ʑJ�ڂł��B",new ErrorInfo("errors.token"));

//		//-----��ʑJ�ځi��^�����j-----
//		if (!errors.isEmpty()) {
//			saveErrors(request, errors);
//			return forwardFailure(mapping);
//		}
//		return forwardSuccess(mapping);
	}
}
