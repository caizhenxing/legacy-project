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
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.commons.beanutils.*;
import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 審査依頼書（審査員管理用）出力アクションクラス。
 * 
 * ID RCSfile="$RCSfile: CreateIraishoAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class CreateIraishoAction extends BaseAction {
	
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
		ShinsainSearchForm searchForm = (ShinsainSearchForm)form;
			
		//-------▼ VOにデータをセットする。
		ShinsainSearchInfo searchInfo = new ShinsainSearchInfo();
			
		try {
			PropertyUtils.copyProperties(searchInfo, searchForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//-----ビジネスロジック実行-----
		FileResource fileRes =
				getSystemServise(
					IServiceName.SHINSAIN_MAINTENANCE_SERVICE).createIraisho(
					container.getUserInfo(),
					searchInfo);
		
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//-----ファイルのダウンロード
		DownloadFileUtil.downloadFile(response, fileRes);	
		return forwardSuccess(mapping);
		
	}

}
