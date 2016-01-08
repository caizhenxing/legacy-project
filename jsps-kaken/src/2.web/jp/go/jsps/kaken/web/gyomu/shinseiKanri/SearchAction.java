/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchAction.java
 *    Description : 申請情報検索前アクションクラス
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請情報検索前アクションクラス。
 * 申請情報検索画面を表示する。
 * 
 * ID RCSfile="$RCSfile: SearchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class SearchAction extends BaseAction {

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

		//検索条件があればクリアする。
		removeFormBean(mapping,request);
		
		//検索条件をフォームをセットする。
		ShinseiSearchForm searchForm = new ShinseiSearchForm();

		//事業リストの取得（担当する事業区分のみ）
		UserInfo userInfo = container.getUserInfo();
		List jigyoList = LabelValueManager.getJigyoNameList(userInfo);
		
		//------プルダウンデータセット
		searchForm.setJigyoNameList(jigyoList);											//事業名リスト
		searchForm.setKantenList(LabelValueManager.getKantenList());					//推薦の観点リスト
		searchForm.setJokyoList(LabelValueManager.getGyomuJokyoList());					//申請状況リスト
		searchForm.setHyojiSentakuList(LabelValueManager.getShinseiHyojiSentakuList());	//表示選択リスト

		//2005/9/2 リスト項目追加
		searchForm.setShinkiKeibetuList(LabelValueManager.getSinkiKeizokuFlgList(true));//新規継続リスト
// 2006/07/24 dyh update start 理由：getZennendoOboList方法変更したから
//        searchForm.setZennendoList(LabelValueManager.getZennendoOboList());        //前年度の応募リスト
		searchForm.setZennendoList(LabelValueManager.getZennendoOboList(true));		//前年度の応募リスト
// 2006/07/24 dyh update end
		searchForm.setBuntankinList(LabelValueManager.getBuntankinList());				//分担金の有無リスト
		searchForm.setKenkyuKubunList(LabelValueManager.getKenkyuKubunList());			//計画研究・公募研究・終了研究領域区分
		searchForm.setKaijiKiboList(LabelValueManager.getKaijiKiboList());				//開示希望の有無追加のため
		searchForm.setChouseiList(LabelValueManager.getChouseiList());					//調整班のリスト

		updateFormBean(mapping,request,searchForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}