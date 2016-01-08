/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchListAction.java
 *    Description : 割り振り結果情報一覧画面を表示する
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.warifuri;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.jigyoKubun.JigyoKubunFilter;
import jp.go.jsps.kaken.model.*;
import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.WarifuriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 割り振り結果検索アクションクラス。
 * 割り振り結果情報一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
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

		//-----キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//検索条件の取得
		WarifuriSearchForm searchForm = (WarifuriSearchForm)form;
		
		//-------▼ VOにデータをセットする。
		WarifuriSearchInfo searchInfo = new WarifuriSearchInfo();
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		//チェックボックスの事業コードリストをセット
		if(!searchForm.getValueList().isEmpty()){
			searchInfo.setJigyoCdValueList(searchForm.getValueList());	
		}else{
			//指定されていない場合は、（業務担当者ならば）自分が担当する事業区分から審査対象分の事業区分のみ取得	
			Set shinsaTaishoSet = JigyoKubunFilter.getShinsaTaishoJigyoKubun(container.getUserInfo());
			//審査対象分の事業区分を検索条件にセット
			searchInfo.setTantoJigyoKubun(shinsaTaishoSet);
			//2005.05.17 iso アクセス管理が事業区分→事業CDにかわったので追加
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setJigyoCdValueList(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}
		
		//サービス取得
		ISystemServise servise = getSystemServise(IServiceName.SHINSAIN_WARIFURI_SERVICE);
		
		//-----1申請に割り振られる審査員数
		int shinsainCount = 0;
		//TODO 2段階審査　下に審査員人数は、学創6?と基盤12?で分ける。(事業コードから取得する)
		String jigyoKubun = servise.selectJigyoKubun(container.getUserInfo(),
													(String)searchForm.getValues(0));
		
		if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_GAKUSOU_HIKOUBO)){
			//学術創成の場合
			shinsainCount = IShinsainWarifuri.SHINSAIN_NINZU_GAKUSOU;
		}else{ // if(jigyoKubun.equals(IJigyoKubun.JIGYO_KUBUN_KIBAN)){
			//基盤と若手スタートアップの場合 2006/04/10修正
			shinsainCount = IShinsainWarifuri.SHINSAIN_NINZU_KIBAN;
		}

		//-----1申請×割り振られる審査員数分だけDBより取得する
		searchInfo.setStartPosition(searchForm.getStartPosition() * shinsainCount);
		searchInfo.setPageSize(searchForm.getPageSize() * shinsainCount);
		searchInfo.setMaxSize(searchForm.getMaxSize() * shinsainCount);

		//検索実行
		Page result =servise.search(container.getUserInfo(), searchInfo);

		int countShinsain = 0;							//同事業内での審査員数をカウントする変数
		int rowSize = 0;								//ダミー審査員を除いたrowのサイズ		2005.10.28 iso 追加
		int rigaiSize = 0;								//審査完了し、利害関係ありの審査員数
		int dairiSize = 0;								//代理審査員数
		List list = result.getList();
		List newList = new ArrayList();				//事業ごとに編成しなおすList
		List shinsainList = new ArrayList();			//同事業の審査員情報(HashMap)をまとめるList
        
		for(int i = 0; i < result.getSize(); i++) {
			HashMap hashMap = (HashMap)list.get(i);
			String systemNo = hashMap.get("SYSTEM_NO").toString();
			
			String nextSystemNo;
			if(i == result.getSize()-1) {
				nextSystemNo = "Not match";
			} else {
				HashMap nextHashMap = (HashMap)list.get(i+1);
				nextSystemNo = nextHashMap.get("SYSTEM_NO").toString();
			}

			countShinsain++;
			
			//2005.10.28 iso 2次割り振り対応
			//基本はダミーの審査員を出さないよう
			if(!hashMap.get("SHINSAIN_NO").toString().startsWith("@")) {
				rowSize++;
			}
//2006/10/27 modify by liucy start            
            // 2段階審査 利害関係者人数分、ダミーデータに代打フラグを立てる。
            // if(hashMap.get("RIGAI") != null &&
            // "1".equals(hashMap.get("RIGAI"))
            // && "1".equals(hashMap.get("SHINSA_JOKYO"))){
            // rigaiSize++;
            // }

            // TODO 2段階審査 利害関係者人数分、ダミーデータに代打フラグを立てる。
            if (hashMap.get("RIGAI") != null
                    && "1".equals(hashMap.get("RIGAI"))
                    && "1".equals(hashMap.get("NYURYOKU_JOKYO"))) {
                rigaiSize++;
            }
//2006/10/27 modify by liucy end 
			//代理審査員のカウント
			if(hashMap.get("DAIRI") != null && "1".equals(hashMap.get("DAIRI"))){
				dairiSize++;
			}
			
			//審査員情報は審査員HashMapに移動
			HashMap shinsainMap = new HashMap();
			shinsainMap.put("ROWSHINSAIN", new Integer(countShinsain));						//同事業内で何行目の審査員か？　1人目：1
			shinsainMap.put("SHINSAIN_NO", hashMap.get("SHINSAIN_NO"));							//審査員番号
			shinsainMap.put("JIGYO_KUBUN", hashMap.get("JIGYO_KUBUN"));							//事業区分（審査結果）
			shinsainMap.put("SHINSAIN_NAME_KANJI_SEI", hashMap.get("SHINSAIN_NAME_KANJI_SEI"));	//審査員氏名（漢字-姓）
			shinsainMap.put("SHINSAIN_NAME_KANJI_MEI", hashMap.get("SHINSAIN_NAME_KANJI_MEI"));	//審査員氏名（漢字-名）
			shinsainMap.put("SHINSAIN_SHOZOKU_NAME", hashMap.get("SHINSAIN_SHOZOKU_NAME"));		//審査員所属機関名
			shinsainMap.put("SHINSAIN_BUKYOKU_NAME", hashMap.get("SHINSAIN_BUKYOKU_NAME"));		//審査員部局名
			shinsainMap.put("SHINSAIN_SHOKUSHU_NAME", hashMap.get("SHINSAIN_SHOKUSHU_NAME"));	//審査員職名
			shinsainMap.put("SHINSA_JOKYO", hashMap.get("SHINSA_JOKYO"));						//審査状況
			shinsainMap.put("KOSHIN_DATE", hashMap.get("KOSHIN_DATE"));							//割り振り更新日
//2006/11/14 苗　追加ここから
            shinsainMap.put("RIGAI", hashMap.get("RIGAI"));                         			//利害関係
            shinsainMap.put("NYURYOKU_JOKYO", hashMap.get("NYURYOKU_JOKYO"));                   //利害関係入力状況
//2006/11/14 苗　追加ここまで            

			//利害関係者人数は、利害フラグ・完了フラグの両方が立っている人数
			shinsainList.add(shinsainMap);			//同事業の審査員情報(HashMap)はshinsainListにまとめる

			//次のデータが違う事業なら、同事業の審査員情報をまとめ、新しいデータリストに格納
			if(systemNo != null && nextSystemNo != null && !systemNo.equals(nextSystemNo)) {
				
				//（表示させるダミーデータ数 = 審査完了&利害関係ありの審査員数 - 代理審査員数）
				int dairiCnt = rigaiSize - dairiSize;
				//表示させるダミーデータに代理フラグを立てる
				for(int k = 0; k < shinsainList.size(); k++){
					if(dairiCnt > 0 && ((HashMap)shinsainList.get(k)).get("SHINSAIN_NO").toString().startsWith("@")){
						((HashMap)shinsainList.get(k)).put("DAIRI_FLG", new Integer(1));
						dairiCnt--;
					}else{
						((HashMap)shinsainList.get(k)).put("DAIRI_FLG", new Integer(0));
					}
				}
				
				//事業内審査員情報はListとして格納するので、従来の審査員情報は削除
				List regShinsainList = new ArrayList(shinsainList);
				hashMap.put("SHINSAIN", regShinsainList);
				hashMap.remove("SHINSAIN_NO");				//審査員番号
//				hashMap.remove("JIGYO_KUBUN");				//事業区分（審査結果）
				hashMap.remove("SHINSAIN_NAME_KANJI_SEI");	//審査員氏名（漢字-姓）
				hashMap.remove("SHINSAIN_NAME_KANJI_MEI");	//審査員氏名（漢字-名）
				hashMap.remove("SHINSAIN_SHOZOKU_NAME");	//審査員所属機関名
				hashMap.remove("SHINSAIN_BUKYOKU_NAME");	//審査員部局名
				hashMap.remove("SHINSAIN_SHOKUSHU_NAME");	//審査員職名
				
				//rowSizeが取得した審査員数を超える場合、ROWSIZE = 12とする
				//2005.12.06 iso
				//審査代理人を割り当てた後、利害関係をつけた審査員が審査をやり直し「利害関係なし」にすると
				//rigaiSize - dairiSizeが負になり、実質減算となりROWSIZEが不足する。。
				//負になる場合は、減らさないよう修正 
//				rowSize = rowSize + (rigaiSize - dairiSize);
				if(rigaiSize - dairiSize > 0) {
//2006/11/24ダミーデータがない場合、代理フラグを立てない為、
//					rowSize = rowSize + (rigaiSize - dairiSize);
					rowSize = rowSize + (rigaiSize - dairiSize - dairiCnt);
				}
				if(rowSize > 12){
					hashMap.put("ROWSIZE", new Integer(12));		//2005.10.28 iso サイズを追加					
				}else{
					hashMap.put("ROWSIZE", new Integer(rowSize));	//2005.10.28 iso サイズを追加
				}

				newList.add(hashMap);
				shinsainList.clear();
				countShinsain = 0;
				rowSize = 0;			//2005.10.18 iso 追加
				rigaiSize = 0;			//2005.11.02 tanabe 追加
				dairiSize = 0;			//2005.11.04 tanabe 追加
			}
		}

		//-----トータル件数は申請書数をセットする（申請書数＝DBの検索レコード数／審査員数）
		Page newResult = new Page(newList, 
								  searchForm.getStartPosition(), 
								  searchForm.getPageSize(),
								  result.getTotalSize() / shinsainCount);

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO, newResult);
		
		//トークンをセッションに保存する。（削除の場合に、必要）
		saveToken(request);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}