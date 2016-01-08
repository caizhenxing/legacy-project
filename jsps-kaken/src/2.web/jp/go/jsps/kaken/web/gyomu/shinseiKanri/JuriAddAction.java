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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理登録アクションクラス。
 * 受理登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: JuriAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class JuriAddAction extends BaseAction {

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

		//検索条件の取得
		JuriAddForm addForm = (JuriAddForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinseiDataPk searchPk = new ShinseiDataPk(addForm.getSystemNo());		
					
		//検索実行
		SimpleShinseiDataInfo  selectInfo =
			getSystemServise(
				IServiceName.SHINSEI_MAINTENANCE_SERVICE).selectSimpleShinseiDataInfo(
				container.getUserInfo(),
				searchPk);
	
		
		//-----フォームに受理登録情報をセットする。
        addForm.setJigyoCd(selectInfo.getJigyoId().substring(2,7));
		addForm.setJuriBiko(selectInfo.getJuriBiko());		//備考
		addForm.setJuriKekka(selectInfo.getJuriKekka());	//受理結果
		addForm.setJuriSeiriNo(selectInfo.getSeiriNo());	//受理整理番号

		//------プルダウンデータセット
		//受理情報
		addForm.setJuriKekkaList(LabelValueManager.getJuriKekkaList());
	
		//-----セッションに申請データ情報（受理登録情報）をセットする。
		container.setSimpleShinseiDataInfo(selectInfo);		
		
		//------トークンの保存
		saveToken(request);
		
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