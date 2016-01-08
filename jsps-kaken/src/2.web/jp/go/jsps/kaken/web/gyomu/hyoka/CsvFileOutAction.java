/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/01/28
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.hyoka;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.HyokaSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * CSV出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: CsvFileOutAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:19 $"
 */
public class CsvFileOutAction extends BaseAction {

	/**
	 * CSVファイル名の接頭辞。
	 */
	public static final String filename = "HYOKAICHIRAN";

	/* (非 Javadoc)
	 * @see jp.go.jsps.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.web.common.UserContainer)
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

		//検索条件の取得
		HyokaSearchForm searchForm = (HyokaSearchForm)form;
			
		//-------▼ VOにデータをセットする。
		HyokaSearchInfo searchInfo = new HyokaSearchInfo();
			
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//検索実行
		List result =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).searchCsvData(
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
