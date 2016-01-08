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

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.common.ITaishoId;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.model.vo.RulePk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請者情報登録前アクションクラス。
 * フォームに申請者情報登録画面に必要なデータをセットする。
 * 申請者情報新規登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: AddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:23 $"
 */
public class AddAction extends BaseAction {

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

		//------新規登録フォーム情報の作成
		ShinseishaForm addForm = new ShinseishaForm();

		//------更新モード
		addForm.setAction(BaseForm.ADD_ACTION);

		//------プルダウンデータセット
//		addForm.setShubetuCdList(LabelValueManager.getBukyokuShubetuCdList());
		addForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		addForm.setHikoboFlgList(LabelValueManager.getHikoboFlgList());

		//非公募応募可フラグ初期値セット
		addForm.setHikoboFlg("0");

		//------有効期限初期値セット
		RulePk rulePk = new RulePk();
		rulePk.setTaishoId(ITaishoId.SHINSEISHA);
		RuleInfo ruleInfo =
			getSystemServise(
				IServiceName.RULE_MAINTENANCE_SERVICE).select(
				container.getUserInfo(),
				rulePk);
		Calendar cal = Calendar.getInstance();
		cal.setTime(ruleInfo.getYukoDate());
		addForm.setYukoDateYear(Integer.toString(cal.get(Calendar.YEAR)));
		addForm.setYukoDateMonth(Integer.toString(cal.get(Calendar.MONTH)+1));
		addForm.setYukoDateDay(Integer.toString(cal.get(Calendar.DATE)));

		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, addForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
