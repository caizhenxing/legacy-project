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
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.IShinsaKekkaMaintenance;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaReferenceInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.ShinsaKekka2ndInfo;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 2次審査結果登録アクションクラス。
 * 2次審査結果登録画面を表示する。
 * 
 * ID RCSfile="$RCSfile: ShinsaKekka2ndAddAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class ShinsaKekka2ndAddAction extends BaseAction {

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

		//検索条件の取得
		ShinsaKekka2ndForm addForm = (ShinsaKekka2ndForm)form;
		
		//-------▼ VOにデータをセットする。
		ShinseiDataPk searchPk = new ShinseiDataPk(addForm.getSystemNo());		
					
		//検索実行
		Map result =
			getSystemServise(
				IServiceName.SHINSAKEKKA_MAINTENANCE_SERVICE).getShinsaKekkaBoth(
				container.getUserInfo(),
				searchPk);
		
		//-----Mapの値を取得する。
		ShinsaKekkaReferenceInfo shinsaKekka1stInfo = 
				(ShinsaKekkaReferenceInfo)result.get(IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_1ST);	//1次審査結果情報
		ShinsaKekka2ndInfo shinsaKekka2ndInfo = 
				(ShinsaKekka2ndInfo)result.get(IShinsaKekkaMaintenance.KEY_SHINSAKEKKA_2ND);		//2次審査結果情報
		
		//-----セッションに1次審査結果情報、2次審査結果情報をセットする。
		container.setShinsaKekkaReferenceInfo(shinsaKekka1stInfo);
		container.setShinsaKekka2ndInfo(shinsaKekka2ndInfo);

		//------プルダウンデータセット
		//2次審査結果情報
		addForm.setKekka2List(LabelValueManager.getShinsaKekka2ndList());

		//-----フォームに2次審査結果情報をセットする。
		addForm.setKekka2(shinsaKekka2ndInfo.getKekka2());			//2次審査結果
		addForm.setSouKehi(shinsaKekka2ndInfo.getSouKehi());		//総経費
		addForm.setShonenKehi(shinsaKekka2ndInfo.getShonenKehi());	//初年度経費		
		addForm.setShinsa2Biko(shinsaKekka2ndInfo.getShinsa2Biko());//業務担当者記入欄
		
		//------トークンの保存
		saveToken(request);
		
		//------新規登録フォームにセットする。
		updateFormBean(mapping, request, addForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
