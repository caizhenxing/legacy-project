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
package jp.go.jsps.kaken.web.system.gyomutantoKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 業務担当者情報削除前アクションクラス。
 * 削除対象業務担当者情報を取得。セッションに登録する。 
 * 削除確認画面を表示する。
 * 
 * ID RCSfile="$RCSfile: DeleteAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class DeleteAction extends BaseAction {

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

		//## 削除情報プロパティ(セッションに保持)　$!userContainer.shinseishaInfo.プロパティ名

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------修正登録フォーム情報の取得
		GyomutantoForm deleteForm = (GyomutantoForm) form;
		
		//------削除対象業務担当者情報の取得
		GyomutantoPk pkInfo = new GyomutantoPk();
		//------キー情報
		String gyomutantoId = deleteForm.getGyomutantoId();
		pkInfo.setGyomutantoId(gyomutantoId);

		//------キー情報を元に削除データ取得	
		GyomutantoInfo deleteInfo = getSystemServise(IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		//------担当事業表示用に設定
		//deleteInfo.setJigyoNameList(deleteForm.getJigyoNameList());
		deleteInfo.setJigyoNameList(LabelValueManager.getJigyoNameList(container.getUserInfo()));
		
		//------削除対象情報をセッションに登録。
		container.setGyomutantoInfo(deleteInfo);

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
