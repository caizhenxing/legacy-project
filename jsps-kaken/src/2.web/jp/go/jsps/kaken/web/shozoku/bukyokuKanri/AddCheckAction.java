/*
 * Created on 2005/04/05
 *
 */
package jp.go.jsps.kaken.web.shozoku.bukyokuKanri;

import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.exceptions.ValidationException;
import jp.go.jsps.kaken.model.vo.BukyokutantoInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 所属担当者登録/修正確認用アクションクラス。
 * 新規登録、及び修正の確認画面表示時に呼び出される。
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
			
		BukyokuForm bukyokuForm = (BukyokuForm)form;
		
		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();
		
		//フォームから取得した部局コードの配列
		ArrayList array = (ArrayList)bukyokuForm.getBukyokuList();
		//格納されたデータを保持するHashSet
		HashSet set = new HashSet();
		
		if(array != null){			
			int count = 0;
			for(int i = 0; i < array.size(); i++){
				if(!array.isEmpty() && array.get(i) != null && !array.get(i).equals("")){
					set.add(array.get(i));
					count++;
				}
			}
			//配列内に重複した値がある場合に重複エラーを返す
			if(count != set.size()){
				errors.add("errors.2010", new ActionError("errors.2010","部局コード"));
				//エラーを保存。
				saveErrors(request, errors);
				//---入力内容に不備があるので再入力
				return forwardInput(mapping);
			}
		}
		
		//-------▼ VOにデータをセットする。
		BukyokutantoInfo addInfo = new BukyokutantoInfo();
		try {
			PropertyUtils.copyProperties(addInfo, bukyokuForm);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		//-------▲
		
		//---サーバ入力チェック
		if(set.size() != 0){
			try{
				//部局コードが入力されている場合にコードの確認
				getSystemServise(
					IServiceName.BUKYOKUTANTO_MAINTENANCE_SERVICE).CheckBukyokuCd(
					container.getUserInfo(),
					set);
			
			}catch (ValidationException e) {
				//サーバーエラーを保存。
				saveServerErrors(request, errors, e);
				//---入力内容に不備があるので再入力
				return forwardInput(mapping);
			}
		}
	
		//-----セッションに部局担当者情報を登録する。
		container.setBukyokutantoInfo(addInfo);

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
