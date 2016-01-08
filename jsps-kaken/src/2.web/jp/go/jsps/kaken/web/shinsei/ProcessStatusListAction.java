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
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書処理状況一覧アクションクラス。
 * 処理状況一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ProcessStatusListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class ProcessStatusListAction extends BaseAction {

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

		//申請者情報
		UserInfo userInfo = container.getUserInfo();
		ShinseishaInfo shinseishaInfo = userInfo.getShinseishaInfo();
		if(shinseishaInfo == null){
			throw new ApplicationException(
				"申請者情報を取得できませんでした。",
				new ErrorInfo("errors.application"));
		}
		
		//申請者IDを検索条件にセットする
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		searchInfo.setShinseishaId(shinseishaInfo.getShinseishaId());
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);	//システム受付番号の昇順
		
		//ページ制御は行わない
		searchInfo.setPageSize(0);
		searchInfo.setMaxSize(0);
		searchInfo.setStartPosition(0);
				
		Page page = null;
		try{
			//サーバサービスの呼び出し（処理状況一覧ページ情報取得）
			ISystemServise servise = getSystemServise(
							IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			page = servise.searchApplication(userInfo, searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			page = Page.EMPTY_PAGE;
		}

		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO, page);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}

}
