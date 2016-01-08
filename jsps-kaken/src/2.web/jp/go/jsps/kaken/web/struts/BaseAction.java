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
package jp.go.jsps.kaken.web.struts;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.log.*;
import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;

import org.apache.commons.logging.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.*;
import org.apache.struts.util.*;

/**
 * アクションの基本となるクラス。
 * 注意）performメソッドをサブクラスで使用しないでください。
 * 
 * ID RCSfile="$RCSfile: BaseAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public abstract class BaseAction extends Action {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	/**
	 * ログクラス。 
	 */
	protected static final Log log = LogFactory.getLog(BaseAction.class);

	//---------------------------------------------------------------------
	// Public Methods
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see org.apache.struts.action.Action#execute(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public final ActionForward execute(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		//フォーム情報の確認
		if (log.isDebugEnabled()) {
			if (form != null) {
				log.debug(
					"ActionForm " + form.getClass().getName() + "\n" + form);
			}
		}

		//		//デバック用メッセージの表示
		//		if (log.isDebugEnabled()) {
		//			HttpSession session = request.getSession();
		//			Enumeration se = session.getAttributeNames();
		//			StringBuffer buffer = new StringBuffer();
		//			buffer.append("\nsession Attribute\n");
		//			while (se.hasMoreElements()) {
		//				String attributeName = (String) se.nextElement();
		//				buffer.append(
		//					"\tName '"
		//						+ attributeName
		//						+ "' Value '"
		//						+ session.getAttribute(attributeName)
		//						+ "'\n");
		//			}
		//
		//			Enumeration re = request.getAttributeNames();
		//			buffer.append("request Attribute\n");
		//			while (re.hasMoreElements()) {
		//				String attributeName = (String) re.nextElement();
		//				buffer.append(
		//					"\tName '"
		//						+ attributeName
		//						+ "' Value '"
		//						+ request.getAttribute(attributeName)
		//						+ "'\n");
		//			}
		//
		//			Enumeration pe = request.getParameterNames();
		//			buffer.append("request parameter\n");
		//			while (pe.hasMoreElements()) {
		//				String parameterName = (String) pe.nextElement();
		//				buffer.append(
		//					"\tName '"
		//						+ parameterName
		//						+ "' Value '"
		//						+ Arrays.asList(
		//							request.getParameterValues(parameterName))
		//						+ "'\n");
		//			}
		//			log.debug(buffer.toString());
		//		}
		
		
		if (mapping instanceof jp.go.jsps.kaken.web.struts.ActionMapping) {
			jp.go.jsps.kaken.web.struts.ActionMapping actionMapping = 
					(jp.go.jsps.kaken.web.struts.ActionMapping) mapping;
			//----------------------------------
			//メモリチェックを行うか判断し、空きメモリチェックを行なう。
			//----------------------------------
			if (actionMapping.isMemoryCheck()) {
				int usedMemRate = PerformanceLogWriter.checkUsedMemRate();
				int maxMemRate  = ApplicationSettings.getInt(ISettingKeys.MAX_MEMORY_USED_RATE);
				if(usedMemRate >= maxMemRate){
					return forwardMemoryError(mapping);	//最大値を超えていた場合はSorryページへ遷移する
				}
			}
		}
		
		
		//ユーザ情報の取得
		UserContainer container = getUserContainer(request);
		//セッション情報の取得
		HttpSession session = request.getSession();
		
		synchronized (session.getId().intern()) {
			//前処理
			doPreProcessing(mapping, form, request, response);

			//★★パフォーマンスログ
			PerformanceLogWriter pw = null;
			if (log.isDebugEnabled()) {
				pw = new PerformanceLogWriter();
			}

			//メイン処理
			ActionForward forward =
				doMainProcessing(mapping, form, request, response, container);
			
			//★★パフォーマンスログ
			if(pw != null){
				if (mapping instanceof jp.go.jsps.kaken.web.struts.ActionMapping) {
					String s = ((jp.go.jsps.kaken.web.struts.ActionMapping)mapping).getDescription();
					pw.out("処理名："+s);
				}else{
					pw.out("処理名は不明です。");	
				}
			}

			//後処理
			doPostProcessing(mapping, form, request, response);
			
			return forward;
		}
	}

	/**
	 *Actionクラスの主要な機能を実装する。
	 * 戻り値として、次の遷移先をActionForward型で返する。
	 * 
	 * @param mapping ActonMappingが渡されます。
	 * @param form ActionFormが渡されます。
	 * @param req HttpServletRequestが渡されます。
	 * @param res HttpServletResponseが渡されます。
	 *
	 * @exception Exception Exception発生時、Throwされます。
	 */
	public abstract ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException;

	/**
	 * 事後処理を実装する。この実装は省略可能。
	 * 
	 * @param mapping ActonMappingが渡されます。
	 * @param form ActionFormが渡されます。
	 * @param req HttpServletRequestが渡されます。
	 * @param res HttpServletResponseが渡されます。
	 *
	 * @exception Exception Exception発生時、Throwされます。
	 */
	public void doPostProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {
		
		//ブラウザにキャッシュを保存させない。
		response.setHeader("Expires", new DateUtil(new Date()).getHTTPDate());
		response.setHeader("Pragma","no-cache");
		response.setHeader("Cache-Control","no-cache");
		
		return;
	}

	/**
	 * 事前処理を実装する。この実装は省略可能。
	 * 
	 * @param mapping ActonMappingが渡されます。
	 * @param form ActionFormが渡されます。
	 * @param req HttpServletRequestが渡されます。
	 * @param res HttpServletResponseが渡されます。
	 *
	 * @exception Exception Exception発生時、Throwされます。
	 */
	public void doPreProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws Exception {

		return;
	}

	/**
	 * 成功時のページ遷移を行う。
	 * @param mapping	ActionMapping
	 * @return	ActionForward ページ遷移先情報
	 */
	protected ActionForward forwardSuccess(ActionMapping mapping) {
		return mapping.findForward(IConstants.SUCCESS_KEY);
	}

	/**
	 * 失敗時のページ遷移を行う。
	 * @param mapping	ActionMapping
	 * @return	ActionForward ページ遷移先情報
	 */
	protected ActionForward forwardFailure(ActionMapping mapping) {
		return mapping.findForward(IConstants.FAILURE_KEY);
	}

	/**
	 * 失敗時のページ遷移を行う。
	 * @param mapping	ActionMapping
	 * @return	ActionForward ページ遷移先情報
	 */
	protected ActionForward forwardTokenError(ActionMapping mapping) {
		return mapping.findForward(IConstants.TOKEN_ERROR_KEY);
	}

	/**
	 * CANCEL時のページ遷移を行う。
	 * @param mapping	ActionMapping
	 * @return	ActionForward ページ遷移先情報
	 */
	protected ActionForward forwardCancel(ActionMapping mapping) {
		return mapping.findForward(IConstants.CANCEL_KEY);
	}

	/**
	 * 入力ページへ遷移をする。
	 * @param mapping	ActionMapping
	 * @return	ActionForward ページ遷移先情報
	 */
	protected ActionForward forwardInput(ActionMapping mapping) {
		return new ActionForward(mapping.getInput());
	}

	/**
	 * メモリエラーページ（Sorryページ）へ遷移をする。
	 * ※モジュール相対ではなく、コンテキスト相対となる。
	 * @param mapping	ActionMapping
	 * @return	ActionForward ページ遷移先情報
	 */
	protected ActionForward forwardMemoryError(ActionMapping mapping) {
		return new ActionForward("memSorryForward",
								  ApplicationSettings.getString(ISettingKeys.MAX_MEMORY_ERROR_PAGE),
								  false,
								  true);
	}

	/**
	 * 定義されたアクションフォームを取得する。
	 * @param mapping	マッピング情報
	 * @param request	リクエスト情報
	 * @return			アクションフォーム
	 */
	protected ActionForm getFormBean(
		ActionMapping mapping,
		HttpServletRequest request) {
		ActionForm actionForm = null;
		// Retrieve the form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				actionForm =
					(ActionForm) request.getAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();
				actionForm =
					(ActionForm) session.getAttribute(mapping.getAttribute());
			}
		}
		return actionForm;
	}

	/**
	 * 定義されたアクションフォームを更新する。
	 * @param mapping	マッピング情報
	 * @param request	リクエスト情報
	 * @param form		アクションフォーム
	 */
	protected void updateFormBean(
		ActionMapping mapping,
		HttpServletRequest request,
		ActionForm form) {
		// Update the form bean
		if (mapping.getAttribute() != null) {
			if ("session".equals(mapping.getScope())) {
				HttpSession session = request.getSession();
				session.setAttribute(mapping.getAttribute(), form);
			} else {
				request.setAttribute(mapping.getAttribute(), form);
			}
		}
	}
	
	/**
	 * 定義されたアクションフォームを更新する。
	 * @param mapping	マッピング情報
	 * @param session	セッション情報
	 * @param form		アクションフォーム
	 */
	protected void updateFormBean(
			ActionMapping mapping,
			HttpSession session,
			ActionForm form) {
			// Update the form bean
			if (mapping.getAttribute() != null) {
				session.setAttribute(mapping.getAttribute(), form);
			}
		}
	
	
	/**
	 * パス情報でしていされたアクションフォームを更新する。
	 * @param path			パス情報
	 * @param request		リクエスト情報
	 * @param form			アクションフォーム
	 */
	protected void updateFormBean(
		String path,
		HttpServletRequest request,
		ActionForm form) {
		this.updateFormBean(findMapping(request, path), request, form);
	}

	/**
	 * パス名に一致するマッピング情報を取得する。
	 * @param request	リクエスト情報
	 * @param path		マッピング情報を取得するためのパス
	 * @return			アクションマッピング
	 */
	protected ActionMapping findMapping(
		HttpServletRequest request,
		String path) {
		ModuleConfig moduleConfig =
			RequestUtils.getModuleConfig(
				request,
				getServlet().getServletContext());
		return (ActionMapping) moduleConfig.findActionConfig(path);
	}

	/**
	 * 定義されたアクションフォームを削除する。
	 * @param mapping		マッピング情報
	 * @param request		リクエスト情報
	 */
	protected void removeFormBean(
		ActionMapping mapping,
		HttpServletRequest request) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.removeAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();
				session.removeAttribute(mapping.getAttribute());
			}
		}
	}

	/**
	 * システムサービスを取得する。
	 * @param serviceName	サービス名称
	 * @return				システムサービス実装クラス。
	 */
	protected final ISystemServise getSystemServise(String serviceName) {
		return SystemServiceFactory.getSystemService(serviceName);
	}

	/**
	 * ユーザ情報コンテナを取得する。
	 * @param request	リクエスト情報
	 * @return	ユーザ情報保持用コンテナ
	 */
	protected UserContainer getUserContainer(HttpServletRequest request) {

		UserContainer userContainer =
			(UserContainer) request.getSession().getAttribute(
				IConstants.USER_CONTAINER_KEY);
		if (userContainer == null) {
			userContainer = new UserContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute(IConstants.USER_CONTAINER_KEY, userContainer);
		}
		return userContainer;
	}

	/**
	 * ユーザ情報を取得する。
	 * @param request	リクエスト情報
	 * @return	ログインユーザ情報
	 */
	protected UserInfo getUserInfo(HttpServletRequest request) {
		UserContainer container = getUserContainer(request);
		return container.getUserInfo();
	}
	/**
	 * サーバ上での処理結果をリクエスト情報に保存する。
	 * @param request	リクエスト情報
	 * @param errors	アクションエラー情報
	 * @param e			サーバ検証例外オブジェクト
	 */
	protected void saveServerErrors(
		HttpServletRequest request,
		ActionErrors errors,
		ValidationException e) {
		for (Iterator iter = e.getErrors().iterator(); iter.hasNext();) {
			ErrorInfo element = (ErrorInfo) iter.next();
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError(
					element.getErrorCode(),
					element.getErrorArgs()));
		}
		saveErrors(request, errors);
	}

}
