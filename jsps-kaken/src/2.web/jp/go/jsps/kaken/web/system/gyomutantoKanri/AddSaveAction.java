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
package jp.go.jsps.kaken.web.system.gyomutantoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 業務担当者登録情報値オブジェクトを登録する。
 * フォーム情報、登録情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: AddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:37 $"
 */
public class AddSaveAction extends BaseAction {

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

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}

		//------セッションより新規登録情報の取得
		GyomutantoInfo addInfo = container.getGyomutantoInfo();

		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE);
		GyomutantoInfo result = new GyomutantoInfo();

		try {
			result = servise.insert(container.getUserInfo(),addInfo);
		} catch(ApplicationException e) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//担当事業表示用
		result.setJigyoNameList(addInfo.getJigyoNameList());
		
		if(log.isDebugEnabled()){
			log.debug("業務担当者情報　登録情報 '"+ request);
		}


		//-------▼ VOにデータをセットする。
		GyomutantoInfo searchInfo = new GyomutantoInfo();
		searchInfo.setGyomutantoId(result.getGyomutantoId());
				
		//登録結果をリクエスト属性にセット→登録したパスワード情報等を表示するため。
		request.setAttribute(IConstants.RESULT_INFO,result);

		//------トークンの削除	
		resetToken(request);
		//------セッションより新規登録情報の削除
		container.setGyomutantoInfo(null);
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
