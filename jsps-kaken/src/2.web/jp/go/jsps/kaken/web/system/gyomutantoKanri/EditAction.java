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
package jp.go.jsps.kaken.web.system.gyomutantoKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.GyomutantoInfo;
import jp.go.jsps.kaken.model.vo.GyomutantoPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 業務担当者情報更新前アクションクラス。
 * キーに一致する業務担当者情報を取得、初期値としてデータをセットする。
 * 業務担当者情報更新画面を表示する。
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:06:38 $"
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

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.gyomutantoInfo.プロパティ名
		//## 更新対象プロパティ 				$!gymutantoForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------更新対象業務担当者情報の取得
		GyomutantoPk pkInfo = new GyomutantoPk();
		//------キー情報
		String gyomutantoId = ((GyomutantoForm)form).getGyomutantoId();
		pkInfo.setGyomutantoId(gyomutantoId);
		
		//------キー情報を元に更新データ取得	
		GyomutantoInfo editInfo = getSystemServise(IServiceName.GYOMUTANTO_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
	
		//------更新対象情報をセッションに登録。
		container.setGyomutantoInfo(editInfo);
		
		//------更新対象業務担当者情報より、更新登録フォーム情報の更新
		GyomutantoForm editForm = new GyomutantoForm();
		editForm.setAction(BaseForm.EDIT_ACTION);

		//------チェックボックスデータセット
		editForm.setJigyoNameList(LabelValueManager.getJigyoNameList(container.getUserInfo()));
		
		try {
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"bukaName",editInfo.getBukaName());
			BeanUtils.copyProperty(editForm,"kakariName",editInfo.getKakariName());
			BeanUtils.copyProperty(editForm,"biko",editInfo.getBiko());
			BeanUtils.copyProperty(editForm,"valueList",editInfo.getJigyoValues());
			
			//有効期限の設定		
			Calendar calendar = Calendar.getInstance();
			if(editInfo.getYukoDate() != null){
				calendar.setTime(editInfo.getYukoDate());
				editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
				editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
			}
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
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
