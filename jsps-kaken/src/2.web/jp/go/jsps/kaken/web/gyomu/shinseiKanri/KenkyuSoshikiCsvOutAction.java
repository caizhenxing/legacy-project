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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.CombinedStatusSearchInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinseiSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究組織CSV出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: KenkyuSoshikiCsvOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class KenkyuSoshikiCsvOutAction extends BaseAction {
	
	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String filename = "SOSHIKIHYO_";

	/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		 throws ApplicationException
		{
			
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//検索条件の取得
		ShinseiSearchForm searchForm = (ShinseiSearchForm)form;
			
		//-------▼ VOにデータをセットする。
		ShinseiSearchInfo searchInfo = new ShinseiSearchInfo();
		
		//事業コード
		if(!searchForm.getJigyoCd().equals("")){	
			searchInfo.setJigyoCd(searchForm.getJigyoCd());	//指定されていた場合は当該事業コードのみ
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
		//申請者氏名（漢字等-姓）
		if(!searchForm.getNameKanjiSei().equals("")){			
			searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());
		}
		//申請者氏名（漢字等-名）
		if(!searchForm.getNameKanjiMei().equals("")){
			searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());
		}		
		//申請者氏名（フリガナ-姓）
		if(!searchForm.getNameKanaSei().equals("")){			
			searchInfo.setNameKanaSei(searchForm.getNameKanaSei());
		}
		//申請者氏名（フリガナ-名）
		if(!searchForm.getNameKanaMei().equals("")){
			searchInfo.setNameKanaMei(searchForm.getNameKanaMei());
		}
		//申請者氏名（ローマ字-姓）
		if(!searchForm.getNameRoSei().equals("")){
			searchInfo.setNameRoSei(searchForm.getNameRoSei());
		}
		//申請者氏名（ローマ字-名）
		if(!searchForm.getNameRoMei().equals("")){
			searchInfo.setNameRoMei(searchForm.getNameRoMei());
		}
		//所属機関コード
		if(!searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
		//研究者番号
		if(!searchForm.getKenkyuNo().equals("")){
			searchInfo.setKenkyuNo(searchForm.getKenkyuNo());
		}
		//系等の区分番号
		if(!searchForm.getKeiName().equals("")){
			searchInfo.setKeiName(searchForm.getKeiName());
		}
		//推薦の観点番号
		if(!searchForm.getKantenNo().equals("")){
			searchInfo.setKantenNo(searchForm.getKantenNo());
		}
		//申請状況条件
		String jokyo_id = searchForm.getJokyoId();
		if("0".equals(jokyo_id)){
			//条件未選択
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU});	// 再申請中（申請状況に関わらず） 
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, null);     				 	//「学振処理中」:04
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, null);       					//「学振受理」:06
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, null);      					//「学振不受理」:07
			statusInfo.addOrQuery(StatusCode.STATUS_SHINSAIN_WARIFURI_SHORIGO, null);    			//「審査員割り振り処理後」:08
			statusInfo.addOrQuery(StatusCode.STATUS_WARIFURI_CHECK_KANRYO, null);     			//「割り振りチェック完了」:09
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, null);       					//「1次審査中」:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, null);      				//「1次審査完了」:11
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, null);      				//「2次審査完了」:12
			searchInfo.setStatusSearchInfo(statusInfo);	
		}else if("1".equals(jokyo_id)){
			//受理前
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_SHORITYU, saishinseiArray);    				 //「学振処理中」:04
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("2".equals(jokyo_id)){
			//受理済み
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};     //再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_JYURI, saishinseiArray);      					//「学振受理」:06
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("3".equals(jokyo_id)){
			//不受理
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};    //再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_GAKUSIN_FUJYURI, saishinseiArray);      				//「学振不受理」:07
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("4".equals(jokyo_id)){
			//修正依頼
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			statusInfo.addOrQuery(null, new String[]{StatusCode.SAISHINSEI_FLG_SAISHINSEITYU}); 			// 再申請中（申請状況に関わらず）
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("5".equals(jokyo_id)){
			//審査中
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSATYU, saishinseiArray);						//「1次審査中」:10
			statusInfo.addOrQuery(StatusCode.STATUS_1st_SHINSA_KANRYO, saishinseiArray);					//「1次審査完了」:11
			searchInfo.setStatusSearchInfo(statusInfo);
		}else if("6".equals(jokyo_id)){
			//採択
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);    				//「2次審査完了」:12
			searchInfo.setStatusSearchInfo(statusInfo);
			int[] int_array = new int[]{StatusCode.KEKKA2_SAITAKU};    									//審査結果：「採択」:1
			searchInfo.setKekka2(int_array);
		}else if("7".equals(jokyo_id)){
			//不採択
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//「2次審査完了」:12
			searchInfo.setStatusSearchInfo(statusInfo);
			int[] int_array = new int[]{StatusCode.KEKKA2_FUSAITAKU};    								//審査結果：「不採択」:8
			searchInfo.setKekka2(int_array);
		}else if("8".equals(jokyo_id)){
			//採用候補
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//「2次審査完了」:12
			searchInfo.setStatusSearchInfo(statusInfo);
			int[] int_array = new int[]{StatusCode.KEKKA2_SAIYOKOHO};									//審査結果：「採用候補」:2
			searchInfo.setKekka2(int_array);
		}else if("9".equals(jokyo_id)){
			//補欠
			CombinedStatusSearchInfo statusInfo = new CombinedStatusSearchInfo();
			String[] saishinseiArray = new String[]{StatusCode.SAISHINSEI_FLG_DEFAULT,
															 StatusCode.SAISHINSEI_FLG_SAISHINSEIZUMI};	//再申請フラグ（初期値、再申請済み）
			statusInfo.addOrQuery(StatusCode.STATUS_2nd_SHINSA_KANRYO, saishinseiArray);					//「2次審査完了」:12
			searchInfo.setStatusSearchInfo(statusInfo);
			searchInfo.setKekka2(StatusCode.KEKKA2_HOKETU_ARRAY);											//審査結果：「補欠1～5」
		}

		//作成日（開始日）
		if(!searchForm.getSakuseiDateFromYear().equals("")){	
			searchInfo.setSakuseiDateFrom(								
									searchForm.getSakuseiDateFromYear() + "/" +
									searchForm.getSakuseiDateFromMonth() + "/" +
									searchForm.getSakuseiDateFromDay()
									);
		}
		//作成日（終了日）
		if(!searchForm.getSakuseiDateToYear().equals("")){
			searchInfo.setSakuseiDateTo(									
									searchForm.getSakuseiDateToYear() + "/" +
									searchForm.getSakuseiDateToMonth()+ "/" +
									searchForm.getSakuseiDateToDay()
									);
		}
		//所属機関承認日（開始日）
		if(!searchForm.getShoninDateFromYear().equals("")){
			searchInfo.setShoninDateFrom(									
									searchForm.getShoninDateFromYear() + "/" +
									searchForm.getShoninDateFromMonth() + "/" +
									searchForm.getShoninDateFromDay()
									);
		}
		//所属機関承認日（終了日）
		if(!searchForm.getShoninDateToYear().equals("")){
			searchInfo.setShoninDateTo(										
									searchForm.getShoninDateToYear() + "/" +
									searchForm.getShoninDateToMonth()+ "/" +
									searchForm.getShoninDateToDay()
									);
		}
		//申請番号
		if(!searchForm.getUketukeNo().equals("")){		
			searchInfo.setUketukeNo(searchForm.getUketukeNo());		
		}
		//細目番号
		if(!searchForm.getBunkaSaimokuCd().equals("")){		
			searchInfo.setBunkasaimokuCd(searchForm.getBunkaSaimokuCd());		
		}
		
		//研究組織表と申請データテーブルでカラム名がかぶるため、明示的にテーブル接頭辞をセットする
		searchInfo.setTablePrefix("A");
		
		if("0".equals(searchForm.getHyojiSentaku())){								//表示形式（研究種目毎に表示）
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--事業ID順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--申請番号順
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--システム受付番号順
		}else if("1".equals(searchForm.getHyojiSentaku())){							//表示形式（申請者毎に表示）
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_NAME_KANA_SEI);		//--申請者名（カナ－姓）順				
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_NAME_KANA_MEI);		//--申請者名（カナ－名）順
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHINSEISHA_ID);                  //--申請者ID順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--事業ID順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--申請番号順
		}else if("2".equals(searchForm.getHyojiSentaku())){							//表示形式（機関毎に表示）
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SHOZOKU_CD);						//--所属機関順			
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--事業ID順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--申請番号順
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--システム受付番号順
		}else if("3".equals(searchForm.getHyojiSentaku())){							//表示形式（系等の区分毎に表示）
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_KEI_NAME_NO);					//--系等の区分番号順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--事業ID順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--申請番号順
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--システム受付番号順
		}else if("4".equals(searchForm.getHyojiSentaku())){							//表示形式（推薦の観点番号毎に表示）
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_KANTEN_NO);						//--推薦の観点番号順				
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_JIGYO_ID);						//--事業ID順
			searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_UKETUKE_NO);						//--申請番号順
            searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SYSTEM_NO);                  	//--システム受付番号順
		}
		//研究組織表のシーケンス号を追加　2007/3/29
		searchInfo.setTablePrefix("B");
        searchInfo.setOrder(ShinseiSearchInfo.ORDER_BY_SEQ_NO);                  		//--シーケンス号順
		//-------▲

		//検索実行
		List result =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchKenkyuSoshikiCsvData(
				container.getUserInfo(),
				searchInfo);
		
		DownloadFileUtil.downloadCSV(request, response, result, filename);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
