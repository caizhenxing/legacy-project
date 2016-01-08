/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 書類ファイルのダウンロードアクションクラス。
 * 書類ファイルをダウンロードする。
 * 
 * ID RCSfile="$RCSfile: DownloadShoruiFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class DownloadShoruiFileAction extends BaseAction {

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

			//------新規登録フォーム情報の作成
			ShoruiKanriForm addForm = (ShoruiKanriForm)form;
			
			//キー情報を取得
			String systemNo = addForm.getSystemNo();//システム番号
  			ShoruiKanriPk searchInfo = new ShoruiKanriInfo();
			searchInfo.setSystemNo(systemNo);//システム番号

			//ファイルリソースを取得
			FileResource fileRes  =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).getShoruiKanriFileRes(
					container.getUserInfo(),
					searchInfo);
		
			//ファイルのダウンロード
			//DownloadFileUtil.downloadFile(response, fileRes, DownloadFileUtil.CONTENT_TYPE_PDF);
			DownloadFileUtil.downloadFile(response, fileRes);
			
			//-----画面遷移（定型処理）-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}

			return forwardSuccess(mapping);
	}
}
