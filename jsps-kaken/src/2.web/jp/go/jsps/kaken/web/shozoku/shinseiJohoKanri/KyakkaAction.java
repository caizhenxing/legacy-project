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
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

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
 * 申請情報却下前アクションクラス。
 * 却下対象申請情報を取得。セッションに登録する。 
 * 却下確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: KyakkaAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:39 $"
 */
public class KyakkaAction extends BaseAction {

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

		//## 削除情報プロパティ(セッションに保持)　$!userContainer.shinseishaInfo.プロパティ名

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------却下対象申請情報システム番号の取得
		ShinseiDataForm shoninForm = (ShinseiDataForm)form;
		
		//------却下対象申請システム番号の取得
		ShinseiDataPk pkInfo = new ShinseiDataPk();
		//------キー情報
		String systemNo = shoninForm.getSystemNo();
		pkInfo.setSystemNo(systemNo);

		//------キー情報を元に申請データ取得	
		SimpleShinseiDataInfo shinseiInfo = getSystemServise(IServiceName.SHINSEI_MAINTENANCE_SERVICE).selectSimpleShinseiDataInfo(container.getUserInfo(),pkInfo);
		
		//------却下対象情報をリクエスト属性にセット
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
