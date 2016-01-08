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

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.model.vo.JigyoKanriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 公開確定確認画面表示前アクションクラス。
 * 公開確定確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: KokaiKakuteiCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:50 $"
 */
public class KokaiKakuteiCheckAction extends BaseAction {

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
			
		//------検索対象公開確定情報の取得
		KokaiKakuteiSearchForm searchForm = (KokaiKakuteiSearchForm)form;
		
		//------キー情報
		List jigyoIds = searchForm.getJigyoIds();
		JigyoKanriPk[] jigyoPks = new JigyoKanriPk[jigyoIds.size()];
		for(int i = 0; i < jigyoPks.length; i++){
		 	String jigyoId = (String)jigyoIds.get(i);
			JigyoKanriPk jigyoPk = new JigyoKanriPk();			 
			jigyoPk.setJigyoId(jigyoId);
			jigyoPks[i] = jigyoPk;
		}
		
		//------検索条件をセット
		JigyoKanriSearchInfo searchInfo = new JigyoKanriSearchInfo();
		searchInfo.setJigyoPks(jigyoPks);
		
		//ページ制御
		searchInfo.setPageSize(0);		
		searchInfo.setMaxSize(0);
				
		//------キー情報を元に更新データ取得	
		Page result = 
					getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).search(container.getUserInfo(), searchInfo);
			
		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);	
				
		//------トークンをセットする。
		saveToken(request);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
