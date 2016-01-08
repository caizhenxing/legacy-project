/*
 * Created on 2005/04/07
 * 
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 部局担当者削除/パスワード変更確認用アクションクラス。
 * 削除確認画面、及びパスワード変更確認画面表示時に呼び出される。
 *
 */
public class ChangeAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------修正登録フォーム情報の取得
		BukyokuForm deleteForm = (BukyokuForm)form;

		//------キー情報を元に削除データ取得	
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(deleteForm.getBukyokutantoId());	
		
		//削除対象情報を取得する
		info =
			getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
			container.getUserInfo(),
			info);
		
		//------削除対象情報をセッションに登録。
		container.setBukyokutantoInfo(info);
			
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
