/*======================================================================
 *    SYSTEM      : 電子申請システム（科学研究費補助金）
 *    Source name : SearchTokuteiAction
 *    Description : チェックリスト検索画面表示アクション
 *
 *    Author      : Horikoshi
 *    Date        : 2005/06/06
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IJigyoKubun;

/**
 * チェックリスト検索画面表示アクション
 */
public class SearchTokuteiAction extends BaseAction {
//delete start dyh 原因：使用しない
//// 20050606 Start
//	private static final String JIGYOU_TOKUTEI = "5";
//// Horikoshi End
//delete end dyh

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//検索条件があればクリアする。
		removeFormBean(mapping,request);
		
		//検索条件をフォームをセットする。
		CheckListSearchForm searchForm = new CheckListSearchForm();
		
		//------プルダウンデータセット
// 20050606 Start
//		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(),"4"));
		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container
                .getUserInfo(), IJigyoKubun.JIGYO_KUBUN_TOKUTEI));
// Horikoshi End
// 2006/06/16 dyh add start 原因：メニュー仕様変更
        searchForm.setJigyoCd(IJigyoCd.JIGYO_CD_TOKUTEI_KEIZOKU);
// 2006/06/16 dyh add end

		//2005/04/21 追加ここから--------------------------
		//理由 受理状況のリスト追加
		searchForm.setJuriList(LabelValueManager.getJuriJokyoList());
		//追加 ここまで------------------------------------
		
		updateFormBean(mapping,request,searchForm);

		return forwardSuccess(mapping);
	}
}