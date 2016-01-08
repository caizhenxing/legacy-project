/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2005/04/11
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 申請者情報削除前アクションクラス。
 * 削除対象申請者情報を取得。セッションに登録する。 
 * 削除確認画面を表示する。
 * 
 */
public class DeleteAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//------修正登録フォーム情報の取得
		ShinseishaForm deleteForm = (ShinseishaForm) form;
		
		//------削除対象申請者情報の取得
		ShinseishaPk pkInfo = new ShinseishaPk();
		//------キー情報
		String shinseishaId = deleteForm.getShinseishaId();
		pkInfo.setShinseishaId(shinseishaId);

		//------キー情報を元に削除データ取得	
		ShinseishaInfo deleteInfo = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		//------削除対象情報をセッションに登録。
		container.setShinseishaInfo(deleteInfo);
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}
}
