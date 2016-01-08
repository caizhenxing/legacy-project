/*
 * Created on 2005/04/20
 *
 */
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseishaSearchInfo;
import jp.go.jsps.kaken.util.Page;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shozoku.shinseishaKanri.ShinseishaListForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 処理中画面表示用アクション
 * 
 *  @author masuo_t
 *
 */
public class MultipleAddCheckAction extends BaseAction {

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
		
		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
			new ActionError("error.transaction.token"));
			saveErrors(request, errors);
				return forwardTokenError(mapping);
		}
				  
		//------キー情報
		String kenkyuNo = ((ShinseishaListForm)form).getSelectRadioButton();
		if(kenkyuNo == null || kenkyuNo.equals("false")){
			//エラーメッセージ
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("errors.requiredSelect","登録する応募者の情報"));
	
			ShinseishaSearchInfo searchInfo = container.getShinseishaSearchInfo();
			//検索実行
			Page result =
				getSystemServise(
					IServiceName.KENKYUSHA_MAINTENANCE_SERVICE).searchUnregist(
					container.getUserInfo(),
					searchInfo);

			//登録結果をリクエスト属性にセット
			request.setAttribute(IConstants.RESULT_INFO,result);
		
			//トークンをセッションに保存する。
			saveToken(request);
			saveErrors(request, errors);
			return forwardFailure(mapping);	
		}
		
		return forwardSuccess(mapping);
	}

}
