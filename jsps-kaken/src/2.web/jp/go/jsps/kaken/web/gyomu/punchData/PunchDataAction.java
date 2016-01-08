/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（JSPS)
 *    Source name : PunchDataAction.java
 *    Description : パンチデータ作成画面アクション
 *					DBより作成種類の取得、リスト画面が表示する
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2004/02/16    V1.0                       新規作成
 *
 *====================================================================== 
 */

package jp.go.jsps.kaken.web.gyomu.punchData;

import java.util.Collections;
import java.util.List;

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
 * パンチデータ作成前アクションクラス。
 * パンチデータ作成画面を表示する。
 * 
 * ID RCSfile="$RCSfile: PunchDataAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:55 $"
 */
public class PunchDataAction extends BaseAction {


	
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

		//------キャンセル時		
		if (isCancelled(request)) {
			return forwardCancel(mapping);
		}

		//宣言
		PunchDataForm punchdataform = new PunchDataForm();

		//-------▼ VOにデータをセットする。
		//SearchInfo searchInfo = new SearchInfo();

		List result = null;
		try{
			//マスタ管理情報を取得
			result = getSystemServise(
					IServiceName.PUNCHDATA_MAINTENANCE_SERVICE).selectList(container.getUserInfo());
	 		punchdataform.setPunchKanriList(result);
		}catch(ApplicationException e){
			//0件のリストオブジェクトを生成
			result = Collections.EMPTY_LIST;
		}
		
		//セッションにフォームをセット
		updateFormBean(mapping, request, punchdataform);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}