/*
 * 作成日: 2005/04/20
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

/**
 * @author yoshikawa_h
 *
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
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
 * 部局担当者情報削除アクションクラス。
 * 
 */
public class DeleteBukyokuSaveAction extends BaseAction {

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
		
		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------セッションより削除情報の取得
		BukyokutantoInfo deleteInfo = container.getBukyokutantoInfo();

		//------削除
		ISystemServise servise = getSystemServise(
							IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE);
		servise.delete(container.getUserInfo(),deleteInfo);

		if (log.isDebugEnabled()) {
			log.debug("部局担当者情報 削除   '" + deleteInfo + "'");
		}

		//------トークンの削除	
		resetToken(request);
		
		//------セッションより処理対象所属機関情報の削除
		container.setBukyokutantoInfo(null);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}

