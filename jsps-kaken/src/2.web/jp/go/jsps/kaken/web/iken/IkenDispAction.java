/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム(JSPS)
 *    Source name : IkenDispAction.java
 *    Description : 意見情報表示アクションクラス。
 *
 *    Author      : Admin
 *    Date        : 2005/05/23
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/05/23    1.0         Xiang Emin     新規
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.iken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
//import jp.go.jsps.kaken.util.Page;
//import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.model.vo.IkenInfo;

/**
 * @author user1
 *
 * TODO この生成された型コメントのテンプレートを変更するには次を参照。
 * ウィンドウ ＞ 設定 ＞ Java ＞ コード・スタイル ＞ コード・テンプレート
 */
public class IkenDispAction extends BaseAction {

	/* (Javadoc なし)
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


		//意見検索条件フォームの取得
		IkenForm ikenForm = (IkenForm)form;

		if (log.isDebugEnabled()){
			log.debug("意見情報取得キー：" + ikenForm.getSystem_no());
		}

		//検索用サービスの取得
		ISystemServise servise = getSystemServise(IServiceName.IKEN_MAINTENANCE_SERVICE);

		//検索実行
		IkenInfo result = servise.selectIkenDataInfo(
									ikenForm.getSystem_no(),	//システム受付番号
									ikenForm.getTaishoID()		//対象者ID
								);

		//登録結果をリクエスト属性にセット
		//request.setAttribute(IConstants.RESULT_INFO,result);
		request.setAttribute("ikeninfo",result);

		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		// TODO 自動生成したメソッド・スタブ
		return forwardSuccess(mapping);
	}

}
