/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchListAction.java
 *    Description : 審査状況検索アクションクラス
 *
 *    Author      : Admin
 *    Date        : 2004/01/27
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/01/27    V1.0        Admin          新規作成
 *    2006/07/03    V1.1        DIS.dyh        変更
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinsaJokyo;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.ShinsaJokyoSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 審査状況検索アクションクラス。
 * 審査状況一覧画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchListAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:16 $"
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
		ShinsaJokyoSearchForm searchForm = (ShinsaJokyoSearchForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinsaJokyoSearchInfo searchInfo = new ShinsaJokyoSearchInfo();
		
//		searchInfo.setValues(searchForm.getValues());
		searchInfo.setShinsainNo(searchForm.getShinsainNo());           //審査員番号
		searchInfo.setNameKanjiSei(searchForm.getNameKanjiSei());       //審査員名（漢字-氏）
		searchInfo.setNameKanjiMei(searchForm.getNameKanjiMei());       //審査員名（漢字-名）
		searchInfo.setNameKanaSei(searchForm.getNameKanaSei());         //審査員名（フリガナ-氏）
		searchInfo.setNameKanaMei(searchForm.getNameKanaMei());         //審査員名（フリガナ-名）
// 2006/07/03 dyh add start
        searchInfo.setShozokuName(searchForm.getShozokuName());         //審査員所属研究機関名
// 2006/07/03 dyh add end
		searchInfo.setShozokuCd(searchForm.getShozokuCd());             //応募者所属研究機関番号
		searchInfo.setNendo(searchForm.getNendo());                     //年度
		searchInfo.setKaisu(searchForm.getKaisu());                     //回数
		searchInfo.setKeiName(searchForm.getKeiName());                 //系等の区分
		searchInfo.setRigaiJokyo(searchForm.getRigaiJokyo());			//利害関係入力完了状況　2007/5/8追加
		searchInfo.setShinsaJokyo(searchForm.getShinsaJokyo());         //審査状況
		searchInfo.setLoginDate(searchForm.getLoginDate());				//最終ログイン日　追加2005/10/24
		searchInfo.setRigaiKankeisha(searchForm.getRigaiKankeisha());	//利害関係者追加2005/10/25
		searchInfo.setSeiriNo(searchForm.getSeiriNo());					//整理番号（学創用）を追加	2005/11/2
		//2005.11.03 iso 表示方式を追加
		searchInfo.setHyojiHoshikiShinsaJokyo(searchForm.getHyojiHoshikiShinsaJokyo());
		//2005.05.18 iso アクセス管理を事業区分→事業CDに変更
		//チェックボックスの事業コードリストをセット
		if(!searchForm.getValues().isEmpty()){
			searchInfo.setValues(searchForm.getValues());	
		}else{
			//割り振りの検索では、ここで審査担当区分をセットしているが、
			//審査状況管理では、ShinsaJokyoKakuninクラスで行っている。
			//動作が違うので注意。
			if(container.getUserInfo().getRole().equals(UserRole.GYOMUTANTO)){
				GyomutantoInfo gyomutantoInfo = container.getUserInfo().getGyomutantoInfo(); 
				searchInfo.setValues(new ArrayList(gyomutantoInfo.getTantoJigyoCd()));
			}
		}

		//ページ制御
		searchInfo.setStartPosition(searchForm.getStartPosition());
		searchInfo.setPageSize(searchForm.getPageSize());
		searchInfo.setMaxSize(searchForm.getMaxSize());

		//検索実行
		Page result =
			getSystemServise(
				IServiceName.SHINSAJOKYO_KAKUNIN_SERVICE).search(
				container.getUserInfo(),
				searchInfo);

		//2005.11.07 iso 件数一覧で使わないので、vmで実行
//		List list = result.getList();
//		for(int i = 0; i < result.getSize(); i++) {
//			HashMap hashMap = (HashMap)list.get(i);
//			//第1回は表示しないので空にする
//			String kaisu = hashMap.get("KAISU").toString();
//			if(kaisu != null && kaisu.equals("1")) {
//				hashMap.put("KAISU", null);
//			}
//		}

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
