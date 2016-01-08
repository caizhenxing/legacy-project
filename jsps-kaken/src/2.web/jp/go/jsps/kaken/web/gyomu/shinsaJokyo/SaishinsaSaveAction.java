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
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 再審査設定アクションクラス。
 * 
 * ID RCSfile="$RCSfile: SaishinsaSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
 */
public class SaishinsaSaveAction extends BaseAction {
	
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
		
		//------セッションより再審査設定情報の取得
		ShinsaJokyoInfo shinsaJokyoInfo = container.getShinsaJokyoInfo();

		try {
			//実行
			getSystemServise(
				IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE).saishinsa(
				container.getUserInfo(),
				shinsaJokyoInfo);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			return forwardInput(mapping);
		}

		//再申請結果をリクエスト属性にセット→再申請設定した審査状況情報等を表示するため。
		request.setAttribute(IConstants.RESULT_INFO, shinsaJokyoInfo);

		if (log.isDebugEnabled()) {
			log.debug("審査状況情報 再審査設定   '" + shinsaJokyoInfo + "'");
		}

		//------トークンの削除	
		resetToken(request);
		//------セッションより処理対象申請情報の削除
		container.setShinsaJokyoInfo(null);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
		
	}

}
