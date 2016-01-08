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
package jp.go.jsps.kaken.web.gyomu.shinsainKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsainInfo;
import jp.go.jsps.kaken.model.vo.ShinsainPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 審査員パスワード再設定前アクションクラス。
 * 再設定審査員情報を取得。セッションに登録する。 
 * パスワード再設定画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ReconfigurePasswordAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:40 $"
 */
public class ReconfigurePasswordAction extends BaseAction {

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

		//## パスワード再設定情報プロパティ(セッションに保持)　$!userContainer.shinsainInfo.プロパティ名

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------パスワード再設定フォーム情報の取得
		ShinsainForm reconfigurePasswordForm = (ShinsainForm) form;
		
		//------パスワード再設定対象申請者情報の取得
		ShinsainPk pkInfo = new ShinsainPk();
		//------キー情報
		String shinainNo = reconfigurePasswordForm.getShinsainNo();
		String jigyoKubun = reconfigurePasswordForm.getJigyoKubun();
		pkInfo.setShinsainNo(shinainNo);
		pkInfo.setJigyoKubun(jigyoKubun);

		//------キー情報を元にパスワード再設定データ取得	
		ShinsainInfo reconfigurePasswordInfo = getSystemServise(IServiceName.SHINSAIN_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
		
		//------削除対象情報をセッションに登録。
		container.setShinsainInfo(reconfigurePasswordInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
