/*
 * 作成日: 2005/03/25
 *
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 未登録申請者情報検索アクションクラス。
 * 未登録申請者情報一覧画面を表示する。
 *
 * @author yoshikawa_h
 *
 */
public class SearchUnregistListAction extends BaseAction {
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

		searchInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
		
		//検索実行
		Page result =
			getSystemServise(
				IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).searchUnregist(
				container.getUserInfo(),
				searchInfo);

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);
		
		//2005/04/20 追加 ここから------------------------------------
		//検索条件保持のため
		container.setShinseishaSearchInfo(searchInfo);
		//追加 ここまで-----------------------------------------------
		
		//トークンをセッションに保存する。
		saveToken(request);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}
