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

import org.apache.struts.action.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * メールアドレス登録完了アクションクラス。
 * メールアドレス登録完了画面を表示する。
 * 
 * ID RCSfile="$RCSfile: RegistMailAddrOKAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class RegistMailAddrOKAction extends BaseAction {

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
		
		//------フォーム情報を取得
		RegistMailAddrForm mailForm = (RegistMailAddrForm)form;
		
		//------infoに更新情報をセット
		ShinsainInfo info = container.getUserInfo().getShinsainInfo();
		
		info.setMailFlg(mailForm.getMailFlg());				//メールフラグ
		info.setSofuZipemail(mailForm.getMailAddress());	//メールアドレス
		
		//------更新
		ShinsainInfo result = getSystemServise(
								IServiceName.SHINSAIN_MAINTENANCE_SERVICE).update(
												container.getUserInfo(),
												info);
		
		//UserInfoの審査員情報を更新
		container.getUserInfo().setShinsainInfo(info);
		
		//更新結果をリクエスト属性にセット→更新したメールアドレス情報等を表示するため。
		request.setAttribute(IConstants.RESULT_INFO,result);
		
		//------トークンの削除	
		resetToken(request);

		//------フォーム情報の削除
		removeFormBean(mapping,request);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//メールアドレスを登録しないを選択した場合、完了画面に遷移せず一覧画面に戻る
		if("1".equals(mailForm.getMailFlg())){
			return mapping.findForward("noregist");
		}else{
			return forwardSuccess(mapping);
		}

	}

}
