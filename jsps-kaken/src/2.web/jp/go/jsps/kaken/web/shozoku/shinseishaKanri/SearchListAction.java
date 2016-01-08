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
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請者情報検索アクションクラス。
 * 申請者情報一覧画面を表示する。
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
			
		//2005/04/08 追加 ここから-------------------------------------------------------------
		//キャンセル時に検索条件が保持されるように追加

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		//追加 ここまで------------------------------------------------------------------------

		//検索条件の取得
		ShinseishaSearchForm searchForm = (ShinseishaSearchForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		searchInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
		
		// 2005/03/25 削除 ここから--------------------------------------------
		// 理由 不要な条件のため	
		//	searchInfo.setShozokuName(container.getUserInfo().getShozokuInfo().getShozokuName());
		// 削除 ここまで-------------------------------------------------------
		
		//検索実行
		Page result =
			getSystemServise(
				IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).search(
				container.getUserInfo(),
				searchInfo);

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		return forwardSuccess(mapping);
	}

}
