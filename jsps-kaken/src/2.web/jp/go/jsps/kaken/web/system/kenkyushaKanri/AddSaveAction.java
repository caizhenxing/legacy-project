/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究者登録情報値オブジェクトを登録する。
 * フォーム情報、登録情報をクリアする。
 */
public class AddSaveAction extends BaseAction {

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
		KenkyushaInfo addInfo = container.getKenkyushaInfo();

		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		servise.insert(container.getUserInfo(), addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("研究者情報　登録情報 '"+ request);
		}

		//登録結果をリクエスト属性にセット→登録したパスワード情報等を表示するため。
		request.setAttribute(IConstants.RESULT_INFO, addInfo);

		//------トークンの削除	
		resetToken(request);
		//------セッションより新規登録情報の削除
		container.setKenkyushaInfo(null);
		//------フォーム情報の削除
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}

}
