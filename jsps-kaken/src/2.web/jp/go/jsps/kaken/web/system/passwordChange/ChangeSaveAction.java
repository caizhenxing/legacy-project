/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/03
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.passwordChange;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * パスワードの変更を行うアクションクラス。
 * 
 * ID RCSfile="$RCSfile: ChangeSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class ChangeSaveAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログクラス。 */
	private static final Log log = LogFactory.getLog(ChangeSaveAction.class);

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

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

			//検索条件の取得
			PasswordChangeForm changeForm = (PasswordChangeForm)form;

			try {
				//既存のパスワードのチェックとパスワード更新
				ISystemServise servise =  getSystemServise(
						IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE);
				boolean result = servise.changePassword(
						container.getUserInfo(),container.getUserInfo().getGyomutantoInfo(),changeForm.getPassword(),changeForm.getNewPassword1());
			} catch (ValidationException e) {
				//サーバーエラーを保存。
				saveServerErrors(request, errors, e);
				//フォームをリセット
				changeForm.setPassword("");
				//---入力内容に不備があるので再入力
				return forwardInput(mapping);
			}
	
			if(log.isDebugEnabled()){
				log.debug("パスワード変更情報'"+ request);
			}
		
			//------トークンの削除	
			resetToken(request);

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
