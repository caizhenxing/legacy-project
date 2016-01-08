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
package jp.go.jsps.kaken.web.system.dataKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 事業データ削除アクション。
 * 
 * ID RCSfile="$RCSfile: DeleteDataAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:08:02 $"
 */
public class DeleteDataAction extends BaseAction {

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

		//データ管理フォーム
		DataKanriForm dataKanriForm = (DataKanriForm)form;
		
		//サービス呼び出し（事業データ削除）
		JigyoKanriPk jigyoPk = new JigyoKanriPk(dataKanriForm.getJigyoId());
		JigyoKanriInfo info = getSystemServise(
								IServiceName.SYSTEM_MAINTENANCE_SERVICE).deleteJigyo(
									container.getUserInfo(),
									jigyoPk);
		
		//削除処理を施した事業情報の情報をセットする
		dataKanriForm.setJigyoName(info.getJigyoName());
		dataKanriForm.setNendo(info.getNendo());
		dataKanriForm.setKaisu(info.getKaisu());
		
		//-----フォームをリクエストにセット
		updateFormBean(mapping, request, dataKanriForm);	
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}

}
