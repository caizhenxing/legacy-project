/*
 * Created on 2005/03/31
 *
 */
package jp.go.jsps.kaken.web.shozoku.checkList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.CheckListSearchInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * チェックリストの更新処理を行うクラス。
 * チェックリスト管理サービスでチェックリストの版と状況IDを更新する。
 * 
 * @author masuo_t
 *
 */
public class CheckListDataUpdateAction extends BaseAction {

	private static final String SYOZOKU_UKETUKETYU = "03";
	private static final String GAKUSIN_UKETUKETYU = "04";
	
	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {
	
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//-----取得したトークンが無効であるとき
		if (!isTokenValid(request)) {
			errors.add(ActionErrors.GLOBAL_ERROR,
					   new ActionError("error.transaction.token"));
			saveErrors(request, errors);
			return forwardTokenError(mapping);
		}
		
		CheckListSearchInfo checkInfo = new CheckListSearchInfo();
		checkInfo.setShozokuCd(container.getUserInfo().getShozokuInfo().getShozokuCd());
// 20050606 Start
//update start dyh 2006/2/8
		CheckListForm listForm = (CheckListForm)form;
		checkInfo.setJigyoKubun(listForm.getJigyoKbn().trim());//事業区分
//		checkInfo.setJigyoKubun(IJigyoKubun.JIGYO_KUBUN_KIBAN);
//update end dyh 2006/2/8
// Horikoshi End
		CheckListForm checkForm = (CheckListForm)form;
		try {
			PropertyUtils.copyProperties(checkInfo, checkForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}

		//版の値を1つ上げる
		String edition = checkInfo.getEdition();
		if( edition == null || edition.equals("") ){
				edition = "1";
		}else{
			edition = new Integer(new Integer(edition).intValue()+1).toString();
		}
		
		//------トークンの削除	
		resetToken(request);
		
		checkInfo.setEdition(edition);
		checkInfo.setJokyoId(SYOZOKU_UKETUKETYU);
		checkInfo.setChangeJokyoId(GAKUSIN_UKETUKETYU);
		try{
			//更新
			getSystemServise(
				IServiceName.CHECKLIST_MAINTENANCE_SERVICE).checkListUpdate(
				container.getUserInfo(),
				checkInfo, true);

		//2005/04/13 追加 ここから--------------------------------------------------
		//有効期限外の場合にチェックリスト出力確認画面に戻るよう修正
		
		//サーバー側で有効期限外エラーの場合
		}catch(ValidationException e){
			return forwardFailure(mapping);		
		}
		//追加 ここまで-------------------------------------------------------------
		return forwardSuccess(mapping);
	}
}