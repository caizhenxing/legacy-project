/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/26
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.logon;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * ログオンのチェックを行うアクションクラス。
 * 
 * ID RCSfile="$RCSfile: LogonCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:34 $"
 */
public class LogonCheckAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログクラス。 */
	private static final Log log = LogFactory.getLog(LogonCheckAction.class);

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.web.common.UserContainer)
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

		//-----フォームの取得
		LogonForm logonForm = (LogonForm) form;

		//------キャンセル時の処理
		if (isCancelled(request)) {
			form.reset(mapping, request);
			return forwardCancel(mapping);
		}

		//-----ログオンユーザ情報
		UserInfo userInfo = null;
		try {
			//-----サーバサービスを取得する。
			ISystemServise servise = getSystemServise(mapping.getParameter());

			//-----ログオン認証を行う。
			userInfo = servise.authenticate(logonForm.getUserid(),logonForm.getPassword());

			//セッションクリア。
			HttpSession session = request.getSession(false);
			if (session != null) {
				session.invalidate();
			}

			//-----セッションにユーザ情報を登録する。
			getUserContainer(request).setUserInfo(userInfo);
			
			//-----ロードバランサー制御用のCookieをセットする。
			addCookie4LB(request, response);

		} catch (InvalidLogonException e) {
			//ログオンエラーの表示
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError(e.getErrorCode(),e.getErrorArgs()));
			saveErrors(request, errors);
			return forwardInput(mapping);
		}

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		//ユーザーが「審査員」でメールフラグ未入力でメールアドレスが未登録の場合は、メールアドレス登録画面に遷移する
		if(userInfo.getRole().equals(UserRole.SHINSAIN) 
		&& StringUtil.isBlank(userInfo.getShinsainInfo().getMailFlg()) 
		&& StringUtil.isBlank(userInfo.getShinsainInfo().getSofuZipemail())){
			return mapping.findForward("mail");
		}else{
			return forwardSuccess(mapping);
		}
	}
	
	
	
	/**
	 * Cookieの設定
	 */
	private void addCookie4LB(HttpServletRequest request, HttpServletResponse response){
		
		boolean flgCookie   = false;
		String   cookieName  = SystemUtil.LB_COOKIE_NAME;
		String   cookieValue = SystemUtil.LB_COOKIE_VALUE;
		
		//システムプロパティがセットされていない場合は処理しない
		if(cookieName == null || cookieValue == null){
			if(log.isDebugEnabled()){
				log.debug("Java System Properties for LB is not set. -> Cookie was not set.");
			}
			return;
		}
		
		//ブラウザから受信したCookieを取得
		Cookie[] ck = request.getCookies();
		if (ck != null) {
			for (int i = 0; i < ck.length; i++) {
				if (ck[i].getName().equals(cookieName)) {
					if(log.isDebugEnabled()){
						log.debug("Cookie for LB is already set. " + ck[i].getName() + "=" + ck[i].getValue());
					}
					flgCookie = true;
				}
			}
		}
		
		//Cookieが既にセットされている場合は処理しない
		if (!flgCookie) {
			Cookie cookie = new Cookie(cookieName, cookieValue);
			cookie.setPath("/");
			response.addCookie(cookie);
			if(log.isDebugEnabled()){
				log.debug("Cookie for LB was set. " + cookieName + "=" + cookieValue);
			}
		}
		       	
	}


}
