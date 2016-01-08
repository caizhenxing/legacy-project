/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : IkkatsuJuriCheckTokuteiAction.java
 *    Description : 一括受理処理中(特定)アクション。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 一括受理処理中(特定)アクション
 * 
 * @author masuo_t
 */
public class IkkatsuJuriCheckTokuteiAction extends BaseAction {
		
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
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		//------トークンの削除	
		resetToken(request);
		
		return forwardSuccess(mapping);
	}
}