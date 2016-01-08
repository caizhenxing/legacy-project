/*
 * Created on 2005/04/15
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IMaintenanceName;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.util.DateUtil;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 新規登録された研究者情報の入力チェックを行う。
 * 研究者登録情報値オブジェクトを作成する。
 * 登録確認画面を表示する。
 * 
 */
public class AddCheckAction extends BaseAction {

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
		KenkyushaForm addForm = (KenkyushaForm)form;

		//-------▼ VOにデータをセットする。
		KenkyushaInfo addInfo = new KenkyushaInfo();
		try {
			PropertyUtils.copyProperties(addInfo, addForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//生年月日（String→Date)
		if(addForm.getBirthYear() != null && !addForm.getBirthYear().equals("")
					&& addForm.getBirthMonth()!= null && !addForm.getBirthMonth().equals("") 
					&& addForm.getBirthDay() != null && !addForm.getBirthDay().equals("")){
			DateUtil dateUtil = new DateUtil();
			dateUtil.setCal(addForm.getBirthYear(),addForm.getBirthMonth(),addForm.getBirthDay());
			addInfo.setBirthday(dateUtil.getCal().getTime());
		}
		//-------▲
		
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		try {
			//サーバ入力チェック
			addInfo = service.validate(container.getUserInfo(),addInfo, IMaintenanceName.ADD_MODE);
		} catch (ValidationException e) {
			//サーバーエラーを保存。
			saveServerErrors(request, errors, e);
			//---入力内容に不備があるので再入力
			return forwardInput(mapping);
		}

		//-----セッションに研究者情報を登録する。
		container.setKenkyushaInfo(addInfo);

		//トークンをセッションに保存する。
		saveToken(request);

		return forwardSuccess(mapping);
	}

}
