/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 添付ファイルダウンロードアクションクラス。
 * ID RCSfile="$RCSfile: DownloadTenpuFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:48 $"
 */
public class DownloadTenpuFileAction extends BaseAction {

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

		//-----簡易申請書入力フォームの取得
		SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
		
		//サーバサービスの呼び出し（添付ファイル取得）
		ShinseiDataPk shinseiPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		FileResource tenpuFileRes = servise.getTenpuFileRes(container.getUserInfo(),shinseiPk);
		
		//-----クライアントへPDFファイルをダウンロードさせる
//2007/03/23 苗　修正ここから                
//		String contentType = DownloadFileUtil.CONTENT_TYPE_MS_WORD;
        String contentType = "";
        String fileType = tenpuFileRes.getPath().substring(tenpuFileRes.getPath().length() - 3);
        if ("pdf".equals(fileType)) {
            contentType = DownloadFileUtil.CONTENT_TYPE_PDF;
        } else if ("doc".equals(fileType)) {
            contentType = DownloadFileUtil.CONTENT_TYPE_MS_WORD;
        }
//2007/03/23　苗　修正ここまで        
		DownloadFileUtil.downloadFile(response, tenpuFileRes, contentType);
		
		//------フォーム情報の削除
		removeFormBean(mapping,request);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
