/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : AddCheckAction.java
 *    Description : 新規登録された事業管理情報の入力チェックを行う
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    v1.0        Admin          新規作成
 *    2006/06/15    v1.1        DIS.liuYang    変更
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
import jp.go.jsps.kaken.web.common.LabelValueManager;
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
 * 新規登録された事業管理情報の入力チェックを行う。
 * 事業管理登録情報値オブジェクトを作成する。
 * 登録確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: AddCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class AddCheckAction extends BaseAction {
	
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
		
		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//------新規登録フォーム情報の取得
		JigyoKanriForm addForm = (JigyoKanriForm) form;

		//-------▼ VOにデータをセットする。
		JigyoKanriInfo addInfo = new JigyoKanriInfo();			

		//2005/04/21 追加 ここから------------------------------------------------------
		//理由 申請内容ファイルの必須チェックの追加
		String url = addForm.getDlUrl();
		FormFile win = addForm.getTenpuWinUploadFile();
		if((win == null || win.getFileSize() == 0) && (url==null || url.equals(""))){
			errors.add(null, new ActionError("errors.2002", "応募内容ファイル(WIN)を指定しない場合、URL"));
			saveErrors(request, errors);
			return forwardInput(mapping);
		}
		//追加 ここまで-----------------------------------------------------------------

		//------事業管理情報に重複チェックのキーをセット
		addInfo.setJigyoName(LabelValueManager.getJigyoNameList(addForm.getJigyoCd()));		//事業名
		addInfo.setNendo(addForm.getNendo());		//年度
		addInfo.setKaisu(addForm.getKaisu());		//回数	
		
		//事業コード（事業マスタ検索用・事業ID作成用）をセットする。
		addInfo.setJigyoCd(addForm.getJigyoCd());	
		
		try {
			//サーバ入力チェック
			addInfo =
				getSystemServise(
					IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					addInfo,
					IMaintenanceName.ADD_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//ファイル再度選択エラーを追加
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("errors.2009"));
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}

		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
	
		//2005/04/21 追加 ここから------------------------------------------------------
		//理由 URLをinfoに格納するため
		if((win == null || win.getFileSize() == 0) && url != null && !url.equals("")){
			addInfo.setDlUrl(url);
		}else{
			addInfo.setDlUrl(null);
		}
		
		String urlAddress = addForm.getUrlAddress();
		String title = addForm.getUrlTitle();
		if(urlAddress != null && !urlAddress.equals("")){
			addInfo.setUrlAddress(urlAddress);
		}
		if(title != null && !title.equals("")){
			addInfo.setUrlTitle(title);
		}
		//追加 ここまで-----------------------------------------------------------------

		//期間（期間）をセットする。（「年」*12+「ヶ月」）
		//------String→Date変換
		DateUtil dateUtil = new DateUtil();
		//学振受付期間（開始）（String→Date)
		if(addForm.getUketukekikanStartYear().length() != 0){
			dateUtil.setCal(addForm.getUketukekikanStartYear(),
                    addForm.getUketukekikanStartMonth(),
                    addForm.getUketukekikanStartDay());
			addInfo.setUketukekikanStart(dateUtil.getCal().getTime());
		}
		//学振受付期間（終了）（String→Date)
		if(addForm.getUketukekikanEndYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getUketukekikanEndYear(),
                    addForm.getUketukekikanEndMonth(),
                    addForm.getUketukekikanEndDay());
			addInfo.setUketukekikanEnd(dateUtil.getCal().getTime());
		}	
		//研究者名簿登録最終締切日（String→Date)「年」が""でない場合のみセット
		if(addForm.getMeiboDateYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getMeiboDateYear(),
                    addForm.getMeiboDateMonth(),
                    addForm.getMeiboDateDay());
			addInfo.setMeiboDate(dateUtil.getCal().getTime());
		}
		// 2006/06/23 追加  易旭　ここから
	    // 利害関係入力締切日追加
	    if(addForm.getRigaiEndDateYear().length() != 0){
	    	dateUtil = new DateUtil();
	    	dateUtil.setCal(addForm.getRigaiEndDateYear(),
	    			addForm.getRigaiEndDateMonth(),
	    			addForm.getRigaiEndDateDay());
	    	addInfo.setRigaiEndDate(dateUtil.getCal().getTime());
	    }
	    // 2006/06/23 追加  易旭　ここまで


//2006/06/15 Add by liuYang start
		//仮領域番号発行締切日
		if(addForm.getKariryoikiNoEndDateYear().length() != 0) {
			dateUtil.setCal(addForm.getKariryoikiNoEndDateYear(),
                            addForm.getKariryoikiNoEndDateMonth(),
                            addForm.getKariryoikiNoEndDateDay());
			addInfo.setKariryoikiNoEndDate(dateUtil.getCal().getTime());
		
			//仮領域番号発行締切日
			boolean hasErrSaiyo = false;
			int iBefore = 0;
			int iAfter = 0;
			
			// 学振受付期間（開始）
            if (addForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
    			iBefore = addInfo.getKariryoikiNoEndDate()
                                 .compareTo(addInfo.getUketukekikanStart());
                if (iBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // 学振受付期間（終了）
                    iAfter = addInfo.getKariryoikiNoEndDate()
                                    .compareTo(addInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && iAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("errors.2007", "仮領域番号発行締切日",
                                    "学振受付期間（開始）", "学振受付期間（終了）"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
        }
        else {
            addInfo.setKariryoikiNoEndDate(null);
        }
//2006/06/15 Add liuYang end
        
//　2006/07/10　追加　李義華　ここからRyoiki  領域代表者確定締切日
        //領域代表者確定締切日
        if(addForm.getRyoikiEndDateYear().length() != 0) {
            dateUtil.setCal(addForm.getRyoikiEndDateYear(),
                            addForm.getRyoikiEndDateMonth(),
                            addForm.getRyoikiEndDateDay());
            addInfo.setRyoikiEndDate(dateUtil.getCal().getTime());
        
            //仮領域番号発行締切日以前のとき（当日含まない）エラー。
            boolean hasErrSaiyo = false;
            int iBefore = 0;
            int iAfter = 0;
            
            // 仮領域番号発行締切日
            if (addForm.getJigyoCd().equals(IJigyoCd.JIGYO_CD_TOKUTEI_SINKI)) {
                iBefore = addInfo.getRyoikiEndDate()
                                 .compareTo(addInfo.getKariryoikiNoEndDate());
                if (iBefore < 0) {
                    hasErrSaiyo = true;
                }
                else {
                    // 学振受付期間（終了）以後のとき（当日含まない）エラー。
                    iAfter = addInfo.getRyoikiEndDate()
                                    .compareTo(addInfo.getUketukekikanEnd());
                    if (hasErrSaiyo == false && iAfter > 0) {
                        hasErrSaiyo = true;
                    }
                }
                if (hasErrSaiyo) {
                    errors.add(ActionErrors.GLOBAL_ERROR,
                            new ActionError("errors.2007", "領域代表者確定締切日",
                                    "仮領域番号発行締切日", "学振受付期間（終了）"));
                    saveErrors(request, errors);
                    return forwardInput(mapping);
                }
            }
        }
        else {
            addInfo.setRyoikiEndDate(null);
        }
//　2006/07/10　追加　李義華　ここまで
		
		
		//審査期限（String→Date)「年」が""でない場合のみセット
		if(addForm.getShinsaKigenYear().length() != 0){
			dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getShinsaKigenYear(),
                            addForm.getShinsaKigenMonth(),
                            addForm.getShinsaKigenDay());
			addInfo.setShinsaKigen(dateUtil.getCal().getTime());
		}

		//-------アップロードファイルをセット
		//---添付ファイル（Win）
		FormFile tenpuWinFile = addForm.getTenpuWinUploadFile();		
		if(tenpuWinFile != null && tenpuWinFile.getFileSize() != 0){
			FileResource tenpuWinFileRes = createFileResorce(tenpuWinFile);	
			addInfo.setTenpuWinFileRes(tenpuWinFileRes);
		}else{
			addInfo.setTenpuWinFileRes(null);			
		}
		//---添付ファイル（Mac）
		FormFile tenpuMacFile = addForm.getTenpuMacUploadFile();
		//2005/04/21 修正 ここから-----------------------------------------------
		//URLを指定した場合はMACのファイルを無視するため、URLを指定したかどうかの条件を追加
		if(tenpuMacFile != null && tenpuMacFile.getFileSize() != 0
			    && (addInfo.getDlUrl() == null || addInfo.getDlUrl().equals(""))){
			FileResource tenpuMacFileRes = createFileResorce(tenpuMacFile);	
			addInfo.setTenpuMacFileRes(tenpuMacFileRes);				
		}else{
			addInfo.setTenpuMacFileRes(null);			
		}
		//修正 ここまで----------------------------------------------------------
		
		//---評価用ファイル
		FormFile hyokaFile = addForm.getHyokaUploadFile();	
		if(hyokaFile != null && hyokaFile.getFileSize() != 0){
			FileResource hyokaFileRes = createFileResorce(hyokaFile);	
			addInfo.setHyokaFileRes(hyokaFileRes);
		}else{
			addInfo.setHyokaFileRes(null);			
		}
		//-------▲

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);	
			return forwardFailure(mapping);
		}

		//-----セッションに申請者情報を登録する。
		container.setJigyoKanriInfo(addInfo);

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