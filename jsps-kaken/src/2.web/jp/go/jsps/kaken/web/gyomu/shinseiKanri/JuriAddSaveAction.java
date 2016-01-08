/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriAddSaveAction.java
 *    Description : 受理登録情報値オブジェクトを登録する
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.shinseiKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.NoDataFoundException;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.ShinseiDataPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 受理登録情報値オブジェクトを登録する。
 * フォーム情報、登録情報をクリアする。
 * 
 * ID RCSfile="$RCSfile: JuriAddSaveAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:54 $"
 */
public class JuriAddSaveAction extends BaseAction {

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

		//------新規登録フォーム情報の取得
		JuriAddForm addForm = (JuriAddForm) form;

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		//-------▼ VOにデータをセットする。
		ShinseiDataPk addPk = new ShinseiDataPk(addForm.getSystemNo());
        String jigyoCd = addForm.getJigyoCd();
		String JuriBiko = addForm.getJuriBiko();
		String juriKekka = addForm.getJuriKekka();
		String seiriNo = addForm.getJuriSeiriNo();
		//-------▲
		
		//DB登録
		ISystemServise servise = getSystemServise(
						IServiceName.SHINSEI_MAINTENANCE_SERVICE);
		
		if("0".equals(juriKekka)){
			//受理の場合
			try{
				servise.registShinseiJuri(container.getUserInfo(), addPk, jigyoCd, JuriBiko, seiriNo);
			}catch(NoDataFoundException ex){
				ActionError error = new ActionError("errors.5051", ex.getCause().getMessage().toString());
				errors.add("受理実行時にエラーが発生しました。",error);
			}
		}else if("1".equals(juriKekka)){
			//不受理の場合
			servise.registShinseiFujuri(container.getUserInfo(), addPk, jigyoCd, JuriBiko, seiriNo);			
		}else if("2".equals(juriKekka)){
			//修正依頼の場合
			servise.registShinseiShuseiIrai(container.getUserInfo(), addPk, jigyoCd, JuriBiko, seiriNo);						
		}
		
		if(log.isDebugEnabled()){
			log.debug("受理結果　登録情報 '"+ addForm);
		}

		//-----画面遷移（定型処理）-----
		if(!errors.isEmpty()){
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

//		resetToken(request);							//------トークンの削除	
		removeFormBean(mapping,request);				//------フォーム情報の削除
		container.setSimpleShinseiDataInfo(null);		//------セッション情報の削除
		return forwardSuccess(mapping);
	}
}