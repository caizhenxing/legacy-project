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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * 推薦書情報値オブジェクトを登録する。
 * フォーム情報、推薦書情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: SuisenshoAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class SuisenshoAddSaveAction extends BaseAction {

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

		//------新規登録フォーム情報の取得
		SuisenshoAddForm addForm = (SuisenshoAddForm) form;

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-------▼ VOにデータをセットする。
		ShinseiDataPk addPk = new ShinseiDataPk(addForm.getSystemNo());
		
		//---推薦書登録ファイル
		FormFile suisenshoFile = addForm.getSuisenshoUploadFile();	
		FileResource hyokaFileRes = createFileResorce(suisenshoFile);	
		//-------▲
		
		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		
		servise.registSuisenFile(container.getUserInfo(), addPk, hyokaFileRes);
				
		//------トークンの削除	
		resetToken(request);
		
		//------フォーム情報の削除
		removeFormBean(mapping, request);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
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
