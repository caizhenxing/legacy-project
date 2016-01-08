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
package jp.go.jsps.kaken.web.gyomu.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 所属機関情報の表示クラス。
 * 所属機関情報確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShozokuInfoAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:25 $"
 */
public class ShozokuInfoAction extends BaseAction {

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

		//------表示対象所属機関情報の取得
		ShozokuPk pkInfo = new ShozokuPk();
		//------キー情報
		String shozokuTantoId = ((ShozokuForm)form).getShozokuTantoId();
		pkInfo.setShozokuTantoId(shozokuTantoId);
		
		//------キー情報を元に表示データ取得	
		ShozokuInfo editInfo = 
			getSystemServise(IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
		
		//------表示対象情報をセッションに登録。
		container.setShozokuInfo(editInfo);
		
		//検索結果をリクエスト属性にセット
//		request.setAttribute(IConstants.RESULT_INFO,editInfo);
		//------セッションより新規登録情報の削除
//		container.setShozokuInfo(null);
		//------フォーム情報の削除
//		removeFormBean(mapping,request);
				
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
