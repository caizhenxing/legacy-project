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
package jp.go.jsps.kaken.web.system.shozokuKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShozokuInfo;
import jp.go.jsps.kaken.model.vo.ShozokuPk;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 所属機関情報更新前アクションクラス。
 * キーに一致する所属機関情報を取得、初期値としてデータをセットする。
 * 所属機関情報更新画面を表示する。
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:03 $"
 */
public class EditAction extends BaseAction {

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

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.shozokuInfo.プロパティ名
		//## 更新対象プロパティ 				$!shozokuForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------更新対象所属機関情報の取得
		ShozokuPk pkInfo = new ShozokuPk();
		//------キー情報
		String shozokuTantoId = ((ShozokuForm)form).getShozokuTantoId();
		pkInfo.setShozokuTantoId(shozokuTantoId);
		
		//------キー情報を元に更新データ取得	
		ShozokuInfo editInfo = 
			getSystemServise(IServiceName.SHOZOKU_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);

		
		//------更新対象所属機関情報より、更新登録フォーム情報の更新
		ShozokuForm editForm = new ShozokuForm();
		//------データ入力は2ページ目
		editForm.setPage(2);
		editForm.setAction(BaseForm.EDIT_ACTION);
	
		try {
			PropertyUtils.copyProperties(editForm, editInfo);

		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
				
		//有効期限の設定		
		Calendar calendar = Calendar.getInstance();
		if(editInfo.getYukoDate() != null){
			calendar.setTime(editInfo.getYukoDate());
			editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
			editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
		}
		
		//------更新対象情報をセッションに登録。
		container.setShozokuInfo(editInfo);
		
		//------修正登録フォームにセットする。
		updateFormBean(mapping,request,editForm);

		//-----画面遷移（定型処理）-----
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
			return forwardFailure(mapping);
		}
		return forwardSuccess(mapping);
	}

}
