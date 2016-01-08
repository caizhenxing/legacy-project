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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * データ保管画面アクションクラス。
 * データ保管対象事業を指定する画面を表示する。
 * ID RCSfile="$RCSfile: DataHokanInvokeAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:23 $"
 */
public class DataHokanInvokeAction extends BaseAction {

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
		
		//事業ID
		JigyoKanriPk jigyoKanriPk = new JigyoKanriPk(dataHokanForm.getJigyoId());
		
		//保管有効期限の構築
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(dataHokanForm.getYukoKigenYear(),
						dataHokanForm.getYukoKigenMonth(),
						dataHokanForm.getYukoKigenDate());
		Date dateYukoKigen = dateUtil.getCal().getTime();
		
		//サーバサービスの呼び出し（データ保管）
		ISystemServise servise = getSystemServise(
						IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE);
		int dataCount = servise.dataHokanInvoke(
		                             container.getUserInfo(),
		                             jigyoKanriPk,
									 dateYukoKigen);		
		
		//データ保管件数（申請データ数）をセットする
		dataHokanForm.setShoriKensu(dataCount);
	
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
