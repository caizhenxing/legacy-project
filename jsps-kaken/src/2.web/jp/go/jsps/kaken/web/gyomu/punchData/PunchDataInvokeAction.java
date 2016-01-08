/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（JSPS)
 *    Source name : PunchDataInvokeAction.java
 *    Description : パンチデータ処理中画面を表示するアクション
 *
 *    Author      : Admin
 *    Date        : 2005/04/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/28    V1.0                       新規作成
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * パンチデータ処理中画面を表示するアクション
 *
 */
public class PunchDataInvokeAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
		//宣言
		PunchDataForm punchDataForm = (PunchDataForm)form;

		//if( punchDataForm.getPunchShubetu() != null && punchDataForm.getPunchShubetu().equals("7")){
		//	return forwardCancel(mapping);
		//}

		return forwardSuccess(mapping);
	}

}
