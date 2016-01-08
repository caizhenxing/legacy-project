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
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
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
 * 申請者登録情報値オブジェクトを登録する。
 * フォーム情報、登録情報をクリアする。
 * 
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
		ShinseishaInfo addInfo = container.getShinseishaInfo();

		//所属機関担当者連絡先取得
		ShozokuSearchInfo searchInfo = new ShozokuSearchInfo();
		searchInfo.setShozokuCd(addInfo.getShozokuCd());
		try {
			Page resultTnto =
				getSystemServise(
					IServiceName.SHOZOKU_MAINTENANCE_SERVICE).search(
					container.getUserInfo(),
					searchInfo);
			request.setAttribute(IConstants.RESULT_TANTO,resultTnto);
		} catch(ApplicationException e) {
			//該当担当者なし→通常ありえないので空表示
		}

		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEISHA_MAINTENANCE_SERVICE);
		ShinseishaInfo result = servise.insert(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("申請者情報　登録情報 '"+ request);
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
