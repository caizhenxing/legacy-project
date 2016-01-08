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

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
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
 * 書類管理登録情報値オブジェクトを登録する。
 * フォーム情報、登録情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: ShoruiAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiAddSaveAction extends BaseAction {

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

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------新規登録フォーム情報の取得
		ShoruiKanriForm addForm = (ShoruiKanriForm) form;
		
		//-------▼ VOにデータをセットする。
		ShoruiKanriInfo addInfo = container.getShoruiKanriInfo();
		
		//-------アップロードファイルをセット
		//---書類ファイル
		FormFile shoruiFile = addForm.getShoruiUploadFile();//書類ファイル
		FileResource shoruiFileRes = createFileResorce(shoruiFile);	
		addInfo.setShoruiFileRes(shoruiFileRes);
		
		addInfo.setShoruiName(addForm.getShoruiName());//書類名
		addInfo.setTaishoId(addForm.getTaishoId());//対象ID
		//-------▲
		
		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
			List result = servise.insert(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("書類管理情報　登録情報 '"+ result);
		}
		
		//書類管理情報をリセット（事業ID、事業名、年度、回数以外）
		addInfo.reset();
		
		//フォーム情報をリセット
		addForm.reset(mapping, request);
				
		//-----セッションに書類管理情報を登録する。
		container.setShoruiKanriInfo(addInfo);
		
		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, addForm);

		//------書類管理情報リストをセッションに登録。
		container.setShoruiKanriList(result);
				
		//------トークンの削除
		resetToken(request);
	
		//------トークンの登録
		saveToken(request);	

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
