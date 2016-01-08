/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * データ保管事業問い合わせアクションクラス。
 * 指定事業情報を取得する。
 * ID RCSfile="$RCSfile: DataHokanQueryAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:24 $"
 */
public class DataHokanQueryAction extends BaseAction {

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
	
		//フォームの取得
		DataHokanForm dataHokanForm = (DataHokanForm)form;
		
		//サーバサービスの呼び出し（事業情報の取得）
		ISystemServise servise = getSystemServise(
						IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
		JigyoKanriInfo info = servise.getJigyoKanriInfo(
										container.getUserInfo(),
										dataHokanForm.getJigyoCd(),
										dataHokanForm.getNendo(),
										dataHokanForm.getKaisu());				
		
		//事業ID、事業名のセット
		dataHokanForm.setJigyoId(info.getJigyoId());
		dataHokanForm.setJigyoName(info.getJigyoName());		
		
		//-----フォームをリクエストにセット
		updateFormBean(mapping, request, dataHokanForm);	
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
