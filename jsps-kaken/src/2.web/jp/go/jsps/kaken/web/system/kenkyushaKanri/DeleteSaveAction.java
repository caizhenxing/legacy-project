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
 * 登録されている研究者情報を削除する。
 * フォーム情報、更新情報をクリアする。
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
		KenkyushaInfo deleteInfo = container.getKenkyushaInfo();

		//------削除
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		service.delete(container.getUserInfo(),deleteInfo);

		if (log.isDebugEnabled()) {
			log.debug("研究者情報 削除   '" + deleteInfo + "'");
		}

		//------トークンの削除	
		resetToken(request);
		//------セッションより処理対象研究者情報の削除
		container.setShinseishaInfo(null);
		//------フォーム情報の削除
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}
}
