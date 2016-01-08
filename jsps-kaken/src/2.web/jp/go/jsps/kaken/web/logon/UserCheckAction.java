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

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ModuleConfig;

/**
 * ログオンしないユーザのチェックを行うアクションクラス。
 * 
 * ID RCSfile="$RCSfile: UserCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:34 $"
 */
public class UserCheckAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/** ログクラス。 */
	private static final Log log = LogFactory.getLog(UserCheckAction.class);

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

		//------キャンセル時の処理
		if (isCancelled(request)) {
			form.reset(mapping, request);
			return forwardCancel(mapping);
		}

		//セッションクリア。
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		ModuleConfig moduleConfig = mapping.getModuleConfig();
		UserInfo userInfo = new UserInfo();
		if (moduleConfig.getPrefix() != null) {
			//モジュール名より該当ロール情報の取得
			if(moduleConfig.getPrefix().indexOf("/question/oubosya") == 0) {
				userInfo.setRole(UserRole.QUESTION_SHINSEISHA);
			} else if(moduleConfig.getPrefix().indexOf("/question/kikan") == 0) {
				userInfo.setRole(UserRole.QUESTION_SHOZOKUTANTO);
			} else if(moduleConfig.getPrefix().indexOf("/question/bukyoku") == 0) {
				userInfo.setRole(UserRole.QUESTION_BUKYOKUTANTO);
			} else if(moduleConfig.getPrefix().indexOf("/question/shinsa") == 0) {
				userInfo.setRole(UserRole.QUESTION_SHINSAIN);
			} else  {
				log.info("No Role::" + moduleConfig.getPrefix());
			}
			getUserContainer(request).setUserInfo(userInfo);
		} else {
			log.info("No Prefix::" + moduleConfig);
		}

		//-----セッションにユーザ情報を登録する。
		getUserContainer(request).setUserInfo(userInfo);
		
		//-----ロードバランサー制御用のCookieをセットする。
		addCookie4LB(request, response);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		return forwardSuccess(mapping);
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
