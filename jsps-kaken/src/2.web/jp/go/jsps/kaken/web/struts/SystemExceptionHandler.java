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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

/**
 * システムエラー処理ハンドラ。
 * 
 * ID RCSfile="$RCSfile: SystemExceptionHandler.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:28 $"
 */
public class SystemExceptionHandler extends ExceptionHandler {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------
	/** 
	 * ログ
	 */
	protected static Log log = LogFactory.getLog(SystemExceptionHandler.class);

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

        log.error("システム例外が発生しました。",exception);
		
		return forward;
	}
}
