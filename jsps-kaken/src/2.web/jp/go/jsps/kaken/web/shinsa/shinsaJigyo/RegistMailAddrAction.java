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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.*;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * メールアドレス登録アクションクラス。
 * メールアドレス登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: RegistMailAddrAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RegistMailAddrAction extends BaseAction {

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

		//------ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
		
		RegistMailAddrForm mailForm = new RegistMailAddrForm();
		
		//------審査員情報取得
		ShinsainInfo info = getSystemServise(
								IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(
												container.getUserInfo(),
												container.getUserInfo().getShinsainInfo());
		
		//------フォームデータセット
		mailForm.setMailFlg(info.getMailFlg());							//メールフラグ
		mailForm.setMailAddress(info.getSofuZipemail());				//メールアドレス
		
		//------プルダウンデータセット
		mailForm.setMailFlgList(LabelValueManager.getMailFlgList());	//メールフラグリスト
		
		//------トークンをセッションに保存する。
		saveToken(request);
		
		//------検索フォームをリセット
		updateFormBean(mapping, request, mailForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
