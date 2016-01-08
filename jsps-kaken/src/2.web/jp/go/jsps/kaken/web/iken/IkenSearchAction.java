/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : IkenSearchAction.java
 *    Description : 意見情報検索アクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.IkenSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

/**
 * 意見情報検索アクションクラス。
 * ID RCSfile="$RCSfile: IkenSearchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:18 $"
 */
public class IkenSearchAction extends BaseAction {

	/* (Javadoc なし)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form, 
			HttpServletRequest request,
			HttpServletResponse response, 
			UserContainer container) throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
		
		//検索条件フォームの取得
		IkenSearchForm searchForm = (IkenSearchForm)form;
		
		// VOに検索条件をセットする。
		IkenSearchInfo searchInfo = new IkenSearchInfo();
		
		//申請者フラグ
		searchInfo.setShinseisya( searchForm.getShinseisya() );
		
		//所属機関担当者フラグ
		searchInfo.setSyozoku( searchForm.getSyozoku() );

		//部局担当者フラグ
		searchInfo.setBukyoku( searchForm.getBukyoku() );
		
		//審査員フラグ
		searchInfo.setShinsyain( searchForm.getShinsyain() );
		
		//作成日（開始日）
		if(!searchForm.getSakuseiDateFromYear().equals("")){
			//画面に01から1へ変更した為
			String month = searchForm.getSakuseiDateFromMonth();
			if (month.length() == 1){
				month = "0" + month;
			}
			String day = searchForm.getSakuseiDateFromDay();
			if (day.length() == 1){
				day = "0" + day;
			}
			
			searchInfo.setSakuseiDateFrom(								
							searchForm.getSakuseiDateFromYear() + "/" +
//							searchForm.getSakuseiDateFromMonth() + "/" +
//							searchForm.getSakuseiDateFromDay()
							month + "/" + day
							);
		}
		//作成日（終了日）
		if(!searchForm.getSakuseiDateToYear().equals("")){
			//画面に01から1へ変更した為
			String month = searchForm.getSakuseiDateToMonth();
			if (month.length() == 1){
				month = "0" + month;
			}
			String day = searchForm.getSakuseiDateToDay();
			if (day.length() == 1){
				day = "0" + day;
			}

			searchInfo.setSakuseiDateTo(									
							searchForm.getSakuseiDateToYear() + "/" +
//							searchForm.getSakuseiDateToMonth()+ "/" +
//							searchForm.getSakuseiDateToDay()
							month + "/" + day
							);
		}
		
		//表示方式
		searchInfo.setDispmode( searchForm.getDispmode() );

		//ページ制御
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//検索実行
		Page result =
				getSystemServise(
					IServiceName.IKEN_MAINTENANCE_SERVICE).searchIken(searchInfo);

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		return forwardSuccess(mapping);
	}

}
