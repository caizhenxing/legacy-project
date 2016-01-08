/*======================================================================
 *    SYSTEM      : 日本学術振興会電子申請システム（科学研究費補助金）
 *    Source name : EditAction.java
 *    Description : 事業管理情報更新前アクションクラス
 *
 *    Author      : Admin
 *    Date        : 2003/11/14
 *
 *    Revision history
 *    Date          Revision    Author         Description
 *    2003/11/14    V1.0        Admin          新規作成
 *    2006/06/13    V1.0        DIS.liYH       修正
 *====================================================================== 
 */
package jp.go.jsps.kaken.web.gyomu.jigyoKanri;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.go.jsps.kaken.model.common.IServiceName;
import jp.go.jsps.kaken.model.exceptions.ApplicationException;
import jp.go.jsps.kaken.model.exceptions.SystemException;
import jp.go.jsps.kaken.model.vo.JigyoKanriInfo;
import jp.go.jsps.kaken.model.vo.JigyoKanriPk;
import jp.go.jsps.kaken.web.common.LabelValueManager;
import jp.go.jsps.kaken.web.common.UserContainer;
import jp.go.jsps.kaken.web.struts.BaseAction;
import jp.go.jsps.kaken.web.struts.BaseForm;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 事業管理情報更新前アクションクラス。
 * キーに一致する事業管理情報を取得、初期値としてデータをセットする。
 * 事業管理情報更新画面を表示する。
 * 
 * ID RCSfile="$RCSfile: EditAction.java,v $"
 * Revision="$Revision: 1.1 $"
 * Date="$Date: 2007/06/28 02:07:05 $"
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

		//## 更新不可のプロパティ(セッションに保持)　$!userContainer.jigyoKanriInfo.プロパティ名
		//## 更新対象プロパティ 				$!jigyoKanriForm.プロパティ名
		//##

		//-----ActionErrorsの宣言（定型処理）-----
		ActionErrors errors = new ActionErrors();

		//------更新対象事業管理情報の取得
		JigyoKanriPk pkInfo = new JigyoKanriPk();
		//------キー情報
		String jigyoId = ((JigyoKanriForm)form).getJigyoId();
		pkInfo.setJigyoId(jigyoId);
		
		//------キー情報を元に更新データ取得	
		JigyoKanriInfo editInfo = getSystemServise(IServiceName.JIGYOKANRI_MAINTENANCE_SERVICE).select(container.getUserInfo(),pkInfo);
	
		//------更新対象情報をセッションに登録。
		container.setJigyoKanriInfo(editInfo);
		
		//------更新対象事業管理情報より、更新登録フォーム情報の更新
		JigyoKanriForm editForm = new JigyoKanriForm();
		editForm.setAction(BaseForm.EDIT_ACTION);
	
		try {
			PropertyUtils.copyProperties(editForm, editInfo);
			
		} catch (Exception e) {
			log.error(e);
			throw new SystemException("プロパティの設定に失敗しました。", e);
		}
			
		//------ラジオボタンデータセット
		//評価用ファイル有無（なし／あり）
		editForm.setFlgList(LabelValueManager.getFlgList());

		//------日付データセット
		//学振受付期間（開始）		
		Calendar calendar = Calendar.getInstance();
		if(editInfo.getUketukekikanStart() != null){
			calendar.setTime(editInfo.getUketukekikanStart());
			editForm.setUketukekikanStartYear("" + calendar.get(Calendar.YEAR));
			editForm.setUketukekikanStartMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setUketukekikanStartDay("" + calendar.get(Calendar.DATE));
		}		
		//学振受付期間（終了）
		calendar = Calendar.getInstance();	
		if(editInfo.getUketukekikanEnd() != null){
			calendar.setTime(editInfo.getUketukekikanEnd());
			editForm.setUketukekikanEndYear("" + calendar.get(Calendar.YEAR));
			editForm.setUketukekikanEndMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setUketukekikanEndDay("" + calendar.get(Calendar.DATE));
		}
		//研究者名簿登録最終締切日
		calendar = Calendar.getInstance();
		if(editInfo.getMeiboDate() != null){
			calendar.setTime(editInfo.getMeiboDate());
			editForm.setMeiboDateYear("" + calendar.get(Calendar.YEAR));
			editForm.setMeiboDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setMeiboDateDay("" + calendar.get(Calendar.DATE));
		}
		
		//　2006/06/13　追加　李義華　ここから
		//仮領域番号発行締切日
		calendar = Calendar.getInstance();
		if (editInfo.getKariryoikiNoEndDate() != null) {
			calendar.setTime(editInfo.getKariryoikiNoEndDate());
			editForm.setKariryoikiNoEndDateYear("" + calendar.get(Calendar.YEAR));
			editForm.setKariryoikiNoEndDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setKariryoikiNoEndDateDay("" + calendar.get(Calendar.DATE));
		}
		//　2006/06/13　追加　李義華　ここまで
//　2006/07/10　追加　李義華　ここから
        //領域代表者確定締切日
        calendar = Calendar.getInstance();
        if (editInfo.getRyoikiEndDate() != null) {
            calendar.setTime(editInfo.getRyoikiEndDate());
            editForm.setRyoikiEndDateYear("" + calendar.get(Calendar.YEAR));
            editForm.setRyoikiEndDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
            editForm.setRyoikiEndDateDay("" + calendar.get(Calendar.DATE));
        }
//　2006/07/10　追加　李義華　ここまで
		
//　2006/10/24　追加　易旭　ここから
        //利害関係入力締切日
        calendar = Calendar.getInstance();
        if (editInfo.getRigaiEndDate() != null) {
            calendar.setTime(editInfo.getRigaiEndDate());
            editForm.setRigaiEndDateYear("" + calendar.get(Calendar.YEAR));
            editForm.setRigaiEndDateMonth("" + (calendar.get(Calendar.MONTH) + 1));
            editForm.setRigaiEndDateDay("" + calendar.get(Calendar.DATE));
        }
//　2006/10/24　追加　易旭　ここまで
		
		//審査期限
		calendar = Calendar.getInstance();
		if(editInfo.getShinsaKigen() != null){
			calendar.setTime(editInfo.getShinsaKigen());
			editForm.setShinsaKigenYear("" + calendar.get(Calendar.YEAR));
			editForm.setShinsaKigenMonth("" + (calendar.get(Calendar.MONTH) + 1));
			editForm.setShinsaKigenDay("" + calendar.get(Calendar.DATE));
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