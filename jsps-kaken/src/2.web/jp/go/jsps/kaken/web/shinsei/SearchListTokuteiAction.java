/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchListTokuteiAction.java
 *    Description : 事業管理情報検索アクションクラス。受付中公募事業一覧画面を表示する
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    v1.0        Admin          新規作成
 *    2006/07/10    v1.1        Dis.dyh        変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
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
 * 事業管理情報検索アクションクラス。
 * 受付中公募事業一覧画面を表示する。
 */
public class SearchListTokuteiAction extends BaseAction {

	/** 検索対象事業ID */
	private final static String[] JIGYO_KUBUN = new String[] { IJigyoKubun.JIGYO_KUBUN_TOKUTEI };

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

		//検索条件の取得
//2006/06/21 苗　修正ここから        
//		BaseSearchForm searchForm = (BaseSearchForm)form;
        TokuteiSearchForm searchForm = (TokuteiSearchForm)form;
//2006/06/21 苗　修正ここまで        
		//リストのみ表示させるため、ページ件数を1件にする。
		searchForm.setPageSize(0);

		//-------▼ VOにデータをセットする。
		JigyoKanriSearchInfo searchInfo = new JigyoKanriSearchInfo();

		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//検索実行
		Page result = null;
		try{
			//事業区分セット
			searchInfo.setJigyoKubun(JIGYO_KUBUN);
//2006/06/21 苗　追加ここから
            searchInfo.setJigyoCds(searchForm.getJigyoCds());
//2006/06/21　苗　修正ここまで            
				
			result =
					getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).searchUketukeJigyo(
									container.getUserInfo(),searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}

		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);       

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}