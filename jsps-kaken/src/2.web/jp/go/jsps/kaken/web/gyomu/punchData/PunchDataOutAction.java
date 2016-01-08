/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（JSPS)
 *    Source name : PunchDataOutAction.java
 *    Description : パンチデータダウンロードアクション
 *
 *    Author      : 
 *    Date        : 2004/10/18
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.UserInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PunchDataOutAction extends BaseAction{
	
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
		PunchDataForm punchdataform = (PunchDataForm)form;
			  
		//ユーザ情報の獲得
		UserInfo userInfo = container.getUserInfo();

		//サービスオブジェクトを取得する
		ISystemServise service =  getSystemServise(
							IServiceName.PUNCHDATA_MAINTENANCE_SERVICE);
	
		FileResource fileResource = null; 
	
		//PunchDataMaintenaceからパンチデータファイルを受け取る
		try {
			 fileResource = service.getPunchDataResource(userInfo, punchdataform.getPunchShubetu());
		} catch (ApplicationException e) {
			e.printStackTrace();
			throw e;
		}
	
		DownloadFileUtil.downloadFile(response, fileResource);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
