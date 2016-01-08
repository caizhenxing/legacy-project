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
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.vo.CheckListInfo;
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
 * チェックリスト一覧アクションクラス。
 * チェックリスト一覧画面を表示する。
 * 
 */
public class SearchCheckListAction extends BaseAction {

	/** 「すべて：0」：03(所属機関受付中), 04(学振受付中),06(学振受理), 08～12のものを表示 */
	private static String[] JOKYO_IDS = new String[]{
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,		//所属機関受付中	
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//学振受付中
			StatusCode.STATUS_GAKUSIN_JYURI,			//学振受理
			StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,		//審査員割り振り処理後
			StatusCode.STATUS_WARIFURI_CHECK_KANRYO, 		//審査員割り振りチェック完了
			StatusCode.STATUS_1st_SHINSATYU,			//一次審査中
			StatusCode.STATUS_1st_SHINSA_KANRYO,			//一次審査判定
			StatusCode.STATUS_2nd_SHINSA_KANRYO				//二次審査完了
// 20050721 学振不受理を追加
			,
			StatusCode.STATUS_GAKUSIN_FUJYURI			//学振不受理
// Horikoshi
	};
	
	//2005/04/21 追加 ここから--------------------------------------------
	//理由 検索条件に受理状況を追加したため、各状況を追加
	
	/** 「確定解除：03」：状況IDが03(所属機関受付中)のものを表示 */
	private static String[] JOKYO_ID_SYOZOKU = new String[] {
			StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU			//所属機関受付中
	};

	/** 「受理前：04」：状況IDが04(学振受付中：受理前)のものを表示 */
	private static String[] JOKYO_ID_JYURIMAE = new String[]{
			StatusCode.STATUS_GAKUSIN_SHORITYU,		//学振受付中
	};

	/** 「受理済み：06、08以上」：状況IDが06(学振受理),08～12のものを表示 */
	private static String[] JOKYO_ID_JYURIZUMI = new String[]{
			StatusCode.STATUS_GAKUSIN_JYURI,			//学振受理
//2006/05/22 追加ここから
            StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,      //審査員割り振り処理後 
            StatusCode.STATUS_WARIFURI_CHECK_KANRYO,         //割り振りチェック完了
            StatusCode.STATUS_1st_SHINSATYU,            //一次審査中
            StatusCode.STATUS_1st_SHINSA_KANRYO,           //一次審査:判定
            StatusCode.STATUS_2nd_SHINSA_KANRYO,             //二次審査完了
//苗　追加ここまで            
	};

// 20050721 学振不受理
    /** 「不受理 ：07」：状況IDが07(学振不受理)のものを表示 */
	private static String[] JOKYO_ID_FUJYURI = new String[]{
			StatusCode.STATUS_GAKUSIN_FUJYURI,		//学振不受理
	};
// Horikoshi

	/** 検索条件:全て */
	private static final String CHECK_ALL = "0";
	
	/** 検索条件:確定解除 */
	private static final String CHECK_SHOZOKU = "1";
	
//	追加 ここまで-------------------------------------------------------
	
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
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		//検索条件保持用
		CheckListInfo checkInfo = new CheckListInfo();
	
		//フォーム情報取得
		CheckListSearchForm searchForm = (CheckListSearchForm)form;

// 2006/02/13 update		
		//事業区分
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
// 2006/02/13 dhy  end		
		
		//検索条件の設定
		if(searchForm.getJigyoCd() != null && !searchForm.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(searchForm.getJigyoCd());
			checkInfo.setJigyoCd(searchForm.getJigyoCd());
		}
		if(searchForm.getShozokuCd() != null && !searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
			checkInfo.setShozokuCd(searchForm.getShozokuCd());
		}
	
		//2005/04/21 追加 ここから---------------------------------------------
		//理由 検索条件の追加
		if(searchForm.getShozokuName() != null && !searchForm.getShozokuName().equals("")){
			searchInfo.setShozokuName(searchForm.getShozokuName());
			checkInfo.setShozokuName(searchForm.getShozokuName());
				
		}
		String jokyoKubun = searchForm.getJuriJokyo();
        //デフォルトを選択する時、全てにとして
		if(jokyoKubun == null || jokyoKubun.equals("") ||jokyoKubun.equals("0")){
		
			//状況IDが04(学振受付中),06(学振受理), 08～12のものを表示
			searchInfo.setSearchJokyoId(JOKYO_IDS);
			searchInfo.setCancellationFlag(CHECK_ALL);
		}
        //「確定解除：03」を選択する時
		else if(jokyoKubun.equals(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU)){
			//状況IDが03(所属機関受付中)で、解除フラグがあるものを表示
			searchInfo.setSearchJokyoId(JOKYO_ID_SYOZOKU);
			searchInfo.setCancellationFlag(CHECK_SHOZOKU);
		}
        //「受理前：04」を選択する時
		else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_SHORITYU)){
			//状況IDが04(学振受付中)のものを表示
			searchInfo.setSearchJokyoId(JOKYO_ID_JYURIMAE);
		}
        //「受理済み：06、08以上」を選択する時
		else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			//状況IDが06(学振受理),08～12のものを表示
			searchInfo.setSearchJokyoId(JOKYO_ID_JYURIZUMI);
		}
// 20050721
        //「不受理 ：07」を選択する時
		else if(jokyoKubun.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			//状況IDが07(学振不受理)のものを表示
			searchInfo.setSearchJokyoId(JOKYO_ID_FUJYURI);
		}
// Horikoshi
        //上記以外を選択する時
		else{
			//状況IDが04(学振受付中),06(学振受理), 08～12のものを表示 
			searchInfo.setSearchJokyoId(JOKYO_IDS);
		}

		//追加 ここまで-----------------------------------------------------------
		
		//ページ制御
		searchInfo.setStartPosition(searchForm.getStartPosition());
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());

// 20050629 NoDataFoundExceptionをthrowするよう変更
		Page result = null;
		try{
			//検索実行
			result = 
				getSystemServise(
					IServiceName.CHECKLIST_MAINTENANCE_SERVICE).selectCheckList(
					container.getUserInfo(),
					searchInfo);
		}catch(NoDataFoundException e){
			throw new NoDataFoundException("該当データはありません。");
		}
// Horikoshi

		//検索条件をコンテナに格納		
		container.setCheckListInfo(checkInfo);
		container.setCheckListSearchInfo(searchInfo);

		//検索結果をセットする。
		request.setAttribute(IConstants.RESULT_INFO, result);
		
		return forwardSuccess(mapping);
	}
}