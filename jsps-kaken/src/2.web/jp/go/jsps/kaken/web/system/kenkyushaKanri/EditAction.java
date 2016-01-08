/*
 *  Created on 2005/04/18
 */
package jp.go.jsps.kaken.web.system.kenkyushaKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.ISystemServise;
import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.KenkyushaInfo;
import jp.go.jsps.kaken.model.vo.KenkyushaPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 研究者情報更新前アクションクラス。
 * キーに一致する研究者情報を取得、初期値としてデータをセットする。
 * 研究者情報更新画面を表示する。
 */
public class EditAction extends BaseAction {


	public ActionForward doMainProcessing(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response,
		UserContainer container)
		throws ApplicationException {

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.kenkyushaInfo.プロパティ名
		//## 更新対象プロパティ 				$!kenkyushaForm.プロパティ名

		//------更新対象研究者情報の取得
		KenkyushaPk pkInfo = new KenkyushaPk();
		//------キー情報
		String kenkyuNo = ((KenkyushaForm)form).getKenkyuNo();
		String shozokuCd = ((KenkyushaForm)form).getShozokuCd();
		pkInfo.setKenkyuNo(kenkyuNo);
		pkInfo.setShozokuCd(shozokuCd);
		
		ISystemServise service = getSystemServise(IServiceName.KENKYUSHA_MAINTENANCE_SERVICE);
		
		//------キー情報を元に更新データ取得	
		KenkyushaInfo editInfo = service.selectKenkyushaData(container.getUserInfo(),pkInfo, false);
	
		//------更新対象情報をセッションに登録。
		container.setKenkyushaInfo(editInfo);
		
		//------更新対象研究者情報より、更新登録フォーム情報の更新
		KenkyushaForm editForm = new KenkyushaForm();
		editForm.setAction(BaseForm.EDIT_ACTION);

		//------プルダウンデータセット
		editForm.setShokushuCdList(LabelValueManager.getShokushuCdList());
		editForm.setGakuiList(LabelValueManager.getGakuiList());
		editForm.setSeibetsuList(LabelValueManager.getSeibetsuList());
		
		try {
			BeanUtils.copyProperty(editForm,"nameKanjiSei",editInfo.getNameKanjiSei());
			BeanUtils.copyProperty(editForm,"nameKanjiMei",editInfo.getNameKanjiMei());
			BeanUtils.copyProperty(editForm,"nameKanaSei",editInfo.getNameKanaSei());
			BeanUtils.copyProperty(editForm,"nameKanaMei",editInfo.getNameKanaMei());
			BeanUtils.copyProperty(editForm,"kenkyuNo",editInfo.getKenkyuNo());
			BeanUtils.copyProperty(editForm,"shozokuCd",editInfo.getShozokuCd());
			BeanUtils.copyProperty(editForm,"bukyokuCd",editInfo.getBukyokuCd());
			BeanUtils.copyProperty(editForm,"shokushuCd",editInfo.getShokushuCd());
			BeanUtils.copyProperty(editForm,"gakui", editInfo.getGakui());
			
			//2005/05/25 追加 ここから----------------------------------------------------
			//所属機関名、部局名の表示のため
			BeanUtils.copyProperty(editForm, "shozokuNameKanji", editInfo.getShozokuNameKanji());
			BeanUtils.copyProperty(editForm, "shozokuNameEigo", editInfo.getShozokuNameEigo());			
			BeanUtils.copyProperty(editForm, "bukyokuName", editInfo.getBukyokuName());	
			//追加 ここまで---------------------------------------------------------------
			
			//生年月日の設定		
			Calendar calendar = Calendar.getInstance();
			if(editInfo.getBirthday() != null){
				calendar.setTime(editInfo.getBirthday());
				editForm.setBirthYear("" + calendar.get(Calendar.YEAR));
				editForm.setBirthMonth("" + (calendar.get(Calendar.MONTH) + 1));
				editForm.setBirthDay("" + calendar.get(Calendar.DATE));
			}
			
			BeanUtils.copyProperty(editForm,"seibetsu",editInfo.getSeibetsu());
			BeanUtils.copyProperty(editForm, "biko", editInfo.getBiko());
			//2006/02/27 追加ここから
			BeanUtils.copyProperty(editForm, "ouboShikaku", editInfo.getOuboShikaku());
			//Nae ここまで
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
		
		//------修正登録フォームにセットする。
		updateFormBean(mapping,request,editForm);

		return forwardSuccess(mapping);
	}

}
