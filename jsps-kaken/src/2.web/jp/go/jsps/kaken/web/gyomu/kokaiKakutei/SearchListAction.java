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
package jp.go.jsps.kaken.web.gyomu.kokaiKakutei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 公開確定情報検索アクションクラス。
 * 公開確定画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:49 $"
 */
public class SearchListAction extends BaseAction {

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
		BaseSearchForm searchForm = null;

		//struts_config.xmlのparameter=backかどうか
		if("back".equals(mapping.getParameter())){
			//公開確定確認画面からキャンセルボタンで戻る場合は、
			//チェックボックスを初期化しない
			searchForm = (BaseSearchForm) form;
		}else{
			searchForm = new BaseSearchForm();
		}

		//-------▼ VOにデータをセットする。
		JigyoKanriSearchInfo searchInfo = new JigyoKanriSearchInfo();

		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//ページ制御
		searchInfo.setPageSize(0);
		searchInfo.setMaxSize(0);
		
		Page result = null;
		//検索実行
		try{
			result =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).search(
									container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;	
		}

		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);
		
		//kokaiKakuteiFormを初期化
		request.getSession().setAttribute("kokaiKakuteiForm",new KokaiKakuteiForm());
		
		updateFormBean(mapping, request, searchForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
