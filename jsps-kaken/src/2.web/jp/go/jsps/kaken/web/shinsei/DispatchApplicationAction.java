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
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書入力画面振り分けアクションクラス。
 * フォームにセットされている様式種別（事業コード頭２ケタ）で転送する。
 * 
 * ID RCSfile="$RCSfile: DispatchApplicationAction.java,v $"
 * Revision="$Revision: 1.5 $"
 * Date="$Date: 2007/07/20 09:39:32 $"
 */
public class DispatchApplicationAction extends BaseAction {

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
		
		//-----申請書入力フォームの取得
		ShinseiForm shinseiForm = (ShinseiForm)form;
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//様式種別（事業コードの一部）ごとに転送先を変更
		//return mapping.findForward(shinseiForm.getYoshikiShubetu());
		
		//事業コードごとに転送先を変更
		return mapping.findForward(shinseiForm.getShinseiDataInfo().getJigyoCd());
	}
}
