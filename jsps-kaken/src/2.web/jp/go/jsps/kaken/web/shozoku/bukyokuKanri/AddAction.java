/*
 * Created on 2005/04/05
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
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 所属担当者登録用アクションクラス。
 * 
 */
public class AddAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	

		//------新規登録フォーム情報の作成
		BukyokuForm bukyokuForm = new BukyokuForm();
		BukyokutantoInfo bukyokuInfo = new BukyokutantoInfo();
		
		//------更新モード：新規登録
		bukyokuForm.setAction(BaseForm.ADD_ACTION);
	
		//部局担当IDをセットする。
		bukyokuForm.setBukyokutantoId(((BukyokuForm)form).getBukyokutantoId());
		bukyokuInfo.setBukyokutantoId(((BukyokuForm)form).getBukyokutantoId());
		
		//所属担当者情報を取得する
		bukyokuInfo = getSystemServise(
			IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectShozokuData(
			container.getUserInfo(),
			bukyokuInfo);
		
		//所属担当者情報をセット	
		bukyokuForm.setShozokuCd(bukyokuInfo.getShozokuCd());
		bukyokuForm.setShozokuName(bukyokuInfo.getShozokuName());
		bukyokuForm.setShozokuNameEigo(bukyokuInfo.getShozokuNameEigo());
					
		//------新規登録フォームにセットする。
		updateFormBean(mapping,request,bukyokuForm);
		
		return forwardSuccess(mapping);
	}
}
