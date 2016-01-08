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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.gyomu.shinseiKanri.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;


/**
 * 審査結果参照画面表示前アクションクラス。
 * 審査結果参照画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekkaReferDetailAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class ShinsaKekkaReferDetailAction extends BaseAction {

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
		
		//------検索対象審査結果情報の取得
		ShinsaKekkaReferForm selectForm = (ShinsaKekkaReferForm)form;
		
		//------キー情報
		ShinsaKekkaPk pkInfo  = new ShinsaKekkaPk();
		pkInfo.setSystemNo(selectForm.getSystemNo());		//システム受付番号
		pkInfo.setShinsainNo(selectForm.getShinsainNo());	//審査員番号
		pkInfo.setJigyoKubun(selectForm.getJigyoKubun());	//事業区分	
			
		//------キー情報を元に更新データ取得	
		ShinsaKekkaInputInfo selectInfo = 
					getSystemServise(IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).select1stShinsaKekka(
								container.getUserInfo(),
								pkInfo);

		//表示対象情報をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,selectInfo);
		
		//------フォーム情報の削除
		removeFormBean(mapping,request);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
