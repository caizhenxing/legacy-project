/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : KakuninTokusokuAction.java
 *    Description : 確認メール自動通知送信実行アクション
 *
 *    Author      : DIS.zhangjianping
 *    Date        : 2006/06/29
 *
 *    Revision history
 *    Date          Revision    Author                Description
 *    2006/06/29    V1.0        DIS.zhangjianping      新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 確認メール自動通知送信実行アクション。
 * 
 * ID RCSfile="$RCSfile: KakuninTokusokuAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:15 $"
 */
public class KakuninTokusokuAction extends BaseAction {
	
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

		//処理実行リクエストの送信
		getSystemServise(
				IServiceName.TEISHUTU_MAINTENANCE_SERVICE).sendMailKakuninTokusoku(
				container.getUserInfo());
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
