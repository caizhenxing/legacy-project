/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.masterTorikomi;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.MasterKanriInfo;
import jp.go.jsps.kaken.util.FileResource;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * マスタ取込アクションクラス。
 * CSVファイルを読込み、DBを更新する。
 * 
 * ID RCSfile="$RCSfile: RegistMasterAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class RegistMasterAction extends BaseAction {

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
/*		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
*/				
		//-----マスタ取込入力フォームの取得
		MasterTorikomiForm torikomiForm = (MasterTorikomiForm)form;
		
		
		MasterKanriInfo result = null;
		
		//-----マスタ取込メソッドを呼び出す		
		try{
			result = registApplication(container, torikomiForm);
		}catch(ValidationException e){
			List errorList = e.getErrors();
			for(int i=0; i<errorList.size(); i++){
				ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
				errors.add(errInfo.getProperty(),
						   new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
			}
		}
		

		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,result);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		//------トークンの削除	
		resetToken(request);
		//------フォーム情報の削除  →ファイル変換アクションで利用するため削除しない。
		//removeFormBean(mapping,request);

		return forwardSuccess(mapping);

	}
	
	
	/**
	 * CSVファイル取込を実行する。
	 * @param container ログイン申請者情報
	 * @param form      マスタ取込フォームデータ
	 * @throws ValidationException  データチェックエラーが発生した場合
	 * @throws ApplicationException 申請登録に失敗した場合
	 */
	private MasterKanriInfo registApplication(UserContainer container, MasterTorikomiForm form)
		throws ValidationException, ApplicationException
	{
		//添付ファイル
		FileResource annexFileRes = null;
		try{
			FormFile file = form.getUploadCsv();
			if(file != null && file.getFileData().length != 0){
				annexFileRes = new FileResource();
				annexFileRes.setPath(file.getFileName());	//ファイル名
				annexFileRes.setBinary(file.getFileData());	//ファイルサイズ
			}
		}catch(IOException e){
			throw new ApplicationException(
				"CSVファイルの取得に失敗しました。",
				new ErrorInfo("errors.7000"),
				e);
		}

		if(annexFileRes == null){
			//annexFileResがnullの場合
			throw new ApplicationException(
						"指定されたファイルが存在しません。",
						new ErrorInfo("errors.7004"));
		}

		//サーバサービスの呼び出し
		ISystemServise servise = getSystemServise(
						IServiceName.SYSTEM_MAINTENANCE_SERVICE);
	
		MasterKanriInfo mkInfo = servise.torikomimaster(container.getUserInfo(),
														annexFileRes,
														form.getMasterShubetu(),
														form.getShinkiKoshinFlg());
		return mkInfo;

	}
	
}
