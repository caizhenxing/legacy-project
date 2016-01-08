/*
 * Created on 2005/06/06
 *
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト（特定領域）の飛び番号リストを取得するクラス。
 * チェックリスト管理サービスで飛び番号取得用のデータを取得する。
 */
public class TobiListTokuteiAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());
// 20050606 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)request.getSession().getAttribute("checkListForm");	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//2005/05/19 追加 ここから--------------------------------------------------
		//理由　飛び番号リスト画面のタイトル情報取得のため
		
		//タイトル表示情報の取得
		Page titleResult = 
					 getSystemServise(
						  IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTitle(checkInfo);
		//タイトル表示情報をフォームにセットする
		request.setAttribute(IConstants.TITLE_INFO, titleResult);
		
		//追加 ここまで-------------------------------------------------------------
		
		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		Page result = null;
		try{		
		//更新
			result = getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectTobiList(
				container.getUserInfo(),
				checkInfo);
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//追加 ここまで------------------------------------------------------------	
			
		//検索結果をフォームにセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		return forwardSuccess(mapping);
	}

}
