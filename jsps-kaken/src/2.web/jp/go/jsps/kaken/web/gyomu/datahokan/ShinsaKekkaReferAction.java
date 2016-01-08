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
package jp.go.jsps.kaken.web.gyomu.datahokan;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IDataHokanMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.shinsei.SimpleShinseiForm;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 審査結果参照画面生成アクションクラス。
 * ID RCSfile="$RCSfile: ShinsaKekkaReferAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:24 $"
 */
public class ShinsaKekkaReferAction extends BaseAction {

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

		//-----簡易申請書入力フォームの取得
		SimpleShinseiForm simpleShinseiForm = (SimpleShinseiForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinseiDataPk searchPk = new ShinseiDataPk(simpleShinseiForm.getSystemNo());		
					
		//検索実行
		Map result =
			getSystemServise(
				IServiceName.DATA_HOKAN_MAINTENANCE_SERVICE).getShinsaKekkaBoth(
				container.getUserInfo(),
				searchPk);
		
		//-----Mapの値を取得する。
		ShinsaKekkaReferenceInfo shinsaKekka1stInfo = 
				(ShinsaKekkaReferenceInfo)result.get(IDataHokanMaintenance.KEY_SHINSAKEKKA_1ST);	//1次審査結果情報
		ShinsaKekka2ndInfo shinsaKekka2ndInfo = 
				(ShinsaKekka2ndInfo)result.get(IDataHokanMaintenance.KEY_SHINSAKEKKA_2ND);			//2次審査結果情報
		
		//-----セッションに1次審査結果情報、2次審査結果情報をセットする。
		container.setShinsaKekkaReferenceInfo(shinsaKekka1stInfo);
		container.setShinsaKekka2ndInfo(shinsaKekka2ndInfo);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}

}
