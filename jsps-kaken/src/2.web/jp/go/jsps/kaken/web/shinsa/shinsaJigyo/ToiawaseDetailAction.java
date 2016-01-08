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
package jp.go.jsps.kaken.web.shinsa.shinsaJigyo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ApplicationSettings;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ISettingKeys;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 問い合わせ先詳細表示前アクションクラス。
 * 問い合わせ先詳細画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ToiawaseDetailAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:59 $"
 */
public class ToiawaseDetailAction extends BaseAction {

	/** 問い合わせ先郵便番号 */
	private static String GAKUSHIN_TOIAWASE_YUBIN = ApplicationSettings.getString(ISettingKeys.GAKUSHIN_TOIAWASE_YUBIN);

	/** 問い合わせ先住所 */
	private static String GAKUSHIN_TOIAWASE_JUSHO = ApplicationSettings.getString(ISettingKeys.GAKUSHIN_TOIAWASE_JUSHO);

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

		//------表示対象事業管理情報の取得
		JigyoKanriPk pkInfo = new JigyoKanriPk();
		//------キー情報
		String jigyoId = ((ShinsaKekkaForm)form).getJigyoId();
		pkInfo.setJigyoId(jigyoId);
		
		//------キー情報を元に表示データ取得	
		JigyoKanriInfo selectInfo = 
					getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
		
		//------問い合わせ先をセット		
		selectInfo.setToiawaseZip(GAKUSHIN_TOIAWASE_YUBIN);
		selectInfo.setToiawaseJusho(GAKUSHIN_TOIAWASE_JUSHO);		
	
		//検索結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO,selectInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
