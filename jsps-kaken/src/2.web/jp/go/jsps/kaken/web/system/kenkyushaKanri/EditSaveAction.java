/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究者更新情報値オブジェクトを登録する。
 * フォーム情報、更新情報をクリアする。
 *  
 */
public class EditSaveAction extends BaseAction {

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
		
		//------セッションより修正登録情報の取得
		KenkyushaInfo editInfo = container.getKenkyushaInfo();

		//------更新
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		service.update(container.getUserInfo(),editInfo);

		if (log.isDebugEnabled()) {
			log.debug("研究者情報 修正登録 '" + editInfo + "'");
		}
		
		//------トークンの削除	
		resetToken(request);
		//------セッションより対象研究者情報の削除
		container.setShinseishaInfo(null);
		//------フォーム情報の削除
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}
}
