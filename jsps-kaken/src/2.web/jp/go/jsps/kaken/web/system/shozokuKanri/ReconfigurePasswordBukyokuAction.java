/*
 * 作成日: 2005/04/21
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * パスワード変更確認用アクションクラス。
 * パスワード変更確認画面表示時に呼び出される。
 *
 */
public class ReconfigurePasswordBukyokuAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------フォーム情報の取得
		BukyokuForm bukyokuForm = (BukyokuForm)form;

		//------キー情報を元に該当データ取得	
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(bukyokuForm.getBukyokutantoId());	
		
		//対象情報を取得する
		info =
			getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
			container.getUserInfo(),
			info);
		
		//------対象情報をセッションに登録。
		container.setBukyokutantoInfo(info);
			
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
