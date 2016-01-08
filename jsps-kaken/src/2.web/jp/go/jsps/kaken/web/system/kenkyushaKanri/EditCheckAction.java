/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 更新された研究者情報の入力チェックを行う。
 * 研究者登録情報値オブジェクトを作成する。
 * 修正確認画面を表示する。 
 */
public class EditCheckAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------修正登録フォーム情報の取得
		KenkyushaForm editForm = (KenkyushaForm) form;

		//------セッションより更新対象情報の取得
		KenkyushaInfo editInfo = new KenkyushaInfo();

		//VOにデータをセットする。
		editInfo.setNameKanjiSei(editForm.getNameKanjiSei());
		editInfo.setNameKanjiMei(editForm.getNameKanjiMei());
		editInfo.setNameKanaSei(editForm.getNameKanaSei());
		editInfo.setNameKanaMei(editForm.getNameKanaMei());
		editInfo.setKenkyuNo(editForm.getKenkyuNo());
		editInfo.setShozokuCd(editForm.getShozokuCd());
		editInfo.setBukyokuCd(editForm.getBukyokuCd());
		editInfo.setShokushuCd(editForm.getShokushuCd());
		editInfo.setSeibetsu(editForm.getSeibetsu());
		editInfo.setBiko(editForm.getBiko());
		editInfo.setGakui(editForm.getGakui());

		//生年月日（String→Date)
		if(editForm.getBirthYear() != null && !editForm.getBirthYear().equals("")
				&& editForm.getBirthMonth()!= null && !editForm.getBirthMonth().equals("") 
				&& editForm.getBirthDay() != null && !editForm.getBirthDay().equals("")){
			DateUtil dateUtil = new DateUtil();
			dateUtil.setCal(editForm.getBirthYear(),editForm.getBirthMonth(),editForm.getBirthDay());
			editInfo.setBirthday(dateUtil.getCal().getTime());
		}
		//-------▲
	
		//追加ここから　2006/02/27
		editInfo.setOuboShikaku(editForm.getOuboShikaku());
		//追加ここまで　苗
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		try {
			//サーバ入力チェック
			editInfo =service.validate(
					container.getUserInfo(),
					editInfo, IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}
		
		//2005/05/25 追加 ここから-----------------------------------
		//理由 変更した部局コードの部局名をformに保持するため
		editForm.setBukyokuName(editInfo.getBukyokuName());
		//追加 ここまで----------------------------------------------

		//-----セッションに研究者情報を登録する。
		container.setKenkyushaInfo(editInfo);

		//トークンをセッションに保存する。
		saveToken(request);

		return forwardSuccess(mapping);
	}

}
