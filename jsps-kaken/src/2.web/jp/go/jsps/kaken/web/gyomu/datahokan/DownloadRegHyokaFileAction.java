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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.util.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.shinsa.shinsaJigyo.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * 審査結果入力情報用のファイルダウンロードアクションクラス。
 * 登録済みの評価ファイルをダウンロードする。
 * 
 * ID RCSfile="$RCSfile: DownloadRegHyokaFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class DownloadRegHyokaFileAction extends BaseAction {

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

			//------検索条件を取得
			ShinsaKekkaForm downloadForm = (ShinsaKekkaForm)form;			

			ShinsaKekkaPk downloadPk = new ShinsaKekkaPk();			
			downloadPk.setSystemNo(downloadForm.getSystemNo());							//システム受付番号		
			downloadPk.setShinsainNo(downloadForm.getShinsainNo());						//審査員番号
			downloadPk.setJigyoKubun(downloadForm.getJigyoKubun());						//事業区分
			
			//9/15コメントアウト　業務担当者も利用するため、フォームから取得するように修正
//			downloadPk.setShinsainNo(container.getUserInfo().getShinsainInfo().getShinsainNo());		//審査員番号
//			downloadPk.setJigyoKubun(container.getUserInfo().getShinsainInfo().getJigyoKubun());		//事業区分	
						
			//ファイルリソースを取得
			FileResource fileRes  =
				getSystemServise(
					IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).getHyokaFileRes(
					container.getUserInfo(),
					downloadPk);
		
			//ファイルのダウンロード
			DownloadFileUtil.downloadFile(response, fileRes);						

			//-----画面遷移（定型処理）-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
	}
}
