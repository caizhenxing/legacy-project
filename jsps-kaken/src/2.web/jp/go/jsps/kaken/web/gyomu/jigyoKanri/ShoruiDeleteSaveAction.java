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
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShoruiKanriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 書類管理登録情報値オブジェクトを削除する。
 * フォーム情報、登録情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: ShoruiDeleteSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:04 $"
 */
public class ShoruiDeleteSaveAction extends BaseAction {

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

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//------新規登録フォーム情報の取得
		ShoruiKanriForm addForm = (ShoruiKanriForm) form;
		
		//------セッションより削除情報の取得
		ShoruiKanriInfo deleteInfo = container.getShoruiKanriInfo();
//		ShoruiKanriInfo deleteInfo = new ShoruiKanriInfo();
//		deleteInfo.setJigyoId(addForm.getJigyoId());//対象
		deleteInfo.setSystemNo(addForm.getSystemNo());//システム番号

		//------削除
		ISystemServise servise = getSystemServise(
							IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE);
		List result = servise.delete(container.getUserInfo(), deleteInfo);

		if (log.isDebugEnabled()) {
			log.debug("書類管理情報 削除   '" + deleteInfo + "'");
		}
		
		//フォーム情報をリセット
		addForm.reset(mapping, request);
				

		//------書類管理情報リストをセッションに登録。
		container.setShoruiKanriList(result);
		
		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, addForm);
		
		//------トークンの削除（画面遷移を行わないため）
		resetToken(request);

		//------トークンの登録
		saveToken(request);			

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
