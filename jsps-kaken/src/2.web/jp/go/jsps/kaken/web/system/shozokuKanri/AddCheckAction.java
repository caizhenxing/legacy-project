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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 新規登録された所属機関情報の入力チェックを行う。
 * 所属機関登録情報値オブジェクトを作成する。
 * 登録確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: AddCheckAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
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
		
		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}
		
		//------新規登録フォーム情報の取得
		ShozokuForm addForm = (ShozokuForm) form;


		//2重登録チェック
		//所属機関担当者情報テーブルにすでに所属機関コードが
		//同じ所属機関担当者が登録されていないかどうかを確認
		int count =
			getSystemServise(
				IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				addForm.getShozokuCd());	

		//すでに登録されている場合
		if(count > 0){
			String[] error = {"所属機関担当者"};
			throw new ApplicationException("すでに所属機関担当者が登録されています。検索キー：所属機関コード'" 
														+ addForm.getShozokuCd() + "'", 	new ErrorInfo("errors.4007", error));			
		}

		//-------▼ VOにデータをセットする。
		ShozokuInfo addInfo = container.getShozokuInfo();
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

		//-----セッションに所属機関情報を登録する。
		container.setShozokuInfo(addInfo);

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
