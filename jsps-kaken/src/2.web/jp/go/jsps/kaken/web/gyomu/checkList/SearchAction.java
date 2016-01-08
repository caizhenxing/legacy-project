/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : SearchAction.java
 *    Description : チェックリスト検索画面表示アクション。
 *
 *    Author      : Admin
 *    Date        : 2005/04/12
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/12    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリスト検索画面表示アクション
 * 
 * @author masuo_t
 */
public class SearchAction extends BaseAction {


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
		String jigyoKbn = (String)request.getParameter("jigyoKbn");
//2006/06/02 追加ここから
//        searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(),jigyoKbn));
        if(IJigyoKubun.JIGYO_KUBUN_KIBAN.equals(jigyoKbn)){
            searchForm.setJigyoList(LabelValueManager.getJigyoNameListByJigyoCdsWithoutKikanSAB(container.getUserInfo(),jigyoKbn));
        } else {
            searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo(),jigyoKbn));
        }
//苗　追加ここまで        
	    searchForm.setJigyoKbn(jigyoKbn);
		//2005/04/21 追加ここから--------------------------
		//理由 受理状況のリスト追加
		searchForm.setJuriList(LabelValueManager.getJuriJokyoList());
		//追加 ここまで------------------------------------
		
		updateFormBean(mapping,request,searchForm);

		return forwardSuccess(mapping);
	}
}