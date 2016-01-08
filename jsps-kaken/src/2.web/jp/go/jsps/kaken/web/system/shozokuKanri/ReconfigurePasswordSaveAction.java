/*
 * 作成日: 2005/04/21
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * パスワード再設定情報値オブジェクトを登録する。
 * フォーム情報、パスワード再設定情報をクリアする。
 * 
 */
public class ReconfigurePasswordSaveAction extends BaseAction {

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

		//------セッションより情報の取得
		ShozokuInfo reconfigurePasswordInfo = container.getShozokuInfo();

		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.SHOZOKU_MAINTENANCE_SERVICE);
		ShozokuInfo result = servise.reconfigurePassword(container.getUserInfo(),reconfigurePasswordInfo);
		
		if(log.isDebugEnabled()){
			log.debug("所属機関担当者情報　パスワード再設定情報 '"+ request);
		}

		//登録結果をリクエスト属性にセット→登録したパスワード情報等を表示するため。
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