/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（JSPS)
 *    Source name : CreatePunchAction.java
 *    Description : パンチデータ作成アクション
 *
 *    Author      : Yuji Kainuma
 *    Date        : 2004/11/02
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.punchData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.PunchDataKanriInfo;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * パンチデータ作成アクションクラス。
 * パンチデータを作り、、DBを更新する。
 * 
 * ID RCSfile="$RCSfile: CreatePunchAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class CreatePunchAction extends BaseAction{

/* (非 Javadoc)
	 * @see jp.go.jsps.kaken.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.kaken.web.common.UserContainer)
	 */
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException
	{
		
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
			
		//宣言
		PunchDataForm punchDataForm = (PunchDataForm)form;
		
		//-------▼ VOにデータをセットする。
		PunchDataKanriInfo punchDataKanriInfo = new PunchDataKanriInfo();
		try {
			PropertyUtils.copyProperties(punchDataKanriInfo, punchDataForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		//-------▲
		
		//------セッションより新規登録情報の取得
		//PunchDataKanriInfo addInfo = container.getPunchDataKanriInfo();
		
		//サービスの取得
		ISystemServise service = getSystemServise(IServiceName.PUNCHDATA_MAINTENANCE_SERVICE);

		//サービス機能の呼び出し
		punchDataKanriInfo = service.getPunchData(container.getUserInfo(), punchDataForm.getPunchShubetu());
		
		//登録結果をリクエスト属性にセット
		request.setAttribute(IConstants.RESULT_INFO, punchDataKanriInfo);
		
		//-----セッションにパンチデータ管理情報を登録する。
		container.setPunchDataKanriInfo(punchDataKanriInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);

	}
	
}
