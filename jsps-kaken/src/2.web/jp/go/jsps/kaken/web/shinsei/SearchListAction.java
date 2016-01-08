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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
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
 * 事業管理情報検索アクションクラス。
 * 受付中公募事業一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:00 $"
 */
public class SearchListAction extends BaseAction {

	/** 検索対象事業ID */
	
//	2005/04/22 変更 ここから----------
//	理由:推薦フラグが無視されてしまう問題の修正

	//private final static String[] JIGYO_KUBUN=new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO,IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO,IJigyoKubun.JIGYO_KUBUN_TOKUSUI};
	
	//学創は公募のみ
	private final static String[] JIGYO_KUBUN_KOBO=new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO,IJigyoKubun.JIGYO_KUBUN_TOKUSUI};
	//学窓は非公募と公募
	private final static String[] JIGYO_KUBUN_HIKOBO=new String[]{IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO,IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO,IJigyoKubun.JIGYO_KUBUN_TOKUSUI};
	
//	2005/04/22 追加 ここまで----------
	
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
		BaseSearchForm searchForm = (BaseSearchForm)form;
		//リストのみ表示させるため、ページ件数を1件にする。
		searchForm.setPageSize(0);

		//-------▼ VOにデータをセットする。
		//2005/03/26 修正 ------------------------------------------------ここから
		//理由 基盤事業とその他の一覧を別々の画面として表示するため
		//SearchInfo searchInfo = new SearchInfo();

		JigyoKanriSearchInfo searchInfo = new JigyoKanriSearchInfo();
		
		//2005/03/24 修正 ------------------------------------------------ここまで

		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//検索実行
		Page result = null;
		try{
			//2005/03/24 修正 ------------------------------------------------ここから
			//理由 基盤事業とその他の一覧を別々の画面として表示するため
			//result =
			//		getSystemServise(
			//		IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).searchUketukeJigyo(
			//						container.getUserInfo(),
			//		searchInfo);
			
			UserInfo userInfo = container.getUserInfo();
			
			//申請者の場合は、非公募権限があるかどうかチェックする。
			if(userInfo.getRole().equals(UserRole.SHINSEISHA)){
				//申請者の非公募権限がある場合 → 非公募も検索条件にする
				ShinseishaInfo shinseishaInfo = userInfo.getShinseishaInfo();
				if("1".equals(shinseishaInfo.getHikoboFlg())){
					searchInfo.setJigyoKubun(JIGYO_KUBUN_HIKOBO);
				}else{
					//非公募権限がない場合→非公募は検索条件にしない
					searchInfo.setJigyoKubun(JIGYO_KUBUN_KOBO);
				}
			}
			
			result =
					getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).searchUketukeJigyo(
									container.getUserInfo(),searchInfo);
			//2005/03/24 修正 ------------------------------------------------ここまで
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
