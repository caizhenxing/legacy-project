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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * ルール設定前アクションクラス。
 * フォームにルール設定画面に必要なデータをセットする。
 * ルール設定画面を表示する。
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:45 $"
 */
public class EditAction extends BaseAction {

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
		RuleForm ruleForm = new RuleForm();

		//------更新モード
		ruleForm.setAction(BaseForm.EDIT_ACTION);

		//------現在の値をセット
		List ruleList =
			getSystemServise(
				IServiceName.RULE_MAINTENANCE_SERVICE).selectList(
				container.getUserInfo()
				);

		List taishoIdList = new ArrayList();
		List mojisuChkList = new ArrayList();
		List charChk1List = new ArrayList();
		List charChk2List = new ArrayList();
		List charChk3List = new ArrayList();
		List charChk4List = new ArrayList();
		List charChk5List = new ArrayList();
		List bikoList = new ArrayList();
		List yukoDateYearList = new ArrayList();
		List yukoDateMonthList = new ArrayList();
		List yukoDateDayList = new ArrayList();
		for(int i = 0; i < 4; i++) {
			Calendar cal = Calendar.getInstance();
			HashMap ruleMap = (HashMap)ruleList.get(i);
			if(ruleMap.get("TAISHO_ID") != null && !ruleMap.get("TAISHO_ID").equals("")){
				taishoIdList.add(ruleMap.get("TAISHO_ID").toString());
			} else {
				taishoIdList.add("");
			}
			if(ruleMap.get("MOJISU_CHK") != null && !ruleMap.get("MOJISU_CHK").equals("")){
				mojisuChkList.add(ruleMap.get("MOJISU_CHK").toString());
			} else {
				mojisuChkList.add("6");
			}
			if(ruleMap.get("CHAR_CHK1") != null && !ruleMap.get("CHAR_CHK1").equals("")){
				charChk1List.add(ruleMap.get("CHAR_CHK1").toString());
			} else {
				charChk1List.add("0");
			}
			if(ruleMap.get("CHAR_CHK2") != null && !ruleMap.get("CHAR_CHK2").equals("")){
				charChk2List.add(ruleMap.get("CHAR_CHK2").toString());
			} else {
			charChk2List.add("0");
			}
			if(ruleMap.get("CHAR_CHK3") != null && !ruleMap.get("CHAR_CHK3").equals("")){
				charChk3List.add(ruleMap.get("CHAR_CHK3").toString());
			} else {
				charChk3List.add("");
			}
			if(ruleMap.get("CHAR_CHK4") != null && !ruleMap.get("CHAR_CHK4").equals("")){
				charChk4List.add(ruleMap.get("CHAR_CHK4").toString());
			} else {
				charChk4List.add("");
			}
			if(ruleMap.get("CHAR_CHK5") != null && !ruleMap.get("CHAR_CHK5").equals("")){
				charChk5List.add(ruleMap.get("CHAR_CHK5").toString());
			} else {
				charChk5List.add("");
			}
			if(ruleMap.get("BIKO") != null && !ruleMap.get("BIKO").equals("")){
				bikoList.add(ruleMap.get("BIKO").toString());
			} else {
				bikoList.add("");
			}
			if(ruleMap.get("YUKO_DATE") != null && !ruleMap.get("YUKO_DATE").equals("")){
				cal.setTime((Date)ruleMap.get("YUKO_DATE"));
				yukoDateYearList.add(Integer.toString(cal.get(Calendar.YEAR)));
				yukoDateMonthList.add(Integer.toString(cal.get(Calendar.MONTH)+1));
				yukoDateDayList.add(Integer.toString(cal.get(Calendar.DATE)));
			} else {
				yukoDateYearList.add("2099");
				yukoDateMonthList.add("0");
				yukoDateDayList.add("1");
			}
		}

		ruleForm.setTaishoIdList(taishoIdList);
		ruleForm.setMojisuChkList(mojisuChkList);
		ruleForm.setCharChk1List(charChk1List);
		ruleForm.setCharChk2List(charChk2List);
		ruleForm.setCharChk3List(charChk3List);
		ruleForm.setCharChk4List(charChk4List);
		ruleForm.setCharChk5List(charChk5List);
		ruleForm.setBikoList(bikoList);
		ruleForm.setYukoDateYearList(yukoDateYearList);
		ruleForm.setYukoDateMonthList(yukoDateMonthList);
		ruleForm.setYukoDateDayList(yukoDateDayList);

		//大文字・小文字の混在
		ruleForm.setRadioCharChk1List(LabelValueManager.getCharChk1List());
		//アルファベットと数字の混在
		ruleForm.setRadioCharChk2List(LabelValueManager.getCharChk2List());

		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, ruleForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}
}
