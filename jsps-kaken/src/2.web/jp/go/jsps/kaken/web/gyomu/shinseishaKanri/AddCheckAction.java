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
package jp.go.jsps.kaken.web.gyomu.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 新規登録された申請者情報の入力チェックを行う。
 * 申請者登録情報値オブジェクトを作成する。
 * 登録確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: AddCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:23 $"
 */
public class AddCheckAction extends BaseAction {

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

		//------新規登録フォーム情報の取得
		ShinseishaForm addForm = (ShinseishaForm) form;

		//-------▼ VOにデータをセットする。
		ShinseishaInfo addInfo = new ShinseishaInfo();
		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//有効期限（String→Date)
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(addForm.getYukoDateYear(),addForm.getYukoDateMonth(),addForm.getYukoDateDay());
		addInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------▲
		
		try {
			//サーバ入力チェック
			addInfo =
				getSystemServise(
					IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					addInfo,
					IMaintenanceName.ADD_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}

		//-----セッションに申請者情報を登録する。
		container.setShinseishaInfo(addInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		//トークンをセッションに保存する。
		saveToken(request);

		return forwardSuccess(mapping);
	}

}
