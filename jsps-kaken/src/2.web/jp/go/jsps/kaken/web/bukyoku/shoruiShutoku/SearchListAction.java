/*
 * 作成日: 2005/03/24
 *
 */
package jp.go.jsps.kaken.web.bukyoku.shoruiShutoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseSearchForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 書類管理情報検索アクションクラス。
 * 書類一覧画面を表示する。
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
			
			//## 更新不可のプロパティ(セッションに保持)　$!userContainer.shoruiKanriList.プロパティ名
			//##

			//-----ActionErrorsの宣言（定型処理）-----
			ActionErrors errors = new ActionErrors();
						
			//検索条件の取得
			BaseSearchForm searchForm = (BaseSearchForm)form;
			
			//-------▼ VOにデータをセットする。
			ShoruiKanriSearchInfo searchInfo = new ShoruiKanriSearchInfo();
			try {
				PropertyUtils.copyProperties(searchInfo, searchForm);
			} catch (Exception e) {
				log.error(e);
				throw new SystemException("プロパティの設定に失敗しました。", e);
			}

			
			//2005/04/30 修正 ----------------------------------------------ここから
			//理由 部局担当者も所属機関担当者の情報を表示する
			//対象（部局担当者)をセット
			//searchInfo.setTaishoId(ITaishoId.BUKYOKUTANTO);
			//対象（所属機関担当者)をセット
			searchInfo.setTaishoId(ITaishoId.SHOZOKUTANTO);
			//2005/04/30 修正 ----------------------------------------------ここまで
			
			//リストのみ表示させるため、ページ件数を1件にする。
			searchInfo.setPageSize(0);
			searchInfo.setMaxSize(0);
			//-------▲		
						
			//------キー情報を元に事業データ取得	
			//DB登録
			ISystemServise servise = getSystemServise(
							IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
			
			Page result = null;
			try{		
				result = servise.searchShoruiList(container.getUserInfo(), searchInfo);	
			}catch(NoDataFoundException e){
				//0件のページオブジェクトを生成
				result = Page.EMPTY_PAGE;
			}
			
			List shoruiList = new ArrayList();//書類リスト
			if(result.getList() != null && result.getList().size() != 0){
				//------事業ID別のリストにする。	
				Iterator iterator = result.getList().iterator();
				//1レコード分のデータ項目数くりかえす			
				String jigyoId = "";
				Map shoruiMap = null;//書類マップ			
				Map shoruiNameMap = null;//書類名マップ
				List shoruiNameList = null;//書類名リスト
				int count = 0;
				while(iterator.hasNext()){
					Map map = (Map)iterator.next();
					if(count <= 0){
						shoruiMap = new HashMap();	
/* 2005/03/25 削除 ここから------------------------------------------------------------------------
 * 理由 privateメソッド化
						//書類マップに事業ID、事業名、年度、受付期間（開始日）、受付期間（終了日）をセット
						shoruiMap.put("JIGYO_ID", map.get("JIGYO_ID"));
						shoruiMap.put("JIGYO_NAME", map.get("JIGYO_NAME"));
						shoruiMap.put("NENDO", map.get("NENDO"));
						shoruiMap.put("KAISU", map.get("KAISU"));						
						shoruiMap.put("UKETUKEKIKAN_START", map.get("UKETUKEKIKAN_START"));							
						shoruiMap.put("UKETUKEKIKAN_END", map.get("UKETUKEKIKAN_END"));
						
   削除 ここまで----------------------------------------------------------------------------------- */
						
/* 2005/03/25 追加 ここから------------------------------------------------------------------------
 * 理由 privateメソッド化 */
						
						//書類マップに事業ID、事業名、年度、受付期間（開始日）、受付期間（終了日）をセット
						shoruiMap = getShoruiMapFromResult(map);
						
/* 追加 ここまで----------------------------------------------------------------------------------- */
						
						//書類名マップに書類名、システム受付番号をセット
						shoruiNameMap = new HashMap();					
						shoruiNameMap.put("SHORUI_NAME", map.get("SHORUI_NAME"));
						shoruiNameMap.put("SYSTEM_NO", map.get("SYSTEM_NO"));
						
						//書類名リストに書類名マップをセット
						shoruiNameList = new ArrayList();
						shoruiNameList.add(shoruiNameMap);
						
						//事業ID格納用にセット
						jigyoId = (String)map.get("JIGYO_ID");
					}else{
						if(jigyoId.equals((String) map.get("JIGYO_ID"))){
							//--------事業IDが同じだったら					
							//書類名マップに書類名、システム受付番号をセット
							shoruiNameMap = new HashMap();	
							shoruiNameMap.put("SHORUI_NAME", map.get("SHORUI_NAME"));
							shoruiNameMap.put("SYSTEM_NO", map.get("SYSTEM_NO"));
							//書類名リストに書類名マップをセット
							shoruiNameList.add(shoruiNameMap);
						}else{
							//--------事業IDが異なったら				
							//書類マップに書類名リストを格納する
							shoruiMap.put("SHORUI_NAME", shoruiNameList);
							//書類名リストを初期化
							shoruiNameList = new ArrayList();	
							//書類リストに書類マップを追加
							shoruiList.add(shoruiMap);
							
							shoruiMap = new HashMap();	
							
/* 2005/03/25 削除 ここから------------------------------------------------------------------------
 * 理由 privateメソッド化
							//書類マップに事業ID、事業名、年度、受付期間（開始日）、受付期間（終了日）をセット
							shoruiMap.put("JIGYO_ID", map.get("JIGYO_ID"));
							shoruiMap.put("JIGYO_NAME", map.get("JIGYO_NAME"));
							shoruiMap.put("NENDO", map.get("NENDO"));
							shoruiMap.put("KAISU", map.get("KAISU"));				
							shoruiMap.put("UKETUKEKIKAN_START", map.get("UKETUKEKIKAN_START"));							
							shoruiMap.put("UKETUKEKIKAN_END", map.get("UKETUKEKIKAN_END"));
   削除 ここまで----------------------------------------------------------------------------------- */
							
/* 2005/03/25 追加 ここから------------------------------------------------------------------------
 * 理由 privateメソッド化 */
													
							//書類マップに事業ID、事業名、年度、受付期間（開始日）、受付期間（終了日）をセット
							shoruiMap = getShoruiMapFromResult(map);
													
/* 追加 ここまで----------------------------------------------------------------------------------- */
							
							//書類名マップに書類名、システム受付番号をセット
							shoruiNameMap = new HashMap();	
							shoruiNameMap.put("SHORUI_NAME", map.get("SHORUI_NAME"));
							shoruiNameMap.put("SYSTEM_NO", map.get("SYSTEM_NO"));
							//書類名リストに書類名マップをセット
							shoruiNameList.add(shoruiNameMap);
							//事業ID格納用にセット
							jigyoId = (String)map.get("JIGYO_ID");
						}
					}

					count++;
				}
				//後処理
				//書類マップに書類名を格納する
				shoruiMap.put("SHORUI_NAME", shoruiNameList);			
				//書類リストに書類マップを追加
				shoruiList.add(shoruiMap);
				result.setList(shoruiList);
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
	
	private Map getShoruiMapFromResult(Map map){
		Map shoruiMap = null;//書類マップ

		//書類マップに事業ID、事業名、年度、受付期間（開始日）、受付期間（終了日）をセット
		shoruiMap = new HashMap();
		shoruiMap.put("JIGYO_ID", map.get("JIGYO_ID"));
		shoruiMap.put("JIGYO_NAME", map.get("JIGYO_NAME"));
		shoruiMap.put("NENDO", map.get("NENDO"));
		shoruiMap.put("KAISU", map.get("KAISU"));
		shoruiMap.put("UKETUKEKIKAN_START", map.get("UKETUKEKIKAN_START"));
		shoruiMap.put("UKETUKEKIKAN_END", map.get("UKETUKEKIKAN_END"));
		
		return shoruiMap;
	}

}
