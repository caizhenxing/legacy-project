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
package jp.go.jsps.kaken.web.bukyoku.shinseishaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 更新された申請者情報の入力チェックを行う。
 * 申請者登録情報値オブジェクトを作成する。
 * 修正確認画面を表示する。 
 * 
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
		ShinseishaForm editForm = (ShinseishaForm) form;
		
		//------セッションより更新対象情報の取得
		ShinseishaInfo editInfo = container.getShinseishaInfo();

		//VOにデータをセットする。
		editInfo.setNameKanjiSei(editForm.getNameKanjiSei());
		editInfo.setNameKanjiMei(editForm.getNameKanjiMei());
		editInfo.setNameKanaSei(editForm.getNameKanaSei());
		editInfo.setNameKanaMei(editForm.getNameKanaMei());
		editInfo.setNameRoSei(editForm.getNameRoSei());
		editInfo.setNameRoMei(editForm.getNameRoMei());
		editInfo.setKenkyuNo(editForm.getKenkyuNo());
		editInfo.setBukyokuCd(editForm.getBukyokuCd());
		editInfo.setBukyokuName(editForm.getBukyokuName());
//		editInfo.setBukyokuShubetuCd(editForm.getBukyokuShubetuCd());
//		editInfo.setBukyokuShubetuName(editForm.getBukyokuShubetuName());
		editInfo.setShokushuCd(editForm.getShokushuCd());
		editInfo.setShokushuNameKanji(editForm.getShokushuNameKanji());
		editInfo.setBiko(editForm.getBiko());
	
		//有効期限（String→Date)
		DateUtil dateUtil = new DateUtil();
		dateUtil.setCal(editForm.getYukoDateYear(),editForm.getYukoDateMonth(),editForm.getYukoDateDay());
		editInfo.setYukoDate(dateUtil.getCal().getTime());
		//-------▲
		
		try {
			//サーバ入力チェック
			editInfo =
				getSystemServise(
					IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).validate(
					container.getUserInfo(),
					editInfo,
					IMaintenanceName.EDIT_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}
//		//validateで所属機関名(英語)には、所属機関情報か所属機関テーブルの情報が設定される。
//		//申請者情報の所属機関名(英語)が優先なので、戻しておく必要がある。
//		editInfo.setShozokuNameEigo(container.getShinseishaInfo().getShozokuNameEigo());
		
		//-----セッションに申請者情報を登録する。
		container.setShinseishaInfo(editInfo);
		
		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		
		//トークンをセッションに保存する。
		saveToken(request);
		
		return forwardSuccess(mapping);
	}

}
