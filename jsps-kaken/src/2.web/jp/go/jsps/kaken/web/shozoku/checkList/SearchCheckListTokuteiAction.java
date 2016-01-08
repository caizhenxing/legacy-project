/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchCheckListTokuteiAction.java
 *    Description : チェックリスト一覧（特定領域）アクションクラス
 *
 *    Author      : Admin
 *    Date        : 2005/05/24
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/24    V1.0        Admin          新規作成
 *    2006/06/19    V1.0        DIS.dyh        変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト一覧（特定領域）アクションクラス。
 * チェックリスト一覧（特定領域）画面を表示する。
 * 
 */
public class SearchCheckListTokuteiAction extends BaseAction {

	//2005/04/11 追加 ここから--------------------------------------------------
	//状況IDが所属機関受付中以降のものについて表示するよう修正

	/** 所属機関受付中(状況ID:03)以降の状況ID */
	private static String[] JIGYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//所属機関受付中
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//学振受付中
//			StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,			//所属機関却下
			StatusCode.STATUS_GAKUSIN_JYURI,			//学振受理
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//学振不受理
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//審査員割り振り処理後
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO,			//割り振りチェック完了
			StatusCode.STATUS_1st_SHINSATYU,			//一次審査中
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//一次審査：判定
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//二次審査完了
	};
	//追加 ここまで-------------------------------------------------------------
	
	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());

//// 20050601 Start 検索条件に事業区分を追加した検索関数に変更
//      checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
//// Horikoshi End
//2006/06/19 dyh add start 原因：メニュー渡す値を変更
        // 検索条件に事業コードを設定
        checkInfo.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
//2006/06/19 dyh add end
//      2006/08/18 事業区分追加        
        checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);

		//2005/04/11 追加 ここから--------------------------------------------------
		//状況IDが所属機関受付中以降のものについて表示するよう修正
		checkInfo.setSearchJokyoId(JIGYO_IDS);
		//追加 ここまで-------------------------------------------------------------


		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		Page result = null;
		try{
            // 検索実行
            result = getSystemServise(
                    IServiceName.CHECKLIST_MAINTENANCE_SERVICE)
// 20050701
//                    .selectCheckList(container.getUserInfo(),checkInfo);
                    .selectCheckList(container.getUserInfo(), checkInfo, true);
// Horikoshi
		}catch(NoDataFoundException e){
			//0件のページオブジェクトを生成
			result = Page.EMPTY_PAGE;
		}
		//追加 ここまで------------------------------------------------------------
		
		//検索条件をフォームをセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}
}