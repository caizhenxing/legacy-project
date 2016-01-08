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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 審査結果入力情報用のファイルダウンロードアクションクラス。
 * 登録済みの評価ファイルをダウンロードする。
 * 
 * ID RCSfile="$RCSfile: DownloadRegHyokaFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
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
					IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).getHyokaFileRes(
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
