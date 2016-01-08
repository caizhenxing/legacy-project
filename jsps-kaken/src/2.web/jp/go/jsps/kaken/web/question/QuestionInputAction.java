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

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.ILabelKubun;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * アンケート入力画面表示前アクションクラス。
 * アンケート入力画面を表示する。
 * 
 */
public class QuestionInputAction extends BaseAction {

	//---------------------------------------------------------------------
	// Static data
	//---------------------------------------------------------------------

	//---------------------------------------------------------------------
	// Public methods
	//---------------------------------------------------------------------
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

		//------キー情報
		QuestionForm addForm = new QuestionForm();

		//------ラジオボタン、プルダウンデータセット
		//2005.10.28
		String[] labelNames = {ILabelKubun.BENRI1, ILabelKubun.RIKAI1, ILabelKubun.RIKAI2,
							   ILabelKubun.RIKAI3, ILabelKubun.YONDA1, ILabelKubun.BENRI2,
							   ILabelKubun.OS, ILabelKubun.WEB, ILabelKubun.KEISIKI,
							   ILabelKubun.RIYOUTIME, ILabelKubun.TOIAWASE1,
							   ILabelKubun.CALLRIYOU, ILabelKubun.CALLRIKAI,
							   ILabelKubun.OUBO_TOI, ILabelKubun.BUKYOKU_TOI
							   };
		HashMap labelMap = (HashMap)LabelValueManager.getLabelMap(labelNames);
		
		//R1
		addForm.setBenri1List((List)labelMap.get(ILabelKubun.BENRI1));
		addForm.setRikai1List((List)labelMap.get(ILabelKubun.RIKAI1));
		addForm.setRikai2List((List)labelMap.get(ILabelKubun.RIKAI2));
		addForm.setYonda1List((List)labelMap.get(ILabelKubun.YONDA1));
		addForm.setRikai3List((List)labelMap.get(ILabelKubun.RIKAI3));
		addForm.setBenri2List((List)labelMap.get(ILabelKubun.BENRI2));
		addForm.setCallriyouList((List)labelMap.get(ILabelKubun.CALLRIYOU));
		addForm.setCallrikaiList((List)labelMap.get(ILabelKubun.CALLRIKAI));
		
		addForm.setOsList((List)labelMap.get(ILabelKubun.OS));
		addForm.setWebList((List)labelMap.get(ILabelKubun.WEB));
		addForm.setKeisikiList((List)labelMap.get(ILabelKubun.KEISIKI));
		addForm.setRiyoutimeList((List)labelMap.get(ILabelKubun.RIYOUTIME));
		addForm.setToiawase1List((List)labelMap.get(ILabelKubun.TOIAWASE1));

		//応募者からの問い合わせの内容リストをセット
		addForm.setOuboToiList((List)labelMap.get(ILabelKubun.OUBO_TOI));
		//部局担当者からの問い合わせの内容リストをセット
		addForm.setBukyokuToiList((List)labelMap.get(ILabelKubun.BUKYOKU_TOI));

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