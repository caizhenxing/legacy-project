/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.warifuri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 審査員情報検索アクションクラス。
 * 審査員連絡先情報画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShinsainInfoAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class ShinsainInfoAction extends BaseAction {

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
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//------表示対象審査員情報の取得
		ShinsainPk pkInfo = new ShinsainPk();
		
		ShinsainSearchForm shinsainSearchForm = (ShinsainSearchForm)form;
		
		//------キー情報
		pkInfo.setShinsainNo(shinsainSearchForm.getShinsainNo4View());	//審査員番号
		pkInfo.setJigyoKubun(shinsainSearchForm.getJigyoKubun());	//事業区分
		
		//------キー情報を元にデータ取得	
		ShinsainInfo result = getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(
																		container.getUserInfo(),
																		pkInfo);
		//------表示対象情報をセッションに登録。
//		container.setShinsainInfo(result);
		
		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO, result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
