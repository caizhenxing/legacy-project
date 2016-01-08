/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/03/01
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.*;

import javax.servlet.http.*;

import jp.go.jsps.kaken.model.exceptions.*;
import jp.go.jsps.kaken.model.vo.*;
import jp.go.jsps.kaken.web.common.*;
import jp.go.jsps.kaken.web.struts.*;

import org.apache.struts.action.*;
import org.apache.struts.action.ActionMapping;

/**
 * データ保管画面アクションクラス。
 * データ保管対象事業を指定する画面を表示する。
 */
public class DataHokanAction extends BaseAction {

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
		
		//事業リストの取得（担当する事業区分のみ）
		UserInfo userInfo = container.getUserInfo();
		
		List jigyoList = LabelValueManager.getJigyoNameList(userInfo);
		
		//フォーム生成
		DataHokanForm dataHokanForm = new DataHokanForm();
		dataHokanForm.setJigyoList(jigyoList);
		
		//-----フォームをリクエストにセット
		updateFormBean(mapping, request, dataHokanForm);	
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	
	
}
