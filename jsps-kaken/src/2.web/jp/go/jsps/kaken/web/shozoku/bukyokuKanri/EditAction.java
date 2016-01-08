/*
 * Created on 2005/04/06
 *
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 所属担当者修正登録用のアクションクラス。
 *
 */
public class EditAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		BukyokuForm bukyokuForm = (BukyokuForm)form;
		BukyokutantoInfo info = new BukyokutantoInfo();
		info.setBukyokutantoId(bukyokuForm.getBukyokutantoId());	
		
		//所属担当者情報を取得する
		info =
			getSystemServise(
				IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).selectBukyokuData(
				container.getUserInfo(),
				info);
		
		//------更新登録フォーム情報の作成		
		BukyokuForm editForm = new BukyokuForm();
		//------更新モード：修正
		editForm.setAction(BaseForm.EDIT_ACTION);
		
		try {
			PropertyUtils.copyProperties(editForm, info);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//データをフォームにセットする。
		updateFormBean(mapping,request,editForm);
		
		return forwardSuccess(mapping);
	}

}
