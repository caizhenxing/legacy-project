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
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書処理状況一覧アクションクラス。
 * 処理状況一覧画面を表示する。
 * 
 */
public class ShoninKyakaListAction extends BaseAction {


	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//所属機関情報
		UserInfo userInfo = container.getUserInfo();
		ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
		if(shozokuInfo == null){
			throw new ApplicationException(
				"所属機関情報を取得できませんでした。",
				new ErrorInfo("errors.application"));
		}
		
		//検索条件をセットする
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());					//所属機関コード
		searchInfo.setJokyoId(new String[]{StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU});	//申請状況ID
		//TODO 現在保留
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);				//システム受付番号の昇順

		//サーバサービスの呼び出し（処理状況一覧ページ情報取得）
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);

		Page page = null;
		try{
			page = servise.searchApplication(userInfo, searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			page = Page.EMPTY_PAGE;
		}
//		Page page = servise.searchApplication(userInfo, searchInfo);

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
