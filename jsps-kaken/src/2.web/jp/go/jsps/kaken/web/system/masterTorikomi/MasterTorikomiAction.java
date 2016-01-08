/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : Admin
 *    Date        : 2004/02/16
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.system.masterTorikomi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.SearchInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請者情報検索前アクションクラス。
 * 申請者情報検索画面を表示する。
 * 
 * ID RCSfile="$RCSfile: MasterTorikomiAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
 */
public class MasterTorikomiAction extends BaseAction {

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
		MasterTorikomiForm searchForm = new MasterTorikomiForm();

		//-------▼ VOにデータをセットする。
		SearchInfo searchInfo = new SearchInfo();

		//マスタ管理情報を取得
		List result =
					getSystemServise(
					IServiceName.SYSTEM_MAINTENANCE_SERVICE).selectList(container.getUserInfo());
		searchForm.setMasterKanriList(result);

		//コンボデータを取得
		//マスタ種別コンボデータ
		searchForm.setShubetuComboList(LabelValueManager.getLavelValueList(result, "MASTER_NAME", "MASTER_SHUBETU"));
		//新規・更新フラグデータ
		searchForm.setShinkiKoshinList(LabelValueManager.getShinkiKoshinFlgList());

		//検索条件をフォームをセットする。
		updateFormBean(mapping,request,searchForm);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
