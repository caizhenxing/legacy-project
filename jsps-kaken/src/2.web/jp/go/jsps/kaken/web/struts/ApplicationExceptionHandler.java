/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/17
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.struts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.IConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * アプリケーションエラーハンドラクラス。
 * 
 * ID RCSfile="$RCSfile: ApplicationExceptionHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class ApplicationExceptionHandler extends ExceptionHandler {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ログ
	 */
	protected static Log log = LogFactory.getLog(BaseForm.class);

	//---------------------------------------------------------------------
	// Override
	//---------------------------------------------------------------------

	/* (非 Javadoc)
	 * @see org.apache.struts.action.ExceptionHandler#execute(java.lang.Exception, org.apache.struts.config.ExceptionConfig, org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward execute(
		Exception exception,
		ExceptionConfig config,
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response)
		throws ServletException {

		ActionForward forward =
			super.execute(exception, config, mapping, form, request, response);

		//入力画面がある場合は入力画面へ
		if(mapping.getInput() != null){
			forward = mapping.getInputForward();
		//エラー画面がある場合はエラー画面へ
		}else if(mapping.findForward(IConstants.FAILURE_KEY) != null){
			forward = mapping.findForward(IConstants.FAILURE_KEY);
		}
		
		//ログファイル書込み
		log.info("アプリケーション例外が発生しました。", exception);
		
		//発生した例外の取得
		ApplicationException ex = (ApplicationException) exception;
		
		//エラー情報がセットされているとき。
		if(ex.isErrorInfo()){
			ActionError error =	new ActionError(ex.getErrorCode(), ex.getErrorArgs());
			//エラー情報の保存		
			storeException(request, error, config.getScope());
		}
		
		return forward;
	}

	/**
	 * リクエストにエラー情報を保存する。
	 * @param request		リクエスト情報
	 * @param error			エラー情報
	 * @param forward		アクションフォワード情報
	 * @param scope			エラーを保存するスコープ名
	 */
	protected void storeException(
		HttpServletRequest request,
		ActionError error,
		String scope) {
		ActionErrors errors = new ActionErrors();
		errors.add(ActionErrors.GLOBAL_ERROR, error);
		if ("request".equals(scope)) {
			request.setAttribute(Globals.ERROR_KEY, errors);
		} else {
			request.getSession().setAttribute(Globals.ERROR_KEY, errors);
		}
	}
}
