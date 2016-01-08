/*
 * Created on 2005/04/06
 *
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 部局担当者削除処理用アクションクラス。
 *
 */
public class DeleteSaveAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
	
		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------セッションより削除情報の取得
		BukyokutantoInfo deleteInfo = container.getBukyokutantoInfo();

		//削除処理
		getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).delete(
			container.getUserInfo(),
			deleteInfo);

		//------トークンの削除	
		resetToken(request);
		//------フォーム情報の削除
		removeFormBean(mapping,request);
		//------セッションより処理対象申請情報の削除
		container.setShinseishaInfo(null);
		
		return forwardSuccess(mapping);
	}

}
