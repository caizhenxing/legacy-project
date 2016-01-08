/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shozoku.shinseiJohoKanri;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請者情報検索アクションクラス。
 * 申請者情報一覧画面を表示する。
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

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//所属機関情報
		UserInfo userInfo = container.getUserInfo();
		ShozokuInfo shozokuInfo = userInfo.getShozokuInfo();
		if(shozokuInfo == null){
			throw new ApplicationException(
				"所属機関情報を取得できませんでした。",
				new ErrorInfo("errors.application"));
		}
		ShinseiSearchForm searchForm = (ShinseiSearchForm)form;

		//検索条件をセットする
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		//所属機関コード
		searchInfo.setShozokuCd(shozokuInfo.getShozokuCd());
		//事業名
		if(!searchForm.getJigyoCd().equals("")){
			searchInfo.setJigyoCd(searchForm.getJigyoCd());
		}
		//年度
		if(!searchForm.getNendo().equals("")){
			searchInfo.setNendo(searchForm.getNendo());
		}
		//回数
		if(!searchForm.getKaisu().equals("")){
			searchInfo.setKaisu(searchForm.getKaisu());
		}
		//申請者名（漢字等・姓）
		if(!searchForm.getNameKanjiSei().equals("")){
			searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		}
		//申請者名（漢字等・名）
		if(!searchForm.getNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		}
		//申請者名（ローマ字・姓）
		if(!searchForm.getNameRoSei().equals("")){
			searchInfo.setNameRoSei(searchForm.getNameRoSei());
		}
		//申請者名（ローマ字・名）
		if(!searchForm.getNameRoMei().equals("")){
			searchInfo.setNameRoMei(searchForm.getNameRoMei());
		}
		//申請状況条件
		String jokyo_id = searchForm.getJokyoId();
		if(jokyo_id.equals("0")){
//			//条件未選択
//			String[] str_array = new String[]{StatusCode.STATUS_SHINSEISHO_MIKAKUNIN,
//											  StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU,
//											  StatusCode.STATUS_GAKUSIN_SHORITYU,
//											  StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA,
//											  StatusCode.STATUS_GAKUSIN_JYURI,
//											  StatusCode.STATUS_GAKUSIN_FUJYURI,
//											  StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,
//											  StatusCode.STATUS_WARIFURI_CHECK_KANRYO,
//											  StatusCode.STATUS_1st_SHINSATYU,
//											  StatusCode.STATUS_1st_SHINSA_KANRYO,
//											  StatusCode.STATUS_2nd_SHINSA_KANRYO};
//	
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// 再申請中（申請状況に関わらず） 
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, null);				//「申請書未確認」:02
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, null);				//「所属機関受付中」:03
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);					//「学振処理中」:04
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, null);					//「所属機関却下」:05
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);						//「学振受理」:06
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);						//「学振不受理」:07
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);			//「審査員割り振り処理後」:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);				//「割り振りチェック完了」:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);						//「1次審査中」:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);					//「1次審査完了」:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);					//「2次審査完了」:12
// 2006/6/15 劉佳　追加　「21、22、23、24」 ここから----------------------
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN, null);              //「領域代表者確認中」:21
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA, null);               //「領域代表者却下」:22
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, null);          //「領域代表者確定済み」:23
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE, null);              //「領域代表者所属研究機関受付中」:24
// 2006/6/15 劉佳　追加　「21、22、23、24」　ここまで----------------------
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("1")){
			//申請者未確認
//			String[] str_array = new String[]{StatusCode.STATUS_SHINSEISHO_MIKAKUNIN};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};	//再申請フラグ（初期値、再申請中）
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, saishinseiArray);		//「申請書未確認」:02
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("2")){
			//所属機関受付中
//			String[] str_array = new String[]{StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};	//再申請フラグ（初期値、再申請中）
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, saishinseiArray);	//「所属機関受付中」:03
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("3")){
			//学振処理中
//			String[] str_array = new String[]{StatusCode.STATUS_GAKUSIN_SHORITYU};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);			//「学振処理中」:04
			searchInfo.setStatusSearchInfo(statusInfo);
		} else if (jokyo_id.equals("4")) {
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);			//「学振受理」:06
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, saishinseiArray);//「審査員割り振り処理後」:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, saishinseiArray);	//「割り振りチェック完了」:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, saishinseiArray);			//「1次審査中」:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, saishinseiArray);		//「1次審査完了」:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);		//「2次審査完了」:12
			searchInfo.setStatusSearchInfo(statusInfo);		
		}else if(jokyo_id.equals("8")){
			//不受理
//			String[] str_array = new String[]{StatusCode.STATUS_GAKUSIN_FUJYURI};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);			//「学振不受理」:07
			searchInfo.setStatusSearchInfo(statusInfo);
//		}else if(jokyo_id.equals("4")){
//			//採択
////			int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU};
////			searchInfo.setKekka2(int_array);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//「2次審査完了」:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//
//			 int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU};									//審査結果：「採択」:1
//			 searchInfo.setKekka2(int_array);
//		}else if(jokyo_id.equals("5")){
//			//不採択
////			int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};
////			searchInfo.setKekka2(int_array);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//「2次審査完了」:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//
//			 int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};									//審査結果：「不採択」:8
//			 searchInfo.setKekka2(int_array);
//		}else if(jokyo_id.equals("6")){
//			//採択候補
////			int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};
////			searchInfo.setKekka2(int_array);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//「2次審査完了」:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//
//			 int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};									//審査結果：「採用候補」:2
//			 searchInfo.setKekka2(int_array);
//		}else if(jokyo_id.equals("7")){
//			//補欠
////			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);
//			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
//			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
//															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
//			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);				//「2次審査完了」:12
//			searchInfo.setStatusSearchInfo(statusInfo);
//			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);										//審査結果：「補欠1～5」
		}else if(jokyo_id.equals("9")){
			//却下
//			String[] str_array = new String[]{StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA};
//			searchInfo.setJokyoId(str_array);
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
													StatusCode.SAISHINSEI_FLG_SAISHINSEITYU};	//再申請フラグ（初期値、再申請中）
			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, saishinseiArray);		//「所属機関却下」:05
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if(jokyo_id.equals("10")){
			//修正依頼
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	//再申請中（申請状況に関わらず） 
//			String[] saishinseiArray = new String[]{StatusCode.STATUS_GAKUSIN_SHORITYU};		//再申請フラグ（再申請中）
//			statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, saishinseiArray);		//「申請書未確認」:02
//			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, saishinseiArray);	//「所属機関受付中」:03
//			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);			//「学振処理中」:04
//			statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, saishinseiArray);		//「所属機関却下」:05
//			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);			//「学振受理」:06
//			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);			//「学振不受理」:07
			searchInfo.setStatusSearchInfo(statusInfo);
//         2006/7/31 zjp　追加　「21、22、23、24」 ここから----------------------
        }else if(jokyo_id.equals("11")){
            //領域代表者確認中
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUNIN, null);         //「領域代表者確認中」:21
            searchInfo.setStatusSearchInfo(statusInfo);
        }else if(jokyo_id.equals("12")){
            //領域代表者確定済
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KAKUTEIZUMI, null);     //「領域代表者確定済」:23
            searchInfo.setStatusSearchInfo(statusInfo);
        }else if(jokyo_id.equals("13")){
            //領域代表者却下
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKIDAIHYOU_KYAKKA, null);          //「領域代表者却下」:22
            searchInfo.setStatusSearchInfo(statusInfo);
        }else if(jokyo_id.equals("14")){
            //領域代表者所属研究機関受付中
            CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
            statusInfo.addOrQuery(StatusCode.STATUS_RYOUIKISHOZOKU_UKETUKE, null);         //「領域代表者所属研究機関受付中」:24
            searchInfo.setStatusSearchInfo(statusInfo);
        }
//      2006/7/31 zjp　追加　「21、22、23、24」　ここまで----------------------       
        
		//作成日(From)
		if(!searchForm.getSakuseiDateFromYear().equals("")){
			searchInfo.setSakuseiDateFrom(searchForm.getSakuseiDateFromYear()
                    + "/" + searchForm.getSakuseiDateFromMonth()
                    + "/" + searchForm.getSakuseiDateFromDay());
		}
		//作成日(To)
		if(!searchForm.getSakuseiDateToYear().equals("")){
			searchInfo.setSakuseiDateTo(searchForm.getSakuseiDateToYear()
                    + "/" + searchForm.getSakuseiDateToMonth()
                    + "/" + searchForm.getSakuseiDateToDay());
		}
		//所属機関承認日(From)
		if(!searchForm.getShoninDateFromYear().equals("")){
			searchInfo.setShoninDateFrom(searchForm.getShoninDateFromYear()
                    + "/" + searchForm.getShoninDateFromMonth()
                    + "/" + searchForm.getShoninDateFromDay());
		}
		//所属機関承認日(To)
		if(!searchForm.getShoninDateToYear().equals("")){
			searchInfo.setShoninDateTo(searchForm.getShoninDateToYear()
                    + "/" + searchForm.getShoninDateToMonth()
                    + "/" + searchForm.getShoninDateToDay());
		}
		//表示方式
		if(searchForm.getHyojiHoshiki().equals("2")){
			//申請者毎の場合は所属機関CD→申請者ID→申請番号順で表示する
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD);
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID);
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);		//2007/3/27追加
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);
		}else{
			//研究種目毎の場合は事業ID→申請番号→システム受付番号順で表示する
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);
//2007/03/28 劉長宇　追加　ここから
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);
//2007/03/28 劉長宇　追加　ここまで
		}
		//申請者名（フリガナ・姓）
		if(!searchForm.getNameKanaSei().equals("")){
			searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		}
		//申請者名（フリガナ・名）
		if(!searchForm.getNameKanaMei().equals("")){
			searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		}
		//研究者番号
		if(!searchForm.getKenkyuNo().equals("")){
			searchInfo.setKenkyuNo(searchForm.getKenkyuNo());
		}
		//部局コード
		if(!searchForm.getBukyokuCd().equals("")){
			searchInfo.setBukyokuCd(searchForm.getBukyokuCd());
		}
		//2004/12/3update
		//「基盤研究」の申請が表示されないように一時対応
		//事業区分の場合のみ表示
		List jigyoKubunList = new ArrayList();
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO);	//「1:学術創成研究費（推薦分）」
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_KOUBO);		//「2:学術創成研究費（募集分」
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_TOKUSUI);			//「3:特別推進研究」
		
		// 2005/04/12 追加 ここから-------------------------------------
		//「基盤研究」の場合も申請を表示するため条件を追加		
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_KIBAN);				//「4:基盤研究」
		//	追加 ここまで

// 20050606 Start
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);			//「5:特定領域研究」
// Horikoshi End
//add start dyh 2006/2/20 原因：事業区分の追加
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_WAKATESTART);        //「6:若手スタートアップ 」
		jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);        //「7:特別研究促進費」
//add end dyh 2006/2/20
		searchInfo.setJigyoKubun(jigyoKubunList);	

		//ページ制御
		searchInfo.setStartPosition(searchForm.getStartPosition());
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());

		//サーバサービスの呼び出し（処理状況一覧ページ情報取得）
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		Page page = servise.searchApplication(userInfo, searchInfo);
        
        //ADD START LIUYI 2006/07/03 仕様変更 CSV出力の検索条件の取得
        container.setShinseiSearchInfo(searchInfo);
        //ADD END LIUYI 2006/07/03

		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO, page);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}