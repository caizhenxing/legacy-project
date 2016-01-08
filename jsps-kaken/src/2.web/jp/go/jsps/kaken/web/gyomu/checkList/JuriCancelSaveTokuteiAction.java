/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriCancelSaveTokuteiAction.java
 *    Description : 受理解除(特定)を行う。
 *
 *    Author      : Admin
 *    Date        : 2005/04/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理解除(特定)を行う。
 * 
 * @author masuo_t
 */
public class JuriCancelSaveTokuteiAction extends BaseAction {
	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
		
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}

		//------トークンの削除	
		resetToken(request);
		//-------▼ VOにデータをセットする。
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		//検索条件の取得
		CheckListForm searchForm = (CheckListForm)form;

		//検索条件の設定
		if(searchForm.getJigyoId() != null && !searchForm.getJigyoId().equals("")){
			searchInfo.setJigyoId(searchForm.getJigyoId());
		}
		if(searchForm.getShozokuCd() != null && !searchForm.getShozokuCd().equals("")){
			searchInfo.setShozokuCd(searchForm.getShozokuCd());
		}
// 20050617
		searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi

		//状況IDを06から04に変更
		searchInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
		searchInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
		

		ISystemServise servise =
			getSystemServise(IServiceName.CHECKLIST_MAINTENANCE_SERVICE);

		try {
			servise.checkListUpdate(
				container.getUserInfo(),
				searchInfo, true);

		} catch (ValidationException e) {
			return forwardSuccess(mapping);
		}

		return forwardSuccess(mapping);
	}
}