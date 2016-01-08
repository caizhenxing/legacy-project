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
package jp.go.jsps.kaken.web.shozoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 応募者管理メニュー画面アクションクラス。
 * 応募者管理メニュー画面に研究者名簿更新日をセットする。
 * 
 * ID RCSfile="$RCSfile: MenuAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:33 $"
 */
public class MenuAction extends BaseAction {

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

		//研究者名簿更新日
		String updateDate = "";
		
		//研究者名簿更新日を取得する
		ISystemServise servise = getSystemServise(
				IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		updateDate = servise.GetKenkyushaMeiboUpdateDate(container.getUserInfo());

		//Null以外時、フォーマット変換
		if (!StringUtil.isEscapeBlank(updateDate)){
			updateDate = updateDate.substring(0, 4) + "年"
						+ updateDate.substring(4, 6) + "月"
						+ updateDate.substring(6) + "日現在の";
		}

		//結果をリクエスト属性にセット
		request.setAttribute("meiboUpdateDate", updateDate);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
