/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : EditCheckAction.java
 *    Description : 事業管理情報更新前アクションクラス
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2006/06/13    V1.0        DIS.liyh       修正
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoCd;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;


/**
 * 更新された事業管理情報の入力チェックを行う。
 * 事業管理修正情報値オブジェクトを作成する。
 * 修正確認画面を表示する。 
 * 
 * ID RCSfile="$RCSfile: EditCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class EditCheckAction extends BaseAction {

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

		//------修正登録フォーム情報の取得
		JigyoKanriForm editForm = (JigyoKanriForm) form;
		
		//------セッションより更新対象情報の取得
		JigyoKanriInfo editInfo = container.getJigyoKanriInfo();
		if("7".equals(editInfo.getJigyoKubun()))
		{
			if(editForm.getMeiboDateYear()==null||editForm.getMeiboDateYear().length()==0)
			{
				errors.add(null, new ActionError("errors.2002", "研究者名簿登録最終締切日"));
				saveErrors(request, errors);
				return forwardInput(mapping);
			}

		}
		
		//2005/04/24 追加 ここから------------------------------------------------------
		//理由 申請内容ファイルの必須チェックの追加
		String url = editForm.getDlUrl();
		FormFile win = editForm.getTenpuWinUploadFile();
		
		if((win == null || win.getFileSize() == 0) && (url==null || url.equals(""))){
			errors.add(null, new ActionError("errors.2002", "応募内容ファイル(WIN)を指定しない場合、URL"));
			
			if(editInfo.getTenpuWin() != null && !editInfo.getTenpuWin().equals("")){
				errors.add(null, new ActionError("errors.5037"));
			}
			saveErrors(request, errors);
			return forwardInput(mapping);
		}
		//追加 ここまで-----------------------------------------------------------------
		
		//-------▼ VOにデータをセットする。
		try {
			PropertyUtils.copyProperties(editInfo, editForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}	
		
		try {
			//サーバ入力チェック
			editInfo =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					editInfo,
					IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}
		
		//2005/04/24 追加 ここから------------------------------------------------------
		//理由 URLをinfoに格納するため
		String urlAddress = editForm.getUrlAddress();
		String title = editForm.getUrlTitle();
		if(urlAddress != null && !urlAddress.equals("")){
			editInfo.setUrlAddress(urlAddress);
		}
		if(title != null && !title.equals("")){
			editInfo.setUrlTitle(title);
		}
		//追加 ここまで-----------------------------------------------------------------

		
		//期間（期間）をセットする。（「年」*12+「ヶ月」）
		//------プルダウン・ラジオボタンセット
		//評価用ファイル有無をセットする。
		editInfo.setHyokaFileFlg(editForm.getHyokaFileFlg());
		
		DateUtil dateUtil = new DateUtil();
		//学振受付期間（開始）（String→Date)
		if(editForm.getUketukekikanStartYear().length() != 0){
			dateUtil.setCal(editForm.getUketukekikanStartYear(),editForm.getUketukekikanStartMonth(),editForm.getUketukekikanStartDay());
			editInfo.setUketukekikanStart(dateUtil.getCal().getTime());
		}else{
			editInfo.setUketukekikanStart(null);			
		}
		//学振受付期間（終了）（String→Date)
		if(editForm.getUketukekikanEndYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getUketukekikanEndYear(),editForm.getUketukekikanEndMonth(),editForm.getUketukekikanEndDay());
			editInfo.setUketukekikanEnd(dateUtil.getCal().getTime());
		}else{
			editInfo.setUketukekikanEnd(null);			
		}
		//研究者名簿登録最終締切日（String→Date)「年」が""でない場合のみセット
		if(editForm.getMeiboDateYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getMeiboDateYear(),editForm.getMeiboDateMonth(),editForm.getMeiboDateDay());
			editInfo.setMeiboDate(dateUtil.getCal().getTime());
		}else{
			editInfo.setMeiboDate(null);
		}
		
		//　2006/06/13　追加　李義華　ここから
		//仮領域番号発行締切日
		if(editForm.getKariryoikiNoEndDateYear().length() != 0 ){
			dateUtil.setCal(editForm.getKariryoikiNoEndDateYear(),editForm.getKariryoikiNoEndDateMonth(),editForm.getKariryoikiNoEndDateDay());
			editInfo.setKariryoikiNoEndDate(dateUtil.getCal().getTime());
		
			//仮領域番号発行締切日のチェック
			boolean hasErrSaiyo = false;
			int intBefore = 0;
			int intAfter = 0;
			
			// 学振受付期間（開始）以前のとき（当日含まない）エラー。
            if (editForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
                intBefore = editInfo.getKariryoikiNoEndDate().compareTo(
                        editInfo.getUketukekikanStart());
                if (intBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // 学振受付期間（終了）以後のとき（当日含まない）エラー。
                    intAfter = editInfo.getKariryoikiNoEndDate().compareTo(
                            editInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && intAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.2007",
                            "仮領域番号発行締切日", "学振受付期間（開始）", "学振受付期間（終了）"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
		}else{
			editInfo.setKariryoikiNoEndDate(null);			
		}
		//　2006/06/13　追加　李義華　ここまで
      
		//　2006/10/23　追加　易旭　ここから
		//利害関係入力締切日
		if(editForm.getRigaiEndDateYear().length() != 0 ){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getRigaiEndDateYear(),editForm.getRigaiEndDateMonth(),editForm.getRigaiEndDateDay());
			editInfo.setRigaiEndDate(dateUtil.getCal().getTime());
		}else{
			editInfo.setRigaiEndDate(null);
		}
		//　2006/10/23　追加　易旭　ここまで
		
		//　2006/07/10　追加　李義華　ここから
		//領域代表者確定締切日
		if(editForm.getRyoikiEndDateYear().length() != 0){
		    dateUtil.setCal(editForm.getRyoikiEndDateYear(),editForm.getRyoikiEndDateMonth(),editForm.getRyoikiEndDateDay());
		    editInfo.setRyoikiEndDate(dateUtil.getCal().getTime());
		    
		    //領域代表者確定締切日のチェック
		    boolean hasErrSaiyo = false;
		    int intBefore = 0;
		    int intAfter = 0;
		    
		    // 仮領域番号発行締切日以前のとき（当日含まない）エラー。
            if (editForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
		        intBefore = editInfo.getRyoikiEndDate().compareTo(editInfo.getKariryoikiNoEndDate());
            
                if (intBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // 学振受付期間（終了）以後のとき（当日含まない）エラー。
                    intAfter = editInfo.getRyoikiEndDate().compareTo(editInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && intAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.2007",
                            "領域代表者確定締切日", "仮領域番号発行締切日", "学振受付期間（終了）"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
		}else{
		    editInfo.setRyoikiEndDate(null);			
		}
		//　2006/07/10　追加　李義華　ここまで
		
		//審査期限（String→Date)
		if(editForm.getShinsaKigenYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getShinsaKigenYear(),editForm.getShinsaKigenMonth(),editForm.getShinsaKigenDay());
			editInfo.setShinsaKigen(dateUtil.getCal().getTime());
		}else{
			editInfo.setShinsaKigen(null);			
		}
		
		//2005/04/24 追加 ここから------------------------------------------------------
		//理由 ダウンロードURLの追加
		editInfo.setDlUrl(null);
		
		if((win == null || win.getFileSize() == 0) && url != null && !url.equals("")){	
			editInfo.setDlUrl(url);
		}
		//追加 ここまで----------------------------------------------------------
		
		//-------アップロードファイルをセット
		//---添付ファイル（Win）
		FormFile tenpuWinFile = editForm.getTenpuWinUploadFile();		
		if(tenpuWinFile != null && tenpuWinFile.getFileSize() != 0){
			FileResource tenpuWinFileRes = createFileResorce(tenpuWinFile);	
			editInfo.setTenpuWinFileRes(tenpuWinFileRes);
		}else{
			// 2005/04/26 追加 ここから----------------------------------------------
			// 新しいファイルを指定しないとき、パスを空にする
			if(editInfo.getTenpuWin() != null && !editInfo.getTenpuWin().equals("")){
				editInfo.setDelWinFileFlg(true);
			}
			// 追加 ここまで---------------------------------------------------------
			editInfo.setTenpuWinFileRes(null);
		}
		//---添付ファイル（Mac）
		FormFile tenpuMacFile = editForm.getTenpuMacUploadFile();
		
		//2005/04/21 修正 ここから-----------------------------------------------
		//URLを指定した場合はMACのファイルを無視するため、URLを指定したかどうかの条件を追加
		if(tenpuMacFile != null && tenpuMacFile.getFileSize() != 0
			&& (editInfo.getDlUrl() == null || editInfo.getDlUrl().equals(""))){
			FileResource tenpuMacFileRes = createFileResorce(tenpuMacFile);	
			editInfo.setTenpuMacFileRes(tenpuMacFileRes);				
		}else{
			// 2005/04/26 追加 ここから----------------------------------------------
			// 新しいファイルを指定しないとき、パスを空にする
			if(editInfo.getTenpuMac() != null && !editInfo.getTenpuMac().equals("")){
				editInfo.setDelMacFileFlg(true);
			}
			// 追加 ここまで---------------------------------------------------------
			editInfo.setTenpuMacFileRes(null);			
		}
		//修正 ここまで----------------------------------------------------------

		
		//2005/04/24 修正 ここから-----------------------------------------------
		//URLを指定した場合はMACのファイルを無視するため、URLを指定したかどうかの条件を追加
//		if(tenpuWinFile != null && tenpuWinFile.getFileSize() != 0){
//			if(tenpuMacFile != null && tenpuMacFile.getFileSize() != 0){
//				FileResource tenpuMacFileRes = createFileResorce(tenpuMacFile);	
//				editInfo.setTenpuMacFileRes(tenpuMacFileRes);
//			}else{
//				editInfo.setTenpuMacFileRes(null);
//			}
//		}else{
//			
//			editInfo.setTenpuMacFileRes(null);
//		}
		//修正 ここまで----------------------------------------------------------
		
		//---評価用ファイル
		FormFile hyokaFile = editForm.getHyokaUploadFile();	
		if(hyokaFile != null && hyokaFile.getFileSize() != 0){
			FileResource hyokaFileRes = createFileResorce(hyokaFile);	
			editInfo.setHyokaFileRes(hyokaFileRes);
		}else{
			editInfo.setHyokaFileRes(null);				
		}
		//-------▲
		
		//-----セッションに事業管理情報を登録する。
		container.setJigyoKanriInfo(editInfo);

		//------修正確認フォームにセットする。
		updateFormBean(mapping,request,editForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
	
	/**
	 * @param file アップロードファイル
	 * @return ファイルリソース
	 */
	private FileResource createFileResorce(FormFile file)
								 throws ApplicationException {
		FileResource fileRes = null;
		try{	
			if(file != null){
				fileRes = new FileResource();
				fileRes.setPath(file.getFileName());	//ファイル名
				fileRes.setBinary(file.getFileData());	//ファイルサイズ
			}
			return fileRes;
		}catch(IOException e){
			throw new ApplicationException(
				"添付ファイルの取得に失敗しました。",
				new ErrorInfo("errors.7000"),
				e);
		}		
	}
}
