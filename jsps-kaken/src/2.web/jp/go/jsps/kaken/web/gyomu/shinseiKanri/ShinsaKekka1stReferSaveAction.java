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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 1次審査結果参照登録情報値オブジェクトを登録する。
 * フォーム情報、登録情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka1stReferSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class ShinsaKekka1stReferSaveAction extends BaseAction {

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
		ShinsaKekka1stForm addForm = (ShinsaKekka1stForm) form;

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-------▼ VOにデータをセットする。
		ShinsaKekkaReferenceInfo addInfo = new ShinsaKekkaReferenceInfo();
		addInfo.setSystemNo(addForm.getSystemNo());				//システム番号
		addInfo.setShinsa1Biko(addForm.getShinsa1Biko());		//業務担当者備考
		//-------▲
		
		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		servise.regist1stShinsaKekkaBiko(container.getUserInfo(),addInfo);
		
		if(log.isDebugEnabled()){
			log.debug("1次審査結果参照情報　登録情報 '"+ addInfo);
		}
		
		//------トークンの削除	
		resetToken(request);
		
		//------フォーム情報の削除
		removeFormBean(mapping,request);

		//-----セッション情報の削除
		container.setShinsaKekkaReferenceInfo(null);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
