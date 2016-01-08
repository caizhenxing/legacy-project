/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/4/5
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.BukyokuSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 部局担当者情報検索アクションクラス。
 * 部局担当者情報一覧画面を表示する。
 * 
 */
public class SearchListAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-------▼ VOにデータをセットする。
		BukyokuSearchInfo searchInfo = new BukyokuSearchInfo();
		searchInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		Page result = null;
		try{		
		//検索実行
			result =
				getSystemServise(
					IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).searchBukyokuList(
					container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//追加 ここまで------------------------------------------------------------	

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//フォーム情報の初期化
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}
}
