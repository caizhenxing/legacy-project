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
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 申請情報承認前アクションクラス。
 * 承認対象申請情報を取得。セッションに登録する。 
 * 承認確認画面を表示する。
 * 
 */
public class ShoninAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------承認対象申請情報システム番号の取得
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;
		
		//------承認対象申請システム番号の取得
		ShinseiDataPk pkInfo = new ShinseiDataPk();
		//------キー情報
		String systemNo = shoninForm.getSystemNo();
		pkInfo.setSystemNo(systemNo);

		//------キー情報を元に申請データ取得	
		SimpleShinseiDataInfo shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE).selectSimpleShinseiDataInfo(container.getUserInfo(),pkInfo);
		
		//------承認対象情報をリクエスト属性にセット
		container.setSimpleShinseiDataInfo(shinseiInfo);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
//		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
