/*
 * 作成日: 2005/03/28
 *
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shozoku.shinseishaKanri.ShinseishaListForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 未申請者情報一括登録アクションクラス。
 * 未申請者情報を一括登録する。
 * 
 * @author yoshikawa_h
 *
 */
public class MultipleAddSaveAction extends BaseAction {
	
	/* 
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//2005/04/20 削除 ここから-------------------------------------------------------
		//理由 MultipleAddCheckActionでトークンのチェックを行うため
		//-----ActionErrorsの宣言（定型処理）-----
		//ActionErrors errors = new ActionErrors();
		
		//------キャンセル時		
		//if (isCancelled(request)) {
		//	return forwardCancel(mapping);
		//}
		
		//-----取得したトークンが無効であるとき
		//if (!isTokenValid(request)) {
		//	errors.add(ActionErrors.GLOBAL_ERROR,
		//			   new ActionError("error.transaction.token"));
		//	saveErrors(request, errors);
		//	return forwardTokenError(mapping);
		//}
		//削除 ここまで-------------------------------------------------------------------

		//------キー情報
		String[] kenkyuNo = ((ShinseishaListForm)form).getKenkyuNo();
		
		ISystemServise servise = getSystemServise(
				IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		
		servise.registShinseishaFromKenkyusha(container.getUserInfo(), kenkyuNo);
		
		//------トークンの削除	
		resetToken(request);
		//------フォーム情報の削除
		removeFormBean(mapping,request);
		
		//2005/04/29 追加 ----------------------------------------------ここから
		//理由 登録した研究者NoをUserContainerへ直接保持するように修正
		container.setKenkyuNo(kenkyuNo);
		//2005/04/29 追加 ----------------------------------------------ここまで

		

		//2005/04/20 削除 ここから-------------------------------------------------------
		//理由 不要な画面遷移のため
		//-----画面遷移（定型処理）-----
		//if (!errors.isEmpty()) {
		//	saveErrors(request, errors);
		//	return forwardFailure(mapping);
		//}
		//削除 ここまで------------------------------------------------------------------
		return forwardSuccess(mapping);
	}
	
}
