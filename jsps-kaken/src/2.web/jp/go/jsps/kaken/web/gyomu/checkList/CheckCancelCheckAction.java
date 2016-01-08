/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckCancelCheckAction.java
 *    Description : チェックリスト確定解除確認アクション。
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト確定解除確認アクション
 * 
 * @author masuo_t
 */
public class CheckCancelCheckAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
			
		//トークンをセッションに保存する。
		saveToken(request);

		return forwardSuccess(mapping);
	}
}