/*
 * 作成日: 2005/04/21
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 所属機関担当者パスワード再設定前アクションクラス。
 * 再設定申請者情報を取得。セッションに登録する。 
 * パスワード再設定画面を表示する。
 * 
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

		//## パスワード再設定情報プロパティ(セッションに保持)　$!userContainer.shozokuInfo.プロパティ名

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------パスワード再設定フォーム情報の取得
		ShozokuForm reconfigurePasswordForm = (ShozokuForm) form;
		
		//------パスワード再設定対象所属機関担当者情報の取得
		ShozokuPk pkInfo = new ShozokuPk();
		//------キー情報
		String shozokuId = reconfigurePasswordForm.getShozokuTantoId();
		pkInfo.setShozokuTantoId(shozokuId);

		//------キー情報を元に所属機関担当者データ取得	
		ShozokuInfo reconfigurePasswordInfo = getSystemServise(IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		
		//------対象情報をセッションに登録。
		container.setShozokuInfo(reconfigurePasswordInfo);

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