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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.FileIOException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.util.DownloadFileUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.util.FileUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 事業管理情報のファイルダウンロードアクションクラス。
 * 申請内容ファイル（Win）、申請内容ファイル（Mac）、評価ファイルをダウンロードする。
 * ファイル情報は、FileResorceから取得する。
 * 
 * ID RCSfile="$RCSfile: DownloadFileAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class DownloadFileAction extends BaseAction {


	/** ダウンロードファイルフラグ。添付ファイル（Win） */
	public static String FILE_FLG_TENPU_WIN = "0";
	
	/** ダウンロードファイルフラグ。添付ファイル（Mac） */
	public static String FILE_FLG_TENPU_MAC = "1";
	
	/** ダウンロードファイルフラグ。評価用ファイル */
	public static String FILE_FLG_HYOKA = "2";

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

			//------ファイルフラグを取得
			JigyoKanriForm downloadForm = (JigyoKanriForm)form;			
			String downloadFileFlg = downloadForm.getDownloadFileFlg();
						
			//------ファイルパスを取得
			JigyoKanriInfo downloadInfo = container.getJigyoKanriInfo();				
			FileResource fileRes = null;
			if(downloadFileFlg.equals(FILE_FLG_TENPU_WIN)){
				fileRes = downloadInfo.getTenpuWinFileRes();//添付ファイル（Win）
				
				// 2005/04/25 追加 ここから----------------------------------------------
				// 理由 登録済みファイルを確認画面でも表示するため
				if(downloadInfo.isDelWinFileFlg() == false && (downloadInfo.getTenpuWin() != null && !downloadInfo.getTenpuWin().equals(""))){
					try {
						fileRes = FileUtil.readFile(new File(downloadInfo.getTenpuWin()));
					} catch (FileNotFoundException e) {
						throw new FileIOException(
								"ファイルが見つかりませんでした。",			
								e);
					} catch (IOException e) {
						throw new FileIOException(
								"ファイルの入出力中にエラーが発生しました。",
								e);
					}
				}
				// 追加 ここまで---------------------------------------------------------
			
			}else if(downloadFileFlg.equals(FILE_FLG_TENPU_MAC)){
				fileRes = downloadInfo.getTenpuMacFileRes();//添付ファイル（Mac）
				
				// 2005/04/25 追加 ここから----------------------------------------------
				// 理由 登録済みファイルを確認画面でも表示するため
				if(downloadInfo.isDelMacFileFlg() == false && (downloadInfo.getTenpuMac() != null && !downloadInfo.getTenpuMac().equals(""))){
					try {
						fileRes = FileUtil.readFile(new File(downloadInfo.getTenpuMac()));
					} catch (FileNotFoundException e) {
						throw new FileIOException(
								"ファイルが見つかりませんでした。",			
								e);
					} catch (IOException e) {
						throw new FileIOException(
								"ファイルの入出力中にエラーが発生しました。",
								e);
					}
				}
				// 追加 ここまで---------------------------------------------------------

			}else if(downloadFileFlg.equals(FILE_FLG_HYOKA)){
				fileRes = downloadInfo.getHyokaFileRes();//評価用ファイル
				
				// 2005/04/25 追加 ここから----------------------------------------------
				// 理由 登録済みファイルを確認画面でも表示するため
				if(downloadInfo.getHyokaFileRes() == null || downloadInfo.getHyokaFileRes().getBinary()==null || downloadInfo.getHyokaFileRes().getBinary().length <= 0){
					try {
						fileRes = FileUtil.readFile(new File(downloadInfo.getHyokaFile()));
					} catch (FileNotFoundException e) {
						throw new FileIOException(
								"ファイルが見つかりませんでした。",			
								e);
					} catch (IOException e) {
						throw new FileIOException(
								"ファイルの入出力中にエラーが発生しました。",
								e);
					}
				}
				// 追加 ここまで---------------------------------------------------------
			}
		
			//------ファイルをダウンロード
			if(fileRes != null){
//				DownloadFileUtil.downloadFile(
//						response,
//						fileRes,
//						FileUtil.CONTENT_TYPE_MS_WORD
//						);
				DownloadFileUtil.downloadFile(
						response,
						fileRes
						);
			}else{
				throw new FileIOException(
					"ファイルの入出力中にエラーが発生しました。fileRes'" + fileRes +"'" );				
			}

			//-----画面遷移（定型処理）-----
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
			return forwardSuccess(mapping);
	}
}
