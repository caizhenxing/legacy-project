/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : tou
 *    Date        : 2006/02/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 応募情報入力（説明）画面振り分けアクションクラス。
 */
public class DispatchApplicationShokuAction extends BaseAction {

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
		
        //申請資格フラグのチェック
		String ouboShikaku = shinseiForm.getShinseiDataInfo().getOuboShikaku();
		String jigyoCd = shinseiForm.getShinseiDataInfo().getJigyoCd();
		if (jigyoCd.equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_A)
				|| jigyoCd.equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_B)
				|| jigyoCd.equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C)
				|| jigyoCd.equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_A)
				|| jigyoCd.equals(IJigyoCd.JIGYO_CD_SHOKUSHINHI_WAKATE_B)) {
			if (ouboShikaku == null ||!(ouboShikaku.equals("1") || ouboShikaku.equals("2")
					|| ouboShikaku.equals("3"))) {
				ActionError error = new ActionError("errors.9021");
				errors.add(null,error);
			}
		}
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return mapping.findForward(shinseiForm.getShinseiDataInfo().getJigyoCd());
		}
		
		//事業コードごとに転送先を変更
		return mapping.findForward(shinseiForm.getShinseiDataInfo().getJigyoCd());
	}
}
