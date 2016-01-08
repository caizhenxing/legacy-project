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
package jp.go.jsps.kaken.web.system.rule;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.RuleInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ルール設定情報値オブジェクトを登録する。
 * フォーム情報、更新情報をクリアする。
 *  
 * ID RCSfile="$RCSfile: EditSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:45 $"
 */
public class EditSaveAction extends BaseAction {

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

		//------修正登録フォーム情報の取得
		RuleForm editForm = (RuleForm) form;

		//------修正登録情報の設定
		List editList = new ArrayList();
		for(int i = 0; i < editForm.getTaishoIdList().size(); i++){
			RuleInfo editInfo = new RuleInfo();
			if(editForm.getTaishoIdList().get(i) != null){
				editInfo.setTaishoId(editForm.getTaishoIdList().get(i).toString());
			}
			if(editForm.getMojisuChkList().get(i) != null) {
				editInfo.setMojisuChk(editForm.getMojisuChkList().get(i).toString());
			}
			if(editForm.getCharChk1List().get(i) != null) {
				editInfo.setCharChk1(editForm.getCharChk1List().get(i).toString());
			}
			if(editForm.getCharChk2List().get(i) != null) {
				editInfo.setCharChk2(editForm.getCharChk2List().get(i).toString());
			}
/*現在未使用につき除外
			if(editForm.getCharChk3List().get(i) != null) {
				editInfo.setCharChk3(editForm.getCharChk3List().get(i).toString());
			}
			if(editForm.getCharChk4List().get(i) != null) {
				editInfo.setCharChk4(editForm.getCharChk4List().get(i).toString());
			}
			if(editForm.getCharChk5List().get(i) != null) {
				editInfo.setCharChk5(editForm.getCharChk5List().get(i).toString());
			}
			if(editForm.getBikoList().get(i) != null) {
				editInfo.setBiko(editForm.getBikoList().get(i).toString());
			}
*/
			//有効期限（String→Date)
			if(editForm.getYukoDateYearList().get(i) != null && editForm.getYukoDateMonthList().get(i) != null && editForm.getYukoDateDayList().get(i) != null) {
				DateUtil dateUtil = new DateUtil();
				dateUtil.setCal(editForm.getYukoDateYearList().get(i).toString(), editForm.getYukoDateMonthList().get(i).toString(), editForm.getYukoDateDayList().get(i).toString());
				editInfo.setYukoDate(dateUtil.getCal().getTime());
			}

			editList.add(editInfo);
		}

		//------更新
		ISystemServise servise = getSystemServise(
						IServiceName.RULE_MAINTENANCE_SERVICE);
		servise.updateAll(container.getUserInfo(),editList);

		if (log.isDebugEnabled()) {
			log.debug("ルール設定情報 修正登録 '" + editList + "'");
		}
		
		//------トークンの削除	
		resetToken(request);
		//------フォーム情報の削除
		removeFormBean(mapping,request);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
