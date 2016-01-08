/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/03/31
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.checkList;

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
 * チェックリスト（特定領域）一覧アクションクラス。
 * チェックリスト一覧画面を表示する。
 * 
 */
public class SearchCheckListTokuteiAction extends BaseAction {

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

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {


		//-------▼ VOにデータをセットする。
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getBukyokutantoInfo().getShozokuCd());

		//状況IDが所属機関受付中以降のものについて表示する
		checkInfo.setSearchJokyoId(JIGYO_IDS);

		//2006.11.14 iso 事業コード状況が抜けていたので追加
        // 検索条件に事業コードを設定
        checkInfo.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
        
// 20050606 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

		//2005/05/19 追加 ここから--------------------------------------------------
		//理由 データが存在しない場合にpageにEMPTY_PAGEをセットするためtry-catchの追加
		Page result = null;
		try{
			//検索実行
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
					container.getUserInfo(),
					checkInfo
// 20050701
					,
					true
// Horikoshi
					);
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
