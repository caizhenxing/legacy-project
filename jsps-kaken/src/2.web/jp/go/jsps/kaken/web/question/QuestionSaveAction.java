/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * アンケート入力情報値オブジェクトを更新する。
 * フォーム情報、更新情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: QuestionSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:20 $"
 */
public class QuestionSaveAction extends BaseAction {

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
		
		//------セッションより更新情報の取得
		QuestionInfo addInfo = container.getQuestionInfo();
		
		//2005.11.02 iso IP情報を追加
		addInfo.setIp(request.getRemoteAddr());

		//DB登録
		ISystemServise service = getSystemServise(
						IServiceName.QUESTION_MAINTENANCE_SERVICE);
			service.insert(container.getUserInfo(), addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("アンケート入力情報　登録情報 '"+ addInfo);
		}
		
		//2005.11.04 iso セッション情報の完全クリアに変更 
//		//-----セッションのアンケート入力情報をリセット
//		container.setQuestionInfo(null);
		//セッションクリア。
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		//-----フォームを削除
		removeFormBean(mapping, request);

		//------トークンの削除	
		resetToken(request);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
