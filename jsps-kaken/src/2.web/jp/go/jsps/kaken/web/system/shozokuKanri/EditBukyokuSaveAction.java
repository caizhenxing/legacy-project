/*
 * 作成日: 2005/04/21
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.model.vo.ShozokuSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 部局担当者正処理用アクションクラス。
 * 修正で確認画面でOKボタンを押し、DB登録処理を行う際に呼び出される。
 *
 */
public class EditBukyokuSaveAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}	
		
		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------セッションより新規登録情報の取得
		BukyokutantoInfo bukyokuInfo = container.getBukyokutantoInfo();
		
		
		//所属機関担当者連絡先取得
		ShozokuSearchInfo searchInfo = new ShozokuSearchInfo();
		searchInfo.setShozokuCd(bukyokuInfo.getShozokuCd());
		try {
			Page resultTnto =
				getSystemServise(
					IServiceName.SHOZOKU_MAINTENANCE_SERVICE).search(
					container.getUserInfo(),
					searchInfo);
			//所属機関担当者情報を表示するためリクエスト情報にセット
			request.setAttribute(IConstants.RESULT_TANTO,resultTnto);
		} catch(ApplicationException e) {
			//該当担当者なし→通常ありえないので空表示
		}
		
		//部局担当者情報取得
		BukyokutantoInfo result = getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).setBukyokuData(
				container.getUserInfo(),
				bukyokuInfo);
		
		//	登録結果をリクエスト属性にセット→登録したパスワード情報等を表示するため。
		request.setAttribute(IConstants.RESULT_INFO,result);

		//------トークンの削除	
		resetToken(request);
		//------セッションより新規登録情報の削除
		container.setShinseishaInfo(null);
		//------フォーム情報の削除
		removeFormBean(mapping,request);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}

}
