/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : DownloadCoverAction.java
 *    Description : PDF表紙ファイルダウンロード。
 *
 *    Author      : Admin
 *    Date        : 2005/04/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/13    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * PDF表紙ファイルダウンロード
 * 
 * @author masuo_t
 */
public class DownloadCoverAction extends BaseAction {

	public ActionForward doMainProcessing(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response,
			UserContainer container)
			throws ApplicationException {
		
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

// 2006/07/21 dyh delete start 理由：使用しない
//		//contentTypeをPDFに指定 
//		String contentType = DownloadFileUtil.CONTENT_TYPE_PDF;
// 2006/07/21 dyh delete end

		//2005/05/25 削除 ここから----------------------------------------------------
		//表紙ファイルパスをDBから取得するため削除	
		//ApplicationSettingsから表紙ファイルのパス取得
		//String SHINSEI_COVER_FOLDER  = ApplicationSettings.getString(ISettingKeys.PDF_COVER);
							   
		//File file = new File(SHINSEI_COVER_FOLDER);
		//削除 ここまで--------------------------------------------------------------		
		
		//2005/05/25 追加 ここから----------------------------------------------------
		//表紙ファイルパスをDBから取得するため追加
		
		//-----簡易申請書入力フォームの取得
		CheckListForm checkListForm = (CheckListForm)form;
		
		//サーバサービスの呼び出し（表紙PDFファイル取得）
		CheckListSearchInfo checkListInfo = new CheckListSearchInfo();
		checkListInfo.setJigyoId(checkListForm.getJigyoId());
		checkListInfo.setShozokuCd(checkListForm.getShozokuCd());
		
		ISystemServise servise = getSystemServise(
						IServiceName.CHECKLIST_MAINTENANCE_SERVICE);
//		String filePath = servise.getPdfFilePath(container.getUserInfo(), checkListInfo);
//		
//		if(filePath == null || filePath.equals("")){
//			throw new FileIOException("表紙ファイルパスの取得に失敗しました。");
//		}
//		
//		File file = new File(filePath);
//		//追加 ここまで--------------------------------------------------------------
//			
//		//-----クライアントへPDFファイルをダウンロードさせる
//		boolean isDownload = DownloadFileUtil.downloadFile(response, file, contentType);		
//		
//		if(!isDownload){	
//			//ダウンロードできない場合はエラー画面へ遷移する
//			throw new FileIOException("表紙の取得に失敗しました。");
//		}
//		return null;

		FileResource fileRes = null;
		try {
			fileRes = servise.getPdfFile(container.getUserInfo(), checkListInfo);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
		}
		
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