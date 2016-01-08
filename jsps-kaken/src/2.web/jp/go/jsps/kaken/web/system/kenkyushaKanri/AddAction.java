/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 研究者情報登録前アクションクラス。
 * フォームに研究者情報登録画面に必要なデータをセットする。
 * 研究者情報新規登録画面を表示する。
 * 
 * @author masuo_t
 */
public class AddAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------新規登録フォーム情報の作成
		KenkyushaForm searchForm = new KenkyushaForm();

		//------更新モード
		searchForm.setAction(BaseForm.ADD_ACTION);

		//------プルダウンデータセット
		searchForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		searchForm.setGakuiList(LabelValueManager.getGakuiList());
		searchForm.setSeibetsuList(LabelValueManager.getSeibetsuList());
		
		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, searchForm);

		return forwardSuccess(mapping);
	}

}
