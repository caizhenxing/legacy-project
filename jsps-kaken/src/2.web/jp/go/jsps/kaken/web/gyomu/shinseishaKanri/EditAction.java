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
package jp.go.jsps.kaken.web.gyomu.shinseishaKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.ShinseishaInfo;
import jp.go.jsps.kaken.model.vo.ShinseishaPk;
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
 * 申請者情報更新前アクションクラス。
 * キーに一致する申請者情報を取得、初期値としてデータをセットする。
 * 申請者情報更新画面を表示する。
 * 
 */
public class EditAction extends BaseAction {

	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.shinseishaInfo.プロパティ名
		//## 更新対象プロパティ 				$!shinseishaForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------更新対象申請者情報の取得
		ShinseishaPk pkInfo = new ShinseishaPk();
		//------キー情報
		String shinseishaId = ((ShinseishaForm)form).getShinseishaId();
		pkInfo.setShinseishaId(shinseishaId);
		
		//------キー情報を元に更新データ取得	
		ShinseishaInfo editInfo = getSystemServise(IServiceName.SHINSEISHA_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
			
		//------更新対象情報をセッションに登録。
		container.setShinseishaInfo(editInfo);
		
		//------更新対象申請者情報より、更新登録フォーム情報の更新
		ShinseishaForm editForm = new ShinseishaForm();
		editForm.setAction(BaseForm.EDIT_ACTION);

		//------プルダウンデータセット
//		editForm.setShubetuCdList(LabelValueManager.getBukyokuShubetuCdList());
		editForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		editForm.setHikoboFlgList(LabelValueManager.getHikoboFlgList());
		
		try {
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"nameRoSei",editInfo.getNameRoSei());
			BeanUtils.copyProperty(editForm,"nameRoMei",editInfo.getNameRoMei());
			BeanUtils.copyProperty(editForm,"kenkyuNo",editInfo.getKenkyuNo());
			BeanUtils.copyProperty(editForm,"shozokuCd",editInfo.getShozokuCd());
			BeanUtils.copyProperty(editForm,"shozokuName",editInfo.getShozokuName());
			BeanUtils.copyProperty(editForm,"shozokuNameEigo",editInfo.getShozokuNameEigo());
			BeanUtils.copyProperty(editForm,"bukyokuCd",editInfo.getBukyokuCd());
			BeanUtils.copyProperty(editForm,"bukyokuName",editInfo.getBukyokuName());
			//2006/02/09　追加 ここから-------------------------------------------------------
			BeanUtils.copyProperty(editForm,"ouboShikaku",editInfo.getOuboshikaku());
			//追加 ここまで-------------------------------------------------------------------
//			BeanUtils.copyProperty(editForm,"bukyokuShubetuCd",editInfo.getBukyokuShubetuCd());
//			if(editInfo.getBukyokuShubetuCd() != null && editInfo.getBukyokuShubetuCd().equals("9")){
//				BeanUtils.copyProperty(editForm,"bukyokuShubetuName",editInfo.getBukyokuShubetuName());
//			}
			BeanUtils.copyProperty(editForm,"shokushuCd",editInfo.getShokushuCd());
			//2005/04/25　削除 ここから-------------------------------------------------------
			//理由　職名取得のため
			//if(editInfo.getShokushuCd() != null
			//	&& (editInfo.getShokushuCd().equals("24") || editInfo.getShokushuCd().equals("25"))) {
			//削除 ここまで-------------------------------------------------------------------
				BeanUtils.copyProperty(editForm,"shokushuNameKanji",editInfo.getShokushuNameKanji());
			//}
			BeanUtils.copyProperty(editForm,"shokushuNameRyaku",editInfo.getShokushuNameRyaku());
			
			//2005/04/25　追加 ここから-------------------------------------------------------
			//理由 生年月日追加のため
			BeanUtils.copyProperty(editForm,"birthday",editInfo.getBirthday());
			//追加 ここまで-------------------------------------------------------------------
		
			//有効期限の設定		
			Calendar calendar = Calendar.getInstance();
			if(editInfo.getYukoDate() != null){
				calendar.setTime(editInfo.getYukoDate());
				editForm.setYukoDateYear("" + calendar.get(Calendar.YEAR));
				editForm.setYukoDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setYukoDateDay("" + calendar.get(Calendar.DATE));
			}
			
			BeanUtils.copyProperty(editForm,"hikoboFlg",editInfo.getHikoboFlg());
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
