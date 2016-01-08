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
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ID・パスワード通知書の作成
 */
public class CreateTsuchishoAction extends BaseAction {
	
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
		String[] kenkyuNo = container.getKenkyuNo();
		
		ArrayList array = new ArrayList();
		 
		//空データ以外でセット
		for(int i = 0; i < kenkyuNo.length; i++){
			if(kenkyuNo[i] != null && !kenkyuNo[i].equals("")){
				array.add(kenkyuNo[i]);
			}
		}
		String[] kenkyuNoData = new String[array.size()];
		for(int j = 0; j < array.size(); j++){
			kenkyuNoData[j] = (String)array.get(j);
		}
			
		//-----ビジネスロジック実行-----
		FileResource fileRes =
				getSystemServise(
					IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).createTsuchisho(
					container.getUserInfo(),
					kenkyuNoData);
		
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
