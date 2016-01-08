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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.WarifuriPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 審査員割り振り更新情報値オブジェクトを登録する。
 * フォーム情報、更新情報をクリアする。
 *  
 * ID RCSfile="$RCSfile: WarifuriEditSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class WarifuriEditSaveAction extends BaseAction {

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
		
		//------セッションより修正登録情報の取得
//		WarifuriInfo warifuriInfo = container.getWarifuriInfo();

		//------フォーム情報の取得
		WarifuriForm editForm = (WarifuriForm)form;

		WarifuriPk editPk = new WarifuriPk();

		editPk.setSystemNo(editForm.getSystemNo());				//申請番号
		editPk.setShinsainNo(editForm.getShinsainNo());			//審査員番号
		editPk.setJigyoKubun(editForm.getJigyoKubun());			//事業区分
		editPk.setOldShinsainNo(editForm.getOldShinsainNo());	//審査員番号（修正前）		
		
		//------更新
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSAIN_WARIFURI_SERVICE);
		servise.update(container.getUserInfo(), editPk);

		if (log.isDebugEnabled()) {
			log.debug("割り振り結果情報 修正登録 '" + editPk + "'");
		}

		//------トークンの削除	
		resetToken(request);
		
		//------セッションより対象割り振り結果情報の削除
		container.setWarifuriInfo(null);
		
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
