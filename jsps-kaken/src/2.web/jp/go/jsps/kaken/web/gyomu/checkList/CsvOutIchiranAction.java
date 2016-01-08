/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CsvOutIchiranAction.java
 *    Description : CSV出力（一覧）アクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV出力アクションクラス。
 */
public class CsvOutIchiranAction extends BaseAction {
	
	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String filename = "LIST_CHECKLIST";

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

		CheckListSearchInfo searchInfo = container.getCheckListSearchInfo();

// 20050622
		CheckListSearchForm searchForm=(CheckListSearchForm)request.getSession().getAttribute("checkListSearchForm");
		searchInfo.setJigyoKubun(searchForm.getJigyoKbn());
//		searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
// Horikoshi

		//検索実行
		List result =
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).searchCsvDataIchiran(
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