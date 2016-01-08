/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/04/11
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseiJohoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請情報検索前アクションクラス。
 * 申請情報検索画面を表示する。
 * 
 */
public class SearchAction extends BaseAction {


	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
				
		//宣言
		ShinseiSearchForm searchForm = new ShinseiSearchForm();

		//コンボデータの取得
		//事業名
		searchForm.setJigyoList(LabelValueManager.getJigyoNameList(container.getUserInfo()));
		//申請状況
		searchForm.setJokyoList(LabelValueManager.getJokyoList());
		//表示方式
		searchForm.setHyojiHoshikiList(LabelValueManager.getShinseishoHyojiList());

		//検索条件をフォームをセットする。
		updateFormBean(mapping,request,searchForm);

		return forwardSuccess(mapping);
	}

}
