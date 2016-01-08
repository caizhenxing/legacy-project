/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : CheckCancelTokuteiAction.java
 *    Description : チェックリストの確定解除を行うクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/04/13
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/04/13    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IJigyoKubun;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリストの確定解除を行うクラス。
 * チェックリスト管理サービスでチェックリストの状況IDを更新する。
 * 
 * @author masuo_t
 *
 */
public class CheckCancelTokuteiAction extends BaseAction {

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
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
	
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		CheckListForm checkForm = (CheckListForm)form;
	
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		checkInfo.setJokyoId(StatusCode.STATUS_GAKUSIN_SHORITYU);
		checkInfo.setChangeJokyoId(StatusCode.STATUS_SHOZOKUKIKAN_UKETUKETYU);

// 20050613 Start
		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);
// Horikoshi End

		//------トークンの削除	
		resetToken(request);
		
		try{
			//状況ID変更
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkListUpdate(
				container.getUserInfo(),
				checkInfo, false);
		}catch(ValidationException e){
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
	}
}