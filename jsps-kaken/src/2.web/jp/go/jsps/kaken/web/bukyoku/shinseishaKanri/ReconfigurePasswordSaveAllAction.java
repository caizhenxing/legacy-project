/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/4/8
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * パスワード再設定情報値オブジェクトを登録する。
 * フォーム情報、パスワード再設定情報をクリアする。
 * 
 */
public class ReconfigurePasswordSaveAllAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//2005/04/20 削除 ここから-------------------------------------
		//理由 処理中画面表示用にトークンのチェックをReconfigurePasswordCheckで行うように変更したため
		//-----ActionErrorsの宣言（定型処理）-----
		//ActionErrors errors = new ActionErrors();

		//-----取得したトークンが無効であるとき
		//if (!isTokenValid(request)) {
		//	errors.add(ActionErrors.GLOBAL_ERROR,
		//			   new ActionError("error.transaction.token"));
		//	saveErrors(request, errors);
		//	return forwardTokenError(mapping);
		//}
		//削除 ここまで------------------------------------------------
			
		//------申請者情報保持クラス
		ShinseishaInfo reconfigurePasswordInfo = new ShinseishaInfo();

		//------パスワード再発行用
		ShinseishaSearchInfo searchInfo = new ShinseishaSearchInfo();
		ShinseishaSearchForm searchForm = (ShinseishaSearchForm)request.getSession().getAttribute("shinseishaSearchForm");

		//searchFormからsearchInfoにコピー
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
	
		//ページサイズに0を設定することで全てのページ情報を取るようにする
		//パスワード一括再設定でページ表示分のみを処理する場合はこの処理をコメントアウトする
		searchInfo.setPageSize(0);		
	
		//申請者情報一覧取得
		//SearchListAction.javaの処理と同様の処理で申請者情報の一覧を取得しており、
		//上記ソースを修正する場合、同様に修正する必要がある
		searchInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
		Page resultTnto = null;
		try {
			resultTnto =
				getSystemServise(
					IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).search(
					container.getUserInfo(),
					searchInfo);
		} catch(ApplicationException e) {
			//------該当担当者なし→通常ありえないので空表示
		}
		
		if(resultTnto == null){
			return forwardFailure(mapping);
		}
		//------申請者IDの配列
		ArrayList data = new ArrayList();

		//2005/04/29 追加 ----------------------------------------------ここから
		//理由 登録した研究者NoをUserContainerへ直接保持するように修正したため
		ArrayList kenkyuNoList = new ArrayList();
		//2005/04/29 追加 ----------------------------------------------ここまで
		
		
		//------申請者ID項目名：SHINSEISHA_ID
		String shinseishaId = ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID;
		
		//------申請者の数分繰り返し
		for(int i = 0; i < resultTnto.getSize(); i++){
			
			//------各申請者の情報を取得
			HashMap shinseishaDataMap = (HashMap)resultTnto.getList().get(i);
				
			//------申請者IDにデータがある場合は配列に格納	
			if(shinseishaDataMap.get(shinseishaId) != null && !shinseishaDataMap.get(shinseishaId).equals("")){
				 data.add(shinseishaDataMap.get(shinseishaId));

				 //2005/04/29 追加 ----------------------------------------------ここから
				 //理由 登録した研究者NoをUserContainerへ直接保持するように修正したため
				 kenkyuNoList.add(shinseishaDataMap.get(ShinseiSearchInfo.ORDER_BY_KENKYU_NO));
				 //2005/04/29 追加 ----------------------------------------------ここまで
			}
		}
		ISystemServise servise = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE);

		//------DB登録 パスワード再発行処理
		servise.reconfigurePasswordAll(container.getUserInfo(),reconfigurePasswordInfo, data);
		
		// 変更　ここまで
		
		
		//2005/04/29 追加 ----------------------------------------------ここから
		//理由 登録した研究者NoをUserContainerへ直接保持するように修正
		container.setKenkyuNo((String[])kenkyuNoList.toArray(new String[kenkyuNoList.size()]));
		//2005/04/29 追加 ----------------------------------------------ここまで

		
		
		if(log.isDebugEnabled()){
			log.debug("申請者情報　パスワード再設定情報 '"+ request);
		}

		//------トークンの削除	
		resetToken(request);
		//------セッションより新規登録情報の削除
		container.setShinseishaInfo(null);
		//------フォーム情報の削除
		removeFormBean(mapping,request);

		return forwardSuccess(mapping);
	}

}
