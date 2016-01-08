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
package jp.go.jsps.kaken.web.gyomu.warifuri;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinsaKekkaPk;
import jp.go.jsps.kaken.model.vo.WarifuriInfo;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *  審査員割り振り結果修正前アクションクラス。
 * キーに一致する審査員割り振り情報を取得、初期値としてデータをセットする。
 *  審査員割り振り結果修正画面を表示する。
 * 
 * ID RCSfile="$RCSfile: WarifuriEditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:21 $"
 */
public class WarifuriEditAction extends BaseAction {

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

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.warifuriInfo.プロパティ名
		//## 更新対象プロパティ 				$!warifuriForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------更新対象割り振り結果情報の取得
		ShinsaKekkaPk pkInfo = new ShinsaKekkaPk();
		
		//------フォーム情報の取得
		WarifuriForm editForm = (WarifuriForm) form;

		//------キー情報
		pkInfo.setSystemNo(editForm.getSystemNo());			//システム番号
		pkInfo.setShinsainNo(editForm.getShinsainNo());		//審査員番号
		pkInfo.setJigyoKubun(editForm.getJigyoKubun());		//事業区分
		
		//------キー情報を元に更新データ取得	
		WarifuriInfo editInfo =
				 getSystemServise(IServiceName.SHINSAIN_WARIFURI_SERVICE).select(
				 													container.getUserInfo(),
				 													pkInfo);

		//------更新対象申請者情報より、更新登録フォーム情報の更新
//		WarifuriForm editForm = new WarifuriForm();
//		editForm.setAction(BaseForm.EDIT_ACTION);

		try {
			BeanUtils.copyProperties(editForm, editInfo);
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		//審査員番号（修正前）に審査員番号をセット
		editForm.setOldShinsainNo(editInfo.getShinsainNo());
		
		//審査員名（漢字-姓）がNULLの場合は、審査員番号をリセット
		if(editInfo.getShinsainNameKanjiSei() == null){
			editForm.setShinsainNo("");
		}

		//------修正登録フォームにセットする。
		updateFormBean(mapping,request,editForm);

		//-----セッションに割り振り結果情報を登録する。
		container.setWarifuriInfo(editInfo);
		
		//トークンをセッションに保存する。
		saveToken(request);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
