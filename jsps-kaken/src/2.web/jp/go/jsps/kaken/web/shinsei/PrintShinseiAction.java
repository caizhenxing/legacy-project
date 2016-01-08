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
package jp.go.jsps.kaken.web.shinsei;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.model.vo.SimpleShinseiDataInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書情報取得アクションクラス。
 * 申請状況画面を表示する。
 * 
 * ID RCSfile="$RCSfile: PrintShinseiAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:01 $"
 */

public class PrintShinseiAction extends BaseAction {

	/* (非 Javadoc)
	 * @see jp.go.jsps.web.struts.BaseAction#doMainProcessing(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, jp.go.jsps.web.common.UserContainer)
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

		//-----簡易申請データフォームの取得
		SimpleShinseiDataInfo simpleSinseiDataInfo 
			= getSimpleShinseiDataInfo(container, (SimpleShinseiForm)form);
		
		//-----セッションに申請情報を登録する。
		container.setSimpleShinseiDataInfo(simpleSinseiDataInfo);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		return forwardSuccess(mapping);
		
	}
	
	

	/**
	 * 簡易申請状況取得メソッド。
	 * @param container 申請者情報
	 * @param form　申請情報入力フォーム
	 * @return SimpleShinseiDataInfo 簡易申請データ情報
	 * @throws ApplicationException
	 */
	private SimpleShinseiDataInfo getSimpleShinseiDataInfo
		(UserContainer container, SimpleShinseiForm form)
		throws ApplicationException
	{
		//システム受付Noを取得する
		String systemNo = form.getSystemNo();
		
		//事業管理主キーオブジェクトの生成
		ShinseiDataPk shinseiDataPk = new ShinseiDataPk(systemNo);
		
		//DBよりレコードを取得
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
			SimpleShinseiDataInfo simpleShinseiDataInfo = servise.selectSimpleShinseiDataInfo(container.getUserInfo(),shinseiDataPk);
		
		//備考
		//2次審査結果有：2次審査結果備考をセット
		//2次審査結果無時、受理備考有：受理備考をセット
		if(simpleShinseiDataInfo.getShinsa2Biko() != null) {
			simpleShinseiDataInfo.setBiko(simpleShinseiDataInfo.getShinsa2Biko());
		}else if(simpleShinseiDataInfo.getJuriKekka() != null){
			simpleShinseiDataInfo.setBiko(simpleShinseiDataInfo.getJuriBiko());
		}
		
		return simpleShinseiDataInfo;
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}