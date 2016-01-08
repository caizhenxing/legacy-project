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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.ArrayList;
import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.role.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.status.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * データ保管申請情報検索アクションクラス。
 * 申請情報一覧画面を表示する。
 * 
 */
public class SearchListAction extends BaseAction {

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
		DataHokanForm searchForm = (DataHokanForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		
		//事業コード
		if(!searchForm.getJigyoCd().equals("")){
//2007/02/25 苗　修正ここから
            if (IJigyoCd.JIGYO_CD_SHOKUSHINHI_KIBAN_C.equals(searchForm.getJigyoCd())) {
                ArrayList jigyoKubunList = new ArrayList();
                jigyoKubunList.add(IJigyoKubun.JIGYO_KUBUN_SHOKUSHINHI);
                searchInfo.setJigyoKubun(jigyoKubunList);
            } else {
//2007/02/25　苗　修正ここまで            
    			searchInfo.setJigyoCd(searchForm.getJigyoCd());	//指定されていた場合は当該事業コードのみ    
            }
		}else{
			//指定されていない場合は、（業務担当者ならば）自分が担当する事業コードのみ
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setTantoJigyoCd(gyomutantoInfo.getTantoJigyoCd());
			}
		}
		//年度
		if(!searchForm.getNendo().equals("")){		
			searchInfo.setNendo(searchForm.getNendo());
		}
		//回数
		if(!searchForm.getKaisu().equals("")){	
			searchInfo.setKaisu(searchForm.getKaisu());
		}
		//申請者名（漢字）-姓
		if(!searchForm.getShinseishaNameKanjiSei().equals("")){			
			searchInfo.setNameKanjiSei(searchForm.getShinseishaNameKanjiSei());
		}
		//申請者名（漢字）-名
		if(!searchForm.getShinseishaNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getShinseishaNameKanjiMei());
		}		
		//申請者氏名（フリガナ-姓）
		if(!searchForm.getShinseishaNameKanaSei().equals("")){			
			searchInfo.setNameKanaSei(searchForm.getShinseishaNameKanaSei());
		}
		//申請者氏名（フリガナ-名）
		if(!searchForm.getShinseishaNameKanaMei().equals("")){
			searchInfo.setNameKanaMei(searchForm.getShinseishaNameKanaMei());
		}
		//2005/04/25 削除 これから----------------------------------------
		//理由 申請者名のローマ字検索がないため削除
		/*
		//申請者名（ローマ字）-姓
		if(!searchForm.getShinseishaNameRoSei().equals("")){
			searchInfo.setNameRoSei(searchForm.getShinseishaNameRoSei());
		}
		//申請者名（ローマ字）-名
		if(!searchForm.getShinseishaNameRoMei().equals("")){
			searchInfo.setNameRoMei(searchForm.getShinseishaNameRoMei());
		}
		*/
		//削除 ここまで---------------------------------------------------
				
		//所属機関コード
		if(!searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
		//研究者番号
		if(!searchForm.getKenkyuNo().equals("")){
			searchInfo.setKenkyuNo(searchForm.getKenkyuNo());
		}
		//申請番号
		if(!searchForm.getUketukeNo().equals("")){		
			searchInfo.setUketukeNo(searchForm.getUketukeNo());		
		}
		//細目番号
		if(!searchForm.getBunkaSaimokuCd().equals("")){		
			searchInfo.setBunkasaimokuCd(searchForm.getBunkaSaimokuCd());		
		}
		
//		//申請状況条件（固定：業務担当者が参照可能なステータスのもの）
//		String[] str_array = new String[]{StatusCode.STATUS_GAKUSIN_SHORITYU,			//申請状況：「学振処理中」:04
//										  StatusCode.STATUS_GAKUSIN_JYURI,				//申請状況：「学振受理」:06
//										  StatusCode.STATUS_GAKUSIN_FUJYURI,			//申請状況：「学振不受理」:07
//										  StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO,	//申請状況：「審査員割り振り処理後」:08
//										  StatusCode.STATUS_WARIFURI_CHECK_KANRYO,		//申請状況：「割り振りチェック完了」:09
//										  StatusCode.STATUS_1st_SHINSATYU,				//申請状況：「1次審査中」:10
//										  StatusCode.STATUS_1st_SHINSA_KANRYO,			//申請状況：「1次審査完了」:11
//										  StatusCode.STATUS_2nd_SHINSA_KANRYO,			//申請状況：「2次審査完了」:12
//										};
//		searchInfo.setJokyoId(str_array);
		
		//申請状況条件（固定：業務担当者が参照可能なステータスのもの）
		CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
		statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// 再申請中（申請状況に関わらず）	
		//2005/04/27 追加 ここから--------------------------------------------------------
		//理由 不足条件の追加(02:申請書未確認、03:所属機関受付中、05:所属機関却下)
//2006/05/22 再修正（業務担当者が参照可能なものだけに戻す）
//		statusInfo.addOrQuery(StatusCode.STATUS_SHINSEISHO_MIKAKUNIN, null);					//「申請書未確認」:02
//		statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU, null);				//「所属機関受付中」:03
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);						//「学振処理中」:04
//		statusInfo.addOrQuery(StatusCode.STATUS_SHOZOKUKIKAN_KYAKKA, null);					//「所属機関却下」:05
		//追加 ここまで-------------------------------------------------------------------
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);							//「学振受理」:06
		statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);						//「学振不受理」:07
		statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);				//「審査員割り振り処理後」:08
		statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);					//「割り振りチェック完了」:09
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);							//「1次審査中」:10
		statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);						//「1次審査完了」:11
		statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);						//「2次審査完了」:12
		searchInfo.setStatusSearchInfo(statusInfo);
		
		//並び順
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);		//事業ID毎
		searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);		//申請番号順
//2007/03/28 劉長宇　追加　ここから
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO); 		//システム受付番号順
//2007/03/28 劉長宇　追加　ここまで
		
		//ページ制御
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());
		searchInfo.setStartPosition(searchForm.getStartPosition());
	
		//検索実行
		Page result = null;
		try{
			result = getSystemServise(
						IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).searchApplication(
						container.getUserInfo(),
						searchInfo);
		}catch(NoDataFoundException e){
			throw e;
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
