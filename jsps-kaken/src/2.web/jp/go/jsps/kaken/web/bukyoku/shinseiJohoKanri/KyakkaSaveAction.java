/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 申請情報却下アクションクラス。
 * 却下対象申請情報を更新する。 
 * 
 */
public class KyakkaSaveAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------却下対象申請情報システム番号の取得
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;
		
		//------却下対象申請システム番号の取得
		ShinseiDataPk pkInfo = new ShinseiDataPk();
		//------キー情報
		String systemNo = shoninForm.getSystemNo();
		pkInfo.setSystemNo(systemNo);

		//------キー情報を元に申請情報を更新	
		getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE).rejectApplication(container.getUserInfo(),pkInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}
