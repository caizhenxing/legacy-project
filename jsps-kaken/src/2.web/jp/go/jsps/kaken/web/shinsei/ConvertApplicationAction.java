/*======================================================================
 *    SYSTEM      : 
 *    Source name : 
 *    Description : 
 *
 *    Author      : takano
 *    Date        : 2004/01/09
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.shinsei;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ErrorInfo;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.IConstants;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 申請書ファイル変換アクションクラス。
 * 指定システム受付番号の申請書に対して、XML変換、PDF変換要求を投げる。
 * ID RCSfile="$RCSfile: ConvertApplicationAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:02 $"
 */
public class ConvertApplicationAction extends BaseAction {

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

		//-----申請書入力フォームの取得
		ShinseiForm shinseiForm = (ShinseiForm)form;
		
		//サーバサービスの呼び出し（ファイル変換）
		ShinseiDataPk shinseiPk = new ShinseiDataPk(shinseiForm.getShinseiDataInfo().getSystemNo());
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		
		try{
			servise.convertApplication(container.getUserInfo(),shinseiPk);
		}catch(ValidationException e){
			List errorList = e.getErrors();
			for(int i=0; i<errorList.size(); i++){
				ErrorInfo errInfo = (ErrorInfo)errorList.get(i);
				errors.add(errInfo.getProperty(),
						   new ActionError(errInfo.getErrorCode(), errInfo.getErrorArgs()));
			}
			//検証エラー発生時はトークンを再セットし（入力）画面へ遷移させる
			if (!errors.isEmpty()) {
				//トークンをセッションに保存する。
				saveToken(request);
				saveErrors(request, errors);
				return forwardFailure(mapping);
			}
		}catch(ApplicationException e){
			//エラー発生時はトークンを再セットし（入力）画面へ遷移させる
			//トークンをセッションに保存する。
			saveToken(request);
			saveErrors(request, errors);
			throw e;
		}
		
		
		//------フォーム情報の削除
		removeFormBean(mapping,request);
		
		//簡易申請フォーム生成
		SimpleShinseiForm simpleForm = new SimpleShinseiForm();
		simpleForm.setSystemNo(shinseiPk.getSystemNo());	//システム受付番号セット
		
		//リクエストに簡易申請フォームをセットする
		request.setAttribute(IConstants.RESULT_INFO, simpleForm);
		
		return forwardSuccess(mapping);
		
	}
	

	
}
