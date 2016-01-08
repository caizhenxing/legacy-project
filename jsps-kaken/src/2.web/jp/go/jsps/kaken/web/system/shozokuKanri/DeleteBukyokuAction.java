/*
 * 作成日: 2005/04/20
 *
 */
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
/**
 * @author yoshikawa_h
 *
 */

/**
 * 部局担当者情報削除前アクションクラス。
 * 削除対象部局担当者情報を取得。セッションに登録する。 
 * 削除確認画面を表示する。
 * 
 */
public class DeleteBukyokuAction extends BaseAction {

	/* 
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## 削除情報プロパティ(セッションに保持)　$!userContainer.bukyokuInfo.プロパティ名

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------修正登録フォーム情報の取得
		BukyokuForm deleteForm = (BukyokuForm) form;
		
		//------削除対象所属機関情報の取得
		BukyokutantoInfo info = new BukyokutantoInfo();
		//------キー情報
		String bukyokuTantoId = deleteForm.getBukyokutantoId();
		info.setBukyokutantoId(bukyokuTantoId);

		//------キー情報を元に削除データ取得	
		BukyokutantoInfo deleteInfo = getSystemServise(IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(container.getUserInfo(),info);
	
		//------削除対象情報をセッションに登録。
		container.setBukyokutantoInfo(deleteInfo);

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

