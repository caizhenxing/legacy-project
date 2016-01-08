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
package jp.go.jsps.kaken.web.question;


import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IQuestionMaintenance;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.role.UserRole;
import jp.go.jsps.kaken.model.vo.QuestionInfo;
import jp.go.jsps.kaken.util.StringUtil;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 更新されたアンケート入力情報の入力チェックを行う。
 * アンケート入力情報値オブジェクトを作成する。
 * アンケート入力確認画面を表示する。
 * 
 */
public class QuestionCheckAction extends BaseAction {
	
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
		QuestionForm addForm = (QuestionForm) form;
		
		//2005.11.11 iso チェックボックスの選択値をソートする。
		Collections.sort(addForm.getOuboToiValues());
		Collections.sort(addForm.getBukyokuToiValues());

		//-------▼ VOにデータをセットする。
		QuestionInfo addInfo = new QuestionInfo();

		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		//-------▲
	
		//2005.11.02 iso 表示ラベル追加
		addInfo.setBenri1Label(LabelValueManager.getlabelName(addForm.getBenri1List(), addForm.getBenri1()));
		addInfo.setRikai1Label(LabelValueManager.getlabelName(addForm.getRikai1List(), addForm.getRikai1()));
		addInfo.setRikai2Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai2()));
		addInfo.setRikai3Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai3()));
		addInfo.setRikai4Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai4()));
		addInfo.setRikai5Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai5()));
		addInfo.setRikai6Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai6()));
		addInfo.setRikai7Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai7()));
		addInfo.setRikai8Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai8()));
		addInfo.setRikai9Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai9()));
		addInfo.setRikai10Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai10()));
		addInfo.setRikai11Label(LabelValueManager.getlabelName(addForm.getRikai2List(), addForm.getRikai11()));
		addInfo.setYonda1Label(LabelValueManager.getlabelName(addForm.getYonda1List(), addForm.getYonda1()));
		addInfo.setRikai12Label(LabelValueManager.getlabelName(addForm.getRikai3List(), addForm.getRikai12()));
		addInfo.setYonda2Label(LabelValueManager.getlabelName(addForm.getYonda1List(), addForm.getYonda2()));
		addInfo.setRikai13Label(LabelValueManager.getlabelName(addForm.getRikai3List(), addForm.getRikai13()));
		addInfo.setBenri2Label(LabelValueManager.getlabelName(addForm.getBenri2List(), addForm.getBenri2()));
		addInfo.setCallriyouLabel(LabelValueManager.getlabelName(addForm.getCallriyouList(), addForm.getCallriyou()));
		addInfo.setCallrikaiLabel(LabelValueManager.getlabelName(addForm.getCallrikaiList(), addForm.getCallrikai()));
		addInfo.setRiyoutimeLabel(LabelValueManager.getlabelName(addForm.getRiyoutimeList(), addForm.getRiyoutime()));
		addInfo.setToiawase1Label(LabelValueManager.getlabelName(addForm.getToiawase1List(), addForm.getToiawase1()));
		addInfo.setToiawase2Label(LabelValueManager.getlabelName(addForm.getToiawase1List(), addForm.getToiawase2()));
		addInfo.setRikai14Label(LabelValueManager.getlabelName(addForm.getRikai1List(), addForm.getRikai14()));
		//2005.11.09 iso その他コードなら、代替文字を表示するよう変更
//		addInfo.setOsLabel(LabelValueManager.getlabelName(addForm.getOsList(), addForm.getOs()));
		addInfo.setOsLabel(LabelValueManager.getlabelName(addForm.getOsList(), addForm.getOs(), "9", addForm.getKankyoosSonota()));
//		addInfo.setWebLabel(LabelValueManager.getlabelName(addForm.getWebList(), addForm.getWeb()));
		addInfo.setWebLabel(LabelValueManager.getlabelName(addForm.getWebList(), addForm.getWeb(), "5", addForm.getKankyowebSonota()));
//		addInfo.setKeisikiLabel(LabelValueManager.getlabelName(addForm.getKeisikiList(), addForm.getKeisiki()));
		addInfo.setKeisikiLabel(LabelValueManager.getlabelName(addForm.getKeisikiList(), addForm.getKeisiki(), "8", addForm.getOubokeisikiSonota()));
		
		//2005.11.10 iso チェックボックス用ラベル処理の追加
		addInfo.setOuboToiLabelList(
				LabelValueManager.getlabelName(
						addForm.getOuboToiList(), addForm.getOuboToiValues()));
		addInfo.setBukyokuToiLabelList(
				LabelValueManager.getlabelName(
						addForm.getBukyokuToiList(),addForm.getBukyokuToiValues()));
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);	
			return forwardFailure(mapping);
		}

		//2005.11.03 iso 警告メッセージの追加
		//errorsを利用するので、エラー時の画面遷移以降で行う。
		
//		boolean warningFlg = false;
//		//応募者向け
//		String[] values1 = {addForm.getBenri1(), addForm.getRikai1(), addForm.getRikai2(),
//						   addForm.getRikai3(), addForm.getRikai4(), addForm.getRikai5(),
//						   addForm.getRikai6(), addForm.getRikai7(), addForm.getRikai8(),
//						   addForm.getRikai9(), addForm.getRikai10(), addForm.getRikai11(),
//						   addForm.getYonda1(), addForm.getRikai12(), addForm.getYonda2(),
//						   addForm.getRikai13(), addForm.getRiyoutime(), addForm.getBenri2()};
//		if(StringUtil.isBlank(values1)) {
//			warningFlg = true;
//		}
//		
//		//所属研究機関担当者向け
//		String[] values2 = {addForm.getBenri1(), addForm.getRikai1(), addForm.getRikai2(),
//						   addForm.getRikai3(), addForm.getRikai4(), addForm.getRikai5(),
//						   addForm.getRikai6(), addForm.getRikai7(), addForm.getRikai8(),
//						   addForm.getRikai9(), addForm.getRikai10(), addForm.getRikai11(),
//						   addForm.getYonda1(), addForm.getRikai12(), addForm.getYonda2(),
//						   addForm.getRikai13(), addForm.getCallriyou(), addForm.getRiyoutime(),
//						   addForm.getBenri2(), addForm.getToiawase1(), addForm.getToiawase2()};
//		if(StringUtil.isBlank(values2)) {
//			warningFlg = true;
//		}
//		
//		//部局担当者向け
//		String[] values3 = {addForm.getBenri1(), addForm.getRikai1(), addForm.getRikai2(),
//						   addForm.getRikai3(), addForm.getRikai4(), addForm.getRikai5(),
//						   addForm.getRikai6(), addForm.getRikai7(), addForm.getRikai8(),
//						   addForm.getRikai9(), addForm.getRikai10(), addForm.getRikai11(),
//						   addForm.getYonda2(), addForm.getRikai13(), addForm.getRiyoutime(),
//						   addForm.getBenri2(), addForm.getToiawase1()};
//		if(StringUtil.isBlank(values3)) {
//			warningFlg = true;
//		}
//		
//		if(warningFlg) {
//			errors.add(IQuestionMaintenance.WARNING, new ActionError("warning.requireds"));
//			saveErrors(request, errors);
//		}
		
		String[] values = null;
		//応募者向け
		if(container.getUserInfo().getRole().equals(UserRole.QUESTION_SHINSEISHA)) {
			values = new String[21];
			int i = 0;
			values[i++] = addForm.getBenri1();
			values[i++] = addForm.getRikai1();
			values[i++] = addForm.getOs();
			values[i++] = addForm.getWeb();
			values[i++] = addForm.getRikai2();
			values[i++] = addForm.getRikai3();
			values[i++] = addForm.getRikai4();
			values[i++] = addForm.getRikai5();
			values[i++] = addForm.getRikai6();
			values[i++] = addForm.getRikai7();
			values[i++] = addForm.getRikai8();
			values[i++] = addForm.getRikai9();
			values[i++] = addForm.getRikai10();
			values[i++] = addForm.getRikai11();
			values[i++] = addForm.getYonda1();
			values[i++] = addForm.getRikai12();
			values[i++] = addForm.getYonda2();
			values[i++] = addForm.getRikai13();
			values[i++] = addForm.getKeisiki();
			values[i++] = addForm.getRiyoutime();
			values[i++] = addForm.getBenri2();
		}
		//所属研究機関担当者向け
		else if(container.getUserInfo().getRole().equals(UserRole.QUESTION_SHOZOKUTANTO)) {
			values = new String[25];
			int i = 0;
			values[i++] = addForm.getBenri1();
			values[i++] = addForm.getRikai1();
			values[i++] = addForm.getOs();
			values[i++] = addForm.getWeb();
			values[i++] = addForm.getRikai2();
			values[i++] = addForm.getRikai3();
			values[i++] = addForm.getRikai4();
			values[i++] = addForm.getRikai5();
			values[i++] = addForm.getRikai6();
			values[i++] = addForm.getRikai7();
			values[i++] = addForm.getRikai8();
			values[i++] = addForm.getRikai9();
			values[i++] = addForm.getRikai10();
			values[i++] = addForm.getRikai11();
			values[i++] = addForm.getYonda1();
			values[i++] = addForm.getRikai12();
			values[i++] = addForm.getYonda2();
			values[i++] = addForm.getRikai13();
			values[i++] = addForm.getCallriyou();
			values[i++] = addForm.getRiyoutime();
			values[i++] = addForm.getBenri2();
			values[i++] = addForm.getToiawase1();
			values[i++] = addForm.getToiawase2();
			//2005.11.15 iso チェックボックスも空判定に追加
			values[i++] = get1stString(addForm.getOuboToiValues());
			values[i++] = get1stString(addForm.getBukyokuToiValues());
		}
		//部局担当者向け
		else if(container.getUserInfo().getRole().equals(UserRole.QUESTION_BUKYOKUTANTO)) {	
			values = new String[20];
			int i = 0;
			values[i++] = addForm.getBenri1();
			values[i++] = addForm.getRikai1();
			values[i++] = addForm.getOs();
			values[i++] = addForm.getWeb();
			values[i++] = addForm.getRikai2();
			values[i++] = addForm.getRikai3();
			values[i++] = addForm.getRikai4();
			values[i++] = addForm.getRikai5();
			values[i++] = addForm.getRikai6();
			values[i++] = addForm.getRikai7();
			values[i++] = addForm.getRikai8();
			values[i++] = addForm.getRikai9();
			values[i++] = addForm.getRikai10();
			values[i++] = addForm.getRikai11();
			values[i++] = addForm.getYonda2();
			values[i++] = addForm.getRikai13();
			values[i++] = addForm.getRiyoutime();
			values[i++] = addForm.getBenri2();
			values[i++] = addForm.getToiawase1();
			//2005.11.15 iso チェックボックスも空判定に追加
			values[i++] = get1stString(addForm.getOuboToiValues());
		}
		else if(container.getUserInfo().getRole().equals(UserRole.QUESTION_SHINSAIN)) {
			values = new String[11];
			int i = 0;
			values[i++] = addForm.getBenri1();
			values[i++] = addForm.getRikai1();
			values[i++] = addForm.getOs();
			values[i++] = addForm.getWeb();
			values[i++] = addForm.getRikai14();
			values[i++] = addForm.getYonda1();
			values[i++] = addForm.getRikai12();
			values[i++] = addForm.getYonda2();
			values[i++] = addForm.getRikai13();
			values[i++] = addForm.getRiyoutime();
			values[i++] = addForm.getBenri2();
		}

		if(StringUtil.isBlank(values)) {
			errors.add(IQuestionMaintenance.WARNING, new ActionError("warning.requireds"));
			saveErrors(request, errors);
		}
		//-----セッションにアンケート情報を登録する。
		container.setQuestionInfo(addInfo);

		//トークンをセッションに保存する。
		saveToken(request);

		return forwardSuccess(mapping);
	}
	
	/*
	 * Listの先頭文字列を返す。
	 * Listが空の場合は空文字を返す。
	 * @param	list	リスト
	 * @return	values	文字列
	 */
	private String get1stString(List list) {
		if(list.isEmpty() || list.size() == 0) {
			return "";
		} else {
			try {
				return list.get(0).toString();
			} catch(ClassCastException e) {
				//動作は止めない。
				//未入力判定に引っかからないよう、適当な文字列を返す。
				return "1";
			}
		}
	}
}
