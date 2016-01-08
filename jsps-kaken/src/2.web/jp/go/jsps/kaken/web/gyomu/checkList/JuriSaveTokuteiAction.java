/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : JuriSaveTokuteiAction.java
 *    Description : 受理登録(特定)を行う。
 *
 *    Author      : Admin
 *    Date        : 2005/06/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2005/06/14    v1.0        Admin          新規作成
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.status.StatusCode;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import jp.go.jsps.kaken.model.common.IJigyoKubun;

import java.util.List;
import java.util.ArrayList;

/**
 * 受理登録(特定)を行う。
 */
public class JuriSaveTokuteiAction extends BaseAction {

// 2006/07/21 dyh delete start 理由：使用しない
//	/** 状況IDが04(学振受付中)のものを表示 */
//	private static String[] JIGYO_IDS = new String[]{
//		StatusCode.STATUS_GAKUSIN_SHORITYU		//学振受付中
//	};
// 2006/07/21 dyh delete end

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		String chJuriKekka = "";								//変更後の状況IDを取得する
		String chJuriBiko = "";									//備考
		ActionErrors errors = new ActionErrors();				//-----ActionErrorsの宣言（定型処理）-----
		JuriCheckListForm juriForm = (JuriCheckListForm)form;	// フォーム取得

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(
				ActionErrors.GLOBAL_ERROR,
				new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}

		//-------▼ VOにデータをセットする。
		CheckListSearchInfo searchInfo = new CheckListSearchInfo();
		//------トークンの削除
		resetToken(request);

		//検索条件の設定
		if(juriForm.getJigyoID() != null && !juriForm.getJigyoID().equals("")){			//事業ID
			searchInfo.setJigyoId(juriForm.getJigyoID());
		}
		if(juriForm.getShozokuCD() != null && !juriForm.getShozokuCD().equals("")){		//所属CD
			searchInfo.setShozokuCd(juriForm.getShozokuCD());
		}
		searchInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_TOKUTEI);						//事業区分
		searchInfo.setJokyoId(juriForm.getJokyoID());									//変更前の状況ID
		chJuriKekka = request.getParameter("juriCheckListForm.juriKekka");				//変更後の状況ID
		if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			juriForm.setJuriKekka(StatusCode.STATUS_GAKUSIN_JYURI);
			searchInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_JYURI);
		}
		else if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			juriForm.setJuriKekka(StatusCode.STATUS_GAKUSIN_FUJYURI);
			searchInfo.setChangeJokyoId(StatusCode.STATUS_GAKUSIN_FUJYURI);
		}
		chJuriBiko = request.getParameter("juriCheckListForm.juriBiko");				//受理備考
		if(chJuriBiko != null && chJuriBiko.length() > 0){
			juriForm.setJuriBiko(chJuriBiko);
			searchInfo.setJuriComment(chJuriBiko);
		}

		//検索条件となる状態のセット　※変更後の状態が確定(06)の場合は受理解除にて行うため対象外
		if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_JYURI)){
			//変更後の状態が受理(06)の場合、対象は状況IDが(04)または(07)のもの
			String[] JokyoStr = new String[]{
					StatusCode.STATUS_GAKUSIN_SHORITYU,
					StatusCode.STATUS_GAKUSIN_FUJYURI
					};
			searchInfo.setSearchJokyoId(JokyoStr);				//状況IDを検索条件にセット
		}
		else if(chJuriKekka.equals(StatusCode.STATUS_GAKUSIN_FUJYURI)){
			//変更後の状態が不受理(07)の場合、対象は状況IDが(04)のもの
			String[] JokyoStr = new String[]{
					StatusCode.STATUS_GAKUSIN_SHORITYU
					};
			searchInfo.setSearchJokyoId(JokyoStr);				//状況IDを検索条件にセット
		}

		//変更前と変更後の状況が同一の場合
		if(chJuriKekka.equals(searchInfo.getJokyoId())){
			ActionError error = new ActionError("errors.5051", "同一の状況に更新はできません。");
			errors.add("受理登録でエラーが発生しました。", error);

			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		List lstErrors = new ArrayList();
		try{
			ISystemServise servise = getSystemServise(IServiceName.CHECKLIST_MAINTENANCE_SERVICE);	//CheckListMaintenanceのサービスを取得
			lstErrors = servise.CheckListAcceptUnacceptable(container.getUserInfo(), searchInfo);			//受理、不受理の実行
			for(int n=0; n<lstErrors.size(); n++){
				ActionError error = new ActionError("errors.5051",lstErrors.get(n));
				errors.add("kenkyuExists", error);
			}
//		}catch(NoDataFoundException ex){
//			ActionError error = new ActionError("errors.5051",ex.getMessage());
//			errors.add("存在しない研究者情報が登録されています。", error);
		}catch(ApplicationException ex){
			ActionError error = new ActionError("errors.4002");
			errors.add("受理登録でエラーが発生しました。", error);
		}
		finally{
		}

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}

		return forwardSuccess(mapping);
	}
}